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

public class MatchActivity extends AppCompatActivity implements ExitHabDialog.ExitHabDialogListener{

    public static String timd_in_progress;
    public static TextView match_time_view;
    public double match_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

        match_time_view = findViewById(R.id.matchTimer);
        new CountDownTimer(150000, 1000) {

            public void onTick(long millisUntilFinished) {
                match_time_view.setText("T-" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                match_time_view.setText("T-0");
            }
        }.start();

        DialogFragment exitHabFragment = new ExitHabDialog();
        exitHabFragment.show(getSupportFragmentManager(), "ExitHabDialog");

    }

    public void startClimb(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void intakeHatch(View view){

    }
    public void intakeCargo(View view){

    }

    @Override
    public void onDialogLeftHabClick(DialogFragment dialog) {
        timd_in_progress += "Jt|";
    }

    @Override
    public void onDialogNotLeftHabClick(DialogFragment dialog) {
        timd_in_progress += "Jf|";
    }

}
