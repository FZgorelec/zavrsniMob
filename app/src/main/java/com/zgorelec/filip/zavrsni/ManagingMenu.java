package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TableRow;

import java.util.HashMap;
import java.util.Map;

public class ManagingMenu {

    private Context context;
    private MenuButton activeButton;
    private MenuButton aboutButton;
    private Map<String, MenuButton> boardManagingButtons = new HashMap<>();
    private MenuButton calculationButton;
    private TableRow managingTR;
    public ManagingMenu(Context context){
        this.context=context;
    }

    public TableRow initManagingTR() {
        managingTR = new TableRow(context);
        GUIUtils.rowParameters.weight=10;
        managingTR.setLayoutParams(GUIUtils.rowParameters);
        boardManagingButtons = new HashMap<>();
        boardManagingButtons.put("openField", new MenuButtonCounting(context,"openField"));
        boardManagingButtons.put("wall", new MenuButton(context,"wall"));
        boardManagingButtons.put("food", new MenuButtonCounting(context,"food",15));
        boardManagingButtons.put("bomb", new MenuButtonCounting(context,"bomb",3));
        boardManagingButtons.get("openField").setText("Open Field");
        boardManagingButtons.get("wall").setText("Wall");
        calculationButton=new MenuButton(context,"calculate");
        aboutButton=new MenuButton(context,"about");
        activeButton= boardManagingButtons.get("openField");
        for (MenuButton button : boardManagingButtons.values()) {
            GUIUtils.buttonSetup(button);
            button.setBackgroundResource(R.drawable.menu_button_background);
            button.setTypeface(null, Typeface.NORMAL);
            managingTR.addView(button);
        }
        boardManagingButtons.get("openField").setTypeface(null, Typeface.BOLD);
        GUIUtils.buttonSetup(calculationButton);
        GUIUtils.buttonSetup(aboutButton);
        calculationButton.setBackgroundResource(R.drawable.menu_button_background);
        aboutButton.setBackgroundResource(R.drawable.menu_button_background);
        calculationButton.setText("Calculate");
        aboutButton.setText("Settings");
        calculationButton.setTypeface(null, Typeface.NORMAL);
        aboutButton.setTypeface(null, Typeface.NORMAL);

        managingTR.addView(calculationButton);
        managingTR.addView(aboutButton);
        return managingTR;
    }

    public MenuButton getAboutButton() {
        return aboutButton;
    }

    public MenuButton getCalculationButton() {
        return calculationButton;
    }

    public Button getActiveButton() {
        return activeButton;
    }

    public TableRow getManagingTR() {
        return managingTR;
    }

    public Map<String, MenuButton> getBoardManagingButtons() {
        return boardManagingButtons;
    }

    public void disableManagingMenu(){
        changeStateOfButtons(false);
    }

    public void enableManagingMenu(){
        changeStateOfButtons(true);
    }
    private void changeStateOfButtons(boolean state){
        for (MenuButton button:boardManagingButtons.values()) {
            button.setEnabled(state);
        }
        calculationButton.setEnabled(state);
        aboutButton.setEnabled(state);
    }

    public void updateBoardManagmentButtons(Button button) {
        for (Button managmentButton:boardManagingButtons.values()) {
            managmentButton.setTypeface(null, Typeface.NORMAL);
        }
        button.setTypeface(null, Typeface.BOLD);
    }
}
