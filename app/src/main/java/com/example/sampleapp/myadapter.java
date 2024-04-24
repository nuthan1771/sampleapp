package com.example.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
    static Context context;
    static ArrayList<Appuser> appuserArrayList;

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

    }

    @Override
    public int getItemCount() {
        return appuserArrayList.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView email, latitude, longitude;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            View track;


            email = itemView.findViewById(R.id.email);
            longitude = itemView.findViewById(R.id.longitude);
            latitude = itemView.findViewById(R.id.latitude);
            track = itemView.findViewById(R.id.track);
            track.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.track) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Appuser user = appuserArrayList.get(position);
                    String latitudeValue = user.getLatitude();
                    String longitudeValue = user.getLongitude();

                    Uri gmmIntentUri = Uri.parse("geo:" + latitudeValue + "," + longitudeValue + "?q=" + latitudeValue + "," + longitudeValue + "(Label)");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(mapIntent);
                    } else {
                        Toast.makeText(context, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

}

