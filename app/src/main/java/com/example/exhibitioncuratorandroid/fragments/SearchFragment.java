package com.example.exhibitioncuratorandroid.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private String searchQuery;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String param1) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchQuery = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false);
        return binding.getRoot();
    }
}