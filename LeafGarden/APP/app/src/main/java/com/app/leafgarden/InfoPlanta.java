package com.app.leafgarden;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import Classe.Jardim;
import Classe.Planta;

public class InfoPlanta extends AppCompatActivity {
    TextView textViewnomePlanta;
    TextView textViewdescricaoPlanta;
    TextView textViewlocalAdequado;
    TextView textViewtemperaturasIdeais;
    ImageView imageView;
    Planta planta;
    Button buttonCadastroJardim;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    final Random numRandomico = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        textViewnomePlanta =  findViewById(R.id.textViewNomeInfoPlanta);
        textViewdescricaoPlanta = findViewById(R.id.textViewDescricaoPlanta);
        textViewlocalAdequado = findViewById(R.id.textViewLocalAdequadoInfoPlant);
        textViewtemperaturasIdeais = findViewById(R.id.textViewTemperaturaInfoPlanta);
        imageView = findViewById(R.id.imageViewInfoPlanta);
        buttonCadastroJardim = findViewById(R.id.buttonAdcPlantaNoJardim);

        //Adicionando scroll nos textViews
        textViewdescricaoPlanta.setMovementMethod(new ScrollingMovementMethod());
        textViewlocalAdequado.setMovementMethod(new ScrollingMovementMethod());
        planta = (Planta) getIntent().getSerializableExtra("planta");

        textViewnomePlanta.setText(planta.getNomePlanta());
        textViewdescricaoPlanta.setText(planta.getDescricao());
        textViewlocalAdequado.setText(planta.getLocalAdequado());
        Glide.with(getApplicationContext()).load(planta.getImagemUrl()).into(imageView);


        buttonCadastroJardim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroJardim();

            }
        });







    }


    public void cadastroJardim(){
        Jardim jardim= new Jardim();
        String chave=  String.valueOf(numRandomico.nextInt(1000000));

        jardim.setIdJardim(chave);
        jardim.setIdPlanta(planta.getIdPlanta());
        jardim.setIdUsuario(firebaseAuth.getUid());
        FirebaseDatabase.getInstance().getReference().child("Jardim").
                child(chave).setValue(jardim).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e("msg","deu certo");
                }
            }
        });
    }
}
