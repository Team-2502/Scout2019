package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class PreMatchActivity extends AppCompatActivity {
    //TODO Option to Start Match w/o timer button

    private String timd_in_progress;
    RadioGroup start_pos;
    RadioGroup start_piece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

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
        startActivity(intent);
    }

    public void isNoShow(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress + "Gt|");
        startActivity(intent);
    }
}
