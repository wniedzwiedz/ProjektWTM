package com.example.projektwtm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        EditText firstnameET = getView().findViewById(R.id.editText12);
        EditText surnameET = getView().findViewById(R.id.editText16);
        EditText emailET = getView().findViewById(R.id.editText17);
        EditText pass1ET = getView().findViewById(R.id.editText11);
        EditText pass2ET = getView().findViewById(R.id.editText10);

        final String firstname = firstnameET.getText().toString();
        final String surname = surnameET.getText().toString();
        final String email = emailET.getText().toString();
        final String pass1 = pass1ET.getText().toString();
        final String pass2 = pass2ET.getText().toString();

        Button button = getView().findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean nameChange = changeFirstname(firstname);
                if (nameChange) {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("");

                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/"); // + user.getId()
                    //user.setFirstname(firstname);
                    Response response = target.request().get(); //.put(user);
                    response.close();
                }
                else {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("Incorrect data. Try again.");
                }
            }
        });

        Button button1 = getView().findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean surnameChange = changeSurname(surname);
                if (surnameChange) {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("");

                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/"); // + user.getId()
                    //user.setSurname(surname);
                    Response response = target.request().get(); //.put(user);
                    response.close();
                }
                else {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("Incorrect data. Try again.");
                }
            }
        });

        Button button2 = getView().findViewById(R.id.button9);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean emailChange = changeEmail(email);
                if (emailChange) {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("");

                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/"); // + user.getId()
                    //user.setEmail(email);
                    Response response = target.request().get(); //.put(user);
                    response.close();
                }
                else {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("Incorrect data. Try again.");
                }
            }
        });

        Button button3 = getView().findViewById(R.id.button10);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean passChange = changePassword(pass1, pass2);
                if (passChange) {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("");

                    ResteasyClient client = new ResteasyClientBuilder().build();
                    ResteasyWebTarget target = client.target("http://localhost:8080/FindCo/api/users/"); // + user.getId()
                    //user.setPasswordHash(pass1);
                    Response response = target.request().get(); //.put(user);
                    response.close();
                }
                else {
                    TextView error = getView().findViewById(R.id.textView48);
                    error.setText("Incorrect data. Try again.");
                }
            }
        });

        Button button4 = getView().findViewById(R.id.button11);
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
            });

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public boolean changeFirstname(String firstname) {
        if (firstname.equals("")) {
            return false;
        }
        else if (!Pattern.matches("[A-Z][a-z]+", firstname)) {
            return false;
        }
        return true;
    }

    public boolean changeSurname(String surname) {
        if (surname.equals("")) {
            return false;
        }
        else if (!Pattern.matches("[A-Z][a-z]+", surname)) {
            return false;
        }
        return true;
    }

    public boolean changeEmail(String email) {
        if (email.equals("")) {
            return false;
        }
        //else if (!Pattern.matches("[A-Za-z]+[1-9_]*[@]{1}[A-Za-z1-9]+[.]{1}[a-z]+[.]{0,1}[a-z]+}", email)) {
          //  return false;
        //}
        return true;
    }

    public boolean changePassword(String pass1, String pass2) {
        if (pass1.equals("") || pass2.equals("")) {
            return false;
        }
        else if (!pass1.equals(pass2)) {
            return false;
        }
        else if (!Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2)) {
            return false;
        }
        return true;
    }
}
