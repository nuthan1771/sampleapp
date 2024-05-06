package com.example.sampleapp;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.Map;

public class demo1_mainactivity extends AppCompatActivity {

     double longitude;
     LocationCallback locationCallback;

     boolean requestingLocationUpdates = true;
    double latitude;
    String email;

     Button logoutbutton;
     Button map;

    FirebaseAuth futh;
     FirebaseUser user1;
    FirebaseFirestore db;
     FusedLocationProviderClient fusedLocationClient;
     LocationRequest locationRequest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo1_mainactivity);
        Intent intent = getIntent();



        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    // Handle permission results
                });

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        logoutbutton = findViewById(R.id.logoutbutton);

        futh = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user1 = futh.getCurrentUser();


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult( LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (android.location.Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    assert user1 != null;


                    email=user1.getEmail();
                    Log.d("email of current user","email"+email);

                    DocumentReference df = db.collection("users_location").document(user1.getUid());
                    Map<String, Object> userinfo = new HashMap<>();
                    userinfo.put("latitude", "" + latitude);
                    userinfo.put("longitude", "" + longitude);
                    userinfo.put("email", email);


// Update the existing document with the new latitude and longitude values

                    df.set(userinfo, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    Log.d("UPDATE IN FIREBASE", "SUCCESS");
                                    Toast.makeText(demo1_mainactivity.this, "update in firebase", Toast.LENGTH_SHORT).show();
                                    // Handle success
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(demo1_mainactivity.this, "failed to update in firebase", Toast.LENGTH_SHORT).show();
                                    // Handle failure
                                }
                            });

                    Toast.makeText(demo1_mainactivity.this, "New location fetched", Toast.LENGTH_SHORT).show();
                }
            }
        };

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login_email.class);
                Log.d("signing out and_email ", "success" );
                startActivity(intent);
                finish();
            }
        });

//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(demo1_mainactivity.this, "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_SHORT).show();
//                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Label)");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                } else {
//                    Toast.makeText(demo1_mainactivity.this, "Maps app not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



        createLocationRequest(); // Call to create location request
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}
