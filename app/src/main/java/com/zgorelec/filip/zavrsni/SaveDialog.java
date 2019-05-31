package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SaveDialog extends Dialog {

    private BoardManager manager;

    public SaveDialog(Context context,BoardManager manager){
        super(context);
        this.manager=manager;
        setContentView(R.layout.save_dialog);
        setTitle("Save");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(this,layoutParams);
        EditText saveEt=findViewById(R.id.saveET);
        Button saveBtn=findViewById(R.id.save);
        saveBtn.setOnClickListener((v)->{
            String[][] board=manager.boardState();
            String mapName=saveEt.getText().toString();
            MapSaver saver=new MapSaver(context);
            saver.save(board,mapName);
        });
    }

}
