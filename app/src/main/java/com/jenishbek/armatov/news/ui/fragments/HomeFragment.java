package com.jenishbek.armatov.news.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jenishbek.armatov.news.R;
import com.jenishbek.armatov.news.ui.adapters.HomeRecycleViewAdapter;
import com.jenishbek.armatov.news.ui.adapters.NewsPageAdapter;

public class HomeFragment extends Fragment {
    public static RecyclerView homeRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRV = (RecyclerView) root.findViewById(R.id.recycle_view_home);
        HomeRecycleViewAdapter adapter = new HomeRecycleViewAdapter(getContext());
        homeRV.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        homeRV.setLayoutManager(llm);



        return root;
    }
}