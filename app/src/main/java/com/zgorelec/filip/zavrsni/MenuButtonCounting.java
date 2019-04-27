package com.zgorelec.filip.zavrsni;

import android.content.Context;

public class MenuButtonCounting extends MenuButton {
    private int count;

    public MenuButtonCounting(Context context, String state){
        super(context,state);
    }
    public MenuButtonCounting(Context context, String state,int count){
        super(context,state);
        this.count=count;
        updateCount();
    }

    public int getCount() {
        return count;
    }

    public void incrementCount(){
        count++;
        updateCount();
    }

    public void decrementCount(){
        count--;
        updateCount();
    }
    private void updateCount(){
        this.setText(state +": " +count);
    }
}
