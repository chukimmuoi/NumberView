package com.example.chukimmuoi.numberview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NumberView mNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberView = (NumberView) findViewById(R.id.number);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mNumberView.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
