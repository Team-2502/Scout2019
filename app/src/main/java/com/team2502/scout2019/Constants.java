package com.team2502.scout2019;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SHARED_PREF_KEY = "com.2502.scout2019.sp";
    public static final File SCOUTING_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting");

    public static final Map<String, String> SERIAL_TO_SCOUT = new HashMap<String, String>() {{
        put("G000L40763270NAL", "scout1");
        put("G000L40763270V9A", "scout2");
        put("G000L40763270WH5", "scout3");
        put("G000L40763270QKL", "scout4");
        put("G000L40763270NX9", "scout5");
        put("G000L40763270WLQ", "scout6");
        put("G000L40763270R0J", "scout1");
    }};

    public static final Map<String, String> SCOUT_NAME_TO_KEY = new HashMap<String, String>() {{
        put("Adhi", "a");
        put("Big Chief", "b");
        put("Christian", "c");
        put("Christopher", "d");
        put("Danny", "e");
        put("Drew", "f");
        put("Evan", "g");
        put("Isaac A", "s");
        put("Ishan", "p");
        put("Justin", "r");
        put("Kyle", "h");
        put("Michael", "q");
        put("Miguel", "t");
        put("Nathan", "i");
        put("Neel", "j");
        put("Nigel", "k");
        put("Ravisha", "l");
        put("Riley", "m");
        put("Ritik", "n");
        put("Ryan", "o");
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
        put("Offense", "af");
        put("Defense", "ag");
    }};
}
