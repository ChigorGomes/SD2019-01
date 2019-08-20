package com.app.leafgarden.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.leafgarden.Classe.Model.Jardim;
import com.app.leafgarden.Classe.Model.Planta;
import com.app.leafgarden.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

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
    DatabaseReference databaseReference;
    final Random numRandomico = new Random();
    Boolean flag = false;



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
                dialogSensor();

            }
        });







    }


    public void dialogSensor(){


        AlertDialog.Builder msg= new AlertDialog.Builder(InfoPlanta.this);
        msg.setTitle("Código do sensor");
        msg.setMessage("Digite o código do sensor");

        final EditText editText= new EditText(InfoPlanta.this);
        msg.setView(editText);




        msg.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(InfoPlanta.this,"Preencha o campo".toString().trim(),Toast.LENGTH_SHORT).show();

                }else{
                    verificaJardim(editText.getText().toString());
                    flag = true;

                }

            }
        });

        msg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        msg.show();


    }



    public void verificaJardim(final String codigo){
        FirebaseDatabase.getInstance().getReference().child("Sensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().equals(codigo)) {
                                cadastrarJardim(snapshot.getKey());



                        }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}


    public void cadastrarJardim(String idSensor){
        Jardim jardim = new Jardim();

        String chave = String.valueOf(numRandomico.nextInt(1000000));
        jardim.setIdJardim(chave);
        jardim.setIdPlanta(planta.getIdPlanta());
        jardim.setIdUsuario(firebaseAuth.getUid());
        jardim.setNomePlanta(planta.getNomePlanta());
        jardim.setDescricao(planta.getDescricao());
        jardim.setLocalAdequado(planta.getLocalAdequado());
        jardim.setTempAmbiente(planta.getTempAmbiente());
        jardim.setUmidadeAmbiente(planta.getUmidadeAmbiente());
        jardim.setTempSolo(planta.getUmidadeSolo());
        jardim.setLuminosidade(planta.getLuminosidade());
        jardim.setImagemUrl(planta.getImagemUrl());
        jardim.setIdSensor(idSensor);



        if(flag){
            databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.child("Jardim").
                    child(firebaseUser.getUid()).child(idSensor).child(chave).setValue(jardim).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        flag=false;
                        Toast.makeText(InfoPlanta.this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(InfoPlanta.this,Plantas.class);
                        startActivity(intent);


                    }
                }
            });
        }

    }



}
