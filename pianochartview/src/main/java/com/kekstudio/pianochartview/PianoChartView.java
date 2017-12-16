package com.kekstudio.pianochartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PianoChartView extends View {
    public static final int DEFAULT_LARGE_WIDTH = 330;
    public static final int DEFAULT_LARGE_HEIGHT = 100;
    public static final int DEFAULT_STROKE_WIDTH = 2;
    public static final int DEFAULT_SIZE = 1;

    public static final int ALL_KEYS_COUNT_SMALL = 12;
    public static final int WHITE_KEYS_COUNT_SMALL = 7;

    public static final int ALL_KEYS_COUNT_LARGE = 24;
    public static final int WHITE_KEYS_COUNT_LARGE = 14;

    public static final int DEFAULT_CHECKED_KEYS_COLOR = Color.parseColor("#03A9F4");
    public static final int DEFAULT_DARK_KEYS_COLOR = Color.DKGRAY;
    public static final int DEFAULT_LIGHT_KEYS_COLOR = Color.WHITE;

    public static final int DARK_KEYS[] = {1, 3, 6, 8, 10, 13, 15, 18, 20, 22};

    private Paint pianoFillPaint, pianoStrokePaint, pianoLetterPaint;
    private Rect pianoRect;

    private float densityScale;

    private List<Integer> blackKeys;
    private List<Integer> checkedKeys;
    private List<Integer> additionalCheckedKeys;

    private List<String> keyLetters;

    private int  lightColor, darkColor, checkedColor, additionalCheckedColor;

    public enum Size{
        Small, Large
    }
    private Size size;

    public PianoChartView(Context context) {
        super(context);
        initView();
    }

    public PianoChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.PianoChartView);

        //Setting values, and defaults if not specified in xml
        setLightKeysColor(attributes.getColor(  R.styleable.PianoChartView_lightKeysColor,       DEFAULT_LIGHT_KEYS_COLOR));
        setDarkKeysColor(attributes.getColor(   R.styleable.PianoChartView_darkKeysColor,        DEFAULT_DARK_KEYS_COLOR));
        setCheckedKeysColor(attributes.getColor(R.styleable.PianoChartView_checkedKeysColor,     DEFAULT_CHECKED_KEYS_COLOR));
        setAdditionalCheckedKeysColor(attributes.getColor(R.styleable.PianoChartView_additionalCheckedKeysColor,     DEFAULT_CHECKED_KEYS_COLOR));

        setSize(Size.values()[attributes.getInt(R.styleable.PianoChartView_size,                 DEFAULT_SIZE)]);

        int checkedKeysArray = attributes.getResourceId(R.styleable.PianoChartView_checkedKeys, 0);
        if (checkedKeysArray != 0) {
            setCheckedKeys(getResources().getIntArray(checkedKeysArray));
        }

        int additionalCheckedKeysArray = attributes.getResourceId(R.styleable.PianoChartView_additionalCheckedKeys, 0);
        if (additionalCheckedKeysArray != 0) {
            setAdditionalCheckedKeys(getResources().getIntArray(additionalCheckedKeysArray));
        }

        int namesOfKeysArray = attributes.getResourceId(R.styleable.PianoChartView_namesOfKeys, 0);
        if(namesOfKeysArray != 0){
            setNamesOfKeys(getResources().getStringArray(namesOfKeysArray));
        }

        attributes.recycle();
    }

    private void initView() {
        //Corresponding to the # of the key on keyboard
        blackKeys = intsToList(DARK_KEYS);

        checkedKeys = new ArrayList<>();
        additionalCheckedKeys = new ArrayList<>();
        keyLetters = new ArrayList<>();

        densityScale = getResources().getDisplayMetrics().density;

        pianoFillPaint = new Paint();
        pianoFillPaint.setStyle(Paint.Style.FILL);

        pianoStrokePaint = new Paint();
        pianoStrokePaint.setStyle(Paint.Style.STROKE);
        pianoStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        pianoStrokePaint.setAntiAlias(true);
        pianoStrokePaint.setStrokeWidth(DEFAULT_STROKE_WIDTH * densityScale);

        pianoRect = new Rect();

        pianoLetterPaint = new Paint();
        pianoLetterPaint.setAntiAlias(true);
        pianoLetterPaint.setStyle(Paint.Style.FILL);
        pianoLetterPaint.setTextSize(10*densityScale);
        pianoLetterPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setCheckedKeys(int[] numbers) {
        checkedKeys = intsToList(numbers);

        requestLayout();
        invalidate();
    }

    public void setAdditionalCheckedKeys(int[] numbers){
        additionalCheckedKeys = intsToList(numbers);

        requestLayout();
        invalidate();
    }

    public void setNamesOfKeys(String... keyLetters){
        this.keyLetters = Arrays.asList(keyLetters);

        requestLayout();
        invalidate();
    }

    public void setSize(Size size){
        this.size = size;

        requestLayout();
        invalidate();
    }

    public void setLightKeysColor(int color){
        lightColor = color;

        requestLayout();
        invalidate();
    }

    public void setDarkKeysColor(int color) {
        darkColor = color;
        pianoStrokePaint.setColor(darkColor);

        requestLayout();
        invalidate();
    }

    public void setCheckedKeysColor(int color) {
        checkedColor = color;

        requestLayout();
        invalidate();
    }

    public void setAdditionalCheckedKeysColor(int color){
        additionalCheckedColor = color;

        requestLayout();
        invalidate();
    }

    public int[] getCheckedKeys(){
        return listToInts(checkedKeys);
    }

    public int[] getAdditionalCheckedKeys(){return listToInts(additionalCheckedKeys);}

    public Size getSize(){
        return size;
    }

    public int getLightKeysColor(){
        return lightColor;
    }

    public int getDarkKeysColor(){
        return darkColor;
    }

    public int getCheckedKeysColor(){
        return checkedColor;
    }

    public int getAdditionalCheckedKeysColor(){return additionalCheckedColor; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int strokeWidth = (int) (DEFAULT_STROKE_WIDTH * densityScale);

        // Padding shortcuts
        int pLeft   = getPaddingLeft()      + strokeWidth;
        int pRight  = getPaddingRight()     + strokeWidth;
        int pTop    = getPaddingTop()       + strokeWidth;
        int pBottom = getPaddingBottom()    + strokeWidth;

        // Center width and height shortcuts
        int cWidth = getWidth() - pRight - pLeft;
        int cHeight = getHeight() - pBottom - pTop;

        int allKeysCount = size == Size.Large ? ALL_KEYS_COUNT_LARGE : ALL_KEYS_COUNT_SMALL;
        int whiteKeysCount = size == Size.Large ? WHITE_KEYS_COUNT_LARGE : WHITE_KEYS_COUNT_SMALL;

        //Drawing light(white) keys
        int k = 0;
        for(int i = 0; i < allKeysCount; i++){
            if(blackKeys.contains(i))
                continue;
            if(checkedKeys.contains(i)){
                pianoFillPaint.setColor(checkedColor);
            }else if (additionalCheckedKeys.contains(i)) {
                pianoFillPaint.setColor(additionalCheckedColor);
            }else{
                pianoFillPaint.setColor(lightColor);
            }
            pianoRect.set(pLeft +  k*cWidth/whiteKeysCount, pTop,
                        pLeft + (k+1)*cWidth/whiteKeysCount, pTop + cHeight);

            canvas.drawRect(pianoRect, pianoFillPaint);
            canvas.drawRect(pianoRect, pianoStrokePaint);

            if(i < keyLetters.size()) {
                pianoLetterPaint.setColor(Color.BLACK);
                canvas.drawText(keyLetters.get(i),
                        pLeft + (k * cWidth / whiteKeysCount + (k + 1) * cWidth / whiteKeysCount) / 2,
                        pTop + cHeight / 1.1f, pianoLetterPaint);
            }
            k++;

        }

        //Drawing dark(black) keys
        k = 0;
        for(int i = 0; i < allKeysCount; i++){
            if(!blackKeys.contains(i)) {
                k++;
                continue;
            }
            if(checkedKeys.contains(i)){
                pianoFillPaint.setColor(checkedColor);
            }else if (additionalCheckedKeys.contains(i)) {
                pianoFillPaint.setColor(additionalCheckedColor);
            }else{
                pianoFillPaint.setColor(darkColor);
            }
            pianoRect.set(pLeft +  k * cWidth/whiteKeysCount - cWidth/(whiteKeysCount*3), pTop,
                    pLeft + k * cWidth/whiteKeysCount + (cWidth/(whiteKeysCount*3)), pTop + (int)(cHeight/1.5));

            canvas.drawRect(pianoRect, pianoFillPaint);
            canvas.drawRect(pianoRect, pianoStrokePaint);

            if(i < keyLetters.size()) {
                pianoLetterPaint.setColor(Color.WHITE);
                canvas.drawText(keyLetters.get(i),
                        pLeft + k * cWidth / whiteKeysCount,
                        pTop + cHeight / 2.6f, pianoLetterPaint);
            }
        }

    }

    private List<Integer> intsToList(int[] array){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++)
            list.add(array[i]);
        return list;
    }

    private int[] listToInts(List<Integer> list){
        int[] array = new int[list.size()];
        for(int i = 0; i < list.size(); i++)
            array[i] = list.get(i);
        return array;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int preferredWidth = (int) (size == Size.Large ? DEFAULT_LARGE_WIDTH * densityScale : DEFAULT_LARGE_WIDTH * densityScale / 2);

        return getMeasurement(measureSpec, preferredWidth) + getPaddingRight() + getPaddingLeft();
    }

    private int measureHeight(int measureSpec) {
        int preferredHeight = (int) (DEFAULT_LARGE_HEIGHT * densityScale);

        return getMeasurement(measureSpec, preferredHeight) + getPaddingTop() + getPaddingBottom();
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                // This means the width of this view has been given.
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                // Take the minimum of the preferred size and what
                // we were told to be.
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }

        return measurement;
    }

}

