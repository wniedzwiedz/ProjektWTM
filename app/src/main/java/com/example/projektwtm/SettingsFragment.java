package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

//    EditText firstnameET = findViewById(R.id.editText12);
//    EditText surnameET = findViewById(R.id.editText16);
//    EditText emailET = findViewById(R.id.editText17);
//    EditText pass1ET = findViewById(R.id.editText11);
//    EditText pass2ET = findViewById(R.id.editText10);
//
//    final String firstname = firstnameET.getText().toString();
//    final String surname = surnameET.getText().toString();
//    final String email = email.getText().toString();
//    final String pass1 = pass1ET.getText().toString();
//    final String pass2 = pass2ET.getText().toString();

//    Button button = findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//                boolean nameChange = changeFirstname(firstname);
//                if (nameChange) {
// zmiana imienia
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("");
//podmiana fragmentu
//                }
//                else {
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("Incorrect data. Try again.");
//podmiana fragmentu na ten sam
//                }
//        }
//    });

//    Button button = findViewById(R.id.button7);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//                boolean surnameChange = changeSurname(surname);
//                if (surnameChange) {
// zmiana imienia
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("");
//podmiana fragmentu
//                }
//                else {
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("Incorrect data. Try again.");
//podmiana fragmentu na ten sam
//                }
//        }
//    });

//    Button button = findViewById(R.id.button9);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//                boolean emailChange = changeEmail(email);
//                if (emailChange) {
// zmiana imienia
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("");
//podmiana fragmentu
//                }
//                else {
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("Incorrect data. Try again.");
//podmiana fragmentu na ten sam
//                }
//        }
//    });

//    Button button = findViewById(R.id.button10);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//                boolean passChange = changePassword(pass1, pass2);
//                if (emailChange) {
// zmiana imienia
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("");
//podmiana fragmentu
//                }
//                else {
//                    TextView error = findViewById(R.id.textView48);
//                    error.setText("Incorrect data. Try again.");
//podmiana fragmentu na ten sam
//                }
//        }
//    });

//    public boolean changeFirstname(String firstname) {
//        if (firstname.equals("")) {
//            return false;
//        }
//        else if (!Pattern.matches("[A-Z][a-z]+", firstname)) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean changeSurname(String surname) {
//        if (surname.equals("")) {
//            return false;
//        }
//        else if (!Pattern.matches("[A-Z][a-z]+", surname)) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean changeEmail(String email) {
//        if (email.equals("")) {
//            return false;
//        }
//        else if (!Pattern.matches("[A-Za-z]+[1-9_]*[@]{1}[A-Za-z1-9]+[.]{1}[a-z]+[.]{0,1}[a-z]+}", email)) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean changePassword(String pass1, String pass2) {
//        if (pass1.equals("") || pass2.equals("")) {
//            return false;
//        }
//        else if (!pass1.equals(pass2)) {
//            return false;
//        }
//        else if (!Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2)) {
//            return false;
//        }
//        return true;
//    }
    

//    Button button = findViewById(R.id.button11);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//wylogowanie
//podmiana widoku
//    });
//    }

}
