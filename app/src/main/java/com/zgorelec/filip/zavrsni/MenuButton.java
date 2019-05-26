package com.zgorelec.filip.zavrsni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

public class MenuButton extends android.support.v7.widget.AppCompatButton {

    protected String state;
    private  StateToImageMap stateToImageMap=StateToImageMap.getInstance();
    @SuppressLint("RestrictedApi")
    public MenuButton(Context context, String state){
        super(context);
        this.state=state;

    }

    public void changeText(Handler handler,String text){
        handler.post(() -> this.setText(text));
    }

    public String getState() {
        return state;
    }
}
