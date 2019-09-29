package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
    public boolean currently_defense;
    public String last_piece;

    @Override
    public void onBackPressed() {
        //TODO Confirm they really want to go back
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        current_piece = intent.getStringExtra("com.team2502.scout2019.piece");
        String team = intent.getStringExtra("com.team2502.scout2019.team");
        String driver_station = intent.getStringExtra("com.team2502.scout2019.driver_station");

        TextView team_view = findViewById(R.id.teamScouting);
        team_view.setText(team);
        if (driver_station.contains("Red")) {
            team_view.setTextColor(Color.parseColor("#FF0000")); // Red
        }
        else{
            team_view.setTextColor(Color.parseColor("#0000FF")); // Blue
        }

        if(current_piece.equals("None")){
            setIntakeEnabled();
        }
        else{
            setPlaceEnabled(current_piece);
        }

        match_time_view = findViewById(R.id.matchTimer);
        new CountDownTimer(150000, 1000) {

            public void onTick(long millisUntilFinished) {
                match_time = millisUntilFinished / 1000;
                match_time_view.setText("T-" + millisUntilFinished / 1000);
                if(millisUntilFinished / 1000 < 20){    // Endgame
                    match_time_view.setTextColor(Color.RED);
                }
                else if(millisUntilFinished / 1000 < 30){   // Endgame warning
                    match_time_view.setTextColor(Color.parseColor("#E8BA13"));
                }
            }

            public void onFinish() {
                match_time_view.setText("T-0");
            }
        }.start();

        DialogFragment exitHabFragment = new ExitHabDialog();
        exitHabFragment.show(getSupportFragmentManager(), "ExitHabDialog");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 4 ---- Intake
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);
                current_piece = data.getStringExtra("piece");
                last_piece = "None";
                setPlaceEnabled(current_piece);
                findViewById(R.id.undoButton).setEnabled(true);
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
            // Dropped in HPS
            else if(resultCode == 1){
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);

                setIntakeEnabled();
                findViewById(R.id.undoButton).setEnabled(true);

                last_piece = current_piece;
                current_piece = "None";
            }
        }
        // 5 ---- Place
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);

                setIntakeEnabled();
                findViewById(R.id.undoButton).setEnabled(true);
                last_piece = current_piece;
                current_piece = "None";
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }
        // 6 ---- Drop
        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);

                setIntakeEnabled();
                findViewById(R.id.undoButton).setEnabled(true);

                last_piece = current_piece;
                current_piece = "None";
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }
    }

    public void climb(View view){
        Intent intent = new Intent(this, ClimbActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.time", match_time);
        startActivity(intent);
    }

    public void intake(View view){
        Intent intent = new Intent(this, IntakeActivity.class);
        Log.e("Time to send:", Double.toString(match_time));
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", view.getContentDescription().toString());
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 4);
    }

    public void place(View view){
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", current_piece);
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        intent.putExtra("com.team2502.scout2019.place", view.getContentDescription().toString());
        startActivityForResult(intent, 5);
    }

    public void undo(View view){
        //This whole method is just spaghetti code
        boolean undoing_to_incap = false;

        //No actions done
        if(!timd_in_progress.substring(timd_in_progress.length() - 1).equals(",")){
            Toast toast = Toast.makeText(getApplicationContext(), "Nothing to undo!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        //Only one action
        else if(timd_in_progress.substring(0, timd_in_progress.length() - 1).lastIndexOf(",") == -1){
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf("|") + 1);
            if(timd_in_progress.substring(timd_in_progress.lastIndexOf("K") + 1, timd_in_progress.lastIndexOf("K") + 3).equals("ab")){
                undoing_to_incap = true;
            }
            Log.e("timdUndo", timd_in_progress);
        }
        //Multiple actions
        else{
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.length() - 1);
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf(",") + 1);
            if(timd_in_progress.substring(timd_in_progress.lastIndexOf("K") + 1, timd_in_progress.lastIndexOf("K") + 3).equals("ab")){
                undoing_to_incap = true;
            }
            Log.e("timdUndo", timd_in_progress);
        }

        if(currently_incap){
            if(current_piece.equals("None")){
                setIntakeEnabled();
            }
            else{
                setPlaceEnabled(current_piece);
            }
            currently_incap = false;
        }
        else if(undoing_to_incap){
            incapButtons();
            currently_incap = true;
        }
        else{
            current_piece = last_piece;
            undoButtonState();
        }

        findViewById(R.id.undoButton).setEnabled(false);

    }

    public void drop(View view){
        Intent intent = new Intent(this, DropActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.type", current_piece);
        intent.putExtra("com.team2502.scout2019.time",  match_time);
        startActivityForResult(intent, 6);
    }

    public void defense(View view){
        CheckBox defense_box = findViewById(R.id.defense_check);
        if(currently_incap){
            return;
        }
        if(currently_defense){
            if(current_piece.equals("None")){
                setIntakeEnabled();
            }
            else{
                setPlaceEnabled(current_piece);
            }
            timd_in_progress = ExportUtils.createOffenseAction(timd_in_progress, (int)match_time);
            Log.e("timdOffense", timd_in_progress);
            currently_defense = false;
            defense_box.setChecked(false);
        }
        else{
            incapButtons();
            currently_defense = true;
            timd_in_progress = ExportUtils.createDefenseAction(timd_in_progress, (int)match_time);
            Log.e("timdDefense", timd_in_progress);
            defense_box.setChecked(true);
        }

        findViewById(R.id.undoButton).setEnabled(true);
    }

    public void incap(View view){
        CheckBox incap_box = findViewById(R.id.incap_check);
        if(currently_incap && !currently_defense){
            if(current_piece.equals("None")){
                setIntakeEnabled();
            }
            else{
                setPlaceEnabled(current_piece);
            }
            timd_in_progress = ExportUtils.createRecapAction(timd_in_progress, (int)match_time);
            Log.e("timdRecap", timd_in_progress);
            currently_incap = false;
            incap_box.setChecked(false);
        }
        else if(currently_defense && currently_incap){
            timd_in_progress = ExportUtils.createRecapAction(timd_in_progress, (int)match_time);
            Log.e("timdRecap", timd_in_progress);
            currently_incap = false;
            incap_box.setChecked(false);
        }
        else{
            incapButtons();
            currently_incap = true;
            incap_box.setChecked(true);
            timd_in_progress = ExportUtils.createIncapAction(timd_in_progress, (int)match_time);
            Log.e("timdIncap", timd_in_progress);
        }

        findViewById(R.id.undoButton).setEnabled(true);
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

    public void setPlaceEnabled(String piece){
        if(current_piece.equals("Cargo")){
            findViewById(R.id.intakeCargoButton).setBackground(getDrawable(R.drawable.selected_red_border));
            findViewById(R.id.intakeHatchButton).setBackground(getDrawable(R.drawable.red_border));
        }
        else if(current_piece.equals("Hatch")){
            findViewById(R.id.intakeHatchButton).setBackground(getDrawable(R.drawable.selected_red_border));
            findViewById(R.id.intakeCargoButton).setBackground(getDrawable(R.drawable.red_border));
        }
        findViewById(R.id.intakeCargoButton).setEnabled(false);
        findViewById(R.id.intakeHatchButton).setEnabled(false);

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
        if(current_piece.equals("Cargo")){
            findViewById(R.id.intakeCargoButton).setBackground(getDrawable(R.drawable.selected_red_border));
        }
        else{
            findViewById(R.id.intakeHatchButton).setBackground(getDrawable(R.drawable.selected_red_border));
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