package com.example.exhibitioncuratorandroid.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.exhibitioncuratorandroid.ui.MainActivity;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationBarView;


public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initialiseButtons();
    }
    private void initialiseButtons(){
        initialiseSearch();
    }

    private void initialiseSearch(){
        SearchView searchView = binding.homeTabSearchView;
        searchView.clearFocus();

        Button searchButton = binding.homeTabSearchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchView.getQuery().toString();
                SearchFragment searchFragment = SearchFragment.newInstance(query);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeFrameLayoutFragment,searchFragment)
                        .commit();

                NavigationBarView nav = getActivity().findViewById(R.id.BottomNavView);
                nav.setOnItemSelectedListener(null);
                nav.setSelectedItemId(R.id.search); // Update UI highlight only
                nav.setOnItemSelectedListener((MainActivity) getActivity());

            }

        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        return binding.getRoot();
    }
}