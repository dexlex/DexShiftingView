package it.dex.dexshiftingview.utils;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;
import it.dex.dexshiftingview.views.AbsShiftingLayout;

/**
 * DexShiftingView created by Diego on 05/04/2015.
 */
public class DexOnScrollListener extends RecyclerView.OnScrollListener {
    private AbsShiftingLayout absShiftingLayout;
    private OnScrollListener onScrollListener;

    public DexOnScrollListener(AbsShiftingLayout absShiftingLayout) {
        this.absShiftingLayout = absShiftingLayout;
    }

    public interface OnScrollListener {
        public void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int currentScroll = absShiftingLayout.getCurrentScroll();
        currentScroll += dy;
        absShiftingLayout.setCurrentScroll(currentScroll);
        for (int i = 0; i < absShiftingLayout.getChildCount(); i++) {
            View v = absShiftingLayout.getChildAt(i);
            if (!(v instanceof ViewPager) && ((AbsShiftingLayout.LayoutParams) v.getLayoutParams()).getWeight() != 0)
                v.setY(-currentScroll / ((AbsShiftingLayout.LayoutParams) v.getLayoutParams()).getWeight());
        }
        if (absShiftingLayout.getDexToolbarManager() != null)
            absShiftingLayout.getDexToolbarManager().update(dy, currentScroll, absShiftingLayout.getInitialTopMargin() - currentScroll, currentScroll / absShiftingLayout.getInitialTopMargin(), dy > 0);
        if (absShiftingLayout.getOnShiftListener() != null)
            absShiftingLayout.getOnShiftListener().onShift(dy, currentScroll, absShiftingLayout.getInitialTopMargin() - currentScroll, currentScroll / absShiftingLayout.getInitialTopMargin(), dy > 0);
        if (onScrollListener != null) {
            onScrollListener.onScrolled(recyclerView, dx, dy);
        }
    }

    public DexOnScrollListener setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        return this;
    }
}
