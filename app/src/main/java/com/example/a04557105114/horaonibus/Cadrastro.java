package com.example.a04557105114.horaonibus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 04557105114 on 27/01/2017.
 */

public class Cadrastro extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private String caminhoFoto;
    ImageView campoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadrastro);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);

        Intent intent = getIntent();
        final String NomeEmpresa = (String) intent.getSerializableExtra("NEmpresa");


        final Banco_funcoes BF = new Banco_funcoes(this);

        final Spinner horas = (Spinner) findViewById(R.id.spinner_hora);
        final Spinner minutos = (Spinner) findViewById(R.id.spinner_minuto);

        List<String>  hora = new ArrayList<>();
        hora.add("00"); hora.add("01"); hora.add("02"); hora.add("03"); hora.add("04"); hora.add("05");
        hora.add("06"); hora.add("07"); hora.add("08"); hora.add("09"); hora.add("10"); hora.add("11");
        hora.add("12"); hora.add("13"); hora.add("14"); hora.add("15"); hora.add("16"); hora.add("17");
        hora.add("18"); hora.add("19"); hora.add("20"); hora.add("21"); hora.add("22"); hora.add("23");

        List<String> minuto = new ArrayList<>();
        minuto.add("00"); minuto.add("05"); minuto.add("10"); minuto.add("15"); minuto.add("20"); minuto.add("25");
        minuto.add("30"); minuto.add("35"); minuto.add("40"); minuto.add("45"); minuto.add("50"); minuto.add("55");

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(Cadrastro.this, android.R.layout.simple_spinner_item, hora);
        horas.setAdapter(adaptador);


        adaptador = new ArrayAdapter<String>(Cadrastro.this, android.R.layout.simple_spinner_item, minuto);
        minutos.setAdapter(adaptador);

        Button cancelar = (Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        final EditText origem = (EditText) findViewById(R.id.editorigem);
        final EditText destino = (EditText) findViewById(R.id.editdestino);
        final EditText valor = (EditText) findViewById(R.id.editvalor);
        campoFoto = (ImageView) findViewById(R.id.imageView);

        Button cadrastrar = (Button) findViewById(R.id.cadrastrar);

        ImageButton imgm = (ImageButton) findViewById(R.id.imageButton);

        imgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });


        cadrastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Horario = (String) horas.getSelectedItem() + ":"+ (String) minutos.getSelectedItem();
                String Origem = String.valueOf(origem.getText());
                String Destino = String.valueOf(destino.getText());
                String Valor = String.valueOf(valor.getText());
                String linkfoto = (String) campoFoto.getTag();

                BF.inserirDados(NomeEmpresa,Origem,Destino,Valor,Horario,linkfoto);

              //  Intent returnIntent = new Intent();
             //   returnIntent.putExtra("tag","atualizar");
              //  setResult(DetalhesEmpresa.RESULT_OK,returnIntent);
                finish();


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {

                if (caminhoFoto != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    campoFoto.setImageBitmap(bitmapReduzido);
                    campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    campoFoto.setTag(caminhoFoto);
                }
            }
        }

    }

}
