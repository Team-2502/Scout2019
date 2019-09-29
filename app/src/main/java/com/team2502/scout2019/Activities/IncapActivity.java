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

public class IncapActivity extends AppCompatActivity {
    public String timd_in_progress;
    public int match_time;
    public String piece;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incap);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        match_time = (int)intent.getDoubleExtra("com.team2502.scout2019.time", 0);
    }

    public void buttonPress(View view){
        Button b = (Button)view;
        String cause = b.getText().toString();

        if(cause.equals("Cancel")){
            setResult(RESULT_CANCELED);
            finish();
        }
        timd_in_progress = ExportUtils.createIncapAction(timd_in_progress, match_time, cause);
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }
}
