package com.example.exhibitioncuratorandroid;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exhibitioncuratorandroid.fragments.ExhibitionsFragment;
import com.example.exhibitioncuratorandroid.fragments.HomeFragment;
import com.example.exhibitioncuratorandroid.fragments.SearchFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    NavigationBarView navigationBarView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    ExhibitionsFragment exhibitionsFragment = new ExhibitionsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        navigationBarView = findViewById(R.id.BottomNavView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == R.id.home){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayoutFragment, homeFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.search){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayoutFragment, searchFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.exhibitions){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayoutFragment, exhibitionsFragment)
                    .commit();
            return true;
        }
        return false;
    }
}