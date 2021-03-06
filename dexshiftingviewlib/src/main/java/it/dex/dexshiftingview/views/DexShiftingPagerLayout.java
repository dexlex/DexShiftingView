package it.dex.dexshiftingview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import it.dex.dexshiftingview.adapters.DexRecyclerViewFragmentStatePagerAdapter;
import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;
import it.dex.dexshiftingview.utils.DexOnScrollListener;
import it.dex.dexshiftingviewlib.R;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public class DexShiftingPagerLayout extends AbsShiftingLayout implements ViewPager.OnPageChangeListener, DexOnScrollListener.OnScrollListener {
    private ViewPager viewPager;
    private DexRecyclerViewFragmentStatePagerAdapter<DexRecyclerViewFragment> adapter;

    public DexShiftingPagerLayout(Context context) {
        this(context, null);
    }

    public DexShiftingPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DexShiftingPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DexShiftingPagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DexShiftingPagerLayout);
            setInitialTopMargin(a.getDimension(R.styleable.DexShiftingPagerLayout_initial_top_margin, getInitialTopMargin()));
            a.recycle();
        }
        dexOnScrollListener.setOnScrollListener(this);
    }

    @Override
    protected View setupDefaultScrollingView(LayoutInflater inflater, View container) {
        viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.action_bar);
        viewPager.setClipToPadding(false);
        viewPager.setOnPageChangeListener(this);
        return viewPager;
    }

    @Override
    public ViewPager getScrollingView() {
        return viewPager;
    }

    public void setAdapter(DexRecyclerViewFragmentStatePagerAdapter<DexRecyclerViewFragment> adapter) {
        this.adapter = adapter;
        adapter.setInitialTopMargin((int) getInitialTopMargin());
        adapter.setOnScrollListener(dexOnScrollListener);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (adapter != null) {
            Log.d("CURRENT_SCROLLS", adapter.getCurrentScrolls().toString());
            for (int i = 0; i < adapter.getFragmentSparseArray().size(); i++) {
                DexRecyclerViewFragment fragment = adapter.getFragmentSparseArray().get(adapter.getFragmentSparseArray().keyAt(i));
                RecyclerView recyclerView = fragment.getRecyclerView();
                if (adapter.getFragmentSparseArray().keyAt(i) == position)
                    recyclerView.setOnScrollListener(dexOnScrollListener);
                else {
                    recyclerView.setOnScrollListener(null);
                    SparseArray<Integer> currentScrolls = adapter.getCurrentScrolls();
                    int key = currentScrolls.keyAt(i);
                    if (currentScroll > getInitialTopMargin()) {
                        recyclerView.scrollBy(0, (int) (getInitialTopMargin() - currentScroll));
                        currentScrolls.put(key, (int) getInitialTopMargin());
                    }
                }
            }
        }
        if (currentScroll > getInitialTopMargin())
            currentScroll = (int) getInitialTopMargin();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        adapter.setCurrentScroll(currentScroll);
        for (int i = 0; i < adapter.getFragmentSparseArray().size(); i++) {
            SparseArray<Integer> currentScrolls = adapter.getCurrentScrolls();
            int key = currentScrolls.keyAt(i);
            if (adapter.getFragmentSparseArray().keyAt(i) != viewPager.getCurrentItem()) {
                DexRecyclerViewFragment fragment = adapter.getFragmentSparseArray().get(adapter.getFragmentSparseArray().keyAt(i));
                if (fragment != null && fragment.getRecyclerView() != null) {
                    if (currentScroll <= getInitialTopMargin()) {
                        fragment.getRecyclerView().scrollBy(0, dy);
                        currentScrolls.put(key, currentScrolls.get(key) + dy);
                    }
                }
            } else {
                if (currentScroll <= getInitialTopMargin()) {
                    currentScrolls.put(key, currentScrolls.get(key) + dy);
                }
            }
        }
    }
}
