package com.kekstudio.pianochartviewsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kekstudio.pianochartview.PianoChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PianoChartView pianoChartViewSmall = (PianoChartView) findViewById(R.id.piano_chart_view_small);
        pianoChartViewSmall.setCheckedKeys(new int[]{2,5,3, 8, 11,12});
        pianoChartViewSmall.setSize(PianoChartView.Size.Small);
        pianoChartViewSmall.setLightKeysColor(Color.parseColor("#CFD8DC"));
        pianoChartViewSmall.setDarkKeysColor(Color.parseColor("#607D8B"));
        pianoChartViewSmall.setCheckedKeysColor(Color.parseColor("#B2EBF2"));

        PianoChartView pianoChartViewLarge = (PianoChartView) findViewById(R.id.piano_chart_view_large);
        pianoChartViewLarge.setCheckedKeys(new int[]{1,6,20, 9, 16,19});

    }



}
