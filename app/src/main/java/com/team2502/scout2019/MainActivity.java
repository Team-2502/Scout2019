package com.team2502.scout2019;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timd_in_progress = "2502qm1,RA|";
        ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(((ApplicationInstance) this.getApplication()).getSp("scoutSerialNumber", "oof")), "match1");
        TextView team_to_scout_view = findViewById(R.id.textView5);
        team_to_scout_view.setText(Integer.toString(((ApplicationInstance) this.getApplication()).getSp("team", 0)));
        Log.e("last_match sp", Integer.toString(((ApplicationInstance) this.getApplication()).getSp("lastMatch", 0)));
    }

    public void startMatch(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

}