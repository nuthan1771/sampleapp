package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class demo_mainactivity extends AppCompatActivity {
    TextView usernam;
    Button logoutbutton;
    FirebaseAuth auth;
    FirebaseUser user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo_mainactivity);
        logoutbutton=findViewById(R.id.logoutbutton);
        usernam=findViewById(R.id.usernam);
        auth=FirebaseAuth.getInstance();
        user1=auth.getCurrentUser();
        if(user1==null)
        {
            Intent intent = new Intent(getApplicationContext(), login_email.class);
            startActivity(intent);

            // Finish the current activity (optional)
            finish();
        }
        else
        {
         usernam.setText(user1.getDisplayName());
        }
        logoutbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login_email.class);
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