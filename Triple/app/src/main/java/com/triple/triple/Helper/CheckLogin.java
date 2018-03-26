package com.triple.triple.Helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.triple.triple.Presenter.Account.LoginActivity;

/**
 * Created by Kevin on 2018/2/6.
 */

public class CheckLogin {

    public static Boolean directLogin(Context context) {
        if (!UserDataHelper.checkTokenExist(context)) {
            Log.d("sss", "sss`");
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
