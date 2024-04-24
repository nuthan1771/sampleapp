package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth Auth;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DocumentReference df = db.collection("users_details").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {


                            Boolean isadmin = documentSnapshot.getBoolean("isadmin");
                            Boolean isUser = documentSnapshot.getBoolean("isUser");
                            Log.d("loggingadmin_onrestart "+ isadmin, "success" );
                            if (Boolean.TRUE.equals(isadmin)) {
                                // Admin user
                                Intent intent = new Intent(getApplicationContext(), demoadmin.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Admin login successful", Toast.LENGTH_SHORT).show();



                            } else if (Boolean.TRUE.equals(isUser)) {
                                Intent intent = new Intent(getApplicationContext(), demo1_mainactivity.class);
                                Log.d("logginguser_onrestart "+ isadmin, "success" );
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "User login successful", Toast.LENGTH_SHORT).show();

                            } else {
                                // User is neither admin nor regular user
                                Toast.makeText(getApplicationContext(), "User type unknown", Toast.LENGTH_SHORT).show();
                            }





                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Intent intent = new Intent(getApplicationContext(), login_email.class);
                            startActivity(intent);
                            finish();

                        }
                    });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                // Intent to start the next activity

                Intent intent = new Intent(MainActivity.this, register.class);
                Log.d("i_mainact_to_register","success");
                startActivity(intent);

                // Finish the current activity (optional)
                finish();
            }
        }, 4000);}


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}


















