package com.example.projektwtm;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class YourGroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //uzupelnienie danych grupy
        TextView groupname = getView().findViewById(R.id.textView36);
        TextView packageame = getView().findViewById(R.id.textView24);
        TextView payment = getView().findViewById(R.id.textView26);
        TextView membersCount = getView().findViewById(R.id.textView28);
//        LinearLayout linearLayout = getView().findViewById(R.id.linearLayout2);
        //wylistowanie czlonkow
        TextView error = getView().findViewById(R.id.textView50);
        ImageView image = getView().findViewById(R.id.imageView);

        Button button = getView().findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText paymentDeadlineET = getView().findViewById(R.id.editText18);
                EditText accountNumberET = getView().findViewById(R.id.editText19);
                EditText loginET = getView().findViewById(R.id.editText20);

                final String paymentDeadline = paymentDeadlineET.getText().toString();
                final String accountNumber = accountNumberET.getText().toString();
                final String login = loginET.getText().toString();

                    changeGroup(paymentDeadline,accountNumber,login);
                    TextView error = getView().findViewById(R.id.textView50);
                    error.setText("");

            }
        });

        Button button1 = getView().findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //deleteGroup(grupa z grup uzytkownika id);
                //podmiana fragmentu na widok grup uzytkownika - R.layout.fragment_search_groups
            }
        });

        return inflater.inflate(R.layout.fragment_your_group, container, false);
    }

    public void changeGroup(final String paymentDeadline, final String accountNumber, final String login) {
        TextView error = getView().findViewById(R.id.textView50);

        if (paymentDeadline.equals("") || accountNumber.equals("") || login.equals("")) {
            error.setText("Empty data");
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/"); // fragment do grup uzytkownika + .getInt("id"));
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"bankAccountNumber\" : \"" + accountNumber + "\", " +
                            "\"login\" : \"" + login + "\", }";

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
                        TextView error = getView().findViewById(R.id.textView48);

                        if (myConnection.getResponseCode() == 200) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());
                            // save user to sqlLite

                            error.setText("Data changed.");
                        } else if (myConnection.getResponseCode() == 400) {
                            error.setText("Wrong number format.");
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

    public void deleteGroup(final int id) {
        TextView error = getView().findViewById(R.id.textView50);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/groups/"); // fragment do grup uzytkownika + .getInt("id"));
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("DELETE");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"id\" : \"" + id + "\" }";

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
                        TextView error = getView().findViewById(R.id.textView48);

                        if (myConnection.getResponseCode() == 200) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());
                            // save user to sqlLite

                            error.setText("Data changed.");
                        } else if (myConnection.getResponseCode() == 400) {
                            error.setText("Wrong number format.");
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
