package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Classe.Jardim;
import Classe.ListAdapterJardim;

public class MeuJardim extends AppCompatActivity {

    ListView listView;
    List<Jardim> jardimArrayList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Jardim jardimAux;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout);

        jardimArrayList = new ArrayList<>();
        listView = findViewById(R.id.listViewPlants);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MeuJardim.this,SensorPlanta.class);
                intent.putExtra("planta",jardimArrayList.get(position));
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Jardim").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jardimArrayList.clear();
                if(dataSnapshot.getChildrenCount()!=0){

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Jardim jardim= snapshot.getValue(Jardim.class);
                        jardimArrayList.add(jardim);
                        jardimAux = jardim;
                    }

                    ListAdapterJardim listAdapterJardim= new ListAdapterJardim(MeuJardim.this,jardimArrayList);
                    listView.setAdapter(listAdapterJardim);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
