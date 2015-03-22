package it.dex.dexshiftingview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import it.dex.dexshiftingviewlib.R;

/**
 * it.dex.dexshiftingview.views.DexShiftingView created by Diego on 05/03/2015.
 */
public class DexShiftingLayout extends AbsShiftingLayout {
    private RecyclerView recyclerView;

    public DexShiftingLayout(Context context) {
        this(context, null);
    }

    public DexShiftingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DexShiftingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DexShiftingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DexShiftingLayout);
            setInitialTopMargin(a.getDimension(R.styleable.DexShiftingLayout_initial_top_margin, getInitialTopMargin()));
            a.recycle();
        }
    }

    @Override
    protected View setupDefaultScrollingView(LayoutInflater inflater, View container) {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setClipToPadding(false);
        setScrollListener(recyclerView);
        return recyclerView;
    }

    public RecyclerView getScrollingView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        setScrollListener(recyclerView);
    }

    private void setScrollListener(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isScrollingUpwards = dy > 0;
                currentScroll += dy;
                for (int i = 0; i < getChildCount(); i++) {
                    View v = getChildAt(i);
                    if (!(v instanceof RecyclerView) && ((LayoutParams) v.getLayoutParams()).getWeight() != 0)
                        v.setY(-currentScroll / ((LayoutParams) v.getLayoutParams()).getWeight());
                }
                if (onShiftListener != null)
                    onShiftListener.onShift(dy, currentScroll, getInitialTopMargin() - currentScroll, currentScroll / getInitialTopMargin(), isScrollingUpwards);
            }
        });
    }
}
