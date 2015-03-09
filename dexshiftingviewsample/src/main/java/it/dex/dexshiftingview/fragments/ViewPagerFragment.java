package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.DexShiftingRecyclerView;
import it.dex.dexshiftingview.DexShiftingViewPager;
import it.dex.dexshiftingview.R;

/**
 * DexShiftingView created by Diego on 09/03/2015.
 */
public class ViewPagerFragment extends Fragment implements DexShiftingRecyclerView.OnShift {
    private DexShiftingViewPager pager;
    private PagerAdapter adapter;

    public static ViewPagerFragment newInstance() {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        return viewPagerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(it.dex.dexshiftingviewlib.R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = (DexShiftingViewPager) view.findViewById(R.id.pager);
        adapter = new PagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnShift(this);
    }

    @Override
    public float getCurrentTop() {
        return adapter.getItem(pager.getCurrentItem()).getCurrentTop();
    }

    @Override
    public void onShift(float shift) {
        adapter.notifyDataSetChanged();
    }

    private class PagerAdapter extends DexFragmentStatePagerAdapter<ContentFragment> {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public ContentFragment getFragment(int i) {
            return ContentFragment.newInstance();
        }

        @Override
        public void updateAddedItems(ContentFragment fragment, int i) {
            fragment.onShift(getCurrentTop());
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
