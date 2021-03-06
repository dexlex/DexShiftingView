package it.dex.dexshiftingview.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;

/**
 * DexShiftingView created by Diego on 21/03/2015.
 */
public abstract class DexRecyclerViewFragmentStatePagerAdapter<T extends DexRecyclerViewFragment> extends DexFragmentStatePagerAdapter<T> {
    private SparseArray<Integer> currentScrolls = new SparseArray<>();
    private int initialTopMargin, currentScroll = 0;
    private RecyclerView.OnScrollListener onScrollListener;
    private boolean onScrollListenerSet = false;

    public DexRecyclerViewFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        int count = getCount();
        for (int i = 0; i < count; i++) {
            currentScrolls.put(i, currentScroll);
        }
    }

    @Override
    public void updateAddedItems(DexRecyclerViewFragment fragment, int i) {
        fragment.setInitialTopMargin(initialTopMargin);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DexRecyclerViewFragment fragment = (DexRecyclerViewFragment) super.instantiateItem(container, position);
        fragment.setInitialTopMargin(initialTopMargin);
        if (!onScrollListenerSet) {
            fragment.setOnScrollListener(onScrollListener);
            onScrollListenerSet = true;
        }
        int fragmentCurrentScroll = currentScrolls.get(currentScrolls.keyAt(position));
        if (fragmentCurrentScroll != currentScroll) {
            Log.d("SCROLL_BY", currentScroll - fragmentCurrentScroll+ "");
            fragment.setCurrentScroll(currentScroll - fragmentCurrentScroll);
            currentScrolls.put(position, currentScroll);
        }
        return fragment;
    }

    public void setInitialTopMargin(int initialTopMargin) {
        this.initialTopMargin = initialTopMargin;
    }

    public SparseArray<Integer> getCurrentScrolls() {
        return currentScrolls;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setCurrentScroll(int currentScroll) {
        this.currentScroll = currentScroll;
    }
}
