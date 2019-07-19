package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.team2502.scout2019.R;

public class MatchActivity extends AppCompatActivity {

    public static String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

        TextView textView = findViewById(R.id.timd);
        textView.setText(timd_in_progress);
    }

    public void placeHatch(View view){
        timd_in_progress += "h,";
    }

    public void placeCargo(View view){
        timd_in_progress += "c,";
    }

    public void startClimb(View view){
        Intent intent = new Intent(this, ClimbActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }
}
