package com.example.projektwtm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projektwtm.modele.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

public class LoginActivity extends AppCompatActivity {

    public static JSONObject loggedUser = null;

    EditText emailET;
    EditText passET;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check if logged
        DBHelper dbHelper = new DBHelper(this);
        final List<User> users = new ArrayList<>();
        try {
            users.addAll(dbHelper.getUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!users.isEmpty()) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users/" + users.get(0).getId());
                        HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();
                        try {
                            if (myConnection.getResponseCode() == 200) {
                                JSONArray jsonArray = null;
                                try {
                                    BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));
                                    StringBuilder response = new StringBuilder();
                                    String responseLine = null;
                                    while ((responseLine = br.readLine()) != null) {
                                        response.append(responseLine.trim());
                                    }
                                    jsonArray = new JSONArray(response.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (jsonArray != null) {
                                    Intent intent = new Intent(LoginActivity.this, MainPage.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

        emailET = findViewById(R.id.editText2);
        passET = findViewById(R.id.editText5);

        TextView textView = findViewById(R.id.textView12);
        textView.setText(Html.fromHtml("<font color='black'><u>" + getResources().getString(R.string.linkRegistration) + "</u></font>"));
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                final String email = emailET.getText().toString();
                final String pass = passET.getText().toString();

                try {
                    login(email, pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void login(final String email, final String pass) throws JSONException {
        TextView error = findViewById(R.id.textView11);

        if (email.equals("") || pass.equals("")) {
            error.setText("All data required. Try again.");
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL addUserURL = new URL("http://" + Constants.serverIP + ":8080/FindCo/api/users?email=" + email);
                    HttpURLConnection myConnection = (HttpURLConnection) addUserURL.openConnection();

                    try {

                        if (myConnection.getResponseCode() == 200) {

                            JSONArray jsonArray = null;
                            try {
                                BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream(), "utf-8"));

                                StringBuilder response = new StringBuilder();
                                String responseLine = null;
                                while ((responseLine = br.readLine()) != null) {
                                    response.append(responseLine.trim());
                                }
                                jsonArray = new JSONArray(response.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String emailJSON = jsonObject.getString("email");
                                    String passJSON = jsonObject.getString("passwordHash");
                                    if (emailJSON.equals(email) && Encryption.validatePassword(pass, passJSON)) {

                                        // add user to local db
                                        JsonParser parser = new JsonParser();
                                        JsonElement element = parser.parse(jsonObject.toString());
                                        Gson gson = new Gson();
                                        User user = gson.fromJson(element, User.class);
                                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                                        try {
                                            dbHelper.createOrUpdateUser(user);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }


                                        loggedUser = jsonObject;

                                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            CharSequence name = "Login";
                                            String description = "Login - push notification";
                                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                            NotificationChannel channel = new NotificationChannel("ID", name, importance);
                                            channel.setDescription(description);
                                            notificationManager = context.getSystemService(NotificationManager.class);
                                            notificationManager.createNotificationChannel(channel);
                                        }

                                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                                                .setChannelId("ID")
                                                .setContentTitle("Login")
                                                .setContentText("You have logged in. Enjoy your time!")
                                                .setSmallIcon(R.drawable.ic_launcher_foreground);

                                        notificationManager.notify(100, notification.build());

                                        Intent intent = new Intent(LoginActivity.this, MainPage.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
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