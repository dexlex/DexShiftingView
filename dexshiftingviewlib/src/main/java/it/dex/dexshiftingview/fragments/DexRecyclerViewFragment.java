package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * DexShiftingView created by Diego on 20/03/2015.
 */
public abstract class DexRecyclerViewFragment extends Fragment {
    private int initialTopMargin, currentScroll;
    private boolean isInitialTopMarginSet = false, isListenerSet = false, isScrollSet = false;
    private RecyclerView.OnScrollListener onScrollListener;

    public abstract RecyclerView getRecyclerView();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getRecyclerView() == null)
            throw new IllegalStateException("You must return a non null RecyclerView instance in the getRecyclerView() method");
        if (!isInitialTopMarginSet)
            setInitialTopMargin(initialTopMargin);
        if (!isListenerSet)
            setOnScrollListener(onScrollListener);
        if (!isScrollSet)
            setCurrentScroll(currentScroll);
    }

    public void setInitialTopMargin(int initialTopMargin) {
        this.initialTopMargin = initialTopMargin;
        if (getRecyclerView() != null) {
            getRecyclerView().setPadding(getRecyclerView().getPaddingLeft(), getRecyclerView().getPaddingTop() + initialTopMargin,
                    getRecyclerView().getPaddingRight(), getRecyclerView().getPaddingBottom());
            isInitialTopMarginSet = true;
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        if (getRecyclerView() != null) {
            getRecyclerView().setOnScrollListener(onScrollListener);
            isListenerSet = true;
        }
    }

    public void setCurrentScroll(final int currentScroll) {
        this.currentScroll = currentScroll;
        isScrollSet=false;
        if (getRecyclerView() != null)
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    getRecyclerView().scrollBy(0, currentScroll);
                    isScrollSet = true;
                }
            });
    }
}
