package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadDialog extends Dialog {

    private Context context;
    private BoardManager manager;
    private RecyclerView recyclerView;
    private LoadMapAdapter mapAdapter;
    private List<String> mapNames;

    public LoadDialog(Context context,BoardManager manager){
        super(context);
        this.context=context;
        this.manager=manager;
        setContentView(R.layout.load_dialog);
        setTitle("Load/Delete");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(this,layoutParams);
        TextView selectedMapTV=findViewById(R.id.loadTV);
        Button loadBtn=findViewById(R.id.loadMapBtn);
        Button deleteBtn=findViewById(R.id.deleteMapBtn);
        recyclerView=findViewById(R.id.loadRV);
        mapNames=loadMaps();
        mapAdapter=new LoadMapAdapter(context,mapNames);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mapAdapter.setOnItemClickListener((position, v) -> {selectedMapTV.setText(mapAdapter.getMapList().get(position));});
        recyclerView.setAdapter(mapAdapter);
        mapAdapter.notifyDataSetChanged();
        mapAdapter.notifyItemRangeChanged(0,mapAdapter.getItemCount());

        loadBtn.setOnClickListener((v)->{
            MapLoader loader=new MapLoader(context);
            String[][] map=loader.load(selectedMapTV.getText().toString());
            if(map!=null){
                manager.fillMap(map);
                manager.updateFoodBombCount();
            }
        });
        deleteBtn.setOnClickListener((v)->{
            MapDeleter deleter=new MapDeleter(context);
            deleter.deleteMap(selectedMapTV.getText().toString());
        });
    }

    private List<String> loadMaps() {
        List<String> maps=new ArrayList<>();
        File dirFiles = context.getFilesDir();
        for (String strFile : dirFiles.list())
        {
            maps.add(strFile);
        }
        return maps;
    }

}
