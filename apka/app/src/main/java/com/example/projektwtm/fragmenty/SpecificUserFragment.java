package com.example.projektwtm.fragmenty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;

public class SpecificUserFragment extends RootFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specific_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView login = getView().findViewById(R.id.textView33);
        TextView firstname = getView().findViewById(R.id.textView36);
        TextView surname = getView().findViewById(R.id.textView38);
//        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout1);

        //przypisanie danych do text view w widoku uzytkownika
        //dodanie do linear layout grup użytkownika
    }


}
