package com.zgorelec.filip.zavrsni;

import java.util.HashMap;
import java.util.Map;

public class StateToImageMap {
        private static Map<String,Integer> stateToImageMap;
        private static final StateToImageMap SINGLE_INSTANCE = new StateToImageMap();
        private StateToImageMap() {
            stateToImageMap=new HashMap<>();
            stateToImageMap.put("antUp", R.drawable.game_ant_up72);
            stateToImageMap.put("antDown", R.drawable.game_ant_down72);
            stateToImageMap.put("antRight", R.drawable.game_ant_right72);
            stateToImageMap.put("antLeft", R.drawable.game_ant_left72);
            stateToImageMap.put("bomb", R.drawable.gamebomb72);
            stateToImageMap.put("food", R.drawable.game_food72);
            stateToImageMap.put("openField", R.drawable.gameopenfield72);
            stateToImageMap.put("wall", R.drawable.gamewall72);
        }
        public static StateToImageMap getInstance() {
            return SINGLE_INSTANCE;
        }

    public static Map<String, Integer> getStateToImageMap() {
        return stateToImageMap;
    }
}
