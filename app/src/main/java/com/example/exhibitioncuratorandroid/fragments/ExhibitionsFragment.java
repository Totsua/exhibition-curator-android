package com.example.exhibitioncuratorandroid.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.databinding.FragmentExhibitionsBinding;

public class ExhibitionsFragment extends Fragment implements RecyclerViewInterface {

    FragmentExhibitionsBinding binding;

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
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseButtons();
    }

    private void initialiseButtons() {
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
        
    }
}