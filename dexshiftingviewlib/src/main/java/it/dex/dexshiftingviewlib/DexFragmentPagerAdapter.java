package it.dex.dexshiftingviewlib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Diego on 29/11/2014.
 */
public abstract class DexFragmentPagerAdapter extends FragmentPagerAdapter implements DexFragmentGrabber{
    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<Fragment>();

    public DexFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract Fragment getFragment(int position);

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = getFragment(position);
        fragmentSparseArray.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentSparseArray.remove(position);
        super.destroyItem(container, position, object);
    }

    public void updateAddedItems() {
        for (int i = 0; i < fragmentSparseArray.size(); i++) {
            int key = fragmentSparseArray.keyAt(i);
            updateAddedItems(fragmentSparseArray.get(key), key);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateAddedItems();
    }
}
