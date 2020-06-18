package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpecificGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_group, container, false);
    }

//    Button button = findViewById(R.id.button8);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//dodanie grupy uzytkownikowi
//podmiana fragmentu na widok grupy, do ktorej sie teraz nalezy
//    });
//    }
    
}
