package com.triple.triple.Presenter.Registration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.triple.triple.Interface.DataManager;
import com.triple.triple.R;

public class AuthFragment extends Fragment implements BlockingStep {
    TextInputLayout input_field_username, input_field_password, input_field_password_confirmation, input_field_email;
    TextInputEditText et_username, et_email, et_password, et_cpassword;
    String username, password, password_confirmation, email;
    DataManager dataManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        input_field_username = view.findViewById(R.id.input_field_username);
        input_field_password = view.findViewById(R.id.input_field_password);
        input_field_password_confirmation = view.findViewById(R.id.input_field_password_confirmation);
        input_field_email = view.findViewById(R.id.input_field_email);
        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        et_cpassword = view.findViewById(R.id.et_cpassword);
    }

    @Override
    public VerificationError verifyStep() {
        String message = null;
        TextInputLayout target = null;
        input_field_username.setErrorEnabled(false);
        input_field_password.setErrorEnabled(false);
        input_field_password_confirmation.setErrorEnabled(false);
        input_field_email.setErrorEnabled(false);
        username = et_username.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        password_confirmation = et_cpassword.getText().toString();
        if (username.length() < 6) {
            message = getString(R.string.register_error_username);
            target = input_field_username;
        } else if (password.equals("") || password.length() < 6) {
            message = getString(R.string.register_error_password);
            target = input_field_password;
        } else if (!password.equals(password_confirmation)) {
            message = getString(R.string.register_error_password2);
            target = input_field_password_confirmation;
        } else if (checkEmail(email)) {
            message = getString(R.string.register_error_email);
            target = input_field_email;
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
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        dataManager.saveData("username", username);
        dataManager.saveData("email", email);
        dataManager.saveData("password", password);
        dataManager.saveData("password_confirmation", password_confirmation);
        callback.goToNextStep();
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

    private boolean checkEmail(String email) {
        return TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
