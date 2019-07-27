package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class DropActivity extends AppCompatActivity {
    public String timd_in_progress;
    public int match_time;
    public String piece;

    public RadioGroup dropPlace;
    public CheckBox wasDefended;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        match_time = (int)intent.getDoubleExtra("com.team2502.scout2019.time", 0);
        piece = intent.getStringExtra("com.team2502.scout2019.type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        this.setFinishOnTouchOutside(false);

        dropPlace = findViewById(R.id.dropPlace);
        wasDefended = findViewById(R.id.defendedDropCheckBox);
    }

    public void buttonPress(View view){
        int selectedLevelId = dropPlace.getCheckedRadioButtonId();
        RadioButton place = findViewById(selectedLevelId);
        try {
            timd_in_progress = ExportUtils.createDropAction(timd_in_progress, piece, place.getText().toString(), match_time, wasDefended.isChecked());
        }
        catch(NullPointerException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Fill out all the fields!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }
}
