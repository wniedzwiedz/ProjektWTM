package com.example.projektwtm.fragmenty;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektwtm.Constants;
import com.example.projektwtm.DBHelper;
import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;
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

public class AddGroupFragment extends RootFragment {

    private EditText paymentDeadlineET;
    private EditText accountNumberET;
    private EditText nameET;
    private EditText maxNumberMembersET;
    private EditText infoET;

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
        nameET = getView().findViewById(R.id.editText14);
        maxNumberMembersET = getView().findViewById(R.id.editText15);
        infoET = getView().findViewById(R.id.editText13);


        Button button = getView().findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String paymentDeadline = paymentDeadlineET.getText().toString();
                final String accountNumber = accountNumberET.getText().toString();
                final String name = nameET.getText().toString();
                final String maxNumberMembers = maxNumberMembersET.getText().toString();
                final String info = infoET.getText().toString();

                addGroup(paymentDeadline, accountNumber, name, maxNumberMembers, info);
            }
        });
    }


    public void addGroup(final String paymentDeadline, final String accountNumber, final String name, final String maxNumberMembers, final String info) {
        error = getView().findViewById(R.id.textView62);

        if (paymentDeadline.equals("") || accountNumber.equals("") || name.equals("") || maxNumberMembers.equals("") || info.equals("")) {
            error.setText("Empty data.");
            return;
        }

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

                    String jsonInputString = "{\"name\" : \"" + name + "\", " +
                            "\"owner\" : {\"id\" : " + id + "}, " +
                            "\"aPackage\" : {\"id\" : " + SearchPackagesFragment.packID + "}, " +
                            "\"maxNumberOfMembers\" : " + maxNumberMembers + ", " +
                            "\"information\" : \"" + info + "\", " +
                            "\"paymentInfo\" : \"" + paymentDeadline + "\", " +
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

                        Fragment newFragment = new SearchAppsFragment();
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_mainLayout, newFragment);
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
