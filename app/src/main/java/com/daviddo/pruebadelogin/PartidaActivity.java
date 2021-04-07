package com.daviddo.pruebadelogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;

public class PartidaActivity extends AppCompatActivity {

    ImageView btConfig;
    ImageView btHelp;
    ImageView imagenPuzzle;
    ArrayList<Trozo> listaTrozos;
    Bitmap imagenoriginal, imagenvacia;
    ConstraintLayout CLimagenPuzzle;
    int numfilas = 5;
    int numcolumnas = 5;
    GridLayout gridImagen;
    int anchocadapieza;
    int altocadapieza;
    int anchogrid = 324;
    int altogrid = 500;
    int filaHueco = 0;
    int columnaHueco = 01;
    int filadelaimagenpulsada;
    int columnadelaimagenpulsada;
    ImageView imagenpulsada;
    Button button;




    ImageView[][] arrayImagenes = new ImageView[numfilas][numcolumnas];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btConfig = findViewById(R.id.btConfig);
        gridImagen = findViewById(R.id.idGridImagen);
        button = (Button) findViewById(R.id.button);

        Bundle datos = this.getIntent().getExtras();
        numfilas = datos.getInt("numfilas");
        numcolumnas = datos.getInt("numcolumnas");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirbutton();
            }
        });

    }

    public void abrirbutton() {
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);


    }

    public void imagenpulsada(View view) {
        imagenpulsada = (ImageView) view;
        String tag = imagenpulsada.getTag().toString();
        String[] trozos = tag.split(":");
        filadelaimagenpulsada = Integer.parseInt(trozos[0]);
        columnadelaimagenpulsada = Integer.parseInt(trozos[1]);

        //     imagenpulsada.setVisibility(View.INVISIBLE);

        // mirrar si ARRIBA hay hueco
        if (filadelaimagenpulsada - 1 == filaHueco && columnadelaimagenpulsada == columnaHueco) {
            moverImagen();
        }
        // mirrar si ABAJO hay hueco
        if (filadelaimagenpulsada + 1 == filaHueco && columnadelaimagenpulsada == columnaHueco) {
            moverImagen();
        }
        // mirrar si DERECHA hay hueco
        if (filadelaimagenpulsada == filaHueco && columnadelaimagenpulsada + 1 == columnaHueco) {
            moverImagen();
        }
        // mirrar si IZQUIERDA hay hueco
        if (filadelaimagenpulsada == filaHueco && columnadelaimagenpulsada - 1 == columnaHueco) {
            moverImagen();
        }
    }

    public void moverImagen() {
        Drawable drawablepulsada = arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].getDrawable();
        Drawable drawablehueco = arrayImagenes[filaHueco][columnaHueco].getDrawable();
        arrayImagenes[filaHueco][columnaHueco].setImageDrawable(drawablepulsada);
        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setImageDrawable(drawablehueco);

        arrayImagenes[filaHueco][columnaHueco].setTag(filaHueco + ":" + columnaHueco);
        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setTag(filadelaimagenpulsada + ":" + columnadelaimagenpulsada);
        filaHueco = filadelaimagenpulsada;
        columnaHueco = columnadelaimagenpulsada;
    }

    public void onclickEmpezar(View view) {
        iniciarTodo();
    }

    private void iniciarTodo() {
        gridImagen.setColumnCount(numcolumnas);
        gridImagen.setRowCount(numfilas);
        gridImagen.setOrientation(GridLayout.HORIZONTAL);
        ViewGroup.LayoutParams par = gridImagen.getLayoutParams();

        /// anchogrid = gridImagen.getWidth();
        //  altogrid = gridImagen.getHeight();

        // elegir la imagen
        imagenoriginal = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        // imagenoriginal = Bitmap.createScaledBitmap(imagenoriginal,anchogrid, altogrid, false);
        imagenvacia = BitmapFactory.decodeResource(getResources(), R.drawable.image_2935360_960_720);


        // dividir la imagebn
        trocearImagen(imagenoriginal);

        // desordenar images
        desordenar();
        //   Collections.shuffle(listaTrozos);

        // pintr la imagen
        pintarImagen();


        // colocar escuchadores a cada elemento imagen

        // jugar:
        //      voltear ficha
        //      comprobar fin
        //      guardar resutaldos


    }


    private ArrayList<Trozo> trocearImagen(Bitmap imageninicial) {
        listaTrozos = new ArrayList<>();
        anchocadapieza = imageninicial.getWidth() / numcolumnas;
        altocadapieza = imageninicial.getHeight() / numfilas;

        int yCoord = 0;
        for (int f = 0; f < numfilas; f++) {
            int xCoord = 0;
            for (int c = 0; c < numcolumnas; c++) {

                Bitmap b = Bitmap.createBitmap(imageninicial, xCoord, yCoord, anchocadapieza, altocadapieza);
                ImageView imagen = new ImageView(this);
                imagen.setId(View.generateViewId());
                LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parametros.gravity = Gravity.CENTER;
                imagen.setLayoutParams(parametros);
                imagen.setImageBitmap(b);
                //  imagen.setTag(f + ":" + c);
                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagenpulsada(v);
                    }
                });
                Trozo trozo = new Trozo(imagen, f, c, false);
                listaTrozos.add(trozo);

                xCoord = xCoord + anchocadapieza;
            }
            yCoord += altocadapieza;
        }

        Bitmap bitmapvacio = Bitmap.createBitmap(imagenvacia, 0, 0, anchocadapieza, altocadapieza);
        ImageView imagen = new ImageView(this);
        imagen.setId(View.generateViewId());
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametros.gravity = Gravity.CENTER;
        imagen.setLayoutParams(parametros);
        imagen.setImageBitmap(bitmapvacio);
        imagen.setTag("HUECO");
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenpulsada(v);
            }
        });
        Trozo trozo = new Trozo(imagen, numfilas - 1, numcolumnas - 1, true);
        listaTrozos.set(numcolumnas * numfilas - 1, trozo);
        return listaTrozos;
    }


    private void desordenar() {

        // desordenar el arraylist
        Collections.shuffle(listaTrozos);


    }


    private void pintarImagen() {
        // crear el array con el arraylist

        int cont = 0;
        for (int f = 0; f < numfilas; f++) {

            for (int c = 0; c < numcolumnas; c++) {
                //  if( ! ( f==numfilas-1 &&  c==numcolumnas-1) ) {
                ImageView imagen = listaTrozos.get(cont).imagen;
                if (imagen.getTag() != null) {


                    if (imagen.getTag().equals("HUECO")) {
                        filaHueco = f;
                        columnaHueco = c;

                    }
                }
                imagen.setTag(f + ":" + c);
                arrayImagenes[f][c] = imagen;
                gridImagen.addView(imagen);
                cont++;
                //   }

            }
        }

    }


    public void config(View view) {
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }


}
