package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class PreMatchActivity extends AppCompatActivity {
    //TODO Option to Start Match w/o timer button

    private String timd_in_progress;
    private String team;
    private String driver_station;
    RadioGroup start_pos;
    RadioGroup start_piece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        team = intent.getStringExtra("com.team2502.scout2019.team");
        driver_station = intent.getStringExtra("com.team2502.scout2019.driver_station");

        TextView ds_view = findViewById(R.id.driver_station_pre);
        ds_view.setText(driver_station);
        TextView team_view = findViewById(R.id.team_pre);
        team_view.setText(team);

        if (driver_station.contains("Red")) {
            ds_view.setTextColor(Color.parseColor("#FF0000"));  // Red
            team_view.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            ds_view.setTextColor(Color.parseColor("#0000FF")); // Blue
            team_view.setTextColor(Color.parseColor("#0000FF"));
        }

        start_pos = findViewById(R.id.startingPosition);
        start_piece = findViewById(R.id.startingPiece);
    }

    public void checkData(View view){
        if (start_pos.getCheckedRadioButtonId() == -1 || start_piece.getCheckedRadioButtonId() == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Fill out all the fields!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            startMatch();
        }
    }

    public void startMatch(){
        int selectedPieceId = start_piece.getCheckedRadioButtonId();
        RadioButton piece = findViewById(selectedPieceId);
        int selectedLevelId = start_pos.getCheckedRadioButtonId();
        RadioButton level = findViewById(selectedLevelId);

        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", ExportUtils.createSSHeader("false", level.getText().toString(), piece.getText().toString(), timd_in_progress));
        intent.putExtra("com.team2502.scout2019.piece", piece.getText().toString());
        intent.putExtra("com.team2502.scout2019.team", team);
        intent.putExtra("com.team2502.scout2019.driver_station", driver_station);

        startActivity(intent);
    }

    public void isNoShow(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress + "Gt|");
        startActivity(intent);
    }
}
