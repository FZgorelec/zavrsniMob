package com.zgorelec.filip.zavrsni;


import android.os.Handler;
import java.util.HashMap;
import java.util.Map;

public class BoardManager {
    private GameBoard gameBoard;
    private MenuManager menuManager;
    private  Map<String, String> leftOrientations = new HashMap<>();
    private  Map<String, String> rightOrientations = new HashMap<>();
    private int numberOfFood=0;
    private int numberOfBombs=0;
    private int boardXDim;
    private int boardYDim;
    private int animationDelay=200;
    public BoardManager(GameBoard gameBoard, MenuManager manager) {
        this.gameBoard = gameBoard;
        this.menuManager = manager;
        fillMaps();
        setListeners();
        boardXDim=gameBoard.getXDim();
        boardYDim=gameBoard.getYDim();
    }

    private void fillMaps() {
        leftOrientations.put("antLeft", "antDown");
        leftOrientations.put("antDown", "antRight");
        leftOrientations.put("antRight", "antUp");
        leftOrientations.put("antUp", "antLeft");
        rightOrientations.put("antRight", "antDown");
        rightOrientations.put("antDown", "antLeft");
        rightOrientations.put("antLeft", "antUp");
        rightOrientations.put("antUp", "antRight");
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public String[][] boardState() {
        GameButton[][] gameBoardCells = gameBoard.getGameBoardCells();
        int gameBoardHeight = gameBoardCells.length;
        int gameBoardWidth = gameBoardCells[0].length;
        String[][] boardState = new String[gameBoardHeight][gameBoardWidth];
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                boardState[i][j] = gameBoardCells[i][j].getState();
            }
        }
        return boardState;
    }

    public void fillMap(String[][] map) {
        GameButton[][] gameBoardCells = gameBoard.getGameBoardCells();
        int gameBoardHeight = gameBoardCells.length;
        int gameBoardWidth = gameBoardCells[0].length;
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                gameBoardCells[i][j].setState(map[i][j]);
            }
        }
    }

    public void emptyBoard(){
        gameBoard.emptyBoard();
        resetCounters();
    }

    public void moveAnt(String[] moves){
        int antPositionX=0;
        int antPositionY=0;
        Handler handler=new Handler();
        for (int i = 0; i < moves.length; i++) {
            GameButton gameButtonAtCurrentPosition = gameBoard.getGameBoardCells()[antPositionY][antPositionX];
            String currentState=gameButtonAtCurrentPosition.getState();
            if(moves[i].equals("rotateLeft")){

                gameButtonAtCurrentPosition.setState(leftOrientations.get(gameButtonAtCurrentPosition.getState()),handler,(i+1)*animationDelay);
            }
            else if(moves[i].equals("rotateRight")){
                gameButtonAtCurrentPosition.setState(rightOrientations.get(gameButtonAtCurrentPosition.getState()),handler,(i+1)*animationDelay);
            }
            else if(moves[i].equals("moveForward")){

                gameButtonAtCurrentPosition.setState("openField",handler,(i+1)*animationDelay);
                switch (currentState){
                    case "antLeft":
                        if(!isWall(antPositionX-1, antPositionY))antPositionX=antPositionX-1;
                        break;
                    case "antRight":
                        if(!isWall(antPositionX+1, antPositionY))antPositionX=antPositionX+1;
                        break;
                    case "antUp":
                        if(!isWall(antPositionX, antPositionY-1))antPositionY=antPositionY-1;
                        break;
                    case "antDown":
                        if(!isWall(antPositionX, antPositionY+1))antPositionY=antPositionY+1;
                        break;
                }

                gameBoard.getGameBoardCells()[antPositionY][antPositionX].setState(currentState,handler,(i+1)*animationDelay);
            }
            else throw new UnsupportedOperationException();


//            if(isBomb(antPositionX,antPositionY)){
//                //todo gameover
//            }


        }
    }

    private boolean isBomb(int x, int y) {
        return false;
    }

    private boolean isWall(int x, int y){
        return (y<0||x<0||y>=boardYDim||x>=boardXDim||gameBoard.getGameBoardCells()[y][x].getState().equals("wall"));
    }
    private void setListeners() {
        GameButton[][] gameBoardCells = gameBoard.getGameBoardCells();
        for (int i = 0; i < gameBoard.getYDim(); i++) {
            for (int j = 0; j < gameBoard.getXDim(); j++) {
                if (i == 0 && j == 0) {
                    gameBoardCells[0][0].setState("antRight");
                    continue;
                }
                gameBoardCells[i][j].setOnClickListener(v -> {
                    String previousState=((GameButton) v).getState();
                    if(previousState.equals("food")&&!(menuManager.getActiveState().equals("bomb")&&numberOfBombs>=3)){
                        numberOfFood--;
                        menuManager.incrementButtonValue("food");

                    }
                    else if(previousState.equals("bomb")&&!(menuManager.getActiveState().equals("food")&&numberOfFood>=15)){
                        numberOfBombs--;
                        menuManager.incrementButtonValue("bomb");
                    }

                    if(menuManager.getActiveState().equals("food")&&numberOfFood<15){
                        numberOfFood++;
                        menuManager.decrementButtonValue("food");
                    }
                    else if(menuManager.getActiveState().equals("bomb")&&numberOfBombs<3){
                        numberOfBombs++;
                        menuManager.decrementButtonValue("bomb");
                    }
                    else if(menuManager.getActiveState().equals("food")&&numberOfFood>=15)return;

                    else if(menuManager.getActiveState().equals("bomb")&&numberOfBombs>=3)return;

                    ((GameButton) v).setState(menuManager.getActiveState());
                });
            }
        }
    }

    public void resetCounters(){
        menuManager.resetButtonCounters();
        numberOfBombs=0;
        numberOfFood=0;
    }
}
