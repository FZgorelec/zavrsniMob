package com.zgorelec.filip.zavrsni;

import android.content.Context;
import android.os.Handler;

public class GameButton extends android.support.v7.widget.AppCompatImageButton {
    private String state;
    private StateToImageMap stateToImageMap = StateToImageMap.getInstance();

    public GameButton(Context context, String state) {
        super(context);
        this.state = state;
        setState(state);
    }

    public void setState(String state, Handler handler, int delay) {
        final Integer imageResource = stateToImageMap.getStateToImageMap().get(state);
        if (imageResource == null) {
            throw new IllegalArgumentException();
        } else {
            this.state = state;
            handler.postDelayed(() -> setBackgroundResource(imageResource), delay);
        }
    }

    public void setState(String state) {
        Integer imageResource = stateToImageMap.getStateToImageMap().get(state);
        if (imageResource == null) {
            throw new IllegalArgumentException();
        } else {
            this.state = state;
            setBackgroundResource(imageResource);

        }
    }

    public String getState() {
        return state;
    }
}
