package com.example.a04557105114.horaonibus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 04557105114 on 13/02/2017.
 */

public class Img  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);

        Banco BD = new Banco(this);

        ImageView campoFoto = (ImageView) findViewById(R.id.imageView2);

        Intent intent = getIntent();
        String id = (String) intent.getSerializableExtra("Foto");
        TextView md = (TextView) findViewById(R.id.textView2);

        if(id != null){
            String iD = id.substring(0,2);
            String ID = iD.trim();

            String caminho = BD.foto(ID);
            if (caminho != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(caminho);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                campoFoto.setImageBitmap(bitmapReduzido);
                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                campoFoto.setTag(caminho);

        }



}}}
