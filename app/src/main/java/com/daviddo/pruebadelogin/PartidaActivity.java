package com.daviddo.pruebadelogin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PartidaActivity extends AppCompatActivity {

    ImageView btConfig;
    ImageView btHelp;
    ImageView imagenPuzzle;
    ArrayList<Bitmap> listapiezas;
    Bitmap imagenoriginal;
    ConstraintLayout CLimagenPuzzle;
    int numfilas = 4;
    int numcolumnas = 3;
    GridLayout gridImagen;
    int anchocadapieza ;
    int altocadapieza ;
    int anchogrid = 324 ;
    int altogrid = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btConfig = (ImageView) findViewById(R.id.btConfig);

        // preparar el grid
        gridImagen = findViewById(R.id.idGridImagen);

//        final ConstraintLayout CLimagenPuzzle = findViewById(R.id.CLimagenPuzzle);
//
//        imagenPuzzle.post(new Runnable() {
//            @Override
//            public void run() {
//                pieces = splitImage();
//                for(Bitmap piece : pieces) {
//                    ImageView iv = new ImageView(getApplicationContext());
//                    iv.setImageBitmap(piece);
//                    CLimagenPuzzle.addView(iv);
//                }
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gridImagen.setColumnCount(3);
        gridImagen.setRowCount(4);
        gridImagen.setOrientation(GridLayout.HORIZONTAL);
        ViewGroup.LayoutParams par = gridImagen.getLayoutParams();


        /// anchogrid = gridImagen.getWidth();
        //  altogrid = gridImagen.getHeight();

        // elegir la imagen
        imagenoriginal = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        // imagenoriginal = Bitmap.createScaledBitmap(imagenoriginal,anchogrid, altogrid, false);

        // dividir la imagebn
        trocearImagen(imagenoriginal);

        // desordenar images
        Collections.shuffle(listapiezas);

        // pintr la imagen
        pintarImagen();


        // colocar escuchadores a cada elemento imagen

        // jugar:
        //      voltear ficha
        //      comprobar fin
        //      guardar resutaldos


    }

    private ArrayList<Bitmap> trocearImagen(Bitmap imageninicial) {
        listapiezas = new ArrayList<>();
        anchocadapieza = imageninicial.getWidth() / numcolumnas;
        altocadapieza = imageninicial.getHeight() / numfilas;

        int yCoord = 0;
        for (int f = 0; f < numfilas; f++) {
            int xCoord = 0;
            for (int c = 0; c < numcolumnas; c++) {
                Bitmap b = Bitmap.createBitmap(imageninicial, xCoord, yCoord, anchocadapieza, altocadapieza);
                listapiezas.add(b);
                xCoord = xCoord + anchocadapieza;
            }
            yCoord += altocadapieza;
        }
        return listapiezas;
    }

    private void pintarImagen() {

        ImageView[][] arrayImagenes = new ImageView[numfilas][numcolumnas];
        int contadorimagenes = 0;
        for (int f = 0; f < numfilas; f++) {

            for (int c = 0; c < numcolumnas; c++) {
                ImageView imagen = new ImageView(this);

                imagen.setId(View.generateViewId());

                LinearLayout.LayoutParams parametros =    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parametros.gravity = Gravity.CENTER;
                imagen.setLayoutParams(parametros);
                imagen.setImageBitmap(listapiezas.get(contadorimagenes));
                imagen.setTag(f+":"+c);
                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagenpulsada(v);
                    }
                });
                arrayImagenes[f][c] = imagen;
                gridImagen.addView(imagen);
                contadorimagenes++;

            }
        }

    }


    public void imagenpulsada(View view) {
        ImageView imagenpulsada = (ImageView) view;
        String tag = imagenpulsada.getTag().toString();
        String [] trozos = tag.split(":");
        int filadelaimagenpulsada = Integer.parseInt(trozos[0]);
        int columnadelaimagenpulsada = Integer.parseInt(trozos[1]);

        imagenpulsada.setVisibility(View.INVISIBLE);

    }

    public void onclickEmpezar(View view) {
        cargarImagen();

    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagenPuzzle.setImageURI(path);
        }
    }

    public void config(View view) {
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }

}
