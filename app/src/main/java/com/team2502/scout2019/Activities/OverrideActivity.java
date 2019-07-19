package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.team2502.scout2019.ApplicationInstance;
import com.team2502.scout2019.R;

public class OverrideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_override);
    }

    public void onOKClick(View view) {
        EditText match_num = findViewById(R.id.match_num);
        int this_match = Integer.parseInt(match_num.getText().toString());
        ApplicationInstance.setSp("lastMatch", this_match -1);

        EditText team_num = findViewById(R.id.team_num);
        int this_team = Integer.parseInt(team_num.getText().toString());
        ApplicationInstance.setSp("team", this_team);

        Switch replay_view = findViewById(R.id.replay);
        Boolean is_replay = replay_view.isChecked();
        ApplicationInstance.setSp("isReplay", "true");

        Spinner drivers_station = findViewById(R.id.drivers_station);
        ApplicationInstance.setSp("alliance", drivers_station.getSelectedItem().toString());

        ApplicationInstance.setSp("isOverridden", 1);

        Intent intent = new Intent(this, HeaderActivity.class);
        startActivity(intent);
    }

    public void onUpdateMatchNumber(View view){
        EditText match_num = findViewById(R.id.match_num);
        int this_match = Integer.parseInt(match_num.getText().toString());
        ApplicationInstance.setSp("lastMatch", this_match -1);

        Intent intent = new Intent(this, HeaderActivity.class);
        startActivity(intent);
    }
}
