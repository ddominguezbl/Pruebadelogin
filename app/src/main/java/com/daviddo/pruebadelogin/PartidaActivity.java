package com.daviddo.pruebadelogin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


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
    Button botonGameOver, btEmpezar;
    FloatingActionButton btHelp, btPause;
    ProgressBar ProgressBarTimer;
    int ronda;
    boolean haganado;
    TextView numeroronda;
    Animation animation;

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
        btHelp = (FloatingActionButton) findViewById(R.id.btHelp);
        btPause = (FloatingActionButton) findViewById(R.id.btPause);
        btEmpezar = (Button) findViewById(R.id.btEmpezar);
        ProgressBarTimer = findViewById(R.id.ProgressBarTimer);
        numeroronda = findViewById(R.id.numeroRonda);


        haganado = true; // para que inicialmente se pueda usar el boton de empezar
        ronda = 1;

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausar();
            }
        });


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



    private void pausar() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.activity_pause);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btContinuar = dialog.findViewById(R.id.btnContinuar);

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });dialog.show();
    }


    private void iniciarTodo() {
        haganado = false;// tras iniciar se impide asi usar el borton empezar nuevamente
        numeroronda.setText(ronda+"");
        arrayImagenes = new ImageView[numfilas][numcolumnas];
        gridImagen.removeAllViews();
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

        final MediaPlayer ClickOn = MediaPlayer.create(this, R.raw.sonido5);

        imagenpulsada = (ImageView) view;
        Posiciones posicionquetenia  = (Posiciones) imagenpulsada.getTag();
        filadelaimagenpulsada = posicionquetenia.xActual;
        columnadelaimagenpulsada = posicionquetenia.yActual;

        //imagenpulsada.setVisibility(View.INVISIBLE);

        // mirrar si ARRIBA hay hueco
        if (filadelaimagenpulsada - 1 == filaHueco && columnadelaimagenpulsada == columnaHueco) {
            moverImagen();
            animation= AnimationUtils.loadAnimation( PartidaActivity.this,R.anim.bounce);
            ClickOn.start();

       }

        // mirrar si ABAJO hay hueco
        if (filadelaimagenpulsada + 1 == filaHueco && columnadelaimagenpulsada == columnaHueco) {
            moverImagen();
            ClickOn.start();
        }

        // mirrar si DERECHA hay hueco
        if (filadelaimagenpulsada == filaHueco && columnadelaimagenpulsada + 1 == columnaHueco) {
            moverImagen();
            ClickOn.start();
        }

        // mirrar si IZQUIERDA hay hueco
        if (filadelaimagenpulsada == filaHueco && columnadelaimagenpulsada - 1 == columnaHueco) {
            moverImagen();
            ClickOn.start();
        }

    }

    public void moverImagen() {
        //logDePosiciones();

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

        //logDePosiciones();
    }

    //==============================================================================================

    public void abrirbutton() {
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);

    }
    public void irAHelp() {
        Intent intentHelp = new Intent(this, HelpActivity.class);
        startActivity(intentHelp);

    }



    public void nuevoJuego() {
        haganado = true;
        for (int f = 0; f < numfilas; f++) {
            for (int c = 0; c < numcolumnas; c++) {
                ImageView imagen = arrayImagenes[f][c];
                imagen.setOnClickListener(null);
                imagen = null;
            }
        }
        listaTrozos.clear();

        ronda++;
        numfilas++;
        numcolumnas++;
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


    /*public void logDePosiciones(){


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
*/
    public void onclickEmpezar(View view) {
        if(haganado== true){
            iniciarTodo();
            ProgressBarTimer.setProgress(0);
            ProgressBarTimer.setSecondaryProgress(5);
            CountDownTimer cuentaAtras = new CountDownTimer(20000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    System.out.println("seconds remaining: " + millisUntilFinished / 1000);
                    int progresoactual = ProgressBarTimer.getProgress() + 5;
                    ProgressBarTimer.setProgress(progresoactual);
                    if (progresoactual >= ProgressBarTimer.getMax()){
                        progresoactual = 0;
                    }
                }

                @Override
                public void onFinish() {
                    System.out.println("FIN");
                    sinTiempo();
                }
            }.start();
        }

    }

    private void sinTiempo() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.activity_fin_tiempo);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button btContinuar = dialog.findViewById(R.id.btnContinuar);



        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });dialog.show();
    }


    public void config(View view) {
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }


}
