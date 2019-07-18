package com.team2502.scout2019;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SHARED_PREF_KEY = "com.2502.scout2019.sp";
    public static final File SCOUTING_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting");

    public static final Map<String, String> SERIAL_TO_SCOUT = new HashMap<>();
    public static final String[] scoutIDs = new String[]{"scout1"};
    public static final String[] TABLET_SERIAL_NUMBERS = new String[]{"G000L40763270NAL"};
    public static final int NUM_OF_TABLETS = 1;
}
