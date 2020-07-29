package com.example.lesson48.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson48.R;

public class NoConnectionFragment extends Fragment {

    private AppCompatButton btnReload;
    private Refresher refresher;

    public NoConnectionFragment() {
        // Required empty public constructor
    }

    public static NoConnectionFragment newInstance() {
        return new NoConnectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getContext() instanceof Refresher) {
                refresher = (Refresher)getContext();
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_no_connection, container, false);
        findIds(v);

        btnReload.setOnClickListener(view -> refresher.onRefresh());

        return v;
    }

    private void findIds(View v) {
        btnReload = v.findViewById(R.id.btn_no_connection_try_again);
    }

    public interface Refresher {
        void onRefresh();
    }
}