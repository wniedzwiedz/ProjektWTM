package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //wyswietlenie odpowiednich danych o grupie

       Button button = getView().findViewById(R.id.button5);
       button.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            //usuniecie uzytkownika z grupy
            //podmiana fragmentu na widok grup uzytkownika
        }
        });

        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }
}
