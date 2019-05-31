package com.zgorelec.filip.zavrsni;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ResultSaver {
    private Context context;
    private int permissionResult=0;
    public ResultSaver(Context context) {
        this.context = context;
    }

    public void save(String data, String resultFileName) {
        checkPermission();
        if (permissionResult == 0) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), resultFileName+".txt");
                boolean filecreated = file.createNewFile();
                System.out.println(filecreated);
                int a = 5;
                if (filecreated) {
                    FileWriter outputStream = new FileWriter(file);
                    outputStream.write(data);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(context, "File saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "File with that name already exists", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                Toast.makeText(context, "Something went wrong with the result saving", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        permissionResult = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // function which uses the permission
    }
}

