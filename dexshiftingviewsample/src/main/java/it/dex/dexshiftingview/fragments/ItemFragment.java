package it.dex.dexshiftingview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.dex.dexshiftingview.R;

/**
 * DexPagerAdapter created by Diego on 08/02/2015.
 */
public class ItemFragment extends Fragment {
    public static final String PAGE_ARG = "pageArg";
    private TextView textView;

    public static ItemFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_ARG, page);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText("" + getArguments().getInt(PAGE_ARG));
    }

    public void setBackgroundColor(int color) {
        textView.setBackgroundColor(color);
    }
}
