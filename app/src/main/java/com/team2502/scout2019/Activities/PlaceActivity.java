package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class PlaceActivity extends AppCompatActivity {
    public String timd_in_progress;
    public int match_time;
    public String piece;
    public String place;
    public RadioGroup rocketLevel;
    public RadioGroup csPlace;
    public CheckBox wasDefended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        match_time = (int)intent.getDoubleExtra("com.team2502.scout2019.time", 0);
        piece = intent.getStringExtra("com.team2502.scout2019.type");
        place = intent.getStringExtra("com.team2502.scout2019.place");

        super.onCreate(savedInstanceState);
        if(place.equals("CargoShip")){
            setContentView(R.layout.activity_place_cargo_ship);
            csPlace = findViewById(R.id.csPlace);
            wasDefended = findViewById(R.id.defendedCSCheckBox);
        }
        else if(place.equals("Rocket")){
            setContentView(R.layout.activity_place_rocket);
            rocketLevel = findViewById(R.id.rocketLevel);
            wasDefended = findViewById(R.id.defenseRocketCheckBox);
        }
        this.setFinishOnTouchOutside(false);
    }

    public void rocketButtonPress(View view){
        int selectedLevelId = rocketLevel.getCheckedRadioButtonId();
        RadioButton level = findViewById(selectedLevelId);
        timd_in_progress = ExportUtils.createPlaceRocketAction(timd_in_progress, piece, match_time, level.getText().toString(), wasDefended.isChecked());
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }

    public void CSButtonPress(View view){
        int selectedLevelId = csPlace.getCheckedRadioButtonId();
        RadioButton place = findViewById(selectedLevelId);
        timd_in_progress = ExportUtils.createPlaceCSAction(timd_in_progress, piece, match_time, place.getText().toString(), wasDefended.isChecked());
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }
}