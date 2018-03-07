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

    public static void refreshData(Context mcontext) {
        String respone;
        String url = mcontext.getString(R.string.api_prefix) + mcontext.getString(R.string.api_system_protery);
        try {
            respone = new GetSystemProperty().run(url);
        } catch (Exception e) {
            respone = null;
        }
        try {
            JSONObject jsonObject = new JSONObject(respone);
            Type type = new TypeToken<SystemProperty>() {
            }.getType();
            Gson gson = new Gson();
            SystemProperty systemProperty = (SystemProperty) gson.fromJson(jsonObject.toString(), type);
            Log.d("SystemPropertyHelper", systemProperty.toString());
        } catch (JSONException e) {

        }

//        SharedPreferences data = new SecurePreferences(mcontext);
//        SharedPreferences.Editor editor = data.edit();
//
//        if (jsonTripList == null || jsonTripList.equals("[]")) {
//            savedTrips =  new ArrayList<>();
//            savedTrips.add(trip);
//        } else {
//            savedTrips = (List<Trip>) gson.fromJson(jsonTripList.toString(), type);
//            Boolean isFound = false;
//            for(Trip tmpTrip: savedTrips){
//                if(tmpTrip.getId() == trip.getId()){
//                    savedTrips.remove(tmpTrip);
//                    savedTrips.add(trip);
//                    isFound = true;
//                    break;
//                }
//            }
//            if (!isFound) {
//                savedTrips.add(trip);
//            }
//        }
//        String json = gson.toJson(savedTrips);
//        editor.putString("savedTrips", json);
//        editor.commit();
    }

}
