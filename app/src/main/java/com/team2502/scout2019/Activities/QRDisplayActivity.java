package com.team2502.scout2019.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Map;

public class QRDisplayActivity extends AppCompatActivity {
    /* Shamelessly stolen from 1678
       Copyright (c) 2019 FRC Team 1678: Citrus Circuits
     */

    ImageView tQRView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_display);

        Intent intent = getIntent();
        String timd_in_progress = intent.getStringExtra("com.team2502.scout2019.timd");

        showMatchQR(timd_in_progress);

        writeFileToStorage(("QM" + Integer.toString((((ApplicationInstance) this.getApplication()).getSp("lastMatch", 0)) + 1) + new SimpleDateFormat("MM-dd-yyyy-H:mm:ss").format(new Date())), timd_in_progress);
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

    //Takes scout back to Main Activity and increases the match number by 1.
    public void onOKClick(View view) {
        int last_match = (ApplicationInstance.getSp("lastMatch", 0));
        last_match++;
        ApplicationInstance.setSp("lastMatch", last_match);
        Intent intent = new Intent(this, HeaderActivity.class);
        startActivity(intent);
    }
}
