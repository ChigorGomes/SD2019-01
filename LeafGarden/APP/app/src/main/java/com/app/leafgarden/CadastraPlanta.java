package com.app.leafgarden;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CadastraPlanta extends AppCompatActivity {

    EditText nomePlanta;
    EditText descricaoPlanta;
    EditText localAdequado;
    EditText tempAmbiente;
    EditText umidadeAmbiente;
    EditText tempSolo;
    EditText umidadeSolo;
    EditText luminosidade;
    Button cadastrarPlanta;
    ImageView imagemPlanta;
    Button adcImagemPlanta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_planta);


        nomePlanta = (EditText) findViewById(R.id.editTextNomePlanta);
        descricaoPlanta = (EditText) findViewById(R.id.editTextDescricaoPlanta);
        localAdequado = (EditText) findViewById(R.id.editTextLocAdequado);
        tempAmbiente = (EditText) findViewById(R.id.editTextSensorTempAmbiente);
        umidadeAmbiente = (EditText) findViewById(R.id.editTextSensorUmidadeAmbiente);
        tempSolo = (EditText) findViewById(R.id.editTextSensorTempSolo);
        umidadeSolo = (EditText) findViewById(R.id.editTextSensorUmidadeSolo);
        luminosidade = (EditText) findViewById(R.id.editTextLuminosidade);
        cadastrarPlanta = (Button) findViewById(R.id.buttonCadastrarPlanta);
        imagemPlanta = (ImageView) findViewById(R.id.imageViewPlanta);
        adcImagemPlanta = (Button) findViewById(R.id.buttonImagemPlanta);

        cadastrarPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nomePlanta.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo nome!",Toast.LENGTH_SHORT).show();
                }else if(luminosidade.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo luminosidadel!",Toast.LENGTH_SHORT).show();
                } else if(localAdequado.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo local!",Toast.LENGTH_SHORT).show();
                }else if(umidadeSolo.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo umidade solo!",Toast.LENGTH_SHORT).show();
                }else if(umidadeAmbiente.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo umidade ambiente!",Toast.LENGTH_SHORT).show();
                }else if(tempAmbiente.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo temperatura ambiente!",Toast.LENGTH_SHORT).show();
                }else if(tempSolo.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo temperatura solo!",Toast.LENGTH_SHORT).show();
                }else  if(descricaoPlanta.getText().toString().equals("")){
                    Toast.makeText(CadastraPlanta.this,"Preencha o campo descrição!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
