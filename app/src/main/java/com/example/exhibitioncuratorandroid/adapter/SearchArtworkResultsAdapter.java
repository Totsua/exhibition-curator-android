package com.example.exhibitioncuratorandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.ArtworkSearchResultItemBinding;
import com.example.exhibitioncuratorandroid.model.Artwork;
import com.example.exhibitioncuratorandroid.model.ArtworkResults;

import java.util.List;

public class SearchArtworkResultsAdapter extends RecyclerView.Adapter<SearchArtworkResultsAdapter.SearchArtworkResultsViewHolder> {

    ArtworkResults artworkResults;

    List<Artwork> artworkList;
    Context context;
    RecyclerViewInterface recyclerViewInterface;

    public SearchArtworkResultsAdapter(List<Artwork> artworkList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.artworkList = artworkList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public SearchArtworkResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArtworkSearchResultItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.artwork_search_result_item,
                parent,
                false);

        return new SearchArtworkResultsViewHolder(binding,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchArtworkResultsViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.binding.setArtwork(artwork);

    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }

    public static class SearchArtworkResultsViewHolder extends RecyclerView.ViewHolder{
        private ArtworkSearchResultItemBinding binding;
        public SearchArtworkResultsViewHolder(ArtworkSearchResultItemBinding binding,RecyclerViewInterface recyclerViewInterface){
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
