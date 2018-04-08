package com.triple.triple.Presenter.Mytrips;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triple.triple.Helper.BitmapTransform;
import com.triple.triple.Helper.Constant;
import com.triple.triple.Helper.DateTimeHelper;
import com.triple.triple.Helper.SystemPropertyHelper;
import com.triple.triple.Model.City;
import com.triple.triple.R;


public class GeneratingDialog extends DialogFragment {

    private static GeneratingDialog dialog;
    private Context mcontext;

    public GeneratingDialog() {
        // Required empty public constructor
    }

    public static GeneratingDialog instance(String name, int city, String start, int duration) {
        if (dialog == null) {
            synchronized (GeneratingDialog.class) {
                if (dialog == null) {
                    dialog = new GeneratingDialog();
                }
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("city", city);
        bundle.putString("date", start);
        bundle.putInt("duration", duration);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String name = getArguments().getString("name");
        int city = getArguments().getInt("city");
        String date = getArguments().getString("date");
        int duration = getArguments().getInt("duration");

        View view = inflater.inflate(R.layout.fragment_generating, container);
        ImageView iv_dot = view.findViewById(R.id.iv_dot);
        ImageView iv_bkg = view.findViewById(R.id.iv_bkg);
        TextView tv_tripname = view.findViewById(R.id.tv_tripname);
        TextView tv_tripcity = view.findViewById(R.id.tv_tripcity);
        TextView tv_tripdate = view.findViewById(R.id.tv_tripdate);

        tv_tripname.setText(name);

        City c = SystemPropertyHelper.getSystemPropertyByCityId(mcontext, city);
        tv_tripcity.setText(c.getName() + ", " + c.getCountry());

        Picasso.with(mcontext)
                .load(c.getPhoto())
                .fit().centerCrop()
                .transform(new BitmapTransform(Constant.IMAGE_X_WIDTH, Constant.IMAGE_X_WIDTH))
                .into(iv_bkg);

        String dateString = DateTimeHelper.castDateToLocale(date) + " - " + DateTimeHelper.castDateToLocale(DateTimeHelper.endDate(date, duration));
        tv_tripdate.setText(dateString);

        Drawable drawable = iv_dot.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
}
