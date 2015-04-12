package it.dex.dexshiftingview.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;

/**
 * DexShiftingView created by Diego on 21/03/2015.
 */
public class DexToolbarManager {
    private int toolbarBackgroundColor = Color.rgb(0, 0, 0);
    private int toolbarTitleColor = Color.rgb(255, 255, 255);
    private Context context;
    private Toolbar toolbar;

    public DexToolbarManager(Context context, Toolbar toolbar) {
        this.toolbar = toolbar;
        this.context = context;
    }

    public DexToolbarManager(Context context, Toolbar toolbar, int toolbarBackgroundColor, int toolbarTitleColor) {
        this(context, toolbar);
        setToolbarBackgroundColor(toolbarBackgroundColor);
        setToolbarTitleColor(toolbarTitleColor);
    }

    public void setToolbarBackgroundColor(int toolbarBackgroundColor) {
        if (toolbarBackgroundColor != 0)
            this.toolbarBackgroundColor = toolbarBackgroundColor;
    }

    public void setToolbarTitleColor(int toolbarTitleColor) {
        if (toolbarTitleColor != 0)
            this.toolbarTitleColor = toolbarTitleColor;
    }

    public void update(int dy, int shift, float topDistance, float progress, boolean isScrollingUpwards) {
        updateTitleBackground(progress);
        updateTitleY(dy, topDistance, isScrollingUpwards);
        updateTitleColor(progress);
    }

    private void updateTitleBackground(float progress) {
        ColorDrawable drawable = new ColorDrawable(toolbarBackgroundColor);
        if (progress <= 1) {
            drawable.setAlpha((int) (progress * 255));
        } else {
            drawable.setAlpha(255);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            toolbar.setBackground(drawable);
        else
            toolbar.setBackgroundDrawable(drawable);
    }

    private void updateTitleY(int dy, float topDistance, boolean isScrollingUpwards) {
        if (isScrollingUpwards) {
            if (topDistance < toolbar.getHeight()) {
                toolbar.setY(toolbar.getY() - dy);
            }
        } else {
            if (toolbar.getY() < -toolbar.getHeight())
                toolbar.setY(-toolbar.getHeight());
            else if (toolbar.getY() < getStatusBarHeight()) {
                toolbar.setY(toolbar.getY() - dy);
            } else if (toolbar.getY() != getStatusBarHeight()) {
                toolbar.setY(getStatusBarHeight());
            }
        }
    }

    private void updateTitleColor(float progress) {
        int alpha = 0;
        if (progress <= 1) {
            alpha = (int) (progress * 255);
        } else {
            alpha = 255;
        }
        toolbar.setTitleTextColor(Color.argb(alpha, Color.red(toolbarTitleColor), Color.green(toolbarTitleColor), Color.blue(toolbarTitleColor)));
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
