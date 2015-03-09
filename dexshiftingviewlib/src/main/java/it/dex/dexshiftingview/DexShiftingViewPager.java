package it.dex.dexshiftingview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * DexShiftingView created by Diego on 09/03/2015.
 */
public class DexShiftingViewPager extends ViewPager {
    private DexShiftingRecyclerView.OnShift onShift;

    public void setOnShift(DexShiftingRecyclerView.OnShift onShift) {
        this.onShift = onShift;
    }

    public DexShiftingViewPager(Context context) {
        super(context);
    }

    public DexShiftingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (onShift != null && e.getY() < onShift.getCurrentTop())
            return false;
        return super.onTouchEvent(e);
    }
}
