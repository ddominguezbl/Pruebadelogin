package com.daviddo.pruebadelogin;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PartidaActivity extends AppCompatActivity{

    ImageView btConfig;
    ImageView btHelp;
    ImageView imagenPuzzle;
    Button btEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btConfig = (ImageView) findViewById(R.id.btConfig);
        btHelp = (ImageView) findViewById(R.id.btHelp);
        imagenPuzzle = (ImageView) findViewById(R.id.ImagenPuzzle);


    }
    public void onclickEmpezar(View view){
        cargarImagen();
    }

    private void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path=data.getData();
            imagenPuzzle.setImageURI(path);
        }
    }

    public void config(View view){
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }

}
