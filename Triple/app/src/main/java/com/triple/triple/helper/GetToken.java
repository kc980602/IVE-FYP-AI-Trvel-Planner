package com.triple.triple.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin on 2018/1/27.
 */

public class GetToken {
    public static final String DEFAULT = "N/A";

    public static String getToken(Context mcontext) {
        SharedPreferences data = mcontext.getSharedPreferences("user", Context.MODE_PRIVATE);
        return data.getString("token", DEFAULT);
    }

}
