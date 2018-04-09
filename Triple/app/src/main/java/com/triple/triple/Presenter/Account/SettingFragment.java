package com.triple.triple.Presenter.Account;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button bt_signin, bt_signout, bt_edit_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setHasOptionsMenu(true);
        bt_signin = (Button) view.findViewById(R.id.bt_signin);
        bt_signout = (Button) view.findViewById(R.id.bt_signout);
        bt_edit_profile = (Button) view.findViewById(R.id.bt_edit_profile);
        if (UserDataHelper.checkTokenExist(getContext())) {
            bt_edit_profile.setVisibility(View.VISIBLE);
            bt_signout.setVisibility(View.VISIBLE);
            bt_signin.setVisibility(View.GONE);
            bt_signout.setOnClickListener(this);
            bt_edit_profile.setOnClickListener(this);
        } else {
            bt_edit_profile.setVisibility(View.GONE);
            bt_signout.setVisibility(View.GONE);
            bt_signin.setVisibility(View.VISIBLE);
            bt_signin.setOnClickListener(this);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.bt_signin:
                i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.bt_signout:
                UserDataHelper.removeAllData(getContext());
                getActivity().finish();
                break;
            case R.id.bt_edit_profile:
                i = new Intent(getContext(), EditProfileActivity.class);
                startActivity(i);
                break;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_plain, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
