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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.adapter.SearchArtworkResultsAdapter;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionDetailsBinding;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.ui.UpdateExhibitionActivity;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

import java.util.ArrayList;

public class ExhibitionDetailsFragment extends Fragment implements RecyclerViewInterface {

    private FragmentExhibitionDetailsBinding binding;
    private Long exhibitionId;
    private String title;
    private Exhibition currentExhibition;
    private RecyclerView recyclerView;
    private ArrayList<Artwork> artworks = new ArrayList<>();
    private SearchArtworkResultsAdapter adapter;
    private ExhibitionsViewModel viewModel;
    private boolean hasShownEmptyToast = false;
    private ArrayList<Artwork> filteredList;
    private ArrayList<Artwork> displayedList;
    private String currentQuery = "";
    private ActivityResultLauncher<Intent> backendRequestLauncher;


    public ExhibitionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           exhibitionId = getArguments().getLong("ID");
        }
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getExhibitionDetails();
        initialiseBackButton();
        initialiseEditButton();
        initialiseSearchBar();
        if(title != null){
            setTitleText();
        }



        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->{
            if(isLoading != null){
                binding.exhibitionsTabLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });

        backendRequestLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.e("EXHIBITION DETAILS", String.valueOf(result.getResultCode()));
                    if(result.getResultCode() == UpdateExhibitionActivity.RESULT_UPDATE_FRAGMENT){
                        Log.e("EXHIBITION DETAILS", "UPDATING UPDATING UPDATING");
                        getExhibitionDetails();
                    } else if (result.getResultCode() == UpdateExhibitionActivity.RESULT_CLOSE_FRAGMENT) {
                        getParentFragmentManager().popBackStack();
                    }
                }
        );


    }

    private void initialiseSearchBar() {
        binding.exhibitionDetailsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterList(query);
                return true;
            }
        });
    }

    private void initialiseEditButton() {
        binding.exhibitionDetailsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateExhibitionActivity.class);
                intent.putExtra("ID", currentExhibition.getId());
                backendRequestLauncher.launch(intent);
            }
        });
    }

    private void initialiseBackButton() {
        binding.exhibitionDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void setTitleText() {
        binding.exhibitionDetailsTitle.setText(title);
    }

    private void getExhibitionDetails() {
        viewModel.getExhibitionDetails(exhibitionId).observe(getViewLifecycleOwner(), new Observer<Exhibition>() {
            @Override
            public void onChanged(Exhibition exhibition) {
                currentExhibition = exhibition;

                    artworks = (ArrayList<Artwork>) exhibition.getArtworks();

                if(artworks.isEmpty()) {
                    if (!hasShownEmptyToast) {
                        Toast.makeText(getContext(), "There are no artworks", Toast.LENGTH_SHORT).show();
                        hasShownEmptyToast = true;
                    }else {
                        hasShownEmptyToast = false;
                    }
                }

                title = currentExhibition.getTitle();
                setTitleText();

                if(!currentQuery.isEmpty()){
                    binding.exhibitionDetailsSearchView.setQuery(currentQuery,false);
                    displayedList = getFilteredList(currentQuery);
                }else{
                    displayedList = artworks;
                }
                    displayInRecyclerView();
            }
        });

    }

    private void filterList(String query){
        currentQuery = query;
        if(artworks.isEmpty() || adapter == null){return;}

        filteredList = getFilteredList(query);

        displayedList = filteredList;
        adapter.setFilterList(displayedList);
    }

    private ArrayList<Artwork> getFilteredList(String query){
        filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for(Artwork artwork: artworks){
            if(artwork.getTitle().toLowerCase().contains(lowerQuery) ||
                    artwork.getArtist().getName().toLowerCase().contains(lowerQuery)){
                filteredList.add(artwork);
            }
        }
        return filteredList;
    }

    private void displayInRecyclerView(){
        recyclerView = binding.exhibitionDetailsRecyclerView;
        adapter = new SearchArtworkResultsAdapter(displayedList,getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_exhibition_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
       Artwork artwork = displayedList.get(position);
//       ApiArtworkId apiArtworkId = new ApiArtworkId(artwork.getId(),artwork.getApiOrigin());

       Bundle bundle = new Bundle();
       bundle.putLong("ID", exhibitionId);

       bundle.putParcelable("ARTWORK",artwork);


       ExhibitionArtworkDetailsFragment fragment = new ExhibitionArtworkDetailsFragment();
       fragment.setArguments(bundle);
       getParentFragmentManager().beginTransaction()
               .replace(R.id.homeFrameLayoutFragment, fragment)
               .addToBackStack(null)
               .commit();

    }
}