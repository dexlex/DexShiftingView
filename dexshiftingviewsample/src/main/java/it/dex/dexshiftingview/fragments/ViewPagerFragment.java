package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.data.Section;

/**
 * DexPagerAdapter created by Diego on 08/02/2015.
 */
public class ViewPagerFragment extends Fragment {
    private Section.SECTIONS sections;

    public static ViewPagerFragment newInstance(Section.SECTIONS sections) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.sections = sections;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, null);
    }
}
