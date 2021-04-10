package com.daviddo.pruebadelogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;

public class PartidaActivity extends AppCompatActivity {

    ImageView btConfig;
    ImageView imagenPuzzle;
    ArrayList<ImageView> listaTrozos;
    Bitmap imagenoriginal, imagenvacia;
    ConstraintLayout CLimagenPuzzle;
    int numfilas = 2;
    int numcolumnas = 2;
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
    Button botonGameOver, btEmpezar, btHelp;
    int ronda  =1;
    boolean haganado = true;



    ImageView[][] arrayImagenes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        Bundle datos = this.getIntent().getExtras();
        numfilas = datos.getInt("numfilas");
        numcolumnas = datos.getInt("numcolumnas");

        btConfig = findViewById(R.id.btConfig);
        gridImagen = findViewById(R.id.idGridImagen);
        botonGameOver = (Button) findViewById(R.id.button);
        btHelp = (Button) findViewById(R.id.btHelp);

        btEmpezar = (Button) findViewById(R.id.btEmpezar);

        botonGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirbutton();
            }
        });

        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                irAHelp();
            }
        });

    }


    private void iniciarTodo() {
        haganado = false;
        TextView numeroronda = findViewById(R.id.numeroRonda);
        numeroronda.setText(ronda+"");
        arrayImagenes = new ImageView[numfilas][numcolumnas];
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

        desordenar();

        pintarImagen();

    }



    private void trocearImagen(Bitmap imageninicial) {
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

                Posiciones pos = new Posiciones();
                pos.xInicial = f;
                pos.yInicial = c;
                imagen.setTag(pos);

                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagenpulsada(v);
                    }
                });

                listaTrozos.add(imagen);

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

        Posiciones pos = new Posiciones();
        pos.xInicial = numfilas-1;
        pos.yInicial = numcolumnas-1;
        pos.eshueco = true;
        imagen.setTag(pos);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenpulsada(v);
            }
        });

        listaTrozos.set(numcolumnas * numfilas - 1, imagen);

    }

    private void desordenar() {

        // desordenar el arraylist
        Collections.shuffle(listaTrozos);

        // y ahora ya desordenado le ponemos las coordenadas reales que tenga ahora
//        int cont = 0;
//        for (int f = 0; f < numfilas; f++) {
//            for (int c = 0; c < numcolumnas; c++) {
//                ImageView imagen = listaTrozos.get(cont);
//                Posiciones posicionquetenia  = (Posiciones) imagen.getTag();
//                posicionquetenia.xActual = f;
//                posicionquetenia.yActual  = c;
//            }
//        }

    }

    private void pintarImagen() {
        // crear el array con el arraylist

        int cont = 0;
        for (int f = 0; f < numfilas; f++) {

            for (int c = 0; c < numcolumnas; c++) {

                ImageView imagen = listaTrozos.get(cont);
                Posiciones posicionquetenia  = (Posiciones) imagen.getTag();
                posicionquetenia.xActual = f;
                posicionquetenia.yActual  = c;

                if (posicionquetenia.eshueco){
                    filaHueco = f;
                    columnaHueco = c;


                }

                arrayImagenes[f][c] = imagen;
                gridImagen.addView(imagen);



                cont++;
            }
        }

    }

    public void imagenpulsada(View view) {




        imagenpulsada = (ImageView) view;
        Posiciones posicionquetenia  = (Posiciones) imagenpulsada.getTag();
        filadelaimagenpulsada = posicionquetenia.xActual;
        columnadelaimagenpulsada = posicionquetenia.yActual;

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
        logDePosiciones();

        Posiciones posicionesDelHueco     = (Posiciones) arrayImagenes[filaHueco][columnaHueco].getTag();
        Posiciones posicionesDeLaPulsada  = (Posiciones) arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].getTag();

        Drawable drawablepulsada = arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].getDrawable();
        Drawable drawablehueco = arrayImagenes[filaHueco][columnaHueco].getDrawable();

        arrayImagenes[filaHueco][columnaHueco].setImageDrawable(drawablepulsada);
        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setImageDrawable(drawablehueco);

        // op 1
        posicionesDelHueco.xActual = filadelaimagenpulsada;
        posicionesDelHueco.yActual = columnadelaimagenpulsada;
        posicionesDeLaPulsada.xActual = filaHueco;
        posicionesDeLaPulsada.yActual = columnaHueco;
        arrayImagenes[filaHueco][columnaHueco].setTag(posicionesDeLaPulsada);
        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setTag(posicionesDelHueco);

        // op2
//        arrayImagenes[filaHueco][columnaHueco].setTag(posicionesDelHueco);
//        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setTag(posicionesDeLaPulsada);

        //  op3
//        Posiciones posicionesDelHueco2     = (Posiciones) arrayImagenes[filaHueco][columnaHueco].getTag();
//        Posiciones posicionesDeLaPulsada2  = (Posiciones) arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].getTag();
//        posicionesDelHueco2.xInicial = posicionesDeLaPulsada.xInicial;
//        posicionesDelHueco2.yInicial = posicionesDeLaPulsada.yInicial;
//        posicionesDeLaPulsada2.xInicial = posicionesDelHueco.xInicial;
//        posicionesDeLaPulsada2.yInicial = posicionesDelHueco.yInicial;

//        arrayImagenes[filaHueco][columnaHueco].setTag(posicionesDelHueco2);
//        arrayImagenes[filadelaimagenpulsada][columnadelaimagenpulsada].setTag(posicionesDeLaPulsada2);


        filaHueco = filadelaimagenpulsada;
        columnaHueco = columnadelaimagenpulsada;

        if (estaAcabado()){
            Toast.makeText(this, " ENHORABUENA!!!!  HAS GANADO !!!" , Toast.LENGTH_LONG).show();
            nuevoJuego();
        }

        logDePosiciones();
    }

    //==============================================================================================

    public void abrirbutton() {
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);

    }
    public void irAHelp() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);

    }



    public void nuevoJuego() {
//        haganado = true;
//        for (int f = 0; f < numfilas; f++) {
//
//            for (int c = 0; c < numcolumnas; c++) {
//
//                ImageView imagen =   arrayImagenes[f][c];
//                imagen.setOnClickListener(null);
//                imagen = null;
//
//
//            }
//        }
//        listaTrozos.clear();
//
//        ronda ++;
//
//         numfilas ++;
//         numcolumnas ++;


    }

    public boolean estaAcabado(){
        int cont = 0;
        boolean exito = true;
        for (int f = 0; f < numfilas; f++) {
            for (int c = 0; c < numcolumnas; c++) {
                ImageView imagen = arrayImagenes[f][c];
                Posiciones pos  = (Posiciones) imagen.getTag();
                if(pos.yActual != pos.yInicial || pos.xActual != pos.xInicial){
                    exito = false;
                }
            }
        }
        return exito;
    }


    public void logDePosiciones(){


        String s = "";
        for (int f = 0; f < numfilas; f++) {
            for (int c = 0; c < numcolumnas; c++) {
                ImageView imagen = arrayImagenes[f][c];
                Posiciones pos  = (Posiciones) imagen.getTag();
                s+=" \n=================================> FILA  " + f + " COLUMNA" + c;

                s+=" \n=============> X INICIAL " + pos.xInicial;
                s+=" \n=============> Y INICIAL " + pos.yInicial;
                s+=" \n=============> X ACTUAL  " + pos.xActual;
                s+=" \n=============> Y ACTUAL  " + pos.yActual;
                s+=" \n\n";
                Log.e("________________ DAVID", s);
            }
        }

    }

    public void onclickEmpezar(View view) {
        if(haganado== true){
            iniciarTodo();
        }

    }



    public void config(View view) {
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }


}
