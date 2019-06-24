package com.app.leafgarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import Classe.Usuario;

public class TelaMenu extends AppCompatActivity {
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

        buttonPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this,Plantas.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });

        buttomMeuJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, MeuJardim.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);

            }
        });
        buttonEditarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, EditarUsuario.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });

        buttonHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaMenu.this, HistoricoJardim.class);
                intent.putExtra("usuario",usuario);
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
            case R.id.end:
                Intent intent= new Intent(TelaMenu.this, MainActivity.class);
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
