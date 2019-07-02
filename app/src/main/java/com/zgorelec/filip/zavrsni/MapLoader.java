package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

public class MapLoader {
    private Context context;
    MapLoader(Context context){
        this.context=context;
    }
    String[][] load(String mapName){
        String[][] loadedMap=null;
        File file = new File(context.getFilesDir(), mapName);
        try(InputStream is = new FileInputStream(file)){
            InputStream buffer = new BufferedInputStream(is);
            ObjectInput input = new ObjectInputStream (buffer);
            loadedMap=(String[][]) input.readObject();
            is.close();
            Toast.makeText(context,"Map has been loaded",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(context,"Something went wrong with the loading of the requested map",Toast.LENGTH_SHORT).show();
        }

        return loadedMap;
    }
}
