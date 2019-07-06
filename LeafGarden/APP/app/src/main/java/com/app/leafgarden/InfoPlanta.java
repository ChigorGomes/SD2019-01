package com.app.leafgarden;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Classe.Jardim;
import Classe.Planta;
import Classe.Usuario;
import DAO.JardimDAO;

public class InfoPlanta extends AppCompatActivity {
    TextView nomePlanta,descricaoPlanta,localAdequado,temperaturasIdeais;
    ImageView imageView;
    Planta planta;
    Button buttonCadastroJardim;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        planta= (Planta) getIntent().getSerializableExtra("planta");
        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        nomePlanta =  findViewById(R.id.textViewNomeInfoPlanta);
        descricaoPlanta = findViewById(R.id.textViewDescricaoPlanta);
        localAdequado = findViewById(R.id.textViewLocalAdequadoInfoPlant);
        temperaturasIdeais = findViewById(R.id.textViewTemperaturaInfoPlanta);
        imageView = findViewById(R.id.imageViewInfoPlanta);
        buttonCadastroJardim = findViewById(R.id.buttonAdcPlantaNoJardim);

        //Adicionando scroll nos textViews
        descricaoPlanta.setMovementMethod(new ScrollingMovementMethod());
        localAdequado.setMovementMethod(new ScrollingMovementMethod());

        nomePlanta.setText(planta.getNomePlanta());
        descricaoPlanta.setText(planta.getDescricao());
        localAdequado.setText(planta.getLocalAdequado());
        Bitmap bitmap= (Bitmap) BitmapFactory.decodeByteArray(planta.getFoto(),0,planta.getFoto().length);
        String temp="Umidade: "+"Solo: "+planta.getUmidadeSolo()+" | Ambiente: "+planta.getUmidadeAmbiente()+"\n"+
                "Temperatura: "+"Solo: " +planta.getTempSolo()+" |Ambiente: "+planta.getTempAmbiente();
        temperaturasIdeais.setText(temp);
        imageView.setImageBitmap(bitmap);
        buttonCadastroJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jardim jardim= new Jardim(usuario.getIdUsuario(),planta.getIdPlanta());
                JardimDAO jardimDAO =  new JardimDAO(InfoPlanta.this);
                if(jardimDAO.addJardim(jardim,usuario)){
                    Toast.makeText(InfoPlanta.this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(InfoPlanta.this,TelaMenu.class);
                    intent.putExtra("usuario",usuario);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(InfoPlanta.this,"Erro",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
