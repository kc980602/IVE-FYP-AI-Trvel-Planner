package com.triple.triple.Presenter.Registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.triple.triple.Adapter.PreferenceAdapter;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.SpacesItemDecoration;
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Interface.DataManager;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictionFragment extends Fragment implements BlockingStep {
    DataManager dataManager;
    AlertDialog dialog;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private RecyclerView rv_preference;

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) dialog.dismiss();
    }

    @Override
    public void onSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_loader);
        builder.setCancelable(false);
        dialog = builder.show();
        Call<List<KeyValue>> call = Constant.apiService.getPreferences("Bearer " + UserDataHelper.getToken(getContext()));
        call.enqueue(new Callback<List<KeyValue>>() {
            @Override
            public void onResponse(Call<List<KeyValue>> call, Response<List<KeyValue>> response) {
                List<KeyValue> predicts = response.body();
                Context context = getContext();

                if (predicts.size() != 0) {
                    List<KeyValue> keyValues = new ArrayList<>();
                    for (int i = 0; i <= 3; i++) {
                        KeyValue keyValue = new KeyValue();

                        String predictKey = predicts.get(i).getKey();

                        String key = predictKey.toLowerCase().replace('_', ' ');
                        String titile = key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();
                        keyValue.setKey(titile);

                        keyValue.setValue("preference_" + predictKey.toLowerCase());
                        if (predictKey.equals("60+_TRAVELLER")) {
                            keyValue.setValue("preference_60_traveller");
                        }
                        keyValues.add(keyValue);
                    }

                    PreferenceAdapter preferenceAdapter = new PreferenceAdapter(context, keyValues);
                    rv_preference.setLayoutManager(new GridLayoutManager(context, 2));
                    rv_preference.setAdapter(preferenceAdapter);
                    rv_preference.addItemDecoration(new SpacesItemDecoration(10, 2));
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<KeyValue>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.mytrips_create_error_process), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_preference = (RecyclerView) view.findViewById(R.id.rv_preference);
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prediction, container, false);
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
//        getActivity().setResult(Activity.RESULT_OK);
//        getActivity().finish();
//        callback.complete();
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
        startActivity(i);
        System.exit(0);
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManager) {
            dataManager = (DataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }
}
