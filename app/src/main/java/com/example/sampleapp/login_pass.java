package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_pass extends AppCompatActivity {
    EditText logeemail;
    TextInputEditText logpass;
    TextView reglink;
    Button logbutton;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), demo_mainactivity.class);
            startActivity(intent);

            // Finish the current activity (optional)
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_pass);
        logpass=findViewById(R.id.logpass);


        Intent intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra("text");

            // Now you can use the text as needed
            // For example, set it to a TextView in this activity
            logeemail=findViewById(R.id.logeemail);
            logeemail.setText(text);


        logbutton=findViewById(R.id.logbutton);
        mAuth=FirebaseAuth.getInstance();
        reglink=findViewById(R.id.reglink);
        reglink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_pass.this, register.class);
                startActivity(intent);

                // Finish the current activity (optional)
                finish();
            }
        });
        logbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email,password;
                email=String.valueOf(logeemail.getText());
                password=String.valueOf(logpass.getText());
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(login_pass.this ,"enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(login_pass.this, "enter pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),"login successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), demo_mainactivity.class);
                                    startActivity(intent);

                                    // Finish the current activity (optional)
                                    finish();

                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(login_pass.this, "\t\t\t\t\t\t\tAuthentication failed \n try to enter correct email and password.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });}

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}}