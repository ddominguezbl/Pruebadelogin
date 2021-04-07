package com.daviddo.pruebadelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {

    TextView tvNombreUsuario;
    ImageView ivRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        ivRegresar = (ImageView) findViewById((R.id.ivRegresar));


        Intent elintentquellegoaqui = getIntent();

        String nombre = elintentquellegoaqui.getStringExtra("etiquetanombreusuario");
        tvNombreUsuario.setText("Bienvenido, " + nombre);


        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // onCreateOptionsMenu se invoca cuando se construye la actividad y se crea el menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    // onOptionsItemSelected se invoca cuando se selecciona alguna de las opciones del menu // recibe como parametro un objeto MenuItem (el pulsado)

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // con el objeto MenuItem y su metodo getItemId() podemos saber el id del view del menu pulsa
        int id = item.getItemId();
        switch (id) {
            case (R.id.opcion_jugar):
                btJugarPulsado(null);


                break;
            case (R.id.opcion_configuracion):

                Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.opcion_ranking):
                btRankingPulsado( null);

                break;
            case (R.id.opcion_salir):

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btJugarPulsado(View v){
        Toast.makeText(getApplicationContext(), "Pulsado jugar", Toast.LENGTH_SHORT).show();
        Intent intentJugar = new Intent(this, PartidaActivity.class);
        startActivity(intentJugar);

    }


    public void btRankingPulsado(View v){
        Toast.makeText(getApplicationContext(), "Pulsado ranking", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Pulsado ranking ", Toast.LENGTH_SHORT).show();
    }




}