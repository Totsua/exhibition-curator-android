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

    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initialiseButtons();
    }
    private void initialiseButtons(){
        initialiseSearch();
        initialiseSearchButton();
        initialiseRandomButton();
    }

    private void initialiseRandomButton() {
        binding.homeTabRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurpriseArtworkFragment fragment = new SurpriseArtworkFragment();

                getParentFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.homeFrameLayoutFragment,fragment)
                        .commit();

            }
        });
    }

    private void initialiseSearchButton() {
        Button searchButton = binding.homeTabSearchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSearchTab();
            }

        });
    }

    private void initialiseSearch(){
        SearchView searchView = binding.homeTabSearchView;
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                moveToSearchTab();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void moveToSearchTab(){
        SearchView searchView = binding.homeTabSearchView;
        String query = searchView.getQuery().toString();
        SearchFragment searchFragment = SearchFragment.newInstance(query);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFrameLayoutFragment,searchFragment)
                .commit();

        NavigationBarView nav = getActivity().findViewById(R.id.BottomNavView);
        nav.setOnItemSelectedListener(null);
        nav.setSelectedItemId(R.id.search); // Update Navbar UI highlight
        nav.setOnItemSelectedListener((MainActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        return binding.getRoot();
    }
}