package com.example.projektwtm.fragmenty;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projektwtm.Constants;
import com.example.projektwtm.DBHelper;
import com.example.projektwtm.Encryption;
import com.example.projektwtm.LoginActivity;
import com.example.projektwtm.R;
import com.example.projektwtm.RootFragment;
import com.example.projektwtm.modele.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SettingsFragment extends RootFragment {

    private EditText firstnameET;
    private EditText surnameET;
    private EditText emailET;
    private EditText pass1ET;
    private EditText pass2ET;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        firstnameET = getView().findViewById(R.id.editText12);
        surnameET = getView().findViewById(R.id.editText16);
        emailET = getView().findViewById(R.id.editText17);
        pass1ET = getView().findViewById(R.id.editText11);
        pass2ET = getView().findViewById(R.id.editText10);

        Button button = getView().findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String firstname = firstnameET.getText().toString();
                changeFirstname(firstname);
            }
        });

        Button button1 = getView().findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String surname = surnameET.getText().toString();
                changeSurname(surname);
            }
        });

        Button button2 = getView().findViewById(R.id.button9);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String email = emailET.getText().toString();
                changeEmail(email);
            }
        });

        Button button3 = getView().findViewById(R.id.button10);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String pass1 = pass1ET.getText().toString();
                final String pass2 = pass2ET.getText().toString();
                changePassword(pass1, pass2);
            }
        });

        Button button4 = getView().findViewById(R.id.button11);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //wylogowanie
                DBHelper dbHelper = new DBHelper(getContext());
                try {
                    List<User> user = dbHelper.getUser();
                    int id = user.get(0).getId();
                    dbHelper.deleteUserById(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public void changeFirstname(final String firstname) {
        TextView error = getView().findViewById(R.id.textView48);

        if (firstname.equals("")) {
            error.setText("Empty firstname");
            return;
        } else if (!Pattern.matches("[A-Z][a-z]+", firstname)) {
            error.setText("Incorrect firstname.");
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> user = dbHelper.getUser();
                    int id = user.get(0).getId();
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + id);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"firstName\" : \"" + firstname + "\" } ";
                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();

                    TextView error = getView().findViewById(R.id.textView48);
                    BufferedReader bufferedReader = null;

                    if (myConnection.getResponseCode() == 200) {
                        bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        user.get(0).setFirstName(firstname);
                        dbHelper.createOrUpdateUser(user.get(0));

                        error.setText("Firstname changed.");
                    } else if (myConnection.getResponseCode() == 400) {
                        error.setText("Wrong number format.");
                    } else {
                        error.setText("Server error.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void changeSurname(final String surname) {
        TextView error = getView().findViewById(R.id.textView48);

        if (surname.equals("")) {
            error.setText("Empty surname.");
            return;
        } else if (!Pattern.matches("[A-Z][a-z]+", surname)) {
            error.setText("Incorrect surname.");
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> user = dbHelper.getUser();
                    int id = user.get(0).getId();
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + id);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"lastName\" : \"" + surname + "\" } ";
                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();

                    TextView error = getView().findViewById(R.id.textView48);
                    BufferedReader bufferedReader = null;

                    if (myConnection.getResponseCode() == 200) {
                        bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        user.get(0).setLastName(surname);
                        dbHelper.createOrUpdateUser(user.get(0));

                        error.setText("Surname changed.");
                    } else if (myConnection.getResponseCode() == 400) {
                        error.setText("Wrong number format.");
                    } else {
                        error.setText("Server error.");
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changeEmail(final String email) {
        TextView error = getView().findViewById(R.id.textView48);

        if (email.equals("")) {
            error.setText("Empty email.");
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> user = dbHelper.getUser();
                    int id = user.get(0).getId();
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + id);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"email\" : \"" + email + "\" } ";
                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();

                    TextView error = getView().findViewById(R.id.textView48);
                    BufferedReader bufferedReader = null;

                    if (myConnection.getResponseCode() == 200) {
                        bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        user.get(0).setEmail(email);
                        dbHelper.createOrUpdateUser(user.get(0));

                        error.setText("Email changed.");
                    } else if (myConnection.getResponseCode() == 400) {
                        error.setText("Wrong number format.");
                    } else {
                        error.setText("Server error.");
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void changePassword(final String pass1, String pass2) {
        final TextView error = getView().findViewById(R.id.textView48);

        if (pass1.equals("") || pass2.equals("")) {
            error.setText("Empty password.");
        } else if (!pass1.equals(pass2)) {
            error.setText("Passwords are not the same.");
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper dbHelper = new DBHelper(getContext());
                    List<User> user = dbHelper.getUser();
                    int id = user.get(0).getId();
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + id);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("PUT");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    final String passwordHash;
                    try {
                        passwordHash = Encryption.encryptText(pass1);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        error.setText("Incorrect data. Try again.");
                        return;
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                        error.setText("Incorrect data. Try again.");
                        return;
                    }

                    String jsonInputString = "{\"passwordHash\" : \"" + passwordHash + "\"}";
                    OutputStream os = myConnection.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.close();

                    TextView error = getView().findViewById(R.id.textView48);
                    BufferedReader bufferedReader = null;

                    if (myConnection.getResponseCode() == 200) {
                        bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = bufferedReader.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        user.get(0).setPasswordHash(passwordHash);
                        dbHelper.createOrUpdateUser(user.get(0));

                        error.setText("Password changed.");
                    } else if (myConnection.getResponseCode() == 400) {
                        error.setText("Wrong number format.");
                    } else {
                        error.setText("Server error.");
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
