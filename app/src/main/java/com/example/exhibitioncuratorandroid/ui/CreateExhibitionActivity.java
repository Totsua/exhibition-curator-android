package com.example.exhibitioncuratorandroid.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

public class CreateExhibitionActivity extends AppCompatActivity {

    private ExhibitionsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exhibition);
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
        EditText editText = findViewById(R.id.createExhibitionEditText);
        Button button = findViewById(R.id.createExhibitionButton);
        View loadingOverlay = findViewById(R.id.createExhibitionLoadingOverlay);

        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });

        viewModel.getIsSuccessful().observe(this, isSuccessful -> {
            if(isSuccessful){
                setResult(CreateExhibitionActivity.RESULT_OK);
                finish();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if(isValidName(text)){
                    createExhibition(text);
                }
                else {
                    editText.setError("Name must be 1–80 characters and not blank");
                }


            }
        });
    }


    private void createExhibition(String text) {
        viewModel.createExhibition(text);
    }
    private boolean isValidName(String input) {
        return input != null
                && !input.trim().isEmpty()
                && input.length() >= 1
                && input.length() <= 80;
    }
}