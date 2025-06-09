package com.example.exhibitioncuratorandroid.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.viewmodel.SearchArtworkResultsViewModel;

public class SurpriseArtworkActivity extends AppCompatActivity {

    private SearchArtworkResultsViewModel viewModel;
    private Artwork curArtwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise_artwork);
        viewModel = new ViewModelProvider(this).get(SearchArtworkResultsViewModel.class);

        initialiseButtons();
        getRandomArtwork();
        viewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading != null){
                FrameLayout frameLayout = findViewById(R.id.surpriseArtworkLoadingOverlay);
                frameLayout.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void getRandomArtwork() {
        viewModel.getRandomMetArtwork().observe(this, new Observer<Artwork>() {
            @Override
            public void onChanged(Artwork artwork) {
                if(curArtwork != artwork){
                    curArtwork = artwork;
                    setViews();
                }

            }
        });
    }

    private void setViews(){
        TextView title =  findViewById(R.id.surpriseArtworkTitle);
        title.setText(curArtwork.getTitle());
        TextView description  = findViewById(R.id.surpriseArtworkDescription);
        description.setText(curArtwork.getDescription());
        ImageView image =  findViewById(R.id.surpriseArtworkImage);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Glide.with(this)
                .load(curArtwork.getImageUrl())
                .placeholder(R.drawable.ic_exhibition)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(GONE);

                        return false;
                    }
                })
                .into(image);
    }


    private void initialiseButtons() {
        Button backButton = findViewById(R.id.surpriseArtworkBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button saveButton = findViewById(R.id.surpriseArtworkSaveArtworkButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curArtwork != null) {
                    Intent intent = new Intent(SurpriseArtworkActivity.this, AddArtworkExhibitionListActivity.class);
                    ApiArtworkId apiArtworkId = new ApiArtworkId(curArtwork.getId(), curArtwork.getApiOrigin());
                    intent.putExtra("ARTWORK", apiArtworkId);
                    startActivity(intent);
                }
            }
        });

    }


}