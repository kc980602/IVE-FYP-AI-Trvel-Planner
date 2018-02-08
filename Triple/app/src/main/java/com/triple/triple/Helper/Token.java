package com.triple.triple.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

/**
 * Created by Kevin on 2018/1/27.
 */

public class Token {
    public static final String DEFAULT = "N/A";

    public static String getToken(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        return data.getString("token", DEFAULT);
    }

    public static Boolean checkTokenExist(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        String token = data.getString("token", DEFAULT);
        return !token.equals(DEFAULT);
    }

}
