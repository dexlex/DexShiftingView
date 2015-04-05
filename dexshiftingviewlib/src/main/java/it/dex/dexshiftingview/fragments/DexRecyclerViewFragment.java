package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * DexShiftingView created by Diego on 20/03/2015.
 */
public abstract class DexRecyclerViewFragment extends Fragment {
    private static final String INITIAL_SCROLL_SET = "initialScrollSet";
    private int initialTopMargin, initialScroll;
    private boolean isInitialTopMarginSet = false, isInitialScrollSet = false, isListenerSet = false;
    private RecyclerView.OnScrollListener onScrollListener;

    public abstract RecyclerView getRecyclerView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInitialScrollSet = savedInstanceState != null && savedInstanceState.getBoolean(INITIAL_SCROLL_SET, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getRecyclerView() == null)
            throw new IllegalStateException("You must return a non null RecyclerView instance in the getRecyclerView() method");
        if (!isInitialTopMarginSet)
            setInitialTopMargin(initialTopMargin);
        if (!isListenerSet)
            setOnScrollListener(onScrollListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isInitialScrollSet)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setScroll();
                }
            }, 10);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INITIAL_SCROLL_SET, isInitialScrollSet);
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

    private void setScroll() {
        if (getRecyclerView() != null) {
            getRecyclerView().scrollBy(0, initialScroll);
            isInitialScrollSet = true;
        }
    }

    public void setInitialScroll(int initialScroll) {
        if (this.initialScroll != initialScroll) {
            this.initialScroll = initialScroll;
            isInitialScrollSet = false;
        }
    }
}
