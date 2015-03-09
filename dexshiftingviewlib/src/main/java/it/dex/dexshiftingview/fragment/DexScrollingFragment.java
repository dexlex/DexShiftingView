package it.dex.dexshiftingview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexshiftingview.DexShiftingRecyclerView;
import it.dex.dexshiftingviewlib.R;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public abstract class DexScrollingFragment extends Fragment implements DexShiftingRecyclerView.OnShift {
    private OnScrollChangedListener onScrollChangedListener;
    protected DexShiftingRecyclerView recyclerView;
    private float currentScroll;
    private DexShiftingRecyclerView.OnShift onShift;

    public void setOnShift(DexShiftingRecyclerView.OnShift onShift) {
        this.onShift = onShift;
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scrolling, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (DexShiftingRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentScroll -= dy;
                onScrollChangedListener.onScrollChanged(currentScroll);
                onShift(dy);
            }
        });
        recyclerView.setOnShift(this);
        recyclerView.setPadding(0, 600, 0, 0);
    }

    @Override
    public float getCurrentTop() {
        return 600 + currentScroll;
    }

    @Override
    public void onShift(float shift) {
        //TODO Scroll recyclerview to shift
        onShift.onShift(shift);
    }
}
