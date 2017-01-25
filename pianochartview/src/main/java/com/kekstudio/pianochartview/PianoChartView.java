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
import java.util.List;


public class PianoChartView extends View {
    public static final int DEFAULT_LARGE_WIDTH = 330;
    public static final int DEFAULT_LARGE_HEIGHT = 100;
    public static final int DEFAULT_STROKE_WIDTH = 2;
    public static final int DEFAULT_SIZE = 1;

    public static final int KEYS_COUNT_SMALL = 12;
    public static final int LIGHT_KEYS_COUNT_SMALL = 7;

    public static final int KEYS_COUNT_LARGE = 24;
    public static final int LIGHT_KEYS_COUNT_LARGE = 14;

    public static final int DEFAULT_CHECKED_KEYS_COLOR = Color.parseColor("#03A9F4");
    public static final int DEFAULT_DARK_KEYS_COLOR = Color.DKGRAY;
    public static final int DEFAULT_LIGHT_KEYS_COLOR = Color.WHITE;

    public static final int DARK_KEYS[] = {2, 4, 7, 9, 11, 14, 16, 19, 21, 23};

    private Paint pianoFillPaint, pianoStrokePaint;
    private Rect pianoRect;

    private float densityScale;

    private List<Integer> darkKeys;
    private List<Integer> checkedKeys;

    private int  lightColor, darkColor, checkedColor;

    private enum Size{
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

        setSize(Size.values()[attributes.getInt(R.styleable.PianoChartView_size,                 DEFAULT_SIZE)]);

        int checkedKeysArray = attributes.getResourceId(R.styleable.PianoChartView_checkedKeys, 0);

        if (checkedKeysArray != 0) {
            setCheckedKeys(getResources().getIntArray(checkedKeysArray));
        }

        attributes.recycle();
    }

    private void initView() {
        //Corresponding to the # of the key on keyboard
        darkKeys = intsToList(DARK_KEYS);

        checkedKeys = new ArrayList<>();

        densityScale = getResources().getDisplayMetrics().density;

        pianoFillPaint = new Paint();
        pianoFillPaint.setStyle(Paint.Style.FILL);

        pianoStrokePaint = new Paint();
        pianoStrokePaint.setStyle(Paint.Style.STROKE);
        pianoStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        pianoStrokePaint.setAntiAlias(true);
        pianoStrokePaint.setStrokeWidth(DEFAULT_STROKE_WIDTH * densityScale);

        pianoRect = new Rect();
    }

    public void setCheckedKeys(int[] numbers) {
        checkedKeys = intsToList(numbers);

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

    public int[] getCheckedKeys(){
        return listToInts(checkedKeys);
    }

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


        int keysCount = size == Size.Large ? KEYS_COUNT_LARGE : KEYS_COUNT_SMALL;
        int lightKeysCount = size == Size.Large ? LIGHT_KEYS_COUNT_LARGE : LIGHT_KEYS_COUNT_SMALL;

        //Drawing light(white) keys
        int k = 0;
        for(int i = 1; i<=keysCount; i++){
            if(darkKeys.contains(i))
                continue;
            if(checkedKeys.contains(i)){
                pianoFillPaint.setColor(checkedColor);
            }else{
                pianoFillPaint.setColor(lightColor);
            }
            pianoRect.set(pLeft +  k*cWidth/lightKeysCount, pTop,
                        pLeft + (k+1)*cWidth/lightKeysCount, pTop + cHeight);

            canvas.drawRect(pianoRect, pianoFillPaint);
            canvas.drawRect(pianoRect, pianoStrokePaint);

            k++;
        }

        //Drawing dark(black) keys
        k = 0;
        for(int i = 1; i<=keysCount; i++){
            if(!darkKeys.contains(i)) {
                k++;
                continue;
            }
            if(checkedKeys.contains(i)){
                pianoFillPaint.setColor(checkedColor);
            }else{
                pianoFillPaint.setColor(darkColor);
            }
            pianoRect.set(pLeft +  k * cWidth/lightKeysCount - cWidth/(lightKeysCount*3), pTop,
                    pLeft + k * cWidth/lightKeysCount + (cWidth/(lightKeysCount*3)), pTop + (int)(cHeight/1.5));

            canvas.drawRect(pianoRect, pianoFillPaint);
            canvas.drawRect(pianoRect, pianoStrokePaint);
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

        return getMeasurement(measureSpec, preferredWidth);
    }

    private int measureHeight(int measureSpec) {
        int preferredHeight = (int) (DEFAULT_LARGE_HEIGHT * densityScale);

        return getMeasurement(measureSpec, preferredHeight);
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

