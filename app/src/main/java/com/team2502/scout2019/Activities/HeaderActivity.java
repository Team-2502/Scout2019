package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team2502.scout2019.ApplicationInstance;
import com.team2502.scout2019.Constants;
import com.team2502.scout2019.ImportUtils;
import com.team2502.scout2019.R;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        int this_match = (((ApplicationInstance) this.getApplication()).getSp("lastMatch", 0)) + 1;
        String this_match_string = "QM " + Integer.toString(this_match);

        ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(((ApplicationInstance) this.getApplication()).getSp("scoutSerialNumber", "oof")), this_match_string);

        TextView team_to_scout_view = findViewById(R.id.teamToScout);
        team_to_scout_view.setText(Integer.toString(((ApplicationInstance) this.getApplication()).getSp("team", 0)));

        TextView match_number = findViewById(R.id.matchNumber);
        match_number.setText(this_match_string);

        TextView ds_view = findViewById(R.id.drivers_station);
        String alliance = ((ApplicationInstance) this.getApplication()).getSp("alliance", "oof");
        ds_view.setText(alliance);
        if (alliance.contains("Red")) {
            ds_view.setTextColor(Color.parseColor("#FF0000"));  // Red
        }
        else{
            ds_view.setTextColor(Color.parseColor("#0000FF")); // Blue
        }

    }

    public void startMatch(View view){
        String timd_in_progress = "uwu";
        ImageView background = findViewById(R.id.imageView4);
        Intent intent = new Intent(this, PreMatchActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void openOverrideActivity(View view){

    }

    public void openRescanActivity(View view){

    }

}