//package com.triple.triple.Presenter.Profile;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.NavUtils;
//import android.support.v4.content.IntentCompat;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.securepreferences.SecurePreferences;
//import com.triple.triple.Presenter.Account.LoginActivity;
//import com.triple.triple.Presenter.MainActivity;
//import com.triple.triple.R;
//
///**
// * Created by Kevin on 2018/2/10.
// */
//
//public class ProfileFragment extends FragmentActivity {
//
//    private static final String TAG = "ProfileActivity";
//    private static final int ACTIVITY_NUM = 3;
//    public static final String DEFAULT = "N/A";
//
//    private Context mcontext = ProfileFragment.this;
//
//    private Button bt_signin, bt_preference, bt_signout;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_profile, null);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        View v = getView();
//        bt_signin = (Button) v.findViewById(R.id.bt_signin);
//        bt_preference = (Button) v.findViewById(R.id.bt_preference);
//        bt_signout = (Button) v.findViewById(R.id.bt_signout);
//        SharedPreferences data = new SecurePreferences(getActivity(),"sessao");
//        String token = data.getString("token", DEFAULT);
//        if (token.equals(DEFAULT)) {
//            bt_preference.setVisibility(View.INVISIBLE);
//            bt_signout.setVisibility(View.INVISIBLE);
//            bt_signin.setVisibility(View.VISIBLE);
//        } else {
//            bt_preference.setVisibility(View.VISIBLE);
//            bt_signout.setVisibility(View.VISIBLE);
//            bt_signin.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public void onClick(View view) {
//        int id = view.getId();
//
//        switch (id) {
//            case R.id.bt_signout:
//                SharedPreferences data = new SecurePreferences(mcontext);
//                data.edit().clear().commit();
//                PackageManager packageManager = mcontext.getPackageManager();
//                Intent intent = packageManager.getLaunchIntentForPackage(mcontext.getPackageName());
//                ComponentName componentName = intent.getComponent();
//                Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
//                mcontext.startActivity(mainIntent);
//                System.exit(0);
//                break;
//            case R.id.bt_preference:
//                Intent iTravelStyleActivity = new Intent(mcontext, TravelStyleActivity.class);
//                startActivity(iTravelStyleActivity);
//                break;
//            case R.id.bt_signin:
//                Intent iLoginActivity = new Intent(mcontext, LoginActivity.class);
//                startActivity(iLoginActivity);
//                break;
//        }
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(getActivity());
//                MainActivity.openDrawer();
//                break;
//        }
//        return true;
//    }
//}
