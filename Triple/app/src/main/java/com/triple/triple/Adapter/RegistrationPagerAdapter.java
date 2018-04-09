package com.triple.triple.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;
import com.triple.triple.Presenter.Registration.AuthFragment;
import com.triple.triple.Presenter.Registration.DemographicFragment;
import com.triple.triple.Presenter.Registration.PredictionFragment;
import com.triple.triple.R;

/**
 * Created by PokeGuys on 4/9/18.
 */
public class RegistrationPagerAdapter extends AbstractFragmentStepAdapter {
    public RegistrationPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return new AuthFragment();
            case 1:
                return new DemographicFragment();
            case 2:
                return new PredictionFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        switch (position) {
            case 0:
                builder.setTitle(R.string.basic_info_title);
                break;
            case 1:
                builder.setTitle(R.string.user_details_title);
                break;
            case 2:
                builder.setTitle(R.string.prediction_title);
                break;
        }
        return builder.create();
    }
}
