package it.dex.dexshiftingview.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.data.Section;
import it.dex.dexshiftingview.fragments.ContentFragment;
import it.dex.dexshiftingview.fragments.NavigationDrawerFragment;
import it.dex.dexshiftingview.interfaces.OnShiftListener;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnShiftListener {
    private NavigationDrawerFragment navigationDrawerFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);
        addSystemBarMargin(navigationDrawerFragment.getView());
        navigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if (savedInstanceState == null) {
            navigationDrawerFragment.selectItem(1);
        }
    }

    private void addSystemBarMargin(View view) {
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) view.getLayoutParams();
        lp.setMargins(lp.leftMargin, lp.topMargin + getStatusBarHeight(), lp.rightMargin, lp.bottomMargin + getNavigationBarHeight());
        view.setLayoutParams(lp);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        if (resourceId > 0 && !hasMenuKey) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/dexlex/DexPagerAdapter");
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DexPagerAdapter on GitHub");
        sendIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(sendIntent);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment.isDrawerOpen())
            navigationDrawerFragment.close();
        else
            super.onBackPressed();
    }

    @Override
    public void onNavigationDrawerItemSelected(Section section) {
        //TODO Develop app navigation
        getSupportFragmentManager().beginTransaction().replace(R.id.content, ContentFragment.newInstance()).commit();
    }

    @Override
    public void onSocialIconSelected(int selectedSocial) {
        int urlRes = 0;
        switch (selectedSocial) {
            case NavigationDrawerFragment.GOOGLE_PLUS:
                urlRes = R.string.google_plus;
                break;
            case NavigationDrawerFragment.TWITTER:
                urlRes = R.string.twitter;
                break;
            case NavigationDrawerFragment.LINKED_IN:
                urlRes = R.string.linked_in;
                break;
            case NavigationDrawerFragment.GITHUB:
                urlRes = R.string.github;
                break;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(urlRes)));
        startActivity(intent);
    }

    @Override
    public void onShift(int shift, float topDistance, float progress) {
        if (progress <= 1) {
            ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.primary));
            drawable.setAlpha((int) (progress * 255));
            getSupportActionBar().setBackgroundDrawable(drawable);
        }
        if (topDistance < toolbar.getHeight()) {
            toolbar.setY(-toolbar.getHeight() + topDistance + getStatusBarHeight());
        } else {
            if (toolbar.getY() != getStatusBarHeight())
                toolbar.setY(getStatusBarHeight());
        }
        if (progress <= 1) {
            int color = getResources().getColor(R.color.secondary);
            toolbar.setTitleTextColor(Color.argb((int) (progress * 255), Color.red(color), Color.green(color), Color.blue(color)));
        }

    }
}
