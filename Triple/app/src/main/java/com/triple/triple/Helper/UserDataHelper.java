package com.triple.triple.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Model.User;

import java.lang.reflect.Type;

/**
 * Created by KC on 2/28/2018.
 */

public class UserDataHelper {

    public static final String DEFAULT = "N/A";

    public static User getUserInfo(Context mcontext) {
        User user;
        Type type = new TypeToken<User>() {
        }.getType();
        Gson gson = new Gson();
        SharedPreferences data = new SecurePreferences(mcontext);
        String jsonUser = data.getString("userInfo", DEFAULT);
        if (jsonUser.equals(DEFAULT)) {
            user = new User();
        } else {
            user = (User) gson.fromJson(jsonUser.toString(), type);
        }

        return user;
    }

    public static String getToken(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        return data.getString("token", DEFAULT);
    }

    public static Boolean checkTokenExist(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        String token = data.getString("token", DEFAULT);
        return !token.equals(DEFAULT);
    }

    public static void removeAllData(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        data.edit().clear().commit();
    }

}
