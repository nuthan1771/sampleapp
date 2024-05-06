package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

public class login_pass extends AppCompatActivity {

    TextInputEditText logpass;
    TextView reglink;
    Button logbutton;
    FirebaseAuth duth;
    FirebaseFirestore db;
    String text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_pass);
        logpass=findViewById(R.id.logpass);
        db = FirebaseFirestore.getInstance();
        logbutton=findViewById(R.id.logbutton);
        duth=FirebaseAuth.getInstance();
        reglink=findViewById(R.id.reglink);


        Intent intent = getIntent();
        if (intent != null) {
             text = intent.getStringExtra("text");}


            // Now you can use the text as needed
            // For example, set it to a TextView in this activity







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
//                email=String.valueOf(logeemail.getText());
                email=text;
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
                duth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = duth.getCurrentUser();
                                    chkuser(user.getUid());


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
public void chkuser(String uid)
{
    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
        DocumentReference df = db.collection("users_details").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Boolean isadmin = documentSnapshot.getBoolean("isadmin");
                        Boolean isUser = documentSnapshot.getBoolean("isUser");
                        Log.d("loggingadmin_throughlogpass "+ isadmin, "success" );
                        if (Boolean.TRUE.equals(isadmin)) {
                            // Admin user
                            Intent intent = new Intent(getApplicationContext(), demoadmin.class);

                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Admin login successful", Toast.LENGTH_SHORT).show();



                        } else if (Boolean.TRUE.equals(isUser)) {
                            Intent intent = new Intent(getApplicationContext(), demo1_mainactivity.class);
                            Log.d("logginguser_throughlogpass "+ isadmin, "success" );

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
}}