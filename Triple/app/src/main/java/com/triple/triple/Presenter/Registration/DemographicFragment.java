package com.triple.triple.Presenter.Registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.securepreferences.SecurePreferences;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.triple.triple.Interface.ApiInterface;
import com.triple.triple.Interface.DataManager;
import com.triple.triple.Model.AuthData;
import com.triple.triple.R;
import com.triple.triple.Sync.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemographicFragment extends Fragment implements BlockingStep {
    TextInputLayout input_field_firstname, input_field_lastname, input_field_age, input_field_gender, input_field_country;
    TextInputEditText et_fname, et_lname, et_age, et_gender, et_country;
    AlertDialog dialog;
    DataManager dataManager;
    private String[] gender_param = {"M", "F"};
    private String firstName, lastName, country, age, gender;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demographic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        input_field_firstname = view.findViewById(R.id.input_field_firstname);
        input_field_lastname = view.findViewById(R.id.input_field_lastname);
        input_field_age = view.findViewById(R.id.input_field_age);
        input_field_gender = view.findViewById(R.id.input_field_gender);
        input_field_country = view.findViewById(R.id.input_field_country);
        et_fname = view.findViewById(R.id.et_fname);
        et_lname = view.findViewById(R.id.et_lname);
        et_age = view.findViewById(R.id.et_age);
        et_gender = view.findViewById(R.id.et_gender);
        et_country = view.findViewById(R.id.et_country);
        et_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] singleChoiceItems = getResources().getStringArray(R.array.age);
                int itemSelected = 0;
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.register_age_guide))
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                et_age.setText(singleChoiceItems[i]);
                                age = String.valueOf(i + 1);
                            }
                        })
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
                        .show();
            }
        });
        et_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] singleChoiceItems = getResources().getStringArray(R.array.gender);
                int itemSelected = 0;
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.register_gender_guide))
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                et_gender.setText(singleChoiceItems[i]);
                                gender = gender_param[i];
                            }
                        })
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
                        .show();
            }
        });
        et_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance(getString(R.string.register_country_guide));
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        picker.dismiss();
                        et_country.setText(name);
                        country = code;
                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) dialog.dismiss();
    }

    @Override
    public VerificationError verifyStep() {
        String message = null;
        TextInputLayout target = null;
        input_field_firstname.setErrorEnabled(false);
        input_field_lastname.setErrorEnabled(false);
        input_field_age.setErrorEnabled(false);
        input_field_gender.setErrorEnabled(false);
        input_field_country.setErrorEnabled(false);
        firstName = et_fname.getText().toString();
        lastName = et_lname.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            message = getString(R.string.register_error_required);
            target = input_field_firstname;
        } else if (TextUtils.isEmpty(lastName)) {
            message = getString(R.string.register_error_required);
            target = input_field_lastname;
        } else if (age != null && age.equals("")) {
            message = getString(R.string.register_error_age);
            target = input_field_age;
        } else if (gender != null && gender.equals("")) {
            message = getString(R.string.register_error_gender);
            target = input_field_gender;
        } else if (country != null && country.equals("")) {
            message = getString(R.string.register_error_country);
            target = input_field_country;
        }

        if (target != null) {
            target.setErrorEnabled(true);
            target.setError(message);
            target.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
            return new VerificationError(message);
        }

        return null;
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_loader);
        builder.setCancelable(false);
        dialog = builder.show();
        Call<AuthData> call = apiService.register(dataManager.getData("username"), firstName, lastName, dataManager.getData("password"), dataManager.getData("password_confirmation"), gender, age, dataManager.getData("email"), "0");
        call.enqueue(new Callback<AuthData>() {
            @Override
            public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                if(response.code() == 201) {
                    Gson gson = new Gson();
                    AuthData auth = response.body();
                    SharedPreferences data = new SecurePreferences(getContext());
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString("token", auth.getToken());
                    String json = gson.toJson(auth.getUser());
                    editor.putString("userInfo", json);
                    editor.apply();
                    callback.goToNextStep();
                } else {
                    Toast.makeText(getContext(), getString(R.string.mytrips_create_error_process), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
            @Override
             public void onFailure(Call<AuthData> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.mytrips_create_error_process), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    @UiThread
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public void onSelected() {}

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
