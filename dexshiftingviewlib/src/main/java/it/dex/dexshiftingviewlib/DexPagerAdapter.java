package it.dex.dexshiftingviewlib;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Diego on 29/11/2014.
 */
public abstract class DexPagerAdapter extends PagerAdapter implements  DexPageGrabber{
    private SparseArray<View> viewSparseArray = new SparseArray<View>();

    public abstract View getView(int position, ViewPager viewPager);

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager pager = (ViewPager) container;
        View view = getView(position, pager);
        pager.addView(view);
        viewSparseArray.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
        viewSparseArray.remove(position);
    }

    public void updateAddedItems() {
        for (int i = 0; i < viewSparseArray.size(); i++) {
            int key = viewSparseArray.keyAt(i);
            updateAddedItems(viewSparseArray.get(key), key);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateAddedItems();
    }
}