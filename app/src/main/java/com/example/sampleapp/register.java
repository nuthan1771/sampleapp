package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {
    TextInputEditText regpass,regemail;
    Button regbutton;
     TextView loglink;
     FirebaseAuth buth;
    FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        buth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();





        regpass=findViewById(R.id.regpass);
        regemail=findViewById(R.id.regemail);
        regbutton=findViewById(R.id.regbutton);
        loglink=findViewById(R.id.loglink);

        loglink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login_email.class);
                Log.d("register_login_email","success");
                startActivity(intent);

                // Finish the current activity (optional)
                finish();


                }
        });

        regbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email,password;
                email=String.valueOf(regemail.getText());
                password=String.valueOf(regpass.getText());
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(register.this ,"enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(register.this, "enter pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                buth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = buth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),"registered successfully",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), login_email.class);
                                    Log.d("register_login_email1","success");
                                    startActivity(intent);
                                    finish();
                                    assert user != null;
                                    DocumentReference df = db.collection("users_details").document(user.getUid());
                                    Map<String, Object> userinfo = new HashMap<>();
                                    userinfo.put("email", regemail.getText().toString());
                                    userinfo.put("pass", regpass.getText().toString());
                                    userinfo.put("isUser", true);
                                    userinfo.put("isadmin", false);
                                    df.set(userinfo);
                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}