package com.zgorelec.filip.zavrsni;

import android.content.Context;

public class Game {
    private Context context;
    private GUI gui;
    private String[][] boardStateCopy;
    private GeneticProgrammingAlgorithm algorithm=new GeneticProgrammingAlgorithm();
    public Game(Context context){
        this.context=context;
        gui=new GUI(context);
        initGame();
    }
    public void initGame(){
        gui.init();
        gui.getMenuManager().getMenu().getCalculationButton().setOnClickListener((v)->setEmptyMap());
    }
    public void run(){
        boardStateCopy=gui.getBoardManager().boardState();
        preAlgorithmSetup();
        algorithm.run(boardStateCopy);
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
}
