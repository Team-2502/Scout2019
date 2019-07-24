package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.team2502.scout2019.ApplicationInstance;
import com.team2502.scout2019.Constants;
import com.team2502.scout2019.ExportUtils;
import com.team2502.scout2019.ImportUtils;
import com.team2502.scout2019.R;

public class HeaderActivity extends AppCompatActivity {

    public static String current_match_string;
    public static String current_team_scouting;
    public static String current_driver_station;
    public static String current_assignment_mode;
    public static String current_match_is_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        int this_match_number = (ApplicationInstance.getSp("lastMatch", 0)) + 1;
        current_match_string = "QM " + this_match_number;
        ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(ApplicationInstance.getSp("scoutSerialNumber", "oof")), current_match_string);
        current_team_scouting = Integer.toString(ApplicationInstance.getSp("team", 0));
        current_driver_station = ApplicationInstance.getSp("alliance", "oof");
        current_assignment_mode = ApplicationInstance.getSp("assignmentMode", "oof");
        current_match_is_replay = ApplicationInstance.getSp("isReplay", "false");

        TextView team_to_scout_view = findViewById(R.id.teamToScout);
        team_to_scout_view.setText(current_team_scouting);

        TextView match_number = findViewById(R.id.matchNumber);
        match_number.setText(current_match_string);

        TextView ds_view = findViewById(R.id.drivers_station);
        ds_view.setText(current_driver_station);
        if (current_driver_station.contains("Red")) {
            ds_view.setTextColor(Color.parseColor("#FF0000"));  // Red
        }
        else{
            ds_view.setTextColor(Color.parseColor("#0000FF")); // Blue
        }

    }

    public void startMatch(View view){
        Spinner scout_spinner = findViewById(R.id.scout_initials);
        String current_scout = scout_spinner.getSelectedItem().toString();

        String timd_in_progress = ExportUtils.createTIMDHeader(current_match_string, current_team_scouting, current_assignment_mode, current_driver_station, current_scout, current_match_is_replay);
        Intent intent = new Intent(this, PreMatchActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

    public void openOverrideActivity(View view){
        Intent intent = new Intent(this, OverrideActivity.class);
        startActivity(intent);
    }

    public void openRescanActivity(View view){
        //TODO Add a timd from the past to the Intent so QRDisplay displays it
        Intent intent = new Intent(this, QRDisplayActivity.class);
        startActivity(intent);
    }

}