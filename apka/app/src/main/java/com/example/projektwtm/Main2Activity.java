//package com.example.projektwtm;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//
//public class Main2Activity extends AppCompatActivity {
//
//    private MyFragment myFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_carousel);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//
//        if (savedInstanceState == null) {
//            initScreen();
//        } else {
//            myFragment = (MyFragment) getSupportFragmentManager().getFragments().get(0);
//        }
//    }
//
//    private void initScreen() {
//        myFragment = new MyFragment();
//
//        final FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, myFragment) //nie wiem, czy tu nie wystarczy uzyc naszych frame layoutow
//                .commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (!myFragment.onBackPressed()) {
//            super.onBackPressed();
//        } else {
//        }
//    }
//}
