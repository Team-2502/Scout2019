package com.team2502.scout2019;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PreMatchActivity extends AppCompatActivity {

    public static String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
        timd_in_progress = "2502qm1,RA|";

        int this_match = (((ApplicationInstance) this.getApplication()).getSp("lastMatch", 0)) + 1;
        String this_match_string = "QM " + Integer.toString(this_match);

        ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(((ApplicationInstance) this.getApplication()).getSp("scoutSerialNumber", "oof")), this_match_string);

        TextView team_to_scout_view = findViewById(R.id.teamToScout);
        team_to_scout_view.setText(Integer.toString(((ApplicationInstance) this.getApplication()).getSp("team", 0)));

        TextView match_number = findViewById(R.id.matchNumber);
        match_number.setText(this_match_string);

    }

    public void startMatch(View view){
        ImageView background = findViewById(R.id.imageView4);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

}