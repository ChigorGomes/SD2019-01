package com.app.leafgarden.Activity;

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

import com.app.leafgarden.Classe.Model.Planta;
import com.app.leafgarden.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
    private Uri fiUri;
    private FirebaseStorage storage;
    private StorageReference reference;
    private String lclImagem;
    private String urlImagem;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    String caminhoImagem="";
    final Random numRandomico = new Random();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_planta);

        editTextnomePlanta = (EditText) findViewById(R.id.editTextNomePlanta);
        editTextdescricaoPlanta = (EditText) findViewById(R.id.editTextDescricaoPlanta);
        editTextlocalAdequado = (EditText) findViewById(R.id.editTextLocAdequado);
        editTexttempAmbiente = (EditText) findViewById(R.id.editTextSensorUmidadeAmbiente);
        editTextumidadeAmbiente = (EditText) findViewById(R.id.editTextLuminosidade);
        editTexttempSolo = (EditText) findViewById(R.id.editTextSensorTempSolo);
        editTextumidadeSolo = (EditText) findViewById(R.id.editTextSensorUmidadeSolo);
        editTextluminosidade = (EditText) findViewById(R.id.editTextLuminosidade);
        cadastrarPlanta = (Button) findViewById(R.id.buttonCadastrarPlanta);
        imagemPlanta = (ImageView) findViewById(R.id.imageViewPlanta);
        adcImagemPlanta = (Button) findViewById(R.id.buttonImagemPlanta);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();



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
            fiUri= data.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(fiUri,filePath,null,null,null);
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





        if(nomePlanta.isEmpty()){
            editTextnomePlanta.setError("Preencha o campo nome!");
            editTextnomePlanta.requestFocus();
        }else if(umidadeAmbiente.isEmpty()){
            editTextumidadeAmbiente.setError("Preencha o campo umidade!");
            editTextumidadeAmbiente.requestFocus();
        }else if(localAdequado.isEmpty()) {
            editTextlocalAdequado.setError("Preencha o campo local adequado!");
            editTextlocalAdequado.requestFocus();
        }else if(tempAmbiente.isEmpty()){
            editTexttempAmbiente.setError("Preencha o campo temperatura!");
            editTexttempAmbiente.requestFocus();
        }else if(tempSolo.isEmpty()){
            editTexttempSolo.setError("Preencha o campo temperatura!");
            editTexttempSolo.requestFocus();
        }else if(umidadeSolo.isEmpty()){
            editTextumidadeSolo.setError("Preencha o campo umidade!");
            editTextumidadeSolo.requestFocus();
        }else if(luminosidade.isEmpty()){
            editTextluminosidade.setError("Preencha o campo luminosidade!");
            editTextluminosidade.requestFocus();
        } else if(descricao.isEmpty()){
            editTextdescricaoPlanta.setError("Preencha o campo descrição!");
            editTextdescricaoPlanta.requestFocus();

        }else {
            uploadImage();
        }



    }

    private void uploadImage() {
        UploadTask uploadTask = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String nomeImagem = dateFormat.format(new Date()).trim();
        lclImagem = "images/plantas/" + nomeImagem + ".jpg";
        final StorageReference ref = reference.child(lclImagem);

        if(fiUri != null){


            uploadTask = (UploadTask) ref.putFile(fiUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CadastraPlanta.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(CadastraPlanta.this,TelaCadastroLogin.class);
                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CadastraPlanta.this, "Falha ao enviar!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                        }
//                    });


        }

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()) throw task.getException();
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    urlImagem = downloadUri.toString();
                    Log.d("msg", "URL 2: "+ lclImagem);
                    addPlanta();

                }
                else{
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void addPlanta(){
        String nomePlanta= editTextnomePlanta.getText().toString();
        String descricao= editTextdescricaoPlanta.getText().toString();
        String localAdequado= editTextlocalAdequado.getText().toString();

        String tempAmbiente= editTexttempAmbiente.getText().toString();
        String umidadeAmbiente=editTextumidadeAmbiente.getText().toString();
        String tempSolo=editTexttempSolo.getText().toString();
        String umidadeSolo=editTextumidadeSolo.getText().toString();
        String luminosidade=editTextluminosidade.getText().toString();
        String chave=  String.valueOf(numRandomico.nextInt(1000000));


        Planta planta= new Planta();
        planta.setNomePlanta(nomePlanta);
        planta.setIdPlanta(chave);
        planta.setDescricao(descricao);
        planta.setLocalAdequado(localAdequado);
        planta.setImagemUrl(urlImagem);
        float temperaturaAmbiente = Float.parseFloat(tempAmbiente);
        float umidAmbiente = Float.parseFloat(umidadeAmbiente);
        float temperaturaSolo=Float.parseFloat(tempSolo);
        float umidSolo=Float.parseFloat(umidadeSolo);
        float lumi=Float.parseFloat(luminosidade);
        planta.setTempAmbiente(temperaturaAmbiente);
        planta.setUmidadeAmbiente(umidAmbiente);
        planta.setTempSolo(temperaturaSolo);
        planta.setUmidadeSolo(umidSolo);
        planta.setLuminosidade(lumi);





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