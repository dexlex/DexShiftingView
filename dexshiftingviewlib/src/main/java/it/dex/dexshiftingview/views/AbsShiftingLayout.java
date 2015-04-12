package it.dex.dexshiftingview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import it.dex.dexshiftingview.interfaces.OnShiftListener;
import it.dex.dexshiftingview.utils.DexOnScrollListener;
import it.dex.dexshiftingview.utils.DexToolbarManager;
import it.dex.dexshiftingviewlib.R;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public abstract class AbsShiftingLayout extends FrameLayout {
    protected DexToolbarManager dexToolbarManager;
    protected OnShiftListener onShiftListener;
    protected DexOnScrollListener dexOnScrollListener;
    private float initialTopMargin = 600;
    protected int currentScroll = 0;
    private int[] pos = new int[2];

    public AbsShiftingLayout(Context context) {
        this(context, null);
    }

    public AbsShiftingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsShiftingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsShiftingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DexShiftingLayout);
            initialTopMargin = a.getDimension(R.styleable.DexShiftingLayout_initial_top_margin, initialTopMargin);
            a.recycle();
        }
        dexOnScrollListener = new DexOnScrollListener(this);
    }

    protected abstract View setupDefaultScrollingView(LayoutInflater inflater, View container);

    protected abstract View getScrollingView();

    @Override
    protected void onFinishInflate() {
        View scrollingView = getScrollingView();
        if (scrollingView == null)
            scrollingView = setupDefaultScrollingView(LayoutInflater.from(getContext()), this);
        addView(scrollingView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            view.getLocationOnScreen(pos);
            float top = initialTopMargin - currentScroll;
            if (y < top && y >= pos[1] && y <= (pos[1] + view.getHeight()) && x >= pos[0] && x <= pos[0] + view.getWidth()) {
                if (ev.getEventTime() - ev.getDownTime() < ViewConfiguration.getTapTimeout())
                    view.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        private float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DexShiftingLayout);
            weight = a.getFloat(R.styleable.DexShiftingLayout_weight, weight);
            a.recycle();
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.weight = weight;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public float getWeight() {
            return weight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AbsShiftingLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setOnShiftListener(OnShiftListener onShiftListener) {
        this.onShiftListener = onShiftListener;
    }

    public float getInitialTopMargin() {
        return initialTopMargin;
    }

    public void setInitialTopMargin(float initialTopMargin) {
        this.initialTopMargin = initialTopMargin;
    }

    public int getCurrentScroll() {
        return currentScroll;
    }

    public void setCurrentScroll(int currentScroll) {
        this.currentScroll = currentScroll;
    }

    public void setToolbar(Toolbar toolbar) {
        setToolbar(toolbar, 0, 0);
    }

    public void setToolbar(Toolbar toolbar, int toolbarBackgroundColor, int toolbarTitleColor) {
        dexToolbarManager = new DexToolbarManager(getContext(), toolbar, toolbarBackgroundColor, toolbarTitleColor);
        dexToolbarManager.update(0, currentScroll, getInitialTopMargin() - currentScroll, currentScroll / getInitialTopMargin(), false);
    }

    public DexToolbarManager getDexToolbarManager() {
        return dexToolbarManager;
    }

    public OnShiftListener getOnShiftListener() {
        return onShiftListener;
    }
}
