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
import it.dex.dexshiftingview.interfaces.OnShiftListener;
import it.dex.dexshiftingview.views.DexShiftingLayout;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private OnShiftListener onShiftListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ContentFragment newInstance() {
        ContentFragment contentFragment = new ContentFragment();
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
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        DexShiftingLayout dexShiftingView = (DexShiftingLayout) view.findViewById(R.id.dexshiftingview);
        dexShiftingView.setOnShiftListener(onShiftListener);
        RecyclerView recyclerView = dexShiftingView.getScrollingView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TestAdapter());
        VideoView videoView = (VideoView) view.findViewById(R.id.video);
        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                + R.raw.video));
        videoView.start();
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

    private class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.adapter_test, parent, false));
        }

        @Override
        public void onBindViewHolder(TestViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }
}
