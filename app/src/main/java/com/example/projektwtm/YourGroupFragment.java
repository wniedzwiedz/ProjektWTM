package com.example.projektwtm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.regex.Pattern;

public class YourGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_your_group, container, false);
    }


//    EditText paymentDeadlineET = findViewById(R.id.editText18);
//    EditText accountNumberET = findViewById(R.id.editText19);
//    EditText loginET = findViewById(R.id.editText20);
//    EditText pass1ET = findViewById(R.id.editText21);
//    EditText pass2ET = findViewById(R.id.editText22);
//
//    final String paymentDeadline = paymentDeadlineET.getText().toString();
//    final String accountNumber = accountNumberET.getText().toString();
//    final String login = loginET.getText().toString();
//    final String pass1 = pass1ET.getText().toString();
//    final String pass2 = pass2ET.getText().toString();

//    Button button = findViewById(R.id.button13);
//        button.setOnClickListener(new View.OnClickListener(){
//        public void onClick(View v){
//                boolean change = changeGroup(paymentDeadline, accountNumber, login, pass1, pass2);
//                if (log) {
//modyfikacja danych
//                    TextView error = findViewById(R.id.textView50);
//                    error.setText("");
//podmiana fragmentu na ten sam
//                }
//                else {
//                    TextView error = findViewById(R.id.textView50);
//                    error.setText("Incorrect data. Try again.");
//podmiana fragmentu na ten sam
//                }
//        }
//    });


//    public boolean changeGroup(String paymentDeadline, String accountNumber, String login, String pass1, String pass2) {
//        if (paymentDeadline.equals("") || accountNumber.equals("") || login.equals("") || pass1.equals("") || pass2.equals("")) {
//            return false;
//        }
//        else if (!pass1.equals(pass2)) {
//            return false;
//        }
//        else if (!Pattern.matches("20[2-9]{1}[0-9]{1}-[1-9]{1,2}-[1-3]{0,1}[0-9]", paymentDeadline) || !Pattern.matches("[0-9]+", accountNumber) || !Pattern.matches("[a-zA-Z0-9]", login) || !Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2)) {
//            return false;
//        }
//
//        return true;
//    }
}
