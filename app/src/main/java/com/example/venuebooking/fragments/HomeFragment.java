package com.example.venuebooking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venuebooking.R;
import com.example.venuebooking.adapters.VenueAdapter;
import com.example.venuebooking.models.VenueModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class HomeFragment extends Fragment {
    RecyclerView venueRecycler;
    FirebaseFirestore db;
    ArrayList<VenueModel> venueModelList;
    VenueAdapter venueAdpaters;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        venueRecycler = root.findViewById(R.id.venue_recyler);
        venueRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        venueModelList = new ArrayList<>();

        
        venueAdpaters = new VenueAdapter(getActivity(),venueModelList,venueRecycler);
        venueRecycler.setAdapter(venueAdpaters);


        db.collection("venues").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("FirestoreListener", "Listen failed: " + e);
                    return;
                }
                if (querySnapshot != null) {
                    List<VenueModel> newVenueList = new ArrayList<>();

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String documentId = document.getId();
                        String name = document.getString("name");
                        String img_url = document.getString("img_url");
                        String desc = document.getString("desc");
                        VenueModel venueModel = new VenueModel(name,img_url,desc,documentId);
                        newVenueList.add(venueModel);
                    }
                    venueAdpaters.setData(newVenueList);

                }
            }
        });


        return root;
    }
}