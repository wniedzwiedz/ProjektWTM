package com.example.projektwtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektwtm.modele.User;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText firstNameET;
    EditText surnameET;
    EditText emailET;
    EditText pass1ET;
    EditText pass2ET;

    private IntentFilter filter = new IntentFilter("android.password.wrong");
    private IntentFilter filter2 = new IntentFilter("android.intent.action.BATTERY_LOW");

    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Toast.makeText(arg0, "Wrong password!", Toast.LENGTH_LONG).show();
        }
    };

    private BroadcastReceiver broadcast2 = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Toast.makeText(arg0, "Remember to charge your phone not to miss payment deadlines!", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(broadcast, filter);
        registerReceiver(broadcast2, filter2);
        setContentView(R.layout.activity_main);

        // check if logged
        DBHelper dbHelper = new DBHelper(this);
        List users = new ArrayList<>();
        try {
            users.addAll(dbHelper.getUser(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!users.isEmpty()) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }

        TextView textView = findViewById(R.id.textView8);
        textView.setText(Html.fromHtml("<font color='black'><u>" + getResources().getString(R.string.linkLogin) + "</u></font>"));
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        firstNameET = findViewById(R.id.editText9);
        surnameET = findViewById(R.id.editText7);
        emailET = findViewById(R.id.editText6);
        pass1ET = findViewById(R.id.editText4);
        pass2ET = findViewById(R.id.editText3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String firstname = firstNameET.getText().toString();
                String surname = surnameET.getText().toString();
                String email = emailET.getText().toString();
                String pass1 = pass1ET.getText().toString();
                String pass2 = pass2ET.getText().toString();

                register(firstname, surname, email, pass1, pass2);
//                boolean reg = false;
//                try {
//                    reg = register(firstname, surname, email, pass1, pass2);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (reg) {
//                    User user = new User(firstname, surname, email, pass1);

//                    ResteasyClient client = new ResteasyClientBuilder().build();
//                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/");
//                    Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
//                    response.close();

//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    TextView error = findViewById(R.id.textView17);
//                    error.setText("");
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                    TextView error = findViewById(R.id.textView17);
//                    error.setText("Incorrect data. Try again.");
//                    startActivity(intent);
//                }
            }
        });
    }

    public void register(final String firstname, final String surname, final String email, String pass1, String pass2) {
        TextView error = findViewById(R.id.textView17);
        if (firstname.equals("") || surname.equals("") || email.equals("") || pass1.equals("") || pass2.equals("")) {
            error.setText("Required all data. Try again.");
            return;
        } else if (!Pattern.matches("[A-Za-z]+", firstname) || !Pattern.matches("[A-Za-z]+", surname) /*|| !Pattern.matches("[A-Za-z]+[1-9_]*[@]{1}[A-Za-z1-9]+[.]{1}[a-z]+[.]{0,1}[a-z]+}", email) || !Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2) */) {
            error.setText("Incorrect data. Try again.");
            return;
        } else if (!pass1.equals(pass2)) {
            error.setText("Password not the same. Try again.");
            return;
        }

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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users");
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                    myConnection.setRequestMethod("POST");
                    myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    myConnection.setRequestProperty("Accept", "application/json");
                    myConnection.setDoOutput(true);

                    String jsonInputString = "{\"firstName\" : \"" + firstname + "\", " +
                            "\"lastName\" : \"" + surname + "\", " +
                            "\"email\" : \"" + email + "\", " +
                            "\"passwordHash\" : \"" + passwordHash + "\"}";

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
                        TextView error = findViewById(R.id.textView17);
                        if (myConnection.getResponseCode() == 201) {
                            bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = bufferedReader.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            System.out.println(response.toString());

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            error.setText("");
                            startActivity(intent);
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

    @Override
    public void onPause() {
        unregisterReceiver(broadcast);
        unregisterReceiver(broadcast2);
        // trzeba zawsze po sobie posprzątać w tym przypadku wyrejestrować receiver.
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcast, filter);
        registerReceiver(broadcast2, filter2);

    }
}
