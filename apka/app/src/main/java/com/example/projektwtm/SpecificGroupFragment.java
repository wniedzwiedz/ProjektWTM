package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SpecificGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView groupname = getView().findViewById(R.id.textView6);
            TextView packagename = getView().findViewById(R.id.textView24);
            TextView payment = getView().findViewById(R.id.textView26);
            TextView membersCount = getView().findViewById(R.id.textView28);
            
            ImageView image = getView().findViewById(R.id.imageView);
        
            Button button = getView().findViewById(R.id.button8);
            button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //dodanie uzytkownika do grupy
                //podmiana fragmentu na widok grupy, do ktorej sie teraz nalezy, czyli ten bardziej szczegolowy widok - R.layout.fragment_my_group
            }
             });

        return inflater.inflate(R.layout.fragment_specific_group, container, false);
    }
    
}
