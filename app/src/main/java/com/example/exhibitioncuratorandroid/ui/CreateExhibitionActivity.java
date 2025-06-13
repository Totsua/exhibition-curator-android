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
import com.example.exhibitioncuratorandroid.model.ExhibitionCreateDTO;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

public class CreateExhibitionActivity extends AppCompatActivity {

    private ExhibitionsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exhibition);
        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
        EditText editText = findViewById(R.id.createExhibitionEditText);
        EditText descriptionText = findViewById(R.id.createExhibitionDescriptionEditBox);
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

        setBackButton();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText.getText().toString().trim();
                String description = descriptionText.getText().toString().trim();


                //todo: extract validation logic to own method
                boolean isValid = true;

                ExhibitionCreateDTO createDTO = new ExhibitionCreateDTO();

                if(isValidName(title)){
                    createDTO.setTitle(title);
                }
                else {
                    editText.setError("Name must be 1–80 characters and not blank");
                    isValid = false;
                }

                if(isValidDescription(description)){
                    if(!description.isEmpty()){
                        createDTO.setDescription(description);
                    }
                }else {
                    descriptionText.setError("Description must be less than 500 characters");
                    isValid = false;
                }

                if(isValid){
                    createExhibition(createDTO);
                }

            }
        });
    }

    private void setBackButton() {
        Button backButton = findViewById(R.id.createExhibitionBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void createExhibition(ExhibitionCreateDTO createDTO) {
        viewModel.createExhibition(createDTO);
    }

    private boolean isValidName(String input) {
        return input != null
                && !input.isEmpty()
                && input.length() >= 1
                && input.length() <= 80;
    }

    private boolean isValidDescription(String input){
        return input.isEmpty() || input.length() <= 500;
    }


}