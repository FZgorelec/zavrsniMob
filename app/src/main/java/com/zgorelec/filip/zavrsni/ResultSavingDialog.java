package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ResultSavingDialog extends Dialog {
    private BoardManager manager;
    private String result;
    public ResultSavingDialog(Context context, BoardManager manager,String result){
        super(context);
        this.manager=manager;
        this.result=result;
        setContentView(R.layout.save_dialog);
        setTitle("Save Result");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(this,layoutParams);
        EditText saveEt=findViewById(R.id.saveET);
        Button saveBtn=findViewById(R.id.save);
        saveBtn.setOnClickListener((v)->{
            String resultName=saveEt.getText().toString();
            ResultSaver saver=new ResultSaver(context);
            saver.save(result,resultName);
        });
    }

}
