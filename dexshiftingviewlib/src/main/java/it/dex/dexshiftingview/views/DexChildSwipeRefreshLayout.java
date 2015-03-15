package it.dex.dexshiftingview.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * DexShiftingView created by Diego on 15/03/2015.
 */
public class DexChildSwipeRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView recyclerView;
    private DexShiftingLayout dexShiftingView;

    public DexChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public DexChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findRecyclerView(this);
    }

    private void findRecyclerView(ViewGroup v) {
        for (int i = 0; i < v.getChildCount(); i++) {
            View view = v.getChildAt(i);
            if (view instanceof RecyclerView) {
                recyclerView = (RecyclerView) view;
            } else if (view instanceof ViewGroup) {
                if (view instanceof DexShiftingLayout)
                    dexShiftingView = (DexShiftingLayout) view;
                findRecyclerView((ViewGroup) view);
            }
        }
    }

    @Override
    public boolean canChildScrollUp() {
        if (recyclerView != null) {
            int firstVisibleItem = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager)
                firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            else if (recyclerView.getLayoutManager() instanceof GridLayoutManager)
                firstVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (firstVisibleItem >= 0) {
                int currentScroll = dexShiftingView.getCurrentScroll();
                return currentScroll > 0;
            } else
                return super.canChildScrollUp();
        } else
            return super.canChildScrollUp();
    }
}
