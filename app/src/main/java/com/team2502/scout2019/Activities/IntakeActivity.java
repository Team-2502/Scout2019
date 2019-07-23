package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.team2502.scout2019.R;

public class IntakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake);
        this.setFinishOnTouchOutside(false);
    }
}
