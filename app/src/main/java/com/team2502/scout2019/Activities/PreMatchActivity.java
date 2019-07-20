package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team2502.scout2019.R;

public class PreMatchActivity extends AppCompatActivity {

    private String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
    }

    public void checkData(View view){
        RadioGroup start_pos = findViewById(R.id.startingPosition);
        RadioGroup start_piece = findViewById(R.id.startingPiece);
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
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void isNoShow(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        startActivity(intent);
    }
}
