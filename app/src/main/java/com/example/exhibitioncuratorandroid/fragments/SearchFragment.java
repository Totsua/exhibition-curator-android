package com.example.exhibitioncuratorandroid.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.adapter.SearchArtworkResultsAdapter;
import com.example.exhibitioncuratorandroid.databinding.FragmentSearchBinding;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.ArtworkResults;
import com.example.exhibitioncuratorandroid.viewmodel.SearchArtworkResultsViewModel;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements RecyclerViewInterface {

    FragmentSearchBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private String searchQuery;
    private SearchArtworkResultsViewModel viewModel;
    private ArtworkResults searchResults;
    private ArrayList<Artwork> artworkList;
    private RecyclerView recyclerView;
    private SearchArtworkResultsAdapter adapter;
    private int counter = 1;


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
        viewModel = new ViewModelProvider(this).get(SearchArtworkResultsViewModel.class);

    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchQuery = getArguments().getString(ARG_PARAM1);
            getSearchResults(searchQuery,1);
        }
        initialiseButtons();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->{
            if(isLoading != null){
                binding.searchTabLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void initialiseButtons(){
        initialiseSearchBar();
        initialiseResultNavigationButtons();
    }

    private void initialiseSearchBar(){
        SearchView searchView = binding.searchTabSearchView;
        searchView.setQuery(searchQuery,false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // todo: if counter isn't 1, change it to 1
                //  or some sort of check to see what the page is and
                //  a reset if they make another call
                //  Should counter go to 0 if results are empty???

                searchQuery = searchView.getQuery().toString();
                counter = 1;
                getSearchResults(searchQuery,counter);
//                if(counter == 1){
////                getSearchResults(searchQuery,counter);
//
//                }else{
//                    getSearchResults(searchQuery,counter);
//                }
                searchView.setQuery(searchQuery,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initialiseResultNavigationButtons(){
        Button previousButton = binding.searchTabPrevButton;
        Button nextButton = binding.searchTabNextButton;

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter > 1){
                    counter --;
                    getSearchResults(searchQuery,counter);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter ++;
                getSearchResults(searchQuery,counter);
//                if(counter < totalPages){
//                    counter ++;
//                    getSearchResults(searchQuery,counter);
//                }
            }
        });
    }

    private void getSearchResults(String query, Integer page){
        viewModel.getArtworkSearchResults(query,page).observe(getViewLifecycleOwner(), new Observer<ArtworkResults>() {
            @Override
            public void onChanged(ArtworkResults artworkResults) {
                searchResults = artworkResults;
                artworkList = artworkResults.getArtworks();
                verifyResults(artworkResults);
            }
        });
    }

    private void isButtonEnabled(String button, Boolean isEnabled){
        switch (button){
            case"Prev":
                binding.searchTabPrevButton.setEnabled(isEnabled);
                break;
            case"Next":
                binding.searchTabNextButton.setEnabled(isEnabled);
                break;
        }
    }


    //todo: method for changing "page" text

    private void verifyResults(ArtworkResults artworkResults){
        if(artworkResults.getArtworks().isEmpty()){
            counter = 1;
            isButtonEnabled("Prev",false);
            isButtonEnabled("Next",false);
            Toast.makeText(this.getContext(), "There are no results", Toast.LENGTH_SHORT).show();
            // text "page" = "page 0/0" or make it invisible
        }else{
            if(counter != 1){
                isButtonEnabled("Prev",true);
            }
//            if(counter < artworkResults.getTotalPages())
            isButtonEnabled("Next",true);
        }
        displayInRecyclerView();
    }

    private void displayInRecyclerView(){
        recyclerView = binding.searchTabRecyclerView;
        adapter = new SearchArtworkResultsAdapter(artworkList,this.getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {

    }
}