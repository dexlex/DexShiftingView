package it.dex.dexshiftingview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public abstract class DexShiftingFragment extends Fragment {
    private OnViewDefined onViewDefined;

    public interface OnViewDefined {
        public void onViewDefined(int viewHeight);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onViewDefined = (OnViewDefined) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.getClass().getName() + "must implement OnViewDefined interface");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    onViewDefined.onViewDefined(getView().getMeasuredHeight());
                }
            }, 300);
        }
    }

    public void setY(float y){
        getView().setY(y);
    }
}
