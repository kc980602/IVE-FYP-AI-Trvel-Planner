package com.triple.triple.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.triple.triple.R;

/**
 * Created by Kevin on 2018/1/20.
 */

public class HideKeyboardHelper {

    public static void hideKeyboard(@NonNull View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}