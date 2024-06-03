package com.example.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {


    Context context;
    ArrayList<Appuser> appuserArrayList;


    public myadapter(Context context, ArrayList<Appuser> appuserArrayList) {
        this.context = context;
        this.appuserArrayList = appuserArrayList;
    }

    @NonNull
    @Override
    public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {
        Appuser usr = appuserArrayList.get(position);
        holder.email.setText(usr.email);
        holder.latitude.setText(usr.latitude);
        holder.longitude.setText(usr.longitude);
        holder.setEmail(usr.email);
        holder.setudi(usr.id);


    }

    @Override
    public int getItemCount() {
        return appuserArrayList.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener,
            GoogleMap.OnMyLocationButtonClickListener,
            GoogleMap.OnMyLocationClickListener,
            OnMapReadyCallback {
        TextView email, latitude, longitude;
       private MapView mapView;
        private GoogleMap map;
        private String useremail;
        String uid;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            View track;
            String useremail;



            email = itemView.findViewById(R.id.email);

            longitude = itemView.findViewById(R.id.longitude);
            latitude = itemView.findViewById(R.id.latitude);
            mapView = itemView.findViewById(R.id.mapView);
            mapView.onCreate(null);
            mapView.getMapAsync((OnMapReadyCallback) this);
            track = itemView.findViewById(R.id.track);
            track.setOnClickListener(this);

        }

        public void setEmail(String email) {
            this.useremail = email;
        }
        public void setudi(String id) {
            this.uid = id;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.track) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Appuser user = appuserArrayList.get(position);
                    String latitudeValue = user.getLatitude();
                    String longitudeValue = user.getLongitude();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("latitude", latitudeValue);
                    intent.putExtra("longitude", longitudeValue);
                    intent.putExtra("email", useremail);
                    intent.putExtra("id", uid);
                    Log.d("uuuiddd", "uid "+uid+useremail);
                    context.startActivity(intent);
//                    Uri gmmIntentUri = Uri.parse("geo:" + latitudeValue + "," + longitudeValue + "?q=" + latitudeValue + "," + longitudeValue + "(Label)");
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//                        context.startActivity(mapIntent);
//                    } else {
//                        Toast.makeText(context, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            map = googleMap;
            map.clear();
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            map.setMyLocationEnabled(true);
//            map.getUiSettings().setMyLocationButtonEnabled(true);

            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
//           map.setMyLocationEnabled(true);
                map.getUiSettings().setMapToolbarEnabled(true);
                map.getUiSettings().setCompassEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                Appuser user = appuserArrayList.get(position);
                String latitudeValue = user.getLatitude();
                String longitudeValue = user.getLongitude();
                Log.d("adapter2MapActivity", "Latitude: " + latitudeValue + ", Longitude: " + longitudeValue);
                LatLng location = new LatLng(Double.parseDouble(latitudeValue), Double.parseDouble(longitudeValue));
                map.addMarker(new MarkerOptions().position(location).title(useremail));
                Log.d("user email", email + ":email");


                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));



                Log.d("is location enabled", map.getUiSettings().isMyLocationButtonEnabled()+ ":email");


            }







        }
//@Override
//public void onMapReady(GoogleMap googleMap) {
//    map = googleMap;
//
//
//
//
//    // Add a marker in Sydney and move the camera
//    map.clear();
//    map.getUiSettings().setZoomControlsEnabled(true);
//    map.getUiSettings().setMapToolbarEnabled(true);
//    map.getUiSettings().setCompassEnabled(true);
//    Log.d("location buttonenabled", map.getUiSettings().isMyLocationButtonEnabled() + ":email");
//
//    LatLng sydney = new LatLng(-34, 151);
//    map.addMarker(new MarkerOptions().position(sydney).title("sydney"));
//    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        return;
//    }
//    map.setMyLocationEnabled(true);
////        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//    map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
//
//
//}
        public void onMyLocationClick(@NonNull Location location) {
            Toast.makeText(context, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onMyLocationButtonClick() {
            Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
            return false;
        }



    }

}

