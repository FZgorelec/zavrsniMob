package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

public class MapDeleter {
    private Context context;
    MapDeleter(Context context){
        this.context=context;
    }
    void deleteMap(String mapName){
        try{
            File file = new File(context.getFilesDir(), mapName);
            if(file.exists()){
                file.delete();
                Toast.makeText(context,"Map has been deleted",Toast.LENGTH_SHORT).show();
            }else Toast.makeText(context,"File does not exist",Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex){
            Toast.makeText(context,"Something went wrong with the map deletion",Toast.LENGTH_SHORT).show();
        }

    }
}
