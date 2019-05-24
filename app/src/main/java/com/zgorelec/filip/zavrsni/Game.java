package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

public class Game {
    private Context context;
    private GUI gui;
    private String[][] boardStateCopy;
    private GeneticProgrammingAlgorithm algorithm=new GeneticProgrammingAlgorithm();
    private String[] antMoves;
    public Game(Context context){
        this.context=context;
        gui=new GUI(context);
        initGame();
    }
    public void initGame(){
        gui.init();
        gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener((v)->run());
    }
    public void run(){
        boardStateCopy=gui.getBoardManager().boardState();
        preAlgorithmSetup();
        antMoves=algorithm.run(boardStateCopy);
        showDialog();
        postAlgorithmSetup();
    }

    private void setEmptyMap(){
        gui.getBoardManager().emptyBoard();
    }
    private void resetBoard(){
        gui.getBoardManager().fillMap(boardStateCopy);
    }

    private void postAlgorithmSetup() {
        gui.getBoardManager().getGameBoard().enableGameBoard();
        gui.getMenuManager().getMenu().enableManagingMenu();
    }

    private void preAlgorithmSetup() {
        gui.getBoardManager().getGameBoard().disableGameBoard();
        gui.getMenuManager().getMenu().disableManagingMenu();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.result_dialog);
        dialog.setTitle("About the game");

        CheckBox resetCB = dialog.findViewById(R.id.resetMapCB);
        Button returnBtn = (Button) dialog.findViewById(R.id.returnBtn);
        Button seeResultBtn = (Button) dialog.findViewById(R.id.returnBtn);

        seeResultBtn.setOnClickListener((view -> {
            gui.getBoardManager().moveAnt(antMoves);
        }));

        returnBtn.setOnClickListener((v) -> {
            if (resetCB.isChecked()) {
                setEmptyMap();
            }
            else{
                resetBoard();
            }
            dialog.dismiss();

        });
        dialog.setOnDismissListener((v) -> {
            if (resetCB.isChecked()) {
                setEmptyMap();
            }
            else{
                resetBoard();
            }});

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = GUIUtils.displayWidth;
        layoutParams.height = GUIUtils.displayHeight;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }
}
