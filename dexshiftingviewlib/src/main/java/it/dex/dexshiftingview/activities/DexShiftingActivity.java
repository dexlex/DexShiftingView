package it.dex.dexshiftingview.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import it.dex.dexshiftingview.fragment.DexScrollingFragment;
import it.dex.dexshiftingview.fragment.DexShiftingFragment;
import it.dex.dexshiftingview.fragment.DexShiftingOverlayFragment;
import it.dex.dexshiftingviewlib.R;

public abstract class DexShiftingActivity extends ActionBarActivity implements DexScrollingFragment.OnScrollChangedListener, DexShiftingFragment.OnViewDefined {
    private static final String CONTENT_TAG = "dex_content_fragment";
    private static final String SHIFTING_VIEW_TAG = "dex_shifting_view_fragment";
    private static final String SHIFTING_OVERLAY_TAG = "dex_shifting_overlay_fragment";
    private DexShiftingFragment dexShiftingFragment;
    private DexScrollingFragment dexScrollingFragment;
    private DexShiftingOverlayFragment dexShiftingOverlayFragment;
    private float currentScroll = 0;
    private View shiftingView;

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        FrameLayout contentFrame = null;
        try {
            contentFrame = (FrameLayout) view.findViewById(R.id.content);
            if (contentFrame == null)
                throw new IllegalArgumentException("MainLayout must contain a content named FrameLayout to add fragments");
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("MainLayout must contain a content named FrameLayout to add fragments");
        }
        super.setContentView(view);
        contentFrame.addView(LayoutInflater.from(this).inflate(R.layout.activity_dexshifting, null));
        dexScrollingFragment = getScrollingFragment();
        dexShiftingFragment = getShiftingFragment();
        dexShiftingOverlayFragment = getShiftingOverlayFragment();
        if (dexScrollingFragment != null)
            getSupportFragmentManager().beginTransaction().add(R.id.content, dexScrollingFragment).commit();
//        if (dexShiftingFragment != null)
//            getSupportFragmentManager().beginTransaction().add(R.id.shifting_view, dexShiftingFragment).commit();
        if (dexShiftingOverlayFragment != null)
            getSupportFragmentManager().beginTransaction().add(R.id.shifting_overlay, dexShiftingOverlayFragment).commit();
        shiftingView = findViewById(R.id.shifting_view);
    }

    protected abstract DexScrollingFragment getScrollingFragment();

    protected abstract DexShiftingFragment getShiftingFragment();

    protected DexShiftingOverlayFragment getShiftingOverlayFragment() {
        return null;
    }

    @Override
    public void onScrollChanged(float scroll) {
        currentScroll += scroll;
        shiftingView.setY(currentScroll/2);
//        dexShiftingFragment.setY(currentScroll / 2);
        if (dexShiftingOverlayFragment != null)
            dexShiftingOverlayFragment.getView().setY(currentScroll);
    }

    @Override
    public void onViewDefined(int viewHeight) {
        if (dexScrollingFragment != null) {
            dexScrollingFragment.setPadding(600);
        }
    }
}
