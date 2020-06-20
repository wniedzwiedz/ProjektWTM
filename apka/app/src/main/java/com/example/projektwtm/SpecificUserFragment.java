package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpecificUserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView login = getView().findViewById(R.id.textView33);
        TextView firstname = getView().findViewById(R.id.textView36);
        TextView surname = getView().findViewById(R.id.textView38);
        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout1);

        //przypisanie danych do text view w widoku uzytkownika
        //dodanie do linear layout grup użytkownika
        
        return inflater.inflate(R.layout.fragment_specific_user, container, false);
    }
}
