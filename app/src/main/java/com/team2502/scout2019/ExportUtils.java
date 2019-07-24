package com.team2502.scout2019;

import android.util.Log;

public class ExportUtils {
    public static String createTIMDHeader(String match, String team, String mode, String driver_station, String scout_name, String is_replay){
        String rawTIMD = "A" + match.substring(3) + "B" + team + "C" + Constants.TIMD_COMPRESSION_KEYS.get(mode)
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

}
