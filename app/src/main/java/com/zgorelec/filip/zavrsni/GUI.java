package com.zgorelec.filip.zavrsni;

import android.app.Activity;
import android.content.Context;

import android.widget.*;


public class GUI {
    private Context context;
    private BoardManager boardManager;
    public GUI(Context context) {
        this.context = context;
    }

    public void init() {
        ManagingMenu managingMenu=new ManagingMenu(context);
        GameBoard gameBoard=new GameBoard(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TableLayout table = new TableLayout(context);
        table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        int numberOfRows=9;
        TableRow[] tableRows=gameBoard.initGameBoard();
        for (int i = 0; i < numberOfRows; i++) {
            table.addView(tableRows[i]);
        }
        table.addView(managingMenu.initManagingTR());
        linearLayout.addView(table);
        ((Activity) context).setContentView(linearLayout);
        MenuManager menuManager=new MenuManager(managingMenu);
        boardManager=new BoardManager(gameBoard, menuManager);
        managingMenu.getCalculationButton().setOnClickListener((v)->((Activity) context).runOnUiThread(()->boardManager.moveAnt(new String[]{"moveForward","rotateRight","moveForward","rotateLeft","moveForward","moveForward","rotateRight","moveForward","moveForward","rotateLeft","moveForward","moveForward","rotateRight","rotateRight","moveForward",})));
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }
}
