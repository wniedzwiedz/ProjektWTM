package com.example.projektwtm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = (TextView) findViewById(R.id.textView12);
        textView.setText(Html.fromHtml("<font color='black'><u>"+ getResources().getString(R.string.linkRegistration) +"</u></font>"));

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //just for checking if it works
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

    }
}
