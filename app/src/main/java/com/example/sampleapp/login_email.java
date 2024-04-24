package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class login_email extends AppCompatActivity {
    TextInputEditText logemail;
    ImageButton lognextbutton;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_email);
        lognextbutton=findViewById(R.id.lognextbutton);

        logemail=findViewById(R.id.logemail);



        lognextbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                text = String.valueOf(logemail.getText());


                Intent intent = new Intent(login_email.this, login_pass.class);
                intent.putExtra("text", text);
                startActivity(intent);}





        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}

