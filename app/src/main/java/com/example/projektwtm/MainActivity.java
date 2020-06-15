package com.example.projektwtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView8);
        textView.setText(Html.fromHtml("<font color='black'><u>"+ getResources().getString(R.string.linkLogin) +"</u></font>"));

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

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//odkomentowac, gdy bedzie juz mozliwosc rejestracji
//                boolean log = register(firstname, surname, email, pass1, pass2);
//                if (log) {
// dodanie nowego uzytkownika do bazy danych
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    TextView error = findViewById(R.id.textView17);
//                    error.setText("");
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                    TextView error = findViewById(R.id.textView17);
//                    error.setText("Incorrect data. Try again.");
//                    startActivity(intent);
//                }

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean register(String firstname, String surname, String email, String pass1, String pass2) {
        if (firstname.equals("") || surname.equals("") || email.equals("") || pass1.equals("") || pass2.equals("")) {
            return false;
        }
        else if (!pass1.equals(pass2)) {
            return false;
        }
        else if (!Pattern.matches("[A-Za-z]+", firstname) || !Pattern.matches("[A-Za-z]+", surname) || !Pattern.matches("[A-Za-z]+[1-9_]*[@]{1}[A-Za-z1-9]+[.]{1}[a-z]+[.]{0,1}[a-z]+}", email) || !Pattern.matches("[A-Za-z1-9_!@]+", pass1)  || !Pattern.matches("[A-Za-z1-9_!@]+", pass2)) {
            return false;
        }
        else if (!pass1.equals(pass2)) {
            return false;
        }

        //sprawdzenie w bazie danych

        return true;
    }
}
