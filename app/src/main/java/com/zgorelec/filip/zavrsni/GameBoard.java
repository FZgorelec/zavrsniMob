package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.widget.TableRow;

public class GameBoard {
    private GameButton[][] gameBoardCells = new GameButton[9][20];
    private Context context;

    public GameBoard(Context context) {
        this.context=context;
    }

    public TableRow[] initGameBoard() {
        int numberOfButtons=20;
        int numberOfRows=9;
        TableRow[] tableRows = new TableRow[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            tableRows[i] = new TableRow(context);
            tableRows[i].setLayoutParams(GUIUtils.rowParameters);
            for (int j = 0; j < numberOfButtons; j++) {
                GameButton gameButton=new GameButton(context, "openField");
                GUIUtils.buttonSetup(gameButton);
                gameBoardCells[i][j]=gameButton;
                tableRows[i].addView(gameButton);
            }
        }
        return tableRows;
    }

    public GameButton[][] getGameBoardCells() {
        return gameBoardCells;
    }
    public int getXDim(){
        return 20;
    }
    public int getYDim(){
        return 9;
    }
}

