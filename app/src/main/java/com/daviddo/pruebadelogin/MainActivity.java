package com.daviddo.pruebadelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    EditText etUsuario;
    EditText etPassword;
    Button btEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         etUsuario = findViewById(R.id.etUsuario);
         etPassword =  findViewById(R.id.etPassword);
         btEntrar= findViewById(R.id.btEntrar);


    }

    public void btEntrarPulsado(View v){
        String nombreusuario = etUsuario.getText().toString();
        String passwordusuario  = etPassword.getText().toString();

        if(nombreusuario.equals("PEPE") && passwordusuario.equals("111")){
            Toast.makeText(this,"ACCESO CONDEDIDO", Toast.LENGTH_LONG).show();

            Intent miintent = new Intent(MainActivity.this, InicioActivity.class);
            miintent.putExtra(  "etiquetanombreusuario"    ,  nombreusuario   );
            startActivity(miintent);


            /// pasa a la siguiente actividad....
        }else{
            Toast.makeText(this,"ACCESO DENEGADO; el usuario es " + nombreusuario, Toast.LENGTH_LONG).show();
        }
    }



}