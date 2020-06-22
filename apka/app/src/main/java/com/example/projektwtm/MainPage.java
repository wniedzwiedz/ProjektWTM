package com.example.projektwtm;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainPage extends AppCompatActivity {

    private CarouselFragment carouselFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            initScreen();
        } else {
            carouselFragment = (CarouselFragment) getSupportFragmentManager().getFragments().get(0);
        }
    }

    private void initScreen() {
        carouselFragment = new CarouselFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, carouselFragment) //nie wiem, czy tu nie wystarczy uzyc naszych frame layoutow
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!carouselFragment.onBackPressed()) {
            super.onBackPressed();
        } else {
        }
    }
}
