package it.dex.dexshiftingview.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.dex.dexshiftingview.fragment.DexScrollingFragment;
import it.dex.dexshiftingviewlib.R;

public abstract class DexShiftingActivity extends ActionBarActivity implements DexScrollingFragment.OnScrollChangedListener {
    private DexScrollingFragment dexScrollingFragment;
    private float currentScroll = 0;
    private List<View> shiftingViews = new ArrayList<>();

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
        dexScrollingFragment = instantiateScrollingFragment();
        if (dexScrollingFragment != null)
            getSupportFragmentManager().beginTransaction().add(R.id.content, dexScrollingFragment).commit();
        shiftingViews.add(findViewById(R.id.shifting_view));
        shiftingViews.add(findViewById(R.id.shifting_view1));
        shiftingViews.add(findViewById(R.id.shifting_view2));
        shiftingViews.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DexShiftingActivity.this, "Click", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected abstract DexScrollingFragment instantiateScrollingFragment();

    public DexScrollingFragment getDexScrollingFragment() {
        return dexScrollingFragment;
    }

    @Override
    public void onScrollChanged(float scroll) {
        currentScroll += scroll;
        for (View v : shiftingViews) {
            v.setY(currentScroll / (shiftingViews.indexOf(v) + 1) / 2);
            v.setX(currentScroll / (shiftingViews.indexOf(v) + 1) / 2);
        }
    }
}
