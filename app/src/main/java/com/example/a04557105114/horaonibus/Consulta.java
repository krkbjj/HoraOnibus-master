package com.example.a04557105114.horaonibus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 04557105114 on 02/02/2017.
 */

public class Consulta extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosulta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setLogo(R.drawable.icon1);
        toolbar.setTitle("Consultar Rota");



       // final List<String> ListDados = new ArrayList<>();
       // ListEmpresas = BF.BuscarEmpresa();
       // ListEmpresas.add("Origem");
        final String[] ORIGEM = new String[1];
        final String[] DESTINO = new String[1];

        final Banco BD = new Banco(this);

        Spinner origem = (Spinner) findViewById(R.id.spi_origem);
        final Spinner destino = (Spinner) findViewById(R.id.spi_destino);
        final Spinner empresa = (Spinner) findViewById(R.id.spi_empresa);

        final ListView ListConsulta = (ListView) findViewById(R.id.list_consulta);

        List<String> ListDados = new ArrayList<>();

        ListDados = BD.buscaOrigem();
        ListDados.add(0, "Selecione a Origem...");


        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(Consulta.this, android.R.layout.simple_list_item_1, ListDados);


        origem.setAdapter(adaptador);

        origem.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        ORIGEM[0] = item.toString();
                        if(!(ORIGEM[0].equals("Selecione a Origem..."))){
                            List<String> ListDados = new ArrayList<>();

                            ListDados = BD.buscaDestino(ORIGEM[0]);
                            ListDados.add(0, "Selecione o Destino...");


                            ArrayAdapter<String> adaptador;
                            adaptador = new ArrayAdapter<String>(Consulta.this, android.R.layout.simple_list_item_1, ListDados);


                            destino.setAdapter(adaptador);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        destino.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        DESTINO[0] = item.toString();
                        if(!(DESTINO[0].equals("Selecione o Destino..."))){
                            List<String> ListDados = new ArrayList<>();

                            ListDados = BD.buscaEmpresa(ORIGEM[0],DESTINO[0]);
                            ListDados.add(0, "Selecione a Empresa...");


                            ArrayAdapter<String> adaptador;
                            adaptador = new ArrayAdapter<String>(Consulta.this, android.R.layout.simple_list_item_1, ListDados);


                            empresa.setAdapter(adaptador);

                            ListDados = BD.buscaMenosFiltro(ORIGEM[0],DESTINO[0]);


                            adaptador = new ArrayAdapter<String>(Consulta.this, android.R.layout.simple_list_item_1, ListDados);


                            ListConsulta.setAdapter(adaptador);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        empresa.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        String EMPRESA = item.toString();
                        if(!(EMPRESA.equals("Selecione a Empresa..."))){

                            List<String> ListDados = new ArrayList<>();

                            ListDados = BD.buscaFiltrado(ORIGEM[0],DESTINO[0],EMPRESA);
                           // ListDados.add(0, "Selecione a Empresa...");


                            ArrayAdapter<String> adaptador;
                            adaptador = new ArrayAdapter<String>(Consulta.this, android.R.layout.simple_list_item_1, ListDados);


                            ListConsulta.setAdapter(adaptador);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
}



}
