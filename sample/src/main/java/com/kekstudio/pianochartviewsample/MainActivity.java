package com.kekstudio.pianochartviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kekstudio.pianochartview.PianoChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PianoChartView pianoChartView = (PianoChartView) findViewById(R.id.pianochartview);
        pianoChartView.setCheckedKeys(new int[]{1,5,20, 8, 11,12});

    }



}
