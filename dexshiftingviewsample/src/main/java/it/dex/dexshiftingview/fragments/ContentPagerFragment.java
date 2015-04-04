package it.dex.dexshiftingview.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import it.dex.dexpageradapterlib.DexFragmentStatePagerAdapter;
import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.adapters.DexRecyclerViewFragmentStatePagerAdapter;
import it.dex.dexshiftingview.interfaces.OnShiftListener;
import it.dex.dexshiftingview.views.DexShiftingPagerLayout;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ContentPagerFragment extends Fragment {
    private OnShiftListener onShiftListener;
    private MyPagerAdapter adapter;
    private int scroll;

    public static ContentPagerFragment newInstance() {
        ContentPagerFragment contentFragment = new ContentPagerFragment();
        return contentFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onShiftListener = (OnShiftListener) activity;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        DexShiftingPagerLayout dexShiftingView = (DexShiftingPagerLayout) view.findViewById(R.id.dexshiftingview);
        dexShiftingView.setOnShiftListener(onShiftListener);
        dexShiftingView.setToolbar(onShiftListener.getToolbar());
        dexShiftingView.setAdapter(adapter);
        VideoView videoView = (VideoView) view.findViewById(R.id.video);
        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.video));
        videoView.start();
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
        adapter.notifyDataSetChanged();
    }

    private class MyPagerAdapter extends DexRecyclerViewFragmentStatePagerAdapter<DexRecyclerViewFragment> {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public DexRecyclerViewFragment getFragment(int i) {
            return ItemFragment.newInstance();
        }

        @Override
        public void updateAddedItems(DexRecyclerViewFragment dexRecyclerViewFragment, int i) {
            dexRecyclerViewFragment.getRecyclerView().getLayoutManager().scrollToPosition(scroll);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
