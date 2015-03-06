package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.fragment.DexShiftingFragment;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ImageFragment extends DexShiftingFragment {

    public static ImageFragment newInstance() {
        ImageFragment imageFragment = new ImageFragment();
        return imageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }
}
