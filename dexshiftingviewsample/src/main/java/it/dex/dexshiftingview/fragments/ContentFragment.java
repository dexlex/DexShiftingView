package it.dex.dexshiftingview.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.adapter.TestAdapter;
import it.dex.dexshiftingview.data.Section;
import it.dex.dexshiftingview.interfaces.OnShiftListener;
import it.dex.dexshiftingview.views.DexShiftingLayout;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private OnShiftListener onShiftListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Section.SUBSECTION subsection;

    public static ContentFragment newInstance(Section.SUBSECTION subsection) {
        ContentFragment contentFragment = new ContentFragment();
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
                layout = R.layout.fragment_content_video_view;
                break;
            case IMAGE_VIEW:
                layout = R.layout.fragment_content_image_view;
                break;
            case IMAGES:
                layout = R.layout.fragment_content_images;
                break;
            case DEXMOVINGIMAGEVIEW:
                layout = R.layout.fragment_content_dexmovingimageview;
                break;
        }
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        DexShiftingLayout dexShiftingView = (DexShiftingLayout) view.findViewById(R.id.dexshiftingview);
        dexShiftingView.setOnShiftListener(onShiftListener);
        dexShiftingView.setToolbar(onShiftListener.getToolbar(), getResources().getColor(R.color.primary), getResources().getColor(R.color.secondary));
        RecyclerView recyclerView = dexShiftingView.getScrollingView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TestAdapter());
        switch (subsection) {
            case VIDEO_VIEW:
                VideoView videoView = (VideoView) view.findViewById(R.id.video);
                videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.video));
                videoView.start();
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
