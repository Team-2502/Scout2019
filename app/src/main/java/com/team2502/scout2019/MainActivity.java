package com.team2502.scout2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static String timd_in_progress;
    public static File scouting_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting");

    public static String retrieveFile(String pFileName){
        Log.e("Retrieve File", pFileName);
        Log.e("DIr name",scouting_dir.getAbsolutePath() );

        if (!scouting_dir.exists()) {
            scouting_dir.mkdir();
            Log.e("exists","made dir");
        }

        final File[] files = scouting_dir.listFiles();

        Log.e("FilesList", files.toString());

        try{
            if(!(files == null)){
                for(File tfile: files){
                    if(tfile.getName().equals(pFileName)){
                        return readFile(tfile.getPath());
                    }
                }
            }
        }catch(NullPointerException ne){
            Log.e("NULL POINTER EXCEPTION", "getting file path");
        }

        return null;
    }

    public static String readFile(String pPathName) {
        BufferedReader bReader;
        try {
            bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pPathName))));
        } catch (IOException ioe) {
            Log.e("File Error", "Failed To Open File");
            return null;
        }
        String dataOfFile = "";
        String buf;
        try {
            //Add the content of the file
            while ((buf = bReader.readLine()) != null) {
                dataOfFile = dataOfFile.concat(buf + "\n");
            }
        } catch (IOException ioe) {
            Log.e("File Error", "Failed To Read From File");
            return null;
        }
        Log.i("fileData", dataOfFile);
        return dataOfFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timd_in_progress = "2502qm1,RA|";
        TextView team_to_scout_view = findViewById(R.id.textView5);
        team_to_scout_view.setText(retrieveFile("assignments.txt"));

    }

    public void startMatch(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("com.team2502.scout2019.timd", timd_in_progress);
        startActivity(intent);
    }

}