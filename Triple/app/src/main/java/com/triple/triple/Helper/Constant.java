package com.triple.triple.Helper;

import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Sync.ApiClient;

/**
 * Created by Kevin on 2018/3/25.
 */

public class Constant {
    public  static final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    public  static final String SharedPreferences = "DATA";
    public  static final String SP_SYSTEMPROPERTY= "systemProperty";
    public static final int IMAGE_X_WIDTH = 1024;
    public static final int IMAGE_X_HEIGHT = 768;
    public static final int IMAGE_S_WIDTH = 720;
    public static final int IMAGE_S_HEIGHT = 280;
}
