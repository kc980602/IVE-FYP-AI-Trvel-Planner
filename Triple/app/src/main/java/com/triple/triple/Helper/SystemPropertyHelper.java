package com.triple.triple.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.R;
import com.triple.triple.Sync.GetSystemProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Kevin on 2018/3/7.
 */

public class SystemPropertyHelper {
    public static final String DEFAULT = "N/A";


    public static void refreshData(Context mcontext) {
        String respone;
        String url = mcontext.getString(R.string.api_prefix) + mcontext.getString(R.string.api_system_protery);
        try {
            respone = new GetSystemProperty().run(url);
            SharedPreferences data = new SecurePreferences(mcontext);
            SharedPreferences.Editor editor = data.edit();
            editor.putString("systemProperty", respone.toString());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SystemProperty getSystemProperty(Context mcontext) {
        SharedPreferences data = new SecurePreferences(mcontext);
        String json = data.getString("systemProperty", DEFAULT);
        try {
            JSONObject jsonObject = new JSONObject(json);
            Type type = new TypeToken<SystemProperty>() {
            }.getType();
            Gson gson = new Gson();
            SystemProperty systemProperty = (SystemProperty) gson.fromJson(jsonObject.toString(), type);
            return systemProperty;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
