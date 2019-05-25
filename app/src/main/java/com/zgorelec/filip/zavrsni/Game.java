package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Game {
    private Context context;
    private GUI gui;
    private String[][] boardStateCopy;
    private SolutionAlgorithm algorithm;
    private AlgorithmResult result;
    public Game(Context context){
        this.context=context;
        gui=new GUI(context);
        algorithm=new SolutionAlgorithm();
        initGame();
    }
    public void initGame(){
        gui.init();
        gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener((v)->run());
    }
    public void run(){
        boardStateCopy=gui.getBoardManager().boardState();
        preAlgorithmSetup();
        result=algorithm.run(boardStateCopy);
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
        //todo obaviti sa handlerom
        gui.getMenuManager().getMenu().getCalculationButton().setText("Calculate");
    }

    private void preAlgorithmSetup() {
        gui.getBoardManager().getGameBoard().disableGameBoard();
        gui.getMenuManager().getMenu().disableManagingMenu();
        gui.getMenuManager().getMenu().getCalculationButton().setText("Calculating..");
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.result_dialog);
        dialog.setTitle("About the game");

        TextView resultTV=dialog.findViewById(R.id.resultTV);
        CheckBox resetCB = dialog.findViewById(R.id.resetMapCB);
        Button returnBtn = (Button) dialog.findViewById(R.id.returnBtn);
        Button seeResultBtn = (Button) dialog.findViewById(R.id.seeResultBtn);
        resultTV.setText("Vrijeme potrebno labirintu da pronade rjesenje je "+result.getTime()+" milisekundi i pronasao "+(int)(result.getFitness()+1)+" komada hrane");
        seeResultBtn.setOnClickListener((view -> {
            gui.getBoardManager().moveAnt(result.getMoves(),() -> {
                if (resetCB.isChecked()) {
                    setEmptyMap();
                }
                else{
                    resetBoard();
                }
                dialog.dismiss();

            });
            dialog.setOnDismissListener((v)->{});
            dialog.dismiss();
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
