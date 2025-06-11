package com.example.exhibitioncuratorandroid.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.FragmentSurpriseArtworkBinding;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.ui.AddArtworkExhibitionListActivity;
import com.example.exhibitioncuratorandroid.viewmodel.SearchArtworkResultsViewModel;

public class SurpriseArtworkFragment extends Fragment {
    private Artwork curArtwork;
    private SearchArtworkResultsViewModel viewModel;
    private FragmentSurpriseArtworkBinding binding;



    public SurpriseArtworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchArtworkResultsViewModel.class);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        initialiseButtons();
        getRandomArtwork();
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null){
                binding.surpriseArtworkLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void initialiseButtons() {
        initialiseBackButton();
        initialiseSaveButton();
    }

    private void initialiseBackButton() {
        binding.surpriseArtworkBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void initialiseSaveButton() {
        binding.surpriseArtworkSaveArtworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddArtworkExhibitionListActivity.class);
                ApiArtworkId artworkId = new ApiArtworkId(curArtwork.getId(), curArtwork.getApiOrigin());
                intent.putExtra("ARTWORK", artworkId);
                startActivity(intent);
            }
        });
    }

    private void getRandomArtwork() {
        viewModel.getRandomMetArtwork().observe(getViewLifecycleOwner(), new Observer<Artwork>() {
            @Override
            public void onChanged(Artwork artwork) {
                    curArtwork  = artwork;
                    setViews();
            }
        });
    }

    private void setViews() {
        binding.surpriseArtworkTitle.setText(curArtwork.getTitle());
        binding.surpriseArtworkDescription.setText(curArtwork.getDescription());
        ImageView imageView =  binding.surpriseArtworkImage;
        imageView.setContentDescription(curArtwork.getAltText());


        Glide.with(this)
                .load(curArtwork.getImageUrl())
                .placeholder(R.drawable.ic_exhibition)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.progressBar.setVisibility(GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBar.setVisibility(GONE);
                        return false;
                    }
                }).into(imageView);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_surprise_artwork, container, false );
        return binding.getRoot();
    }
}