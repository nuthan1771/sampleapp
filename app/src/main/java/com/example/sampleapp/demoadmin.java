package com.example.sampleapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import androidx.activity.EdgeToEdge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class demoadmin extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Appuser> appuserArrayList;
    myadapter myadapter;

    FirebaseAuth euth;
    FirebaseFirestore dbu;
    Button adminlogoutbutton;
    Context context;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demoadmin);
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    // Handle permission results
                });

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

          context=this;
        euth = FirebaseAuth.getInstance();
        dbu = FirebaseFirestore.getInstance();


        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         adminlogoutbutton=findViewById(R.id.adminlogoutbutton);
         appuserArrayList =new ArrayList<Appuser>();
         myadapter=new myadapter(demoadmin.this, appuserArrayList);
         recyclerView.setAdapter(myadapter);
         Eventchangelistener();



        adminlogoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login_email.class);
                Log.d("demoadmin_to_logemail ", "success" );
                startActivity(intent);
                finish();

            }
        });


               ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void Eventchangelistener()
    {
        dbu.collection("users_location")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("firestoreerror",error.getMessage());
                            return;

                        }
                        for(DocumentChange dc:value.getDocumentChanges())
                        {
                            if(dc.getType()==DocumentChange.Type.ADDED)
                            {

                                appuserArrayList.add(dc.getDocument().toObject(Appuser.class));
                            }
                            else if (dc.getType()==DocumentChange.Type.MODIFIED) {

                                appuserArrayList.clear();
//
                                appuserArrayList.add(dc.getDocument().toObject(Appuser.class));

                            }

                            myadapter.notifyDataSetChanged();


                        }
//

                        // Notify the adapter that the dataset has changed
                        myadapter.notifyDataSetChanged();

                    }
                });


    }



}





