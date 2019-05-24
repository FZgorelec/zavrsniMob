package com.zgorelec.filip.zavrsni;

import android.app.Activity;
import android.content.Context;

import android.widget.*;


public class GUI {
    private Context context;
    private BoardManager boardManager;
    private MenuManager menuManager;
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
        menuManager=new MenuManager(managingMenu);
        boardManager=new BoardManager(gameBoard, menuManager);
    }
    public MenuManager getMenuManager() {
        return menuManager;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }
}
