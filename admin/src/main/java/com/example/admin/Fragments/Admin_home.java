package com.example.admin.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.Models.VenueModel;
import com.example.admin.R;
import com.example.admin.adapters.VenueAdpaters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin_home extends Fragment {
    RecyclerView venueRecycler;
    FirebaseFirestore db;
    ArrayList<VenueModel> venueModelList;
    VenueAdpaters venueAdpaters;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_home, container, false);
        db = FirebaseFirestore.getInstance();

        venueRecycler = root.findViewById(R.id.venuelist);

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
                                VenueModel venueModel = document.toObject(VenueModel.class);
                                venueModelList.add(venueModel);
                                venueAdpaters.notifyDataSetChanged();

                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception != null) {
                                Toast.makeText(getActivity(), "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        return root;
    }
}