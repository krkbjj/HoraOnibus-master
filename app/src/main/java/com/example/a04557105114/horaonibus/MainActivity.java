package com.example.a04557105114.horaonibus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.icon1);

      //  onRestart();

        final Banco_funcoes BF = new Banco_funcoes(this);

        ArrayAdapter<String> adaptador;

        final ListView listaEmpresas = (ListView) findViewById(R.id.listaEmpresas);
        TextView TxtSemEmpresa = (TextView) findViewById(R.id.txtSemEmpresa);

        List<String> ListEmpresas = new ArrayList<>();
        ListEmpresas = BF.BuscarEmpresa();

        if (ListEmpresas.size() > 0){
            TxtSemEmpresa.setText("");
            adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ListEmpresas);
            listaEmpresas.setAdapter(adaptador);
            registerForContextMenu(listaEmpresas);
        }

        listaEmpresas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                final String NEmpresa = (String) listaEmpresas.getItemAtPosition(pos);
                final PopupMenu popup = new PopupMenu(MainActivity.this, arg1);
                final MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_empresas, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                BF.deletarEmpresa(NEmpresa);
                                Toast.makeText(MainActivity.this, NEmpresa+" foi apagada", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
                return true;
            }
        });

        listaEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                String NEmpresa = (String) listaEmpresas.getItemAtPosition(position);
                Intent intentVaiPtoFormulario = new Intent(MainActivity.this, DetalhesEmpresa.class);
                intentVaiPtoFormulario.putExtra("NEmpresa", NEmpresa);
                startActivity(intentVaiPtoFormulario);
            }
        });


        ImageButton consultar = (ImageButton) findViewById(R.id.btn_consultar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Consulta.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
                mensagem.setTitle("Cadrastro de Empresas");
                final EditText input = new EditText(MainActivity.this);
                mensagem.setView(input);

                mensagem.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        String NEmpresa = input.getText().toString();
                        if(NEmpresa.length() > 2){
                        BF.inserirEmpresa(NEmpresa);
                        finish();
                        startActivity(getIntent());}
                        else{
                            Toast.makeText(MainActivity.this, "Cadrastro não realizado.\nNome Invalido", Toast.LENGTH_SHORT).show();
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

                    }
                });

                mensagem.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                });
                mensagem.show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
        });

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
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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

}
