package com.example.projektwtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.regex.Pattern;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MainActivity extends AppCompatActivity {

    private IntentFilter filter = new IntentFilter("android.password.wrong");

    private BroadcastReceiver broadcast = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Toast.makeText(arg0, "Wrong password!", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(broadcast, filter);
        setContentView(R.layout.activity_main);



        TextView textView = findViewById(R.id.textView8);
        textView.setText(Html.fromHtml("<font color='black'><u>"+ getResources().getString(R.string.linkLogin) +"</u></font>"));
        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        EditText firstNameET = findViewById(R.id.editText9);
        EditText surnameET = findViewById(R.id.editText7);
        EditText emailET = findViewById(R.id.editText6);
        EditText pass1ET = findViewById(R.id.editText4);
        EditText pass2ET = findViewById(R.id.editText3);

        final String firstname = firstNameET.getText().toString();
        final String surname = surnameET.getText().toString();
        final String email = emailET.getText().toString();
        final String pass1 = pass1ET.getText().toString();
        final String pass2 = pass2ET.getText().toString();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean reg = false;
                try {
                    reg = register(firstname, surname, email, pass1, pass2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (reg) {
                   User user = new User(firstname, surname, email, pass1);

                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/");
                    Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
                    response.close();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    TextView error = findViewById(R.id.textView17);
                    error.setText("");
                    startActivity(intent);
                }
                else {
                    //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    TextView error = findViewById(R.id.textView17);
                    error.setText("Incorrect data. Try again.");
                    //startActivity(intent);
                }
            }
        });
    }

    public boolean register(String firstname, String surname, String email, String pass1, String pass2) throws JSONException {
        if (firstname.equals("") || surname.equals("") || email.equals("") || pass1.equals("") || pass2.equals("")) {
            return false;
        }
        else if (!Pattern.matches("[A-Za-z]+", firstname) || !Pattern.matches("[A-Za-z]+", surname) /*|| !Pattern.matches("[A-Za-z]+[1-9_]*[@]{1}[A-Za-z1-9]+[.]{1}[a-z]+[.]{0,1}[a-z]+}", email) || !Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2) */) {
            return false;
        }
        else if (!pass1.equals(pass2)) {
            return false;
        }

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/");
        Response response = target.request().get();
        if (response.hasEntity()) {
            JSONArray users = (JSONArray) response.getEntity();
            for (int i = 0; i < users.length(); ++i) {
                JSONObject obj = users.getJSONObject(i);
                String emailJSON = obj.getString("email");
                if (email.equals(emailJSON)) {
                    return false;
                }
            }
        }
        response.close();

        return true;
    }

    @Override
    public void onPause() {
        unregisterReceiver(broadcast);
        // trzeba zawsze po sobie posprzątać w tym przypadku wyrejestrować receiver.
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcast, filter);

    }
}
