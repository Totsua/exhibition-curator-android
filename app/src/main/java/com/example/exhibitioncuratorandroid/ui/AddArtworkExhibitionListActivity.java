package com.example.exhibitioncuratorandroid.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.adapter.ExhibitionListAdapter;
import com.example.exhibitioncuratorandroid.adapter.RecyclerViewInterface;
import com.example.exhibitioncuratorandroid.model.ApiArtworkId;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddArtworkExhibitionListActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<Exhibition> exhibitionsList;
    private RecyclerView recyclerView;
    private ExhibitionsViewModel viewModel;
    private ExhibitionListAdapter adapter;

    private ApiArtworkId apiArtworkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_artwork_exhibition_list_actvity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
        View loadingOverlay = findViewById(R.id.addArtworkToExhibitionLoadingOverlay);

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });

        apiArtworkId = getIntent().getParcelableExtra("ARTWORK");


        initialiseBackButton();
        getAllExhibitions();

    }

    private void initialiseBackButton(){
        Button button = findViewById(R.id.addArtworkToExhibitionBackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getAllExhibitions(){
        viewModel.getAllExhibitions().observe(this, new Observer<List<Exhibition>>() {
            @Override
            public void onChanged(List<Exhibition> exhibitions) {
                exhibitionsList = (ArrayList<Exhibition>) exhibitions;
                displayInRecyclerView();
            }
        });

    }

    private void displayInRecyclerView(){
        recyclerView = findViewById(R.id.addArtworkToExhibitionRecyclerView);
        adapter = new ExhibitionListAdapter(exhibitionsList,this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Long exhibitionId = exhibitionsList.get(position).getId();
        viewModel.addArtworkToExhibition(exhibitionId,apiArtworkId);
    }
}