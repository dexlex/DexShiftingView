package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexshiftingview.R;
import it.dex.dexshiftingview.fragment.DexScrollingFragment;

/**
 * DexShiftingView created by Diego on 06/03/2015.
 */
public class ContentFragment extends DexScrollingFragment {
    private TestAdapter testAdapter;

    public static ContentFragment newInstance() {
        ContentFragment contentFragment = new ContentFragment();
        return contentFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testAdapter = new TestAdapter();
        recyclerView.setAdapter(testAdapter);
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
