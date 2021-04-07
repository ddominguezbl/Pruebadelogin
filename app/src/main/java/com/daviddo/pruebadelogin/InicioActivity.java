package com.daviddo.pruebadelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class InicioActivity extends AppCompatActivity {

    TextView tvNombreUsuario;
    ImageView ivRegresar;
    Chip chipmodoFácil;
    Chip chipModoNormal;
    Chip chipModoDifícil;
    int numfilas;
    int numcolumnas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        ivRegresar = (ImageView) findViewById((R.id.ivRegresar));

        chipmodoFácil = (Chip)findViewById(R.id.modofácil);
        chipModoNormal = (Chip)findViewById(R.id.modonormal);
        chipModoDifícil = (Chip)findViewById(R.id.mododifícil);

        chipmodoFácil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true){
                    numcolumnas=3;
                    numfilas=3;
                    Toast.makeText(getApplicationContext(), "Modo FÁCIL seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipModoNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    numcolumnas=4;
                    numfilas=4;
                    Toast.makeText(getApplicationContext(), "Modo NORMAL seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chipModoDifícil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    numcolumnas=5;
                    numfilas=5;
                    Toast.makeText(getApplicationContext(), "Modo DIFÍCIL seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        if ((chipmodoFácil.isChecked()==false) && (chipModoNormal.isChecked()==false) && (chipModoDifícil.isChecked()==false)){
            Toast.makeText(getApplicationContext(), "Seleccione modo de juego", Toast.LENGTH_SHORT).show();
        }
        else{

            Bundle datos = new Bundle();
            datos.putInt("numfilas", numfilas);
            datos.putInt("numcolumnas", numcolumnas);
            Toast.makeText(getApplicationContext(), "Pulsado jugar", Toast.LENGTH_SHORT).show();
            Intent intentJugar = new Intent(this, PartidaActivity.class);
            intentJugar.putExtras(datos);
            startActivity(intentJugar);
        }

    }


    public void btRankingPulsado(View v){
        Toast.makeText(getApplicationContext(), "Pulsado ranking", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Pulsado ranking ", Toast.LENGTH_SHORT).show();
    }




}