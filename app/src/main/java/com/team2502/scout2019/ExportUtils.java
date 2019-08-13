package com.team2502.scout2019;

import android.util.Log;

public class ExportUtils {
    public static String createTIMDHeader(String match, String team, String mode, String driver_station, String scout_name, String is_replay){
        String rawTIMD = "A" + Integer.parseInt(match.substring(3)) + "B" + team + "C" + Constants.TIMD_COMPRESSION_KEYS.get(mode)
                + "D" + Constants.SCOUT_NAME_TO_KEY.get(scout_name) + "E" + Constants.TIMD_COMPRESSION_KEYS.get(driver_station)
                + "F" + Constants.TIMD_COMPRESSION_KEYS.get(is_replay);
        Log.e("timdHead", rawTIMD);
        return rawTIMD;
    }

    public static String createSSHeader(String is_no_show, String start_level, String preload, String timd_in_progress){
        String rawTIMD = timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get(is_no_show) + "H"
                + Constants.TIMD_COMPRESSION_KEYS.get(start_level) + "I" + Constants.TIMD_COMPRESSION_KEYS.get(preload);
        Log.e("timdSS", rawTIMD);
        return rawTIMD;
    }

    public static String createIntakeAction(String timd_in_progress, String piece, String place, int time){
        return timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Intake") + "L" + Constants.TIMD_COMPRESSION_KEYS.get(place)
                + "M" + time + "N" + Constants.TIMD_COMPRESSION_KEYS.get(piece) + ",";
    }

    public static String createPlaceRocketAction(String timd_in_progress, String piece, int match_time, String level, boolean defense){
        String temp_timd = timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Place") +
                "L" + Constants.TIMD_COMPRESSION_KEYS.get("Rocket") +
                "M" + match_time + "N" + Constants.TIMD_COMPRESSION_KEYS.get(piece) +
                "O" + Constants.TIMD_COMPRESSION_KEYS.get(level);
        if(defense){
            temp_timd += "Pt,";
        }
        else{
            temp_timd += "Pf,";
        }
        return temp_timd;
    }

    public static String createPlaceCSAction(String timd_in_progress, String piece, int match_time, String place, boolean defense){
        String temp_timd = timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Place") +
                "L" + Constants.TIMD_COMPRESSION_KEYS.get("CargoShip") +
                "M" + match_time + "N" + Constants.TIMD_COMPRESSION_KEYS.get(piece) +
                "O" + Constants.TIMD_COMPRESSION_KEYS.get("Level 1") + "Q" + Constants.TIMD_COMPRESSION_KEYS.get(place);
        if(defense){
            temp_timd += "Pt,";
        }
        else{
            temp_timd += "Pf,";
        }
        return temp_timd;
    }

    public static String createDropAction(String timd_in_progress, String piece, String place, int match_time, boolean wasDefended){
        String temp_timd = timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Drop") + "L" + Constants.TIMD_COMPRESSION_KEYS.get(place)
                + "M" + match_time + "N" + Constants.TIMD_COMPRESSION_KEYS.get(piece);
        if(wasDefended){
            temp_timd += "Pt,";
        }
        else{
            temp_timd += "Pf,";
        }
        return temp_timd;
    }

    public static String createRecapAction(String timd_in_progress, int time){
        return timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Recap") + "M" + time + ",";
    }

    public static String createIncapAction(String timd_in_progress, int time){
        return timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Incap") + "M" + time + ",";
    }

    public static String createDefenseAction(String timd_in_progress, int time){
        return timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Defense") + "M" + time + ",";
    }

    public static String createOffenseAction(String timd_in_progress, int time){
        return timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Offense") + "M" + time + ",";
    }

    public static String createClimb(String timd_in_progress, int climb_start_time, String actualC, String attemptC, boolean doubleC, boolean tripleC, boolean assistedC, boolean wasAssistedC){
        String temp_timd = timd_in_progress + "K" + Constants.TIMD_COMPRESSION_KEYS.get("Climb") + "M" + climb_start_time + "R" + Constants.TIMD_COMPRESSION_KEYS.get(actualC) + "S" + Constants.TIMD_COMPRESSION_KEYS.get(attemptC);
        if(doubleC){
            temp_timd += "Tt";
        }
        else if(tripleC){
            temp_timd += "Ut";
        }
        if(assistedC){
            temp_timd += "Vt";
        }
        else if(wasAssistedC){
            temp_timd += "Wt";
        }
        return temp_timd;
    }
}
