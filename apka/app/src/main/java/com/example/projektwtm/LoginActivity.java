package com.example.projektwtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

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

        EditText emailET = findViewById(R.id.editText2);
        EditText passET = findViewById(R.id.editText5);

        final String email = emailET.getText().toString();
        final String pass = passET.getText().toString();

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//odkomentowac, gdy bedzie juz mozliwosc logowania
//                boolean log = login(femail, pass);
//                if (log) {
//                    Intent intent = new Intent(LoginActivity.this, MainPage.class);
//                    TextView error = findViewById(R.id.textView11);
//                    error.setText("");
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(LoginActivity.this, LoginActivity.this);
//                    TextView error = findViewById(R.id.textView11);
//                    error.setText("Incorrect data. Try again.");
//                    startActivity(intent);
//                }
                Intent intent = new Intent(LoginActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

    }

    public boolean login(String email, String pass) {
        if (email.equals("") || pass.equals("")) {
            return false;
        }

        //sprawdzenie w bazie danych

        return true;
    }

}
