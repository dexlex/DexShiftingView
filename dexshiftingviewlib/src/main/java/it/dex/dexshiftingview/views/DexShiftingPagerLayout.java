package it.dex.dexshiftingview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.adapters.DexRecyclerViewFragmentStatePagerAdapter;
import it.dex.dexshiftingview.fragments.DexRecyclerViewFragment;
import it.dex.dexshiftingview.utils.DexToolbarManager;
import it.dex.dexshiftingviewlib.R;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public class DexShiftingPagerLayout extends AbsShiftingLayout implements ViewPager.OnPageChangeListener {
    private DexToolbarManager dexToolbarManager;
    private ViewPager viewPager;
    private DexFragmentStatePagerAdapter<DexRecyclerViewFragment> adapter;

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
        adapter.setInitialScroll((int) (currentScroll % getInitialTopMargin()));
        viewPager.setAdapter(adapter);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentScroll += dy;
            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                if (!(v instanceof ViewPager) && ((LayoutParams) v.getLayoutParams()).getWeight() != 0)
                    v.setY(-currentScroll / ((LayoutParams) v.getLayoutParams()).getWeight());
            }
            for (int i = 0; i < adapter.getFragmentSparseArray().size(); i++) {
                if (adapter.getFragmentSparseArray().keyAt(i) != viewPager.getCurrentItem()) {
                    DexRecyclerViewFragment fragment = adapter.getFragmentSparseArray().get(adapter.getFragmentSparseArray().keyAt(i));
                    if (fragment != null && fragment.getRecyclerView() != null && fragment.getRecyclerView().getLayoutManager() != null) {
                        if (currentScroll <= getInitialTopMargin()) {
                            fragment.getRecyclerView().scrollBy(0, dy);
                        }
                    }
                }
            }
            if (dexToolbarManager != null)
                dexToolbarManager.update(dy, currentScroll, getInitialTopMargin() - currentScroll, currentScroll / getInitialTopMargin(), dy > 0);
            if (onShiftListener != null)
                onShiftListener.onShift(dy, currentScroll, getInitialTopMargin() - currentScroll, currentScroll / getInitialTopMargin(), dy > 0);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (adapter != null) {
            for (int i = 0; i < adapter.getFragmentSparseArray().size(); i++) {
                DexRecyclerViewFragment fragment = adapter.getFragmentSparseArray().get(adapter.getFragmentSparseArray().keyAt(i));
                if (adapter.getFragmentSparseArray().keyAt(i) == position)
                    fragment.getRecyclerView().setOnScrollListener(onScrollListener);
                else {
                    fragment.getRecyclerView().setOnScrollListener(null);
                    if (currentScroll > getInitialTopMargin())
                        fragment.getRecyclerView().scrollBy(0, (int) (getInitialTopMargin() - currentScroll));
                }
            }
        }
        if (currentScroll > getInitialTopMargin())
            currentScroll = (int) getInitialTopMargin();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setToolbar(Toolbar toolbar) {
        dexToolbarManager = new DexToolbarManager(getContext(), toolbar);
    }
}
