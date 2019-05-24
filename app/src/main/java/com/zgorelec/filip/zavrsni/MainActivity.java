package com.zgorelec.filip.zavrsni;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;


public class MainActivity extends AppCompatActivity {
    GUI gui;
    public static final String MY_PREFS = "Preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GUIUtils.displayWidth = (int) (displayMetrics.widthPixels * 0.8f);
        GUIUtils.displayHeight = (int) (displayMetrics.heightPixels * 0.8f);

        Game game=new Game(this);
        game.initGame();
        if (!prefs.getBoolean("neverShowAgain", false)) showDialog(editor);

    }


    private void showDialog(SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.starting_popup_dialog);
        dialog.setTitle("About the game");

        CheckBox neverShowAgainCB = dialog.findViewById(R.id.starting_dialog_checkBox);
        Button dialogButton = (Button) dialog.findViewById(R.id.starting_dialog_button);
        dialogButton.setOnClickListener((v) -> {
            if (neverShowAgainCB.isChecked()) {
                editor.putBoolean("neverShowAgain", true);
                editor.apply();
            }
            dialog.dismiss();

        });


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = GUIUtils.displayWidth;
        layoutParams.height = GUIUtils.displayHeight;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

}