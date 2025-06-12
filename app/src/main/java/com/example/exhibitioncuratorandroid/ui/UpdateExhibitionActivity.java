package com.example.exhibitioncuratorandroid.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.exhibitioncuratorandroid.R;
import com.example.exhibitioncuratorandroid.model.Exhibition;
import com.example.exhibitioncuratorandroid.model.ExhibitionPatchDTO;
import com.example.exhibitioncuratorandroid.viewmodel.ExhibitionsViewModel;

public class UpdateExhibitionActivity extends AppCompatActivity {

    private ExhibitionsViewModel viewModel;
    private Exhibition curExhibition;
    EditText newNameBox;
    EditText newDescriptionBox;
    Long exhibitionId;

    public static final int RESULT_UPDATE_FRAGMENT = UpdateExhibitionActivity.RESULT_FIRST_USER + 1;
    public static final int RESULT_CLOSE_FRAGMENT = UpdateExhibitionActivity.RESULT_FIRST_USER + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_exhibition);
        exhibitionId = getIntent().getLongExtra("ID", 0L);

        viewModel = new ViewModelProvider(this).get(ExhibitionsViewModel.class);
        initialiseButtons();

        viewModel.getIsSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                setResult(UpdateExhibitionActivity.RESULT_UPDATE_FRAGMENT);
                finish();
            }
        });

        viewModel.getIsDeleted().observe(this, isDeleted -> {
            if (isDeleted) {
                setResult(RESULT_CLOSE_FRAGMENT);
                finish();
            }
        });
        newNameBox = findViewById(R.id.editExhibitionNameEditBox);
        newDescriptionBox = findViewById(R.id.editExhibitionDescriptionEditBox);

        getExhibitionDetails(exhibitionId);
    }

    private void getExhibitionDetails(Long id) {
        viewModel.getExhibitionDetails(id).observe(this, new Observer<Exhibition>() {
            @Override
            public void onChanged(Exhibition exhibition) {
                if (curExhibition == null) {
                    curExhibition = exhibition;
                    addEditBoxText();
                }
            }
        });
    }


    private void addEditBoxText() {
        newNameBox.setText(curExhibition.getTitle());
        newDescriptionBox.setText(curExhibition.getDescription());
    }

    private void initialiseButtons() {
        initialiseDeleteButton();
        initialiseUpdateButton();
        initialiseBackButton();
    }

    private void initialiseBackButton() {
        Button button = findViewById(R.id.editExhibitionBackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initialiseUpdateButton() {
        Button button = findViewById(R.id.editExhibitionUpdateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newNameBox.getText().toString().trim();
                String newDescription = newDescriptionBox.getText().toString().trim();
                boolean isValid = true;


                ExhibitionPatchDTO exhibitionChange = new ExhibitionPatchDTO();
                if (!curExhibition.getTitle().equals(newName)) {
                    if (isTitleValid(newName)) {
                        exhibitionChange.setTitle(newName);
                    } else {
                        isValid = false;
                    }
                }

                if (!curExhibition.getDescription().equals(newDescription)) {
                    if (isDescriptionValid(newDescription)) {
                        exhibitionChange.setDescription(newDescription);
                    } else {
                        isValid = false;
                    }
                }

                if (!isValid) {
                    return;
                }

                if (exhibitionChange.getDescription() == null && exhibitionChange.getTitle() == null) {
                    Toast.makeText(UpdateExhibitionActivity.this, "No changes to update", Toast.LENGTH_SHORT).show();
                    return;
                }

                viewModel.updateExhibition(curExhibition.getId(), exhibitionChange);

            }
        });
    }

    private boolean isTitleValid(String title) {
        if (title.isEmpty()) {
            newNameBox.setError("Exhibition name cannot be empty");
            return false;
        }
        if (title.length() > 80) {
            newNameBox.setError("Title must be less than 80 characters");
            return false;
        }
        return true;
    }

    private boolean isDescriptionValid(String description) {
        if (!description.isEmpty() && description.length() > 500) {
            newDescriptionBox.setError("Description must be less than 500 characters");
            return false;
        }
        return true;
    }


    private void initialiseDeleteButton() {
        Button button = findViewById(R.id.editExhibitionDeleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder delete = new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Exhibition")
                        .setMessage("Are you sure you want to delete \"" + curExhibition.getTitle() + "\" ?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewModel.deleteExhibition(curExhibition.getId());
//                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                delete.show();

            }
        });
    }


}