package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
                match_time = millisUntilFinished / 1000;
                match_time_view.setText("T-" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                match_time_view.setText("T-0");
            }
        }.start();

        DialogFragment exitHabFragment = new ExitHabDialog();
        exitHabFragment.show(getSupportFragmentManager(), "ExitHabDialog");

        //TODO Set buttons enabled based on starting piece

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("Return from Intake", timd_in_progress);
            }
        }
    }

    public void startClimb(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void intake(View view){
        findViewById(R.id.intakeCargoButton).setEnabled(false);
        findViewById(R.id.intakeHatchButton).setEnabled(false);
        findViewById(R.id.placeRocketButton).setEnabled(true);
        findViewById(R.id.placeCSButton).setEnabled(true);

        Intent intent = new Intent(this, IntakeActivity.class);
        Log.e("Time to send:", Double.toString(match_time));
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", view.getContentDescription().toString());
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 4);
    }

    public void placeRocket(View view){
        Intent intent = new Intent(this, PlaceRocketActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }
    public void placeCargoShip(View view){
        Intent intent = new Intent(this, PlaceCargoShipActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
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
