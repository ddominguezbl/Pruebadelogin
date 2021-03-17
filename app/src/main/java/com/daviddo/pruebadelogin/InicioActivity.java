package com.daviddo.pruebadelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {

    TextView tvNombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

         tvNombreUsuario =     findViewById(R.id.tvNombreUsuario);

        Intent elintentquellegoaqui = getIntent();

        String nombre = elintentquellegoaqui.getStringExtra("etiquetanombreusuario");
        tvNombreUsuario.setText("Bienvenido, " + nombre);



    }
}