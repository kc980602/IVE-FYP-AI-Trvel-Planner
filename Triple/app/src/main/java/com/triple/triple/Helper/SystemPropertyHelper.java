package com.triple.triple.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Model.City;
import com.triple.triple.Model.SystemProperty;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.triple.triple.Sync.GetSystemProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin on 2018/3/7.
 */

public class SystemPropertyHelper {
    public static final String DEFAULT = "N/A";
    static ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    public static void refreshData(Context mcontext) {
        try {
            final Context context = mcontext;
            Call<SystemProperty> call = apiService.getProperty();
            call.enqueue(new Callback<SystemProperty>() {
                @Override
                public void onResponse(Call<SystemProperty> call, Response<SystemProperty> response) {
                    SystemProperty sp = response.body();
                    Gson gson = new Gson();
                    SharedPreferences data = new SecurePreferences(context);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("systemProperty", gson.toJson(sp));
                    editor.commit();
                }

                @Override
                public void onFailure(Call<SystemProperty> call, Throwable t) {

                }
            });

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
            Log.e("getSystemProperty", e.toString());
            e.printStackTrace();
            return null;
        }

    }


    public static ArrayList<String> getSystemPropertyCityName(Context mcontext) {
        ArrayList<String> cityName = new ArrayList<String>();
        List<City> cities = SystemPropertyHelper.getSystemProperty(mcontext).getCity();
        Iterator<City> itrTemp = cities.iterator();
        while(itrTemp.hasNext()){
            City city = itrTemp.next();
            String strTemp = city.getName() + ", " + city.getCountry();
            cityName.add(strTemp);
        }
        return cityName;
    }

    public static City getSystemPropertySearchCity(Context mcontext, String name) {
        List<City> cities = getSystemProperty(mcontext).getCity();
        for (City city : cities) {
            String location = city.getName() + ", " + city.getCountry();
            if (location.equals(name)) {
                return city;
            }
        }
        return null;
    }

}
