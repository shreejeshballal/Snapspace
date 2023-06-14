package com.example.admin.Fragments;

import android.app.ProgressDialog;
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

import com.example.admin.Models.VenueModel;
import com.example.admin.R;
import com.example.admin.adapters.VenueAdpaters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Admin_home extends Fragment {
    RecyclerView venueRecycler;
    FirebaseFirestore db;
    ArrayList<VenueModel> venueModelList;
    VenueAdpaters venueAdpaters;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_home, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        db = FirebaseFirestore.getInstance();

        venueRecycler = root.findViewById(R.id.venuelist);

        venueRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        venueModelList = new ArrayList<>();
        venueAdpaters = new VenueAdpaters(getActivity(),venueModelList,venueRecycler);
        venueRecycler.setAdapter(venueAdpaters);
        venueAdpaters.setOnclickListener(new VenueAdpaters.OnItemclickListen() {
            @Override
            public void onItemClick(int position) {

                //deleting
            VenueModel item=venueModelList.get(position);
            String id=item.getImg_url();
           Query query =db.collection("venues").whereEqualTo("img_url",id);

           query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if(task.isSuccessful()){
                     for(QueryDocumentSnapshot document:task.getResult()){
                         db.collection("venues").document(document.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 Toast.makeText(getActivity(),"deleted",Toast.LENGTH_SHORT).show();
                                 venueAdpaters.notifyItemRemoved(position);
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Log.e("Admin_home.this","exception ",e);
                                 Toast.makeText(getActivity(),"Error"+e,Toast.LENGTH_SHORT).show();
                             }
                         });
                     }
                   }
               }
           });


            }
        });

        db.collection("venues")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
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