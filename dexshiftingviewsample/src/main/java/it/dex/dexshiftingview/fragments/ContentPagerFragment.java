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

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.adapters.DexRecyclerViewFragmentStatePagerAdapter;
import it.dex.dexshiftingview.data.Section;
import it.dex.dexshiftingview.interfaces.OnShiftListener;
import it.dex.dexshiftingview.views.DexShiftingPagerLayout;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ContentPagerFragment extends Fragment {
    private Section.SUBSECTION subsection;
    private OnShiftListener onShiftListener;
    private MyPagerAdapter adapter;

    public static ContentPagerFragment newInstance(Section.SUBSECTION subsection) {
        ContentPagerFragment contentFragment = new ContentPagerFragment();
        contentFragment.subsection = subsection;
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
        int layout = 0;
        switch (subsection) {
            case VIDEO_VIEW:
                layout = R.layout.fragment_pager_content_video_view;
                break;
            case IMAGE_VIEW:
                layout = R.layout.fragment_pager_content_image_view;
                break;
            case IMAGES:
                layout = R.layout.fragment_pager_content_images;
                break;
            case DEXMOVINGIMAGEVIEW:
                layout = R.layout.fragment_pager_content_dexmovingimageview;
                break;
        }
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        DexShiftingPagerLayout dexShiftingView = (DexShiftingPagerLayout) view.findViewById(R.id.dexshiftingview);
        dexShiftingView.setOnShiftListener(onShiftListener);
        dexShiftingView.setToolbar(onShiftListener.getToolbar(), getResources().getColor(R.color.primary), getResources().getColor(R.color.secondary));
        dexShiftingView.setAdapter(adapter);
        switch (subsection) {
            case VIDEO_VIEW:
                VideoView videoView = (VideoView) view.findViewById(R.id.video);
                videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.video));
                videoView.start();
                break;
        }
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
        public int getCount() {
            return 6;
        }
    }
}
