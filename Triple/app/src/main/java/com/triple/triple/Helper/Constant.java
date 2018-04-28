package com.triple.triple.Helper;

import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import java.util.Random;

/**
 * Created by Kevin on 2018/3/25.
 */

public class Constant {
    public static final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    public static final String SharedPreferences = "DATA";
    public static final String SP_SYSTEMPROPERTY = "systemProperty";
    public static final int IMAGE_X_WIDTH = 1920;
    public static final int IMAGE_X_HEIGHT = 1080;
    public static final int IMAGE_M_WIDTH = 1024;
    public static final int IMAGE_M_HEIGHT = 768;
    public static final int IMAGE_S_WIDTH = 720;
    public static final int IMAGE_S_HEIGHT = 280;
    public static final int[] M600COLOR = {
            R.color.m300_red,
            R.color.m300_pink,
            R.color.m300_purple,
            R.color.m300_deep_purple,
            R.color.m300_indigo,
            R.color.m300_blue,
            R.color.m300_light_blue,
            R.color.m300_cyan,
            R.color.m300_teal,
            R.color.m300_green,
            R.color.m300_light_green,
            R.color.m300_lime,
            R.color.m300_yellow,
            R.color.m300_amber,
            R.color.m300_orange,
            R.color.m300_deep_orange,
            R.color.m300_grey,
            R.color.m300_blue_brown,
            R.color.m300_brown};

    public static final int GETCOLOR() {
        int rnd = new Random().nextInt(M600COLOR.length);
        return M600COLOR[rnd];
    }




}
