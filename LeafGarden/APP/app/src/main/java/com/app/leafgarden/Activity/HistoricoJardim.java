package com.app.leafgarden.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Adapter.ListAdapterHistorico;
import com.app.leafgarden.Classe.Model.Historico;
import com.app.leafgarden.Classe.Model.Jardim;
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

public class HistoricoJardim extends AppCompatActivity {
    ListView listView;
    List<Historico> historicoList;
    DatabaseReference databaseReference, datab;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Jardim jardim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontentlayout );
        listView = findViewById(R.id.listViewPlants);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        historicoList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Historico historico= historicoList.get(position);
                databaseReference.child("Jardim").child(firebaseAuth.getUid()).child(historico.getIdsensor()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()!=0){
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                jardim = snapshot.getValue(Jardim.class);
                                AlertDialog.Builder builder= new AlertDialog.Builder(HistoricoJardim.this);
                                String msg= jardim.getNomePlanta()+
                                        " \nLuminosidade:"+ historico.getLuminosidade()+
                                        "\nTemperatura Ambiente: "+historico.getTemperaturaambiente()+
                                        "\nTemperatura Solo: "+historico.getTemperaturasolo()
                                        +"\nUmidade: Ambiente: "+historico.getUmidadeambiente()+
                                        "\nUmidade Solo:"+historico.getUmidadesolo();
                                builder.setMessage(msg);
                                builder.setNeutralButton("Ok",null);
                                builder.show();
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        historicoList.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        datab = FirebaseDatabase.getInstance().getReference();
       databaseReference.child("Jardim").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.getChildrenCount()!=0){
                   for( final DataSnapshot snapshot: dataSnapshot.getChildren()){

                        datab.child("Historico").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getChildrenCount()!=0){

                                    for (DataSnapshot snapshot1:  dataSnapshot.getChildren()){
                                        if(snapshot.getKey().equals(snapshot1.getKey())){
                                            for(DataSnapshot snapshot2: snapshot1.getChildren()){
                                                Historico historico= snapshot2.getValue(Historico.class);
                                                historicoList.add(historico);
                                            }


                                        }

                                            ListAdapterHistorico listAdapterHistorico= new ListAdapterHistorico(HistoricoJardim.this,historicoList);
                                            listView.setAdapter(listAdapterHistorico);


                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                   }

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }
}
