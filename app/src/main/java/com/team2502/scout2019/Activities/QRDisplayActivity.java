package com.team2502.scout2019.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.team2502.scout2019.ApplicationInstance;
import com.team2502.scout2019.R;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QRDisplayActivity extends AppCompatActivity {
    /* Shamelessly stolen from 1678
       Copyright (c) 2019 FRC Team 1678: Citrus Circuits
     */

    ImageView tQRView;
    String timd_in_progress;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_display);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");
        boolean rescan = intent.getBooleanExtra("com.team2502.scout2019.rescan", false);

        showMatchQR(timd_in_progress);

        if(!rescan){
            writeFileToStorage(("QM" + (ApplicationInstance.getSp("lastMatch", 0) + 1) + "_" + new SimpleDateFormat("MM-dd-yyyy-H:mm:ss", Locale.US).format(new Date())), timd_in_progress);

            int last_match = (ApplicationInstance.getSp("lastMatch", 0));
            last_match++;
            ApplicationInstance.setSp("lastMatch", last_match);
        }
    }

    //Calls displayQR to display the QR.
    public void showMatchQR(String qrString) {
        tQRView = (ImageView) findViewById(R.id.QRCode_Display);
        displayQR(qrString);
    }

    //Set QR code parameters and show QR code to send data
    public void displayQR(String qrCode) {
        try {
            //setting size of qr code
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallestDimension = width < height ? width : height;
            //setting parameters for qr code
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            createQRCode(qrCode, charset, hintMap, smallestDimension, smallestDimension);
        } catch (Exception ex) {
            Log.e("QrGenerate", ex.getMessage());
        }
    }

    //Creates QR code dimensions
    public void createQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth) {

        try {
            //generating qr code in bitmatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view
            tQRView.setImageBitmap(null);
            tQRView.setImageBitmap(bitmap);
        } catch (Exception er) {
            Log.e("QrGenerate", er.getMessage());
        }
    }

    //Saves scout data as text file in tablet internal storage
    public void writeFileToStorage(String sFileName, String sBody) {
        File file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting/rawTIMDs");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Takes scout back to Main Activity
    public void onOKClick(View view) {
        Intent intent = new Intent(this, HeaderActivity.class);
        startActivity(intent);
    }

    public void onWifiClick(View view){
        if(isNetworkAvailable()){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("rawTIMDs").child(getTIMDName(timd_in_progress)).setValue(timd_in_progress);

            Toast toast = Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, HeaderActivity.class);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Not Connected to Wifi!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getTIMDName(String scannedTIMD){
        String TIMDName = "QM" + scannedTIMD.substring(1, scannedTIMD.indexOf('B')) + "-";
        TIMDName += scannedTIMD.substring(scannedTIMD.indexOf('B') + 1, scannedTIMD.indexOf('C')) + "-";
        TIMDName += scannedTIMD.substring(scannedTIMD.indexOf('D') + 1, scannedTIMD.indexOf('E'));
        return TIMDName;
    }

}