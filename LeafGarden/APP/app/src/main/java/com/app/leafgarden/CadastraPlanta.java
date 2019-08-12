package com.app.leafgarden;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import Classe.Planta;

public class CadastraPlanta extends AppCompatActivity {

    EditText editTextnomePlanta;
    EditText editTextdescricaoPlanta;
    EditText editTextlocalAdequado;
    EditText editTexttempAmbiente;
    EditText editTextumidadeAmbiente;
    EditText editTexttempSolo;
    EditText editTextumidadeSolo;
    EditText editTextluminosidade;
    Button cadastrarPlanta;
    ImageView imagemPlanta;
    Button adcImagemPlanta;
    private final int GALERIA_IMAGENS=1;
    private final int PERMISSAO_REQUEST=2;
    String caminhoImagem="";
    final Random numRandomico = new Random();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_planta);

        editTextnomePlanta = (EditText) findViewById(R.id.editTextNomePlanta);
        editTextdescricaoPlanta = (EditText) findViewById(R.id.editTextDescricaoPlanta);
        editTextlocalAdequado = (EditText) findViewById(R.id.editTextLocAdequado);
        editTexttempAmbiente = (EditText) findViewById(R.id.editTextSensorTempAmbiente);
        editTextumidadeAmbiente = (EditText) findViewById(R.id.editTextSensorUmidadeAmbiente);
        editTexttempSolo = (EditText) findViewById(R.id.editTextSensorTempSolo);
        editTextumidadeSolo = (EditText) findViewById(R.id.editTextSensorUmidadeSolo);
        editTextluminosidade = (EditText) findViewById(R.id.editTextLuminosidade);
        cadastrarPlanta = (Button) findViewById(R.id.buttonCadastrarPlanta);
        imagemPlanta = (ImageView) findViewById(R.id.imageViewPlanta);
        adcImagemPlanta = (Button) findViewById(R.id.buttonImagemPlanta);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
            }
        }

        adcImagemPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALERIA_IMAGENS);
            }

        });


        cadastrarPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaPlanta();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode== GALERIA_IMAGENS){
            Uri uri= data.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(uri,filePath,null,null,null);
            cursor.moveToFirst();
            int columnIndex= cursor.getColumnIndex(filePath[0]);
            String picturePath= cursor.getString(columnIndex);
            caminhoImagem= String.valueOf(picturePath);
            cursor.close();

            Bitmap bitmap= (BitmapFactory.decodeFile(picturePath));
            imagemPlanta.setImageBitmap(bitmap);
            Toast.makeText(this,picturePath,Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSAO_REQUEST){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(CadastraPlanta.this,"Permissão aceita",Toast.LENGTH_SHORT).show();

            }else{
                adcImagemPlanta.setClickable(false);
            }
            return;
        }
    }

    private void verificaPlanta(){

        String nomePlanta= editTextnomePlanta.getText().toString();
        String descricao= editTextdescricaoPlanta.getText().toString();
        String localAdequado= editTextlocalAdequado.getText().toString();

        String tempAmbiente= editTexttempAmbiente.getText().toString().trim();
        String umidadeAmbiente=editTextumidadeAmbiente.getText().toString().trim();
        String tempSolo=editTexttempSolo.getText().toString().trim();
        String umidadeSolo=editTextumidadeSolo.getText().toString().trim();
        String luminosidade=editTextluminosidade.getText().toString().trim();



//        float tempAmbiente= Float.parseFloat(editTexttempAmbiente.getText().toString()) ;
//        float umidadeAmbiente=Float.parseFloat(editTextumidadeAmbiente.getText().toString());
//        float tempSolo=Float.parseFloat(editTexttempSolo.getText().toString());
//        float umidadeSolo=Float.parseFloat(editTextumidadeSolo.getText().toString());
//        float luminosidade=Float.parseFloat(editTextluminosidade.getText().toString());

        if(nomePlanta.isEmpty()){
            editTextnomePlanta.setError("Preencha o campo nome!");
            editTextnomePlanta.requestFocus();
        }if(descricao.isEmpty()){
            editTextdescricaoPlanta.setError("Preencha o campo descrição!");
            editTextdescricaoPlanta.requestFocus();

        }if(localAdequado.isEmpty()){
            editTextlocalAdequado.setError("Preencha o campo local adequado!");
            editTextlocalAdequado.requestFocus();
        }if(tempAmbiente.isEmpty()){
            editTexttempAmbiente.setError("Preencha o campo temperatura!");
            editTexttempAmbiente.requestFocus();
        }if(umidadeAmbiente.isEmpty()){
            editTextumidadeAmbiente.setError("Preencha o campo umidade!");
            editTextumidadeAmbiente.requestFocus();
        }if(tempSolo.isEmpty()){
            editTexttempSolo.setError("Preencha o campo temperatura!");
            editTexttempSolo.requestFocus();
        }if(umidadeSolo.isEmpty()){
            editTextumidadeSolo.setError("Preencha o campo umidade!");
            editTextumidadeSolo.requestFocus();
        }if(luminosidade.isEmpty()){
            editTextluminosidade.setError("Preencha o campo luminosidade!");
            editTextluminosidade.requestFocus();
        }

        String chave=  String.valueOf(numRandomico.nextInt(1000000));


        Planta planta= new Planta();
        planta.setNomePlanta(nomePlanta);
        planta.setIdPlanta(Integer.parseInt(chave));
        planta.setDescricao(descricao);
        planta.setLocalAdequado(localAdequado);
        planta.setTempAmbiente(Float.parseFloat(tempAmbiente));
        planta.setUmidadeAmbiente(Float.parseFloat(umidadeAmbiente));
        planta.setTempSolo(Float.parseFloat(tempSolo));
        planta.setUmidadeSolo(Float.parseFloat(umidadeSolo));
        planta.setLuminosidade(Float.parseFloat(luminosidade));




        FirebaseDatabase.getInstance().getReference().child("Plantas").
                child(chave).setValue(planta).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e("msg","deu certo");
                }
            }
        });

    }



}