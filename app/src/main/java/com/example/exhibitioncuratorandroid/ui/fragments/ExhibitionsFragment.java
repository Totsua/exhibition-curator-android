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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.ExhibitionListAdapter;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionsBinding;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExhibitionsFragment extends Fragment implements RecyclerViewInterface {

    FragmentExhibitionsBinding binding;
    private ExhibitionsViewModel viewModel;
    private RecyclerView recyclerView;
    private ArrayList<Exhibition> exhibitionsList;
    private ExhibitionListAdapter adapter;

    public ExhibitionsFragment() {
        // Required empty public constructor
    }

    /*public static ExhibitionsFragment newInstance(String param1, String param2) {
        ExhibitionsFragment fragment = new ExhibitionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseButtons();
        getAllExhibitions();
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->{
            if(isLoading != null){
                binding.exhibitionsTabLoadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });
    }

    private void initialiseButtons() {

        binding.exhibitionsTabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void getAllExhibitions(){
        viewModel.getAllExhibitions().observe(getViewLifecycleOwner(), new Observer<List<Exhibition>>() {
            @Override
            public void onChanged(List<Exhibition> exhibitions) {
                exhibitionsList = (ArrayList<Exhibition>) exhibitions;
                displayInRecyclerView();
            }
        });
    }

    public void displayInRecyclerView(){
        recyclerView = binding.exhibitionsTabRecyclerView;
        adapter = new ExhibitionListAdapter(exhibitionsList,this,this.getContext());
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
        Exhibition exhibition = exhibitionsList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("EXHIBITION",exhibition);
    }
}