package com.xxf.json.gson.booster.kaptdemo.data2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                .registerTypeAdapter(Integer.class,new  IntegerTypeAdapter())
                .create();
    }
}
