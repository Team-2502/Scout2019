package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.team2502.scout2019.R;

public class PlaceRocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_rocket);
        this.setFinishOnTouchOutside(false);
    }
}
