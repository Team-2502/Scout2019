package com.team2502.scout2019.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team2502.scout2019.R;

public class ClimbActivity extends AppCompatActivity {
    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb);

        Intent intent = getIntent();
        String timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

        /*
        Leave for future reference!!!!!
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("rawTIMDs").child(timd_in_progress.split(",")[0]).setValue(timd_in_progress);
        */
    }
}
