package com.example.demo.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TRContants {

    public static final String TR = "TR";

    public static final Map<String,String> mapOfTrStates = new HashMap<>(){
        {
            put("BYKZ","BEYKOZ");
            put("BYCKMC","BUYUKCEKMECE");
            put("KVC","KAVACIK");
        }
    };


    public static final List<String> listOfTrStatesCode = new ArrayList<>(mapOfTrStates.keySet());
    public static final List<String> listOfTrStatesName = new ArrayList<>(mapOfTrStates.values());
}
























