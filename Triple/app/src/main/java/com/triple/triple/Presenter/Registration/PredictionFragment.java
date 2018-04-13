package com.triple.triple.Presenter.Registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
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
import com.triple.triple.Helper.UserDataHelper;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Interface.DataManager;
import com.triple.triple.Model.KeyValue;
import com.triple.triple.Presenter.Home.HomeFragment;
import com.triple.triple.Presenter.MainActivity;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictionFragment extends Fragment implements BlockingStep {
    DataManager dataManager;
    AlertDialog dialog;
    final ImageView[] imageViewsList = new ImageView[5];
    final TextView[] textViewsList = new TextView[5];
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

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
        Call<List<KeyValue>> call = apiService.getPreferences("Bearer " + UserDataHelper.getToken(getContext()));
        call.enqueue(new Callback<List<KeyValue>>() {
            @Override
            public void onResponse(Call<List<KeyValue>> call, Response<List<KeyValue>> response) {
                List<KeyValue> predicts = response.body();
                Context context = getContext();
                for (int i = 0; i <= 3; i++) {
                    String filename = "preference_" + predicts.get(i).getKey();
                    if (predicts.get(i).getKey().equals("60+_traveller")) {
                        filename = "preference_60_traveller";
                    }
                    imageViewsList[i].setImageResource(context.getResources().getIdentifier(filename, "drawable", context.getPackageName()));
                    String key = predicts.get(i).getKey().replace('_', ' ');
                    textViewsList[i].setText(String.format("%s%s", key.substring(0,1).toUpperCase(), key.substring(1).toLowerCase()));
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
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prediction, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int[] imageViewIdList = {R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4};
        int[] textViewIdList = {R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4};
        for (int i = 0; i <= 3; i++) {
            imageViewsList[i] = (ImageView) view.findViewById(imageViewIdList[i]);
            textViewsList[i] = (TextView) view.findViewById(textViewIdList[i]);
        }
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {}

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
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {}

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
