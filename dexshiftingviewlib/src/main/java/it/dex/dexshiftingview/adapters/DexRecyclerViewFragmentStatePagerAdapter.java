package it.dex.dexshiftingview.adapters;

import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;

/**
 * DexShiftingView created by Diego on 21/03/2015.
 */
public abstract class DexRecyclerViewFragmentStatePagerAdapter<T extends DexRecyclerViewFragment> extends DexFragmentStatePagerAdapter<T> {
    private int initialTopMargin;
    private int initialScroll;

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
        fragment.setInitialScroll(initialScroll);
        return fragment;
    }

    public void setInitialTopMargin(int initialTopMargin) {
        this.initialTopMargin = initialTopMargin;
    }

    public void setInitialScroll(int initialScroll) {
        this.initialScroll = initialScroll;
    }
}
