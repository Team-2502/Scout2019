package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.team2502.scout2019.Dialogs.ExitHabDialog;
import com.team2502.scout2019.R;

public class MatchActivity extends AppCompatActivity implements ExitHabDialog.ExitHabDialogListener{

    public static String timd_in_progress;
    public TextView match_time_view;
    public double match_time;
    public String current_piece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        current_piece = intent.getStringExtra("com.team2502.scout2019.piece");

        if(current_piece.equals("None")){
            setIntakeEnabled();
        }
        else{
            setPlaceEnabled();
        }

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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);
            }
        }
    }

    public void startClimb(View view){
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void intake(View view){
        setPlaceEnabled();
        current_piece = view.getContentDescription().toString();

        Intent intent = new Intent(this, IntakeActivity.class);
        Log.e("Time to send:", Double.toString(match_time));
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", view.getContentDescription().toString());
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 4);
    }

    public void place(View view){
        setIntakeEnabled();

        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", current_piece);
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        intent.putExtra("com.team2502.scout2019.place", view.getContentDescription().toString());
        startActivityForResult(intent, 4);
    }

    public void undo(View view){
        //TODO delete last action from TIMD and return it as well as setting buttons back to previous state
    }

    public void drop(View view){
        setIntakeEnabled();

        Intent intent = new Intent(this, DropActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", current_piece);
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 4);
    }

    public void defense(View view){
        //TODO Create entirely new Activity, possibly not a dialog for this?
    }

    public void incap(View view){
        //TODO disable all buttons and add to TIMD
    }

    @Override
    public void onDialogLeftHabClick(DialogFragment dialog) {
        timd_in_progress += "Jt|";
    }

    @Override
    public void onDialogNotLeftHabClick(DialogFragment dialog) {
        timd_in_progress += "Jf|";
    }

    public void setIntakeEnabled(){
        findViewById(R.id.intakeCargoButton).setEnabled(true);
        findViewById(R.id.intakeCargoButton).setBackground(getDrawable(R.drawable.green_border));
        findViewById(R.id.intakeHatchButton).setEnabled(true);
        findViewById(R.id.intakeHatchButton).setBackground(getDrawable(R.drawable.green_border));
        findViewById(R.id.placeRocketButton).setEnabled(false);
        findViewById(R.id.placeRocketButton).setBackground(getDrawable(R.drawable.red_border));
        findViewById(R.id.placeCSButton).setEnabled(false);
        findViewById(R.id.placeCSButton).setBackground(getDrawable(R.drawable.red_border));

        findViewById(R.id.dropButton).setEnabled(false);
    }

    public void setPlaceEnabled(){
        findViewById(R.id.intakeCargoButton).setEnabled(false);
        findViewById(R.id.intakeCargoButton).setBackground(getDrawable(R.drawable.red_border));
        findViewById(R.id.intakeHatchButton).setEnabled(false);
        findViewById(R.id.intakeHatchButton).setBackground(getDrawable(R.drawable.red_border));
        findViewById(R.id.placeRocketButton).setEnabled(true);
        findViewById(R.id.placeRocketButton).setBackground(getDrawable(R.drawable.green_border));
        findViewById(R.id.placeCSButton).setEnabled(true);
        findViewById(R.id.placeCSButton).setBackground(getDrawable(R.drawable.green_border));

        findViewById(R.id.dropButton).setEnabled(true);
    }

}
