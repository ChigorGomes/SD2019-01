package com.app.leafgarden.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Adapter.ListAdapterPlantas;
import com.app.leafgarden.Classe.Model.Planta;
import com.app.leafgarden.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Plantas extends AppCompatActivity{

    ListView listView;
    List<Planta> plantaList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Planta plantaAux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);

        plantaList= new ArrayList<>();
        listView= findViewById(R.id.listViewPlants);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Plantas.this,InfoPlanta.class);
                intent.putExtra("planta",plantaList.get(position));

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Plantas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plantaList.clear();
                if(dataSnapshot.getChildrenCount() != 0){
                    for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                        Planta planta= postSnapShot.getValue(Planta.class);
                        plantaAux = planta;

                        plantaList.add(planta);
                    }

                    ListAdapterPlantas listAdapterPlantas= new ListAdapterPlantas(Plantas.this,plantaList);
                    listView.setAdapter(listAdapterPlantas);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
