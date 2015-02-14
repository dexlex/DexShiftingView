package it.dex.dexshiftingview.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import it.dex.dexshiftingview.fragments.ItemFragment;
import it.dex.dexshiftingviewlib.DexFragmentPagerAdapter;

/**
 * Created by Diego on 29/11/2014.
 */
public class ItemDexPagerAdapter extends DexFragmentPagerAdapter {
    public ItemDexPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int position) {
        return ItemFragment.newInstance(position);
    }

    @Override
    public void updateAddedItems(Fragment fragment, int position) {
        ItemFragment itemFragment = (ItemFragment) fragment;
        itemFragment.setBackgroundColor(Color.RED);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


}
