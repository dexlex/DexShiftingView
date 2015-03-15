package it.dex.dexshiftingview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import it.dex.dexshiftingviewlib.R;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public class DexShiftingPagerLayout extends AbsShiftingLayout {
    private ViewPager viewPager;

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
        return null;
    }

    public ViewPager getScrollingView() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
