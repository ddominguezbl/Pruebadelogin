package com.daviddo.pruebadelogin;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    TextView textHelp;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textHelp = (TextView)findViewById(R.id.textHelp);

        YoYo.with(Techniques.StandUp).duration(3000).repeat(Animation.INFINITE).playOn(textHelp);

    }
}
