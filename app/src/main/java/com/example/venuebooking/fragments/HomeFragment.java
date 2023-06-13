package com.example.venuebooking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.venuebooking.R;
import com.example.venuebooking.adapters.VenueAdpaters;
import com.example.venuebooking.models.VenueModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView venueRecycler;
    FirebaseFirestore db;
    ArrayList<VenueModel> venueModelList;
    VenueAdpaters venueAdpaters;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        venueRecycler = root.findViewById(R.id.venue_recyler);

        venueRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        venueModelList = new ArrayList<>();
        venueAdpaters = new VenueAdpaters(getActivity(),venueModelList,venueRecycler);
        venueRecycler.setAdapter(venueAdpaters);

        db.collection("venues")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                String name = document.getString("name");
                                String img_url = document.getString("img_url");
                                String desc = document.getString("desc");
                            VenueModel venueModel = new VenueModel(name,img_url,desc,documentId);
                venueModelList.add(venueModel);
                venueAdpaters.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}