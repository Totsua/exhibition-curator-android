package com.example.exhibitioncuratorandroid.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

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

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.adapter.SearchArtworkResultsAdapter;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionDetailsBinding;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

import java.util.ArrayList;

public class ExhibitionDetailsFragment extends Fragment implements RecyclerViewInterface {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private FragmentExhibitionDetailsBinding binding;
    private Long exhibitionId;
    private Exhibition currentExhibition;
    private RecyclerView recyclerView;
    private ArrayList<Artwork> artworks;
    private SearchArtworkResultsAdapter adapter;
    private ExhibitionsViewModel viewModel;


    private String mParam1;

    public ExhibitionDetailsFragment() {
        // Required empty public constructor
    }

//    public static ExhibitionDetailsFragment newInstance(String param1, String param2) {
//        ExhibitionDetailsFragment fragment = new ExhibitionDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        initialiseBackButton();;

        getExhibitionDetails();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->{
            if(isLoading != null){
                binding.exhibitionsTabLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void getExhibitionDetails() {
        viewModel.getExhibitionDetails(exhibitionId).observe(getViewLifecycleOwner(), new Observer<Exhibition>() {
            @Override
            public void onChanged(Exhibition exhibition) {
                currentExhibition = exhibition;

                    artworks = (ArrayList<Artwork>) exhibition.getArtworks();
                    displayInRecyclerView();
            }
        });

    }

    private void displayInRecyclerView(){
        recyclerView = binding.exhibitionDetailsRecyclerView;
        adapter = new SearchArtworkResultsAdapter(artworks,getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    private void initialiseBackButton() {
        binding.exhibitionDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
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
       Artwork artwork = artworks.get(position);
       ApiArtworkId apiArtworkId = new ApiArtworkId(artwork.getId(),artwork.getApiOrigin());

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