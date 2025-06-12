package com.example.exhibitioncuratorandroid.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionArtworkDetailsBinding;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

public class ExhibitionArtworkDetailsFragment extends Fragment {
    FragmentExhibitionArtworkDetailsBinding binding;

    Long exhibitionId;
    ApiArtworkId apiArtworkId;
    Artwork artwork;
    ExhibitionsViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exhibitionId = getArguments().getLong("ID");
            artwork = getArguments().getParcelable("ARTWORK");
//            apiArtworkId = new ApiArtworkId(artwork.getId(),artwork.getApiOrigin());
        }
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initialiseButtons();
        setArtworkDetails();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null){
                binding.exhibitionArtworkDetailsLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });


        viewModel.getIsDeleted().observe(getViewLifecycleOwner(), isDeleted -> {
            if(isDeleted){
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void setArtworkDetails() {
        binding.exhibitionArtworkDetailsArtTitle.setText(artwork.getTitle());
        binding.exhibitionArtworkDetailsArtDescription.setText(artwork.getDescription());
        binding.exhibitionArtworkDetailsArtDescription.setMovementMethod(new ScrollingMovementMethod());
        binding.exhibitionArtworkDetailsImage.setContentDescription(artwork.getAltText());
        setImageView();
    }

    private void setImageView() {
        ImageView artworkImage = binding.exhibitionArtworkDetailsImage;
        Glide.with(artworkImage)
                .load(artwork.getImageUrl())
                .into(artworkImage);
    }


    private void initialiseButtons() {
        binding.exhibitionArtworkDetailsArtDeleteArtworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteArtworkFromExhibition(exhibitionId,apiArtworkId);
            }
        });
        binding.exhibitionArtworkDetailsBackButton.setOnClickListener(new View.OnClickListener() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exhibition_artwork_details, container, false);
        return binding.getRoot();
    }
}
