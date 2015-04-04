package it.dex.dexshiftingview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.dexshiftingview.R;

/**
 * DexShiftingView created by Diego on 20/03/2015.
 */


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_test, parent, false));
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
    }
}