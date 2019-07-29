package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class ClimbActivity extends AppCompatActivity {
    double climb_start_time;
    String timd_in_progress;
    RadioGroup attempted;
    RadioGroup actual;
    CheckBox doubleC;
    CheckBox tripleC;
    CheckBox assistedC;
    CheckBox wasAssistedC;

    @Override
    public void onBackPressed() {
        //TODO Go back button
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        climb_start_time = intent.getDoubleExtra("com.team2502.scout2019.time", 0);

        attempted = findViewById(R.id.attemptedClimb);
        actual = findViewById(R.id.actualClimb);
        doubleC = findViewById(R.id.doubleClimb);
        tripleC = findViewById(R.id.tripleClimb);
        assistedC = findViewById(R.id.climbAssist);
        wasAssistedC = findViewById(R.id.climbAssisted);

    }

    public void checkData(View view){
        if (attempted.getCheckedRadioButtonId() == -1 || actual.getCheckedRadioButtonId() == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Fill out all the fields!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            doneWithClimb();
        }
    }

    public void doneWithClimb(){
        int attemptID = attempted.getCheckedRadioButtonId();
        RadioButton attemptC = findViewById(attemptID);
        int actualID = actual.getCheckedRadioButtonId();
        RadioButton actualC = findViewById(actualID);

        timd_in_progress = ExportUtils.createClimb(timd_in_progress, (int)climb_start_time, actualC.getText().toString(),
                attemptC.getText().toString(), doubleC.isChecked(), tripleC.isChecked(), assistedC.isChecked(), wasAssistedC.isChecked());
        Log.e("timdClimb", timd_in_progress);
        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2019.rescan", false);
        startActivity(intent);
    }

    public void toggleHab3OptionsOn(View view){
        findViewById(R.id.doubleClimb).setEnabled(true);
        findViewById(R.id.tripleClimb).setEnabled(true);
    }

    public void toggleHab3OptionsOff(View view){
        findViewById(R.id.doubleClimb).setEnabled(false);
        findViewById(R.id.tripleClimb).setEnabled(false);
    }


}
