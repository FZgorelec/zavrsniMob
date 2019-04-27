package com.zgorelec.filip.zavrsni;

import android.view.View;
import android.widget.*;

import java.util.HashMap;
import java.util.Map;

public class GUIUtils {
    static TableRow.LayoutParams imageButtonParameters = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);
    static TableRow.LayoutParams buttonParameters = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);
    static TableLayout.LayoutParams rowParameters = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 0, 1);


    {
        imageButtonParameters.weight = 1;
        imageButtonParameters.setMargins(0, 0, 0, 0);
        buttonParameters.weight = 1;
        buttonParameters.setMargins(0, 0, 0, 0);
        rowParameters.weight = 1;
        rowParameters.setMargins(0, 0, 0, 0);



    }

    public static TableRow.LayoutParams getImageButtonParameters() {
        return imageButtonParameters;
    }

    public static TableRow.LayoutParams getButtonParameters() {
        return buttonParameters;
    }

    public static TableLayout.LayoutParams getRowParameters() {
        return rowParameters;
    }



    static void buttonSetup(ImageButton button) {
        button.setLayoutParams(imageButtonParameters);
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setAdjustViewBounds(true);
        button.setPadding(0, 0, 0, 0);
        button.setVisibility(View.VISIBLE);
    }

    static void buttonSetup(MenuButton button) {
        button.setLayoutParams(buttonParameters);
        button.setTextSize(18);
        button.setTextColor(0xab100707);
        button.setPadding(-4, -4, -4, -4);
        button.setVisibility(View.VISIBLE);
    }

}
