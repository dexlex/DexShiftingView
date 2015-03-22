package it.dex.dexshiftingview.interfaces;

import android.support.v7.widget.Toolbar;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public interface OnShiftListener {
    public void onShift(int dy, int shift, float topDistance, float progress, boolean isScrollingUpwards);
    public Toolbar getToolbar();
}
