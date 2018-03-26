package com.triple.triple.Presenter.Account;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.securepreferences.SecurePreferences;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;

import java.util.Calendar;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button bt_signin, bt_signout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        bt_signin = (Button) view.findViewById(R.id.bt_signin);
        bt_signout = (Button) view.findViewById(R.id.bt_signout);
        if (UserDataHelper.checkTokenExist(getContext())) {
            bt_signout.setVisibility(View.VISIBLE);
            bt_signin.setVisibility(View.GONE);
            bt_signout.setOnClickListener(this);
        } else {
            bt_signout.setVisibility(View.GONE);
            bt_signin.setVisibility(View.VISIBLE);
            bt_signin.setOnClickListener(this);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_signin:
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.bt_signout:
                UserDataHelper.removeAllData(getContext());
                getActivity().finish();
                break;
        }
    }
}
