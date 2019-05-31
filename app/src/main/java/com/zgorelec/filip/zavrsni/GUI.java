package com.zgorelec.filip.zavrsni;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.view.WindowManager;
import android.widget.*;

import java.io.*;


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
        menuManager=new MenuManager(managingMenu,context);
        boardManager=new BoardManager(gameBoard, menuManager);
        menuManager.getMenu().getAboutButton().setOnClickListener(view -> showSettingsDialog());
    }

    private void showSettingsDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.settings_dialog);
        dialog.setTitle("Settings");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        GUIUtils.setupDialog(dialog,layoutParams);
        Button clearBtn=dialog.findViewById(R.id.clearMapBtn);
        clearBtn.setOnClickListener((v)->boardManager.emptyBoard());
        Button saveBtn=dialog.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener((v)->showSaveDialog());
        Button loadBtn=dialog.findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener((v)->showLoadDialog());
        Button aboutBtn=dialog.findViewById(R.id.aboutBtn);
        aboutBtn.setOnClickListener(view -> menuManager.showAboutDialog());
        dialog.show();
    }

    private void showSaveDialog() {
        SaveDialog dialog=new SaveDialog(context,boardManager);
        dialog.show();
    }

    private void showLoadDialog() {
        LoadDialog dialog=new LoadDialog(context,boardManager);
        dialog.show();
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }
}
