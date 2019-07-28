package com.team2502.scout2019;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SHARED_PREF_KEY = "com.2502.scout2019.sp";
    public static final File SCOUTING_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting");

    public static final int NUM_OF_TABLETS = 1;

    public static final Map<String, String> SERIAL_TO_SCOUT = new HashMap<String, String>() {{
        put("G000L40763270NAL", "scout1");
    }};

    public static final Map<String, String> SCOUT_NAME_TO_KEY = new HashMap<String, String>() {{
        put("Ryan A", "a");
        put("Evan L", "b");
        put("Ritik M", "c");
        put("Ravisha J", "d");
        put("Ishan S", "e");
    }};

    public static final Map<String, String> TIMD_COMPRESSION_KEYS = new HashMap<String, String>() {{
        put("true", "t");
        put("false", "f");
        put("file", "a");
        put("override", "b");
        put("Red 1", "c");
        put("Red 2", "d");
        put("Red 3", "e");
        put("Blue 1", "g");
        put("Blue 2", "h");
        put("Blue 3", "i");
        put("Hab 1", "j");
        put("Hab 2", "k");
        put("Cargo", "l");
        put("Hatch", "m");
        put("None", "n");
        put("Human Player Station", "o");
        put("Ground", "p");
        put("Intake", "q");
        put("Place", "r");
        put("Level 1", "s");
        put("Level 2", "u");
        put("Level 3", "v");
        put("Front", "w");
        put("Side", "x");
        put("Rocket", "y");
        put("CargoShip", "z");
        put("Drop", "aa");
        put("Middle of Field", "ab");
        put("Incap", "ac");
        put("Recap", "ad");
        put("Climb", "ae");
    }};
}
