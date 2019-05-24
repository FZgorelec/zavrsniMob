package com.zgorelec.filip.zavrsni;

import android.widget.Button;

public class MenuManager {

    private ManagingMenu menu;
    private Button activeButton;
    private String activeState="openField";

    public MenuManager(ManagingMenu menu){
        this.menu=menu;
        activeButton=menu.getBoardManagingButtons().get("openField");
        setListeners();
    }

    private void setListeners(){
        for (MenuButton ib :menu.getBoardManagingButtons().values())
            ib.setOnClickListener((v) -> {
                activeState=((MenuButton)v).getState();
                System.out.println(activeState);
                menu.updateBoardManagmentButtons((MenuButton)v);
            });
    }

    public void incrementButtonValue(String state){
        ((MenuButtonCounting)menu.getBoardManagingButtons().get(state)).incrementCount();
    }

    public void resetButtonCounters(){
        ((MenuButtonCounting)menu.getBoardManagingButtons().get("food")).setCount(15);
        ((MenuButtonCounting)menu.getBoardManagingButtons().get("bomb")).setCount(3);
    }

    public void decrementButtonValue(String state){
        ((MenuButtonCounting)menu.getBoardManagingButtons().get(state)).decrementCount();
    }

    public ManagingMenu getMenu() {
        return menu;
    }

    public String getActiveState() {
        return activeState;
    }
}
