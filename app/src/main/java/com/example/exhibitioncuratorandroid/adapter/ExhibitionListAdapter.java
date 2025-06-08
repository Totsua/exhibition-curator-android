package com.example.exhibitioncuratorandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.databinding.ExhibitionListItemBinding;
import com.example.exhibitioncuratorandroid.model.Exhibition;

import java.util.List;

public class ExhibitionListAdapter extends RecyclerView.Adapter<ExhibitionListAdapter.ExhibitionListViewHolder> {

    List<Exhibition> exhibitionList;
    Context context;
    RecyclerViewInterface recyclerViewInterface;

    public ExhibitionListAdapter(List<Exhibition> exhibitionList, RecyclerViewInterface recyclerViewInterface, Context context) {
        this.exhibitionList = exhibitionList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ExhibitionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExhibitionListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.exhibition_list_item,
                parent,
                false);

        return new ExhibitionListViewHolder(binding,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ExhibitionListViewHolder holder, int position) {
        Exhibition exhibition = exhibitionList.get(position);
        holder.binding.setExhibition(exhibition);
    }

    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }


    public static class ExhibitionListViewHolder extends RecyclerView.ViewHolder{
        private ExhibitionListItemBinding binding;
        public ExhibitionListViewHolder(ExhibitionListItemBinding binding,RecyclerViewInterface recyclerViewInterface){
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
