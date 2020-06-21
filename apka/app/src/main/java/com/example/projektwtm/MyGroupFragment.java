package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //wyswietlenie odpowiednich danych o grupie
        TextView groupname = getView().findViewById(R.id.textView6);
        TextView packagename = getView().findViewById(R.id.textView24);
        TextView payment = getView().findViewById(R.id.textView26);
        TextView deadline = getView().findViewById(R.id.textView2);
        TextView bank = getView().findViewById(R.id.textView21);
        TextView membersCount = getView().findViewById(R.id.textView28);
        TextView login = getView().findViewById(R.id.textView23);
        TextView password = getView().findViewById(R.id.textView31);

        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout6);
        //dodanie czlonkow do linear layout

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
