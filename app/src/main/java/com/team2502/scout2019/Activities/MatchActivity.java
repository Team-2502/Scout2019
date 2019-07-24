package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.team2502.scout2019.Dialogs.ExitHabDialog;
import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class MatchActivity extends AppCompatActivity implements ExitHabDialog.ExitHabDialogListener{

    public static String timd_in_progress;
    public TextView match_time_view;
    public double match_time;
    public String current_piece;
    public boolean currently_incap;
    public String last_piece;

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
        last_piece = "None";
        findViewById(R.id.undoButton).setEnabled(true);

        Intent intent = new Intent(this, IntakeActivity.class);
        Log.e("Time to send:", Double.toString(match_time));
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", view.getContentDescription().toString());
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 4);
    }

    public void place(View view){
        setIntakeEnabled();
        last_piece = current_piece;
        current_piece = "None";

        findViewById(R.id.undoButton).setEnabled(true);

        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", current_piece);
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        intent.putExtra("com.team2502.scout2019.place", view.getContentDescription().toString());
        startActivityForResult(intent, 4);
    }

    public void undo(View view){
        //TODO if currently incap fix
        if(!timd_in_progress.substring(timd_in_progress.length() - 1).equals(",")){
            Toast toast = Toast.makeText(getApplicationContext(), "Nothing to undo!", Toast.LENGTH_SHORT);
            toast.show();
        }
        //Only one action
        else if(timd_in_progress.substring(0, timd_in_progress.length() - 1).lastIndexOf(",") == -1){
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf("|") + 1);
            Log.e("timdUndo", timd_in_progress);
        }
        //Multiple actions
        else{
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.length() - 1);
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf(",") + 1);
            Log.e("timdUndo", timd_in_progress);
        }

        undoButtonState();
        current_piece = last_piece;
        findViewById(R.id.undoButton).setEnabled(false);

    }

    public void drop(View view){
        setIntakeEnabled();
        findViewById(R.id.undoButton).setEnabled(true);

        last_piece = current_piece;
        current_piece = "None";

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
        if(currently_incap){
            if(current_piece.equals("None")){
                setIntakeEnabled();
            }
            else{
                setPlaceEnabled();
            }
            timd_in_progress = ExportUtils.createRecapAction(timd_in_progress, (int)match_time);
            Log.e("timdRecap", timd_in_progress);
            currently_incap = false;
        }
        else{
            incapButtons();
            currently_incap = true;
            timd_in_progress = ExportUtils.createIncapAction(timd_in_progress, (int)match_time);
            Log.e("timdIncap", timd_in_progress);
        }
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
    
    public void undoButtonState(){
        ImageButton[] buttons = new ImageButton[]{findViewById(R.id.intakeCargoButton),
                findViewById(R.id.intakeHatchButton),  findViewById(R.id.placeRocketButton), findViewById(R.id.placeCSButton)};
        for (ImageButton button : buttons) {
            button.setEnabled(!button.isEnabled());
            if(button.isEnabled()){
                button.setBackground(getDrawable(R.drawable.green_border));
            }
            else{
                button.setBackground(getDrawable(R.drawable.red_border));
            }
        }
        findViewById(R.id.dropButton).setEnabled(!findViewById(R.id.dropButton).isEnabled());
    }

    public void incapButtons(){
        ImageButton[] buttons = new ImageButton[]{findViewById(R.id.intakeCargoButton),
                findViewById(R.id.intakeHatchButton),  findViewById(R.id.placeRocketButton), findViewById(R.id.placeCSButton)};
        for (ImageButton button : buttons) {
            button.setEnabled(false);
            button.setBackground(getDrawable(R.drawable.red_border));
            }
        findViewById(R.id.dropButton).setEnabled(false);
    }
}