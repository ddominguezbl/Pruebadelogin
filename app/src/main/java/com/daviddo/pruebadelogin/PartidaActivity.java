package com.daviddo.pruebadelogin;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class PartidaActivity extends AppCompatActivity{

    ImageView btConfig;
    ImageView btHelp;
    ImageView imagenPuzzle;
    ArrayList<Bitmap> pieces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btConfig = (ImageView) findViewById(R.id.btConfig);
        //btHelp = (ImageView) findViewById(R.id.btHelp);
        imagenPuzzle = (ImageView) findViewById(R.id.ImagenPuzzle);

        final ConstraintLayout ConstraintImagenPuzzle = findViewById(R.id.ConstraintImagenPuzzle);

        imagenPuzzle.post(new Runnable() {
            @Override
            public void run() {
                pieces = splitImage();
                for(Bitmap piece : pieces) {
                    ImageView iv = new ImageView(getApplicationContext());
                    iv.setImageBitmap(piece);
                    ConstraintImagenPuzzle.addView(iv);
                }
            }
        });
    }

    private ArrayList<Bitmap> splitImage() {
        int piecesNumber = 12;
        int rows = 4;
        int cols = 3;

        ImageView imagenPuzzle = findViewById(R.id.ImagenPuzzle);
        ArrayList<Bitmap> pieces = new ArrayList<>(piecesNumber);

        // Get the bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imagenPuzzle.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // Calculate the with and height of the pieces
        int pieceWidth = bitmap.getWidth()/cols;
        int pieceHeight = bitmap.getHeight()/rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                pieces.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight));
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }


    public void onclickEmpezar(View view){
        cargarImagen();

    }

    private void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path=data.getData();
            imagenPuzzle.setImageURI(path);
        }
    }

    public void config(View view){
        Toast.makeText(getApplicationContext(), "Pulsado configurar", Toast.LENGTH_SHORT).show();
    }

}
