package com.daviddo.pruebadelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;

public class GameOver extends AppCompatActivity {

    ImageView imageView3;
    TextView GameOver;
    Button button2;
    Button btVolverAJugar;
    Button btPuntacion;
    Button btRanking;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        GameOver = (TextView) findViewById(R.id.GameOver);
        button2 = (Button) findViewById(R.id.button2);
        btVolverAJugar = (Button) findViewById(R.id.btVolverAJugar);
        btPuntacion  = (Button) findViewById(R.id.btPuntacion);
        btRanking  = (Button) findViewById(R.id.btRanking);
        imageView3=(ImageView)findViewById(R.id.imageView3);

        YoYo.with(Techniques.Bounce).duration(3000).repeat(Animation.INFINITE).playOn(GameOver);

        btVolverAJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(GameOver.this, "Has seleccionado volver a jugar ", Toast.LENGTH_SHORT).show();
                Intent InicioIntent = new Intent( GameOver.this, PartidaActivity.class);
                startActivity(InicioIntent);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(GameOver.this, "Has seleccionado Inicio", Toast.LENGTH_SHORT).show();
                Intent InicioIntent = new Intent( GameOver.this, MainActivity.class);
                startActivity(InicioIntent);
            }

        });

        btPuntacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(GameOver.this, "Has seleccionado Puntuacion", Toast.LENGTH_SHORT).show();
            }
        });

        btRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(GameOver.this, "Has seleccionado RANKING TOP 10", Toast.LENGTH_SHORT).show();
            }

        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation= AnimationUtils.loadAnimation( GameOver.this,R.anim.fade);
                imageView3.startAnimation(animation);
            }
        });
    }



    }


