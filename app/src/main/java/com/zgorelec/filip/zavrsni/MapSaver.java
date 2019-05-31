package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

public class MapSaver {
    private Context context;
    MapSaver(Context context){
        this.context=context;
    }
    void save(String[][] map,String mapName){
        try{
            File file = new File(context.getFilesDir(), mapName);
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
            Toast.makeText(context,"Map has been saved",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException ex){
            Toast.makeText(context,"File could not be created",Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex){
            Toast.makeText(context,"IO error occurred",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(context,"Error occurred",Toast.LENGTH_SHORT).show();
        }
    }

}
