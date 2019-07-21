package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.team2502.scout2019.Dialogs.ExitHabDialog;
import com.team2502.scout2019.R;

public class MatchActivity extends AppCompatActivity {

    public static String timd_in_progress;
    public static TextView match_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

        match_time = findViewById(R.id.textView4);
        new CountDownTimer(150000, 1000) {

            public void onTick(long millisUntilFinished) {
                match_time.setText("T-" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                match_time.setText("T-0");
            }
        }.start();

        DialogFragment newFragment = new ExitHabDialog();
        newFragment.show(getSupportFragmentManager(), "exit_hab");

    }

    public void startClimb(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }
}
