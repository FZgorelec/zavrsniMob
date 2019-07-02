package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MenuManager {

    private ManagingMenu menu;
    private Button activeButton;
    private String activeState = "openField";
    private Context context;

    public MenuManager(ManagingMenu menu, Context coontext) {
        this.menu = menu;
        activeButton = menu.getBoardManagingButtons().get("openField");
        setListeners();
    }

    private void setListeners() {
        for (MenuButton ib : menu.getBoardManagingButtons().values())
            ib.setOnClickListener((v) -> {
                activeState = ((MenuButton) v).getState();
                menu.updateBoardManagmentButtons((MenuButton) v);
            });


    }


    public void showAboutDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.text_ok_dialog);
        dialog.setTitle("About the game");
        TextView text_ok_TV = dialog.findViewById(R.id.text_ok_TV);
        text_ok_TV.setText(R.string.game_explanation);
        Button text_ok_btn = dialog.findViewById(R.id.text_ok_btn);
        text_ok_btn.setOnClickListener((v) -> dialog.dismiss());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(dialog, layoutParams);
        dialog.show();

    }

    public void incrementButtonValue(String state) {
        ((MenuButtonCounting) menu.getBoardManagingButtons().get(state)).incrementCount();
    }

    public void resetButtonCounters() {
        setCounters(15, 3);
    }

    public void setCounters(int foodCount, int bombCount) {
        ((MenuButtonCounting) menu.getBoardManagingButtons().get("food")).setCount(foodCount);
        ((MenuButtonCounting) menu.getBoardManagingButtons().get("bomb")).setCount(bombCount);
    }

    public void decrementButtonValue(String state) {
        ((MenuButtonCounting) menu.getBoardManagingButtons().get(state)).decrementCount();
    }

    public ManagingMenu getMenu() {
        return menu;
    }

    public String getActiveState() {
        return activeState;
    }
}
