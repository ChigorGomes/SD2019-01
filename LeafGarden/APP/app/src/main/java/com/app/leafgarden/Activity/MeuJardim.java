package com.app.leafgarden.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Adapter.ListAdapterJardim;
import com.app.leafgarden.Classe.Model.Jardim;
import com.app.leafgarden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Jardim").child(firebaseAuth.getUid()).child(jardimArrayList.get(position).getIdJardim()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MeuJardim.this,"Excluído com sucesso!",Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
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
