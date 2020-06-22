package com.example.projektwtm.fragmenty;

import android.annotation.SuppressLint;
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

import com.example.projektwtm.Constants;
import com.example.projektwtm.DBHelper;
import com.example.projektwtm.R;
import com.example.projektwtm.modele.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddGroupFragment extends Fragment {

    private EditText paymentDeadlineET;
    private EditText accountNumberET;
    private EditText loginET;
//    private EditText pass1ET;
//    private EditText pass2ET;

    private TextView error;

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

        Button button = getView().findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String paymentDeadline = paymentDeadlineET.getText().toString();
                final String accountNumber = accountNumberET.getText().toString();
                final String login = loginET.getText().toString();
//                final String pass1 = pass1ET.getText().toString();
//                final String pass2 = pass2ET.getText().toString();

                addGroup(paymentDeadline, accountNumber, login);//, pass1, pass2);
            }
        });
    }


    public void addGroup(final String paymentDeadline, final String accountNumber, final String login) {//, String pass1, String pass2) {
        error = getView().findViewById(R.id.textView62);

        if (paymentDeadline.equals("") || accountNumber.equals("") || login.equals("")) {// || pass1.equals("") || pass2.equals("")) {
            error.setText("Empty data.");
            return;
        }
//        else if (!pass1.equals(pass2)) {
//            error.setText("Passwords are not the same.");
//            return;
//        }
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

                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> users = new ArrayList<>();
                    users.addAll(dbHelper.getUser());
                    int id = users.get(0).getId();

                    String jsonInputString = "{\"name\" : \"" + login + "\", " +
                            "\"owner\" : {\"id\" : " + id + "}, " +
                            "\"package\" : {\"id\" : " + SearchPackagesFragment.packID + "}, " +
                            "\"bankAccountNumber\" : \"" + accountNumber + "\"}";


                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();


                    BufferedReader bufferedReader = null;

                        TextView error = getView().findViewById(R.id.textView62);
                        if (myConnection.getResponseCode() == 201) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());
                            error.setText("");

                            Fragment newFragment = new YourGroupFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.layout.fragment_add_group, newFragment);
                            transaction.addToBackStack(null);

                            transaction.commit();

                        } else {
                            error.setText("Server error.");
                        }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}
