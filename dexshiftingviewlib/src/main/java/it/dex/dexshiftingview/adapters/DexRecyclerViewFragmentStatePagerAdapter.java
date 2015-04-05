package it.dex.dexshiftingview.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;

/**
 * DexShiftingView created by Diego on 21/03/2015.
 */
public abstract class DexRecyclerViewFragmentStatePagerAdapter<T extends DexRecyclerViewFragment> extends DexFragmentStatePagerAdapter<T> {
    private SparseArray<Integer> currentScrolls = new SparseArray<>();
    private int initialTopMargin, initialScroll;
    private RecyclerView.OnScrollListener onScrollListener;
    private boolean onScrollListenerSet = false;

    public DexRecyclerViewFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
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
        fragment.setInitialScroll(initialScroll);
        currentScrolls.put(position, initialScroll);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        currentScrolls.remove(position);
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

    public void setInitialScroll(int initialScroll) {
        this.initialScroll = initialScroll;
    }
}
