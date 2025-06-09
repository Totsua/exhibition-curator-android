package com.example.exhibitioncuratorandroid.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.FragmentArtworkDetailsBinding;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.ui.AddArtworkExhibitionListActvity;

public class ArtworkDetailsFragment extends Fragment {


    FragmentArtworkDetailsBinding binding;
    Artwork currentArtwork;
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public ArtworkDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ArtworkDetailsFragment newInstance(String param1, String param2) {
        ArtworkDetailsFragment fragment = new ArtworkDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentArtwork = getArguments().getParcelable("ARTWORK");
        }
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setArtworkDetails();
        initialiseButton();
    }

    private void initialiseButton(){
        binding.artworkDetailsArtSaveArtworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentArtwork != null) {
                    Intent intent = new Intent(getContext(), AddArtworkExhibitionListActvity.class);
                    ApiArtworkId apiArtworkId = new ApiArtworkId(currentArtwork.getId(), currentArtwork.getApiOrigin());
                    intent.putExtra("ARTWORK", apiArtworkId);
                    startActivity(intent);
                }
            }
        });
    }
    private void setArtworkDetails() {
        binding.artworkDetailsArtTitle.setText(currentArtwork.getTitle());
        binding.artworkDetailsArtDescription.setText(currentArtwork.getDescription());
        binding.artworkDetailsArtDescription.setMovementMethod(new ScrollingMovementMethod());
        binding.artworkDetailsImage.setContentDescription(currentArtwork.getAltText());
        setImageView();
    }

    private void setImageView() {
        ImageView artworkImage = binding.artworkDetailsImage;
        Glide.with(artworkImage)
                .load(currentArtwork.getImageUrl())
                .into(artworkImage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_artwork_details, container, false);
        return binding.getRoot();
    }
}