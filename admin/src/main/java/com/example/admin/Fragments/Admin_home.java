package com.example.admin.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.admin.Models.Venuelist;
import com.example.admin.R;
import com.example.admin.adapters.Venuelistadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class Admin_home extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Venuelist> venuelistArrayList;
    Venuelistadapter adVenuelistadapter;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching .....");
        progressDialog.show();


        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        recyclerView=view.findViewById(R.id.venuelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db=FirebaseFirestore.getInstance();
        venuelistArrayList=new ArrayList<>();
        adVenuelistadapter=new Venuelistadapter(getContext(),venuelistArrayList);
        recyclerView.setAdapter(adVenuelistadapter);

        eventchange();

        return view;


    }

    private void eventchange() {
        db.collection("venues").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> veList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot ds:veList){
                    venuelistArrayList.add(ds.toObject(Venuelist.class));

                }
            }
        });
    }

//    private void eventchange() {
//
//        db.collection("venues").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error !=null){
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    Log.e("Firebase error",error.getMessage());
//                    return;
//                }
//                for(DocumentChange dc:value.getDocumentChanges()){
//                    if(dc.getType()== DocumentChange.Type.ADDED){
//                        venuelistArrayList.add(dc.getDocument().toObject(Venuelist.class));
//                    }
//                    adVenuelistadapter.notifyDataSetChanged();
//                    if(progressDialog.isShowing())
//                        progressDialog.dismiss();
//                }
//            }
//        });
//    }
}