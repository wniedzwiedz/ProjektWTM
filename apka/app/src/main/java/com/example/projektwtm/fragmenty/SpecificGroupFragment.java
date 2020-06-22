package com.example.projektwtm.fragmenty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;

public class SpecificGroupFragment extends RootFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
    }


    
}
