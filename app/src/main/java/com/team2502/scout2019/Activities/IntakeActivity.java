package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.R;

public class IntakeActivity extends AppCompatActivity {
    public String timd_in_progress;
    public int match_time;
    public String piece;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        match_time = (int)intent.getDoubleExtra("com.team2502.scout2019.time", 0);
        piece = intent.getStringExtra("com.team2502.scout2019.type");
    }

    public void buttonPress(View view){
        Button b = (Button)view;
        String place = b.getText().toString();

        if(place.equals("Cancel")){
            Intent data = new Intent();
            data.putExtra("piece", piece);
            setResult(RESULT_CANCELED, data);
            finish();
        }
        timd_in_progress = ExportUtils.createIntakeAction(timd_in_progress, piece, place, match_time);
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        data.putExtra("piece", piece);
        setResult(RESULT_OK, data);
        finish();
    }
}
