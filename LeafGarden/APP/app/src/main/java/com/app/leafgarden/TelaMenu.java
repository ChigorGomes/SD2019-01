package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Classe.Usuario;

public class TelaMenu extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private Usuario usuario;
    Button buttonPlanta, buttomMeuJardim, buttonEditarConta, buttonHistorico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        buttonPlanta = (Button) findViewById(R.id.buttonPlantas);
        buttomMeuJardim= findViewById(R.id.buttonMeuJardim);
        buttonEditarConta = findViewById(R.id.buttonConta);
        buttonHistorico= findViewById(R.id.buttonHistorico);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



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
//
//        buttonHistorico.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(TelaMenu.this, HistoricoJardim.class);
//                intent.putExtra("usuario",usuario);
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.end:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TelaMenu.this,MainActivity.class));
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
