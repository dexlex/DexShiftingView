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
    private RecyclerView recyclerView;
    private TestAdapter testAdapter;
    private int firstItemHeight = 600;

    public static ContentFragment newInstance() {
        ContentFragment contentFragment = new ContentFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testAdapter = new TestAdapter();
        recyclerView.setAdapter(testAdapter);
        recyclerView.setPadding(0, firstItemHeight, 0, 0);
    }

    @Override
    protected View getScrollableView() {
        return recyclerView;
    }

    @Override
    public void setPadding(int padding) {
        firstItemHeight = padding;
        testAdapter.notifyDataSetChanged();
        recyclerView.setPadding(0, firstItemHeight, 0, 0);
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            switch (viewType) {
//                case 0:
//                    View view = new View(getActivity());
//                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, firstItemHeight));
//                    return new TestViewHolder(view);
//                default:
            return new TestViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.adapter_test, parent, false));
//            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
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