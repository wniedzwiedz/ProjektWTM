package com.example.projektwtm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projektwtm.modele.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class AddGroupFragment extends Fragment {

    EditText paymentDeadlineET;
    EditText accountNumberET;
    EditText loginET;
    EditText pass1ET;
    EditText pass2ET;

    TextView error;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        paymentDeadlineET = getView().findViewById(R.id.editText);
        accountNumberET = getView().findViewById(R.id.editText8);
        loginET = getView().findViewById(R.id.editText13);

        final String paymentDeadline = paymentDeadlineET.getText().toString();
        final String accountNumber = accountNumberET.getText().toString();
        final String login = loginET.getText().toString();
        final String pass1 = pass1ET.getText().toString();
        final String pass2 = pass2ET.getText().toString();

        Button button = getView().findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String paymentDeadline = paymentDeadlineET.getText().toString();
                final String accountNumber = accountNumberET.getText().toString();
                final String login = loginET.getText().toString();
                final String pass1 = pass1ET.getText().toString();
                final String pass2 = pass2ET.getText().toString();

                addGroup(paymentDeadline, accountNumber, login, pass1, pass2);
            }
        });
    }




    public void addGroup(final String paymentDeadline, final String accountNumber, final String login, String pass1, String pass2) {
        error = getView().findViewById(R.id.textView62);

        if (paymentDeadline.equals("") || accountNumber.equals("") || login.equals("") || pass1.equals("") || pass2.equals("")) {
            error.setText("Empty data.");
            return;
        }
        else if (!pass1.equals(pass2)) {
            error.setText("Passwords are not the same.");
            return;
        }
//        else if (!Pattern.matches("20[2-9]{1}[0-9]{1}-[1-9]{1,2}-[1-3]{0,1}[0-9]", paymentDeadline) || !Pattern.matches("[0-9]+", accountNumber) || !Pattern.matches("[a-zA-Z0-9]", login) || !Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2)) {
//
//        }

        

        AsyncTask.execute(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups");
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("POST");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);


                    String jsonInputString = "{\"login\" : \"" + login + "\", " +
                            "\"bankAccountNumber\" : \"" + accountNumber + "\"}";

                    try {
                        OutputStream os = myConnection.getOutputStream();
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedReader bufferedReader = null;
                    try {
                        TextView error = getView().findViewById(R.id.textView62);
                        if (myConnection.getResponseCode() == 201) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());
                            // save user to sqlLite

                            error.setText("");

                            Fragment newFragment = new YourGroupFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.layout.fragment_add_group, newFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();

                        } else if (myConnection.getResponseCode() == 409) {
                            error.setText("Email already in use. Try again.");
                        } else {
                            error.setText("Server error.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}
