package com.app.leafgarden.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Model.Usuario;
import com.app.leafgarden.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaMenu extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private Usuario usuario;
    FirebaseDatabase databaseReference;
    Button buttonPlanta, buttomMeuJardim, buttonEditarConta, buttonHistorico;
    TextView textViewBemV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        buttonPlanta = (Button) findViewById(R.id.buttonPlantas);
        buttomMeuJardim= findViewById(R.id.buttonMeuJardim);
        buttonEditarConta = findViewById(R.id.buttonConta);
        buttonHistorico= findViewById(R.id.buttonHistorico);
        textViewBemV = findViewById(R.id.textViewBemV);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance();


        databaseReference.getReference().child("Usuario/"+firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                textViewBemV.setText("Bem-vindo, "+usuario.getNome().substring(0, usuario.getNome().indexOf(" "))+"!");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        buttonPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this,Plantas.class);
                startActivity(intent);
            }
        });

        buttomMeuJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, MeuJardim.class);
                startActivity(intent);

            }
        });
        buttonEditarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, EditarUsuario.class);

                startActivity(intent);
            }
        });

        buttonHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, HistoricoJardim.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.telaActivityPlant:
                startActivity(new Intent(TelaMenu.this,CadastraPlanta.class));
                break;
            case R.id.end:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TelaMenu.this,MainActivity.class));
                finish();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

}
