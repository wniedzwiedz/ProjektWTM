package com.example.projektwtm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projektwtm.modele.User;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

public class LoginActivity extends AppCompatActivity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.textView12);
        textView.setText(Html.fromHtml("<font color='black'><u>"+ getResources().getString(R.string.linkRegistration) +"</u></font>"));
        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v){
                EditText emailET = findViewById(R.id.editText2);
                EditText passET = findViewById(R.id.editText5);

                final String email = emailET.getText().toString();
                final String pass = passET.getText().toString();
                
                boolean log = false;
                try {
                    log = login(email, pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (log) {
                    //push notification

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
                    TextView error = findViewById(R.id.textView11);
                    error.setText("");
                    startActivity(intent);
                }
                else {
                    //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    TextView error = findViewById(R.id.textView11);
                    error.setText("Incorrect data. Try again.");
                    //startActivity(intent);
                }
               // Intent intent = new Intent(LoginActivity.this, MainPage.class);
              //  startActivity(intent);
            }
        });

    }

    public boolean login(String email, String pass) throws JSONException {
        if (email.equals("") || pass.equals("")) {
            return false;
        }

        //sprawdzenie, czy dane poprawne
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users");
        Response response = target.request().get();
        if (response.hasEntity()) {
            JSONArray users = (JSONArray) response.getEntity();
            for (int i = 0; i < users.length(); ++i) {
                JSONObject obj = users.getJSONObject(i);
                String emailJSON = obj.getString("email");
                String passJSON = obj.getString("passwordHash");
                if (email.equals(emailJSON) && pass.equals(passJSON)) {
                    return true;
                }
            }
        }
        response.close();

        return false;
    }

}
