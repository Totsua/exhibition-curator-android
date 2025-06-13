package com.example.exhibitioncuratorandroid.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.ExhibitionListAdapter;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionsBinding;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.ui.CreateExhibitionActivity;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExhibitionsFragment extends Fragment implements RecyclerViewInterface {

    FragmentExhibitionsBinding binding;
    private ExhibitionsViewModel viewModel;
    private RecyclerView recyclerView;
    private ArrayList<Exhibition> exhibitionsList;
    private ArrayList<Exhibition> filteredList;
    private ArrayList<Exhibition> displayedList;
    private String currentQuery = "";
    private ExhibitionListAdapter adapter;

    private ActivityResultLauncher<Intent> backendRequestLauncher;


    public ExhibitionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backendRequestLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == CreateExhibitionActivity.RESULT_OK){
                        viewModel.getAllExhibitions();
                    }
                }
        );

        initialiseButtons();
        getAllExhibitions();
        initialiseSearchBar();
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->{
            if(isLoading != null){
                binding.exhibitionsTabLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void initialiseSearchBar(){
        binding.exhibitionsTabSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterList(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
    }

    private void initialiseButtons() {

        binding.exhibitionsTabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateExhibitionActivity.class);
                backendRequestLauncher.launch(intent);
            }
        });
    }

    private void filterList(String query){
        currentQuery = query;
        if(exhibitionsList.isEmpty() || adapter == null){return;}

        filteredList = getFilteredList(query);

        displayedList = filteredList;
        adapter.setFilterList(displayedList);
    }


    private ArrayList<Exhibition> getFilteredList(String query){
        filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for(Exhibition exhibition: exhibitionsList){
            if(exhibition.getTitle().toLowerCase().contains(lowerQuery)){
                filteredList.add(exhibition);
            }
        }
        return filteredList;
    }


    private void getAllExhibitions(){
        viewModel.getAllExhibitions().observe(getViewLifecycleOwner(), new Observer<List<Exhibition>>() {
            @Override
            public void onChanged(List<Exhibition> exhibitions) {
                exhibitionsList = (ArrayList<Exhibition>) exhibitions;
                binding.exhibitionsTabRecyclerOverlay.setVisibility(exhibitionsList.isEmpty() ? VISIBLE : GONE);

                if(!currentQuery.isEmpty()){
                    binding.exhibitionsTabSearchView.setQuery(currentQuery,false);
                    displayedList = getFilteredList(currentQuery);
                }else{
                    displayedList = exhibitionsList;
                }

                displayInRecyclerView();
            }
        });
    }

    public void displayInRecyclerView(){
        recyclerView = binding.exhibitionsTabRecyclerView;
        adapter = new ExhibitionListAdapter(displayedList,this,this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_exhibitions, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
        }

    @Override
    public void onItemClick(int position) {
        Exhibition exhibition = displayedList.get(position);

        Bundle bundle1 = new Bundle();
        bundle1.putLong("ID",exhibition.getId());

        ExhibitionDetailsFragment fragment = new ExhibitionDetailsFragment();
        fragment.setArguments(bundle1);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFrameLayoutFragment,fragment)
                .addToBackStack(null)
                .commit();
    }
}