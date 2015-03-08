package it.dex.dexshiftingview.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public abstract class DexScrollingFragment extends Fragment implements ViewTreeObserver.OnScrollChangedListener {
    private OnScrollChangedListener onScrollChangedListener;
    private float currentScroll = 0;

    public interface OnScrollChangedListener {
        public void onScrollChanged(float scroll);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onScrollChangedListener = (OnScrollChangedListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.getClass().getName() + " must implement OnScrollChangedListener interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (getView() != null)
//            getView().getViewTreeObserver().addOnScrollChangedListener(this);
        ((RecyclerView)getScrollableView()).setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentScroll -= dy;
                onScrollChangedListener.onScrollChanged(currentScroll);
                recyclerView.setY(600 + currentScroll);
            }
        });
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (getView() != null)
//            getView().getViewTreeObserver().removeOnScrollChangedListener(this);
//    }

    protected abstract View getScrollableView();

    @Override
    public void onScrollChanged() {
//        View scrollableView = getScrollableView();
//        if (scrollableView != null)
//            if (scrollableView instanceof ScrollView) {
//                ScrollView scrollView = (ScrollView) scrollableView;
//                onScrollChangedListener.onScrollChanged(scrollView.getScrollY());
//            } else if (scrollableView instanceof ListView) {
//                ListView listView = (ListView) scrollableView;
//                onScrollChangedListener.onScrollChanged(listView.getScrollY());
//            } else if (scrollableView instanceof GridView) {
//                GridView gridView = (GridView) scrollableView;
//                onScrollChangedListener.onScrollChanged(gridView.getScrollY());
//            } else if (scrollableView instanceof ExpandableListView) {
//                ExpandableListView expandableListView = (ExpandableListView) scrollableView;
//                onScrollChangedListener.onScrollChanged(expandableListView.getScrollY());
//            } else if (scrollableView instanceof RecyclerView) {
//                RecyclerView recyclerView = (RecyclerView) scrollableView;
//                onScrollChangedListener.onScrollChanged(recyclerView.getScrollY());
//            }
    }
}
