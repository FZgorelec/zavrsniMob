package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
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
    private Handler handler;

    public Game(Context context) {
        this.context = context;
        gui = new GUI(context);
        algorithm = new SolutionAlgorithm();
        handler = new Handler();
        initGame();
    }

    public void initGame() {
        gui.init();
        gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener((v) -> {
            if (gui.getBoardManager().getNumberOfFood() != 15) showWarningDialog();
            else {
                ((MenuButton) v).setText("Calculating..");
                handler.postDelayed(() -> run(), 200);
            }
        });
    }

    public void run() {
        boardStateCopy = gui.getBoardManager().boardState();
        preAlgorithmSetup();
        Thread thread = new Thread(() -> result = algorithm.run(boardStateCopy));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showDialog();
        postAlgorithmSetup();
    }

    private void setEmptyMap() {
        gui.getBoardManager().emptyBoard();
    }

    private void resetBoard() {
        gui.getBoardManager().fillMap(boardStateCopy);
    }

    private void postAlgorithmSetup() {

        gui.getBoardManager().getGameBoard().enableGameBoard();
        gui.getMenuManager().getMenu().enableManagingMenu();
        gui.getMenuManager().getMenu().getCalculationButton().changeText(handler, "Calculate");
        //todo obaviti sa handlerom

    }

    private void preAlgorithmSetup() {

        gui.getBoardManager().getGameBoard().disableGameBoard();
        gui.getMenuManager().getMenu().disableManagingMenu();

    }

    private void showWarningDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.text_ok_dialog);
        dialog.setTitle("Not enough food on map");
        TextView warningTV = dialog.findViewById(R.id.text_ok_TV);
        warningTV.setText(R.string.food_warning);
        Button warningBtn = dialog.findViewById(R.id.text_ok_btn);
        warningBtn.setOnClickListener((v) -> dialog.dismiss());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = GUIUtils.displayWidth;
        layoutParams.height = GUIUtils.displayHeight;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.result_dialog);
        dialog.setTitle("Game results");

        TextView resultTV = dialog.findViewById(R.id.resultTV);
        CheckBox resetCB = dialog.findViewById(R.id.resetMapCB);
        Button returnBtn = (Button) dialog.findViewById(R.id.returnBtn);
        Button seeResultBtn = (Button) dialog.findViewById(R.id.seeResultBtn);
        resultTV.setText("The time it took for the ant to calculate the solution is " + result.getTime()/1000 + " seconds and it found " + (int) (result.getFitness() + 1) + " pieces of food");
        seeResultBtn.setOnClickListener((view -> {
            gui.getBoardManager().moveAnt(result.getMoves(), () -> {
                if (resetCB.isChecked()) {
                    setEmptyMap();
                } else {
                    resetBoard();
                }
                dialog.dismiss();

            });
            dialog.setOnDismissListener((v) -> {
            });
            dialog.dismiss();
        }));

        returnBtn.setOnClickListener((v) -> {
            if (resetCB.isChecked()) {
                setEmptyMap();
            } else {
                resetBoard();
            }
            dialog.dismiss();

        });
        dialog.setOnDismissListener((v) -> {
            if (resetCB.isChecked()) {
                setEmptyMap();
            } else {
                resetBoard();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = GUIUtils.displayWidth;
        layoutParams.height = GUIUtils.displayHeight;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }
}
