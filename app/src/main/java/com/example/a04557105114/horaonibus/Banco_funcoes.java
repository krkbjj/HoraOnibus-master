package com.example.a04557105114.horaonibus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Banco_funcoes {
    private SQLiteDatabase bd;

    public Banco_funcoes(Context context) {
        Banco auxBd = new Banco(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirEmpresa(String NomeEmpresa) {
        ContentValues valores = new ContentValues();
        try {
            valores.put("nome", NomeEmpresa);
            bd.insert("empresa", null, valores);
        } catch (SQLiteAbortException e) {
        } catch (SQLException e) {
        } catch (Exception e) {
        }
    }

    public List<String> BuscarEmpresa() {

        String[] colunas = new String[]{"nome"};
        List<String> Empresas = new ArrayList<String>();
        try {
            Cursor cursor = bd.query("empresa", colunas, null, null, null, null, "nome ASC");

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Empresas.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            return Empresas;
        } catch (SQLiteAbortException e) {
            Empresas.add("Erro1: " + e);
        } catch (SQLException e) {
            Empresas.add("Erro2: " + e);
        } catch (Exception e) {
            Empresas.add("Erro3: " + e);
        }

        return Empresas;
    }

    public void deletarEmpresa(String nome) {
        try {
            bd.delete("empresa", "nome = '" + nome+"'", null);
            bd.delete("dados", "empresa = '" + nome+"'", null);
        } catch (SQLiteAbortException e) {
        } catch (SQLException e) {
        } catch (Exception e) {
        }
    }

    public void inserirDados(String empresa, String origem, String destino, String valor, String hora, String foto){
        ContentValues valores = new ContentValues();
        try {
            valores.put("empresa", empresa);
            valores.put("origem", origem);
            valores.put("destino", destino);
            valores.put("horas", hora);
            valores.put("valor", valor);
            valores.put("foto", foto);
            bd.insert("dados", null, valores);
        } catch (SQLiteAbortException e) {
        } catch (SQLException e) {
        } catch (Exception e) {
        }

    }

    public List<String> BuscarDados(String Empresa) {

        String[] colunas = new String[]{"Empresa","origem","destino","horas","valor","id"};
        List<String> Dados = new ArrayList<String>();
        try {
            Cursor cursor = bd.query("dados", colunas, null, null, null, null, "origem ASC");

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {

                    if(cursor.getString(0).equals(Empresa)){

                        Dados.add(cursor.getString(5)+"   "+cursor.getString(1)+" -> "+cursor.getString(2)+"\n"
                        +cursor.getString(3)+"  R$ "+cursor.getString(4));
                    }

                } while (cursor.moveToNext());
            }
            return Dados;
        } catch (SQLiteAbortException e) {
            Dados.add("Erro1: " + e);
        } catch (SQLException e) {
            Dados.add("Erro2: " + e);
        } catch (Exception e) {
            Dados.add("Erro3: " + e);
        }

        return Dados;
    }



    }



