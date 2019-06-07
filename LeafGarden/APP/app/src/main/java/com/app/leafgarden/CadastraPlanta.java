package com.app.leafgarden;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import Classe.Planta;
import DAO.PlantaDAO;

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
    private final int GALERIA_IMAGENS=1;
    private final int PERMISSAO_REQUEST=2;

    String caminhoImagem="";




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


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
            }
        }



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
                }else if(caminhoImagem.equals("")){
                    Toast.makeText(CadastraPlanta.this,"Coloque uma foto!",Toast.LENGTH_SHORT).show();

                }else{

                    String plantaAux= nomePlanta.getText().toString();
                    String descAux= descricaoPlanta.getText().toString();
                    String locAdequadoAux= localAdequado.getText().toString();
                    float tempAmbienteAux= Float.parseFloat(tempAmbiente.getText().toString()) ;
                    float umidadeAmb=Float.parseFloat(umidadeAmbiente.getText().toString());
                    float tempSoloAux=Float.parseFloat(tempSolo.getText().toString());
                    float umidadeSoloAux=Float.parseFloat(umidadeSolo.getText().toString());
                    float lumi=Float.parseFloat(luminosidade.getText().toString());


                    Planta planta= new Planta(plantaAux,descAux,locAdequadoAux,tempAmbienteAux,umidadeAmb,tempSoloAux,umidadeSoloAux,lumi, caminhoImagem);
                    PlantaDAO plantaDAO= new PlantaDAO(CadastraPlanta.this);

                    if(plantaDAO.addPlanta(planta)){
                        Toast.makeText(CadastraPlanta.this,"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(CadastraPlanta.this,"erro ao cadastrar!",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        adcImagemPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALERIA_IMAGENS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode== GALERIA_IMAGENS){
            Uri selectUri= data.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(selectUri,filePath,null,null,null);
            cursor.moveToFirst();
            int columnIndex= cursor.getColumnIndex(filePath[0]);
            String picturePath= cursor.getString(columnIndex);
            caminhoImagem= String.valueOf(picturePath);
            cursor.close();
            Bitmap bitmap= (BitmapFactory.decodeFile(picturePath));
            imagemPlanta.setImageBitmap(bitmap);


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

    public static byte[] ImageViewToByte(ImageView imageView){
        Bitmap bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytesArray=  stream.toByteArray();
        return  bytesArray;
    }



}
