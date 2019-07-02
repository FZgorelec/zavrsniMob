package com.zgorelec.filip.zavrsni;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

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
        //postAlgorithmSetup();
    }

    private void setEmptyMap() {
        gui.getBoardManager().emptyBoard();
    }

    private void resetBoard() {
        gui.getBoardManager().fillMap(boardStateCopy);
    }

    private void postAlgorithmSetup() {
        enableButtons();
        gui.getMenuManager().getMenu().getCalculationButton().changeText(handler, "Calculate");
        resetBoard();

    }

    private void enableButtons() {
        gui.getBoardManager().getGameBoard().enableGameBoard();
        gui.getMenuManager().getMenu().enableManagingMenu();
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
        GUIUtils.setupDialog(dialog, layoutParams);
        dialog.show();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.result_dialog);
        dialog.setTitle("Game results");

        TextView resultTV = dialog.findViewById(R.id.resultTV);
        Button returnBtn = (Button) dialog.findViewById(R.id.returnBtn);
        Button seeResultBtn = (Button) dialog.findViewById(R.id.seeResultBtn);
        Button saveResultBtn=dialog.findViewById(R.id.sendResultBtn);
        Button compressedResultBtn=dialog.findViewById(R.id.seeCompressedResultBtn);
        resultTV.setText("The time it took for the ant to calculate the solution is " + result.getTime() / 1000 + " seconds and it found " + (int) (result.getFitness() + 1) + " pieces of food");
        dialog.setOnCancelListener(dialogInterface -> postAlgorithmSetup());
        seeResultBtn.setOnClickListener((view -> {
            preAlgorithmSetup();
            gui.getMenuManager().getMenu().getCalculationButton().setText("Cancel");
            gui.getMenuManager().getMenu().getCalculationButton().setEnabled(true);
            gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener((v) -> {
                handler.removeCallbacksAndMessages(null);
                resetBoard();
                showDialog();
            });
            Thread animation = new Thread(() -> gui.getBoardManager().moveAnt(result.getMoves(), handler, () -> {
                resetBoard();
                showDialog();
            }));
            animation.start();
            dialog.dismiss();
        }));

        compressedResultBtn.setOnClickListener(view -> {
            gui.getBoardManager().moveAnt(algorithm.getCompressedResult(), handler, () -> {
                resetBoard();
                showDialog();
            });
            dialog.dismiss();
        });

        saveResultBtn.setOnClickListener(view -> {
            ResultSavingDialog resultSavingDialog=new ResultSavingDialog(context,gui.getBoardManager(),getResultRepresentation());
            resultSavingDialog.show();
        });

        returnBtn.setOnClickListener((v) -> {
            dialog.dismiss();
            gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener(view -> {
                if (gui.getBoardManager().getNumberOfFood() != 15) showWarningDialog();
                else {
                    ((MenuButton) view).setText("Calculating..");
                    handler.postDelayed(() -> run(), 200);
                }
            });
            postAlgorithmSetup();
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(dialog, layoutParams);
        dialog.show();
    }

    private String getResultRepresentation(){
        StringBuilder sb=new StringBuilder();
        sb.append("For map:\n");
        String[][] map=gui.getBoardManager().boardState();
        for (String[] row:map) {
            sb.append("||");
            for (String cell:row) {
                sb.append(cell+" ");
            }
            sb.append("||\n");
        }
        sb.append("\n"+result.toString());
        return sb.toString();
    }
}

