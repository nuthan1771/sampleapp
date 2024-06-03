package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;


public class login_email extends AppCompatActivity {
    TextInputEditText logemail;
    ImageButton lognextbutton;
    String text;
    TextView reglink1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_email);
        lognextbutton=findViewById(R.id.lognextbutton);

        logemail=findViewById(R.id.logemail);
        reglink1=findViewById(R.id.reglink1);



        lognextbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                text = String.valueOf(logemail.getText());


                Intent intent = new Intent(login_email.this, login_pass.class);
                intent.putExtra("text", text);
                Log.d("login_email_to_logpass","success");
                startActivity(intent);
                 }






        });
        reglink1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_email.this, register.class);
                startActivity(intent);

                // Finish the current activity (optional)
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}

