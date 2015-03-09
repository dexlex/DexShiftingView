package it.dex.dexshiftingview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * DexShiftingView created by Diego on 09/03/2015.
 */
public class DexShiftingRecyclerView extends RecyclerView {
    private OnShift onShift;

    public void setOnShift(OnShift onShift) {
        this.onShift = onShift;
    }

    public interface OnShift {
        public float getCurrentTop();
        public void onShift(float shift);
    }

    public DexShiftingRecyclerView(Context context) {
        super(context);
    }

    public DexShiftingRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DexShiftingRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (onShift != null && e.getY() < onShift.getCurrentTop())
            return false;
        return super.onTouchEvent(e);
    }
}
