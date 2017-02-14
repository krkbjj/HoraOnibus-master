package com.example.a04557105114.horaonibus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetalhesEmpresa  extends AppCompatActivity {

    ArrayAdapter<String> adaptador;
    List<String> ListDados = new ArrayList<>();
    String NomeEmpresa;
    TextView TxtSemEmpresa;
    ListView listaDados;

   static final int PICK_CONTACT_REQUEST = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_empresa);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.icon1);

        final Banco_funcoes BF = new Banco_funcoes(this);

        Intent intent = getIntent();
        NomeEmpresa = (String) intent.getSerializableExtra("NEmpresa");

        listaDados = (ListView) findViewById(R.id.listDados);
        TxtSemEmpresa = (TextView) findViewById(R.id.txtSemDados);


        carregarLista();


        listaDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                String NEmpresa = (String) listaDados.getItemAtPosition(position);
                Intent intentVaiPtoFormulario = new Intent(DetalhesEmpresa.this, Img.class);
                intentVaiPtoFormulario.putExtra("Foto", NEmpresa);
                startActivity(intentVaiPtoFormulario);
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFormulario = new Intent(DetalhesEmpresa.this, Cadrastro.class);
                intentFormulario.putExtra("NEmpresa", NomeEmpresa);

             //   startActivityForResult(intentFormulario, PICK_CONTACT_REQUEST);
                startActivity(intentFormulario);


                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
            }
        });

      //  TextView TxtSemDados = (TextView) findViewById(R.id.txtSemDados);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                //...
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_sobre) {
            AlertDialog.Builder alert = new AlertDialog.Builder(DetalhesEmpresa.this);
            alert.setMessage("Este aplicativo foi desenvolvido por alunos do IFTO Campus Paraíso para fins academicos");
            alert.show();

            return true;
        }
        if (id == R.id.action_sair) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* public void onStart(){
        super.onStart();
        carregarLista();
    }*/



    @Override
    public void onResume(){
        super.onResume();
        carregarLista();
    }


    public void carregarLista(){
        final Banco_funcoes BF = new Banco_funcoes(this);
        ListDados = BF.BuscarDados(NomeEmpresa);

        if (ListDados.size() > 0){
            TxtSemEmpresa.setText("");
            adaptador = new ArrayAdapter<String>(DetalhesEmpresa.this, android.R.layout.simple_list_item_1, ListDados);
            listaDados.setAdapter(adaptador);
            registerForContextMenu(listaDados);
        } else{
            TxtSemEmpresa.setText("Não há dados cadrastrados\nClique em + para adicionar");
        }
    }


}
