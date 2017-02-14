package com.example.a04557105114.horaonibus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class Banco extends SQLiteOpenHelper {
    private static final String NOME_BD = "Registros";
    private static final int VERSAO_BD = 2;


    public Banco(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table empresa(nome text primary key not null);");

        bd.execSQL("create table dados(id integer primary key autoincrement, " +
                "empresa text not null, " +
                "origem text not null, " +
                "destino text not null, " +
                "horas text not null, " +
                "valor text," +
                "foto text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table empresa");
        bd.execSQL("drop table dados");
        onCreate(bd);
    }

    public List<String> buscaOrigem(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT DISTINCT origem FROM dados;";
        Cursor c = db.rawQuery(sql, null);

        List<String> origens = new ArrayList<>();
        while (c.moveToNext()) {
            origens.add(c.getString(c.getColumnIndex("origem")));
        }
        c.close();
        return origens;
    }
    public List<String> buscaDestino(String Origem){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT DISTINCT destino FROM dados where origem = '"+Origem+"';";
        Cursor c = db.rawQuery(sql, null);

        List<String> origens = new ArrayList<>();
        while (c.moveToNext()) {
            origens.add(c.getString(c.getColumnIndex("destino")));
        }
        c.close();
        return origens;
    }

    public List<String> buscaEmpresa(String Origem, String Destino){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT DISTINCT empresa FROM dados where origem = '"+Origem+"' and destino ='"+Destino+"';";
        Cursor c = db.rawQuery(sql, null);

        List<String> origens = new ArrayList<>();
        while (c.moveToNext()) {
            origens.add(c.getString(c.getColumnIndex("empresa")));
        }
        c.close();
        return origens;
    }

    public List<String> buscaMenosFiltro(String Origem, String Destino){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM dados where origem = '"+Origem+"' and destino ='"+Destino+"';";
        Cursor c = db.rawQuery(sql, null);

        List<String> origens = new ArrayList<>();
        while (c.moveToNext()) {
            origens.add(c.getString(c.getColumnIndex("origem"))+" -> "+c.getString(c.getColumnIndex("destino"))+
                    "\n"+c.getString(c.getColumnIndex("horas"))+"    R$ "+c.getString(c.getColumnIndex("valor"))+
            "  ("+c.getString(c.getColumnIndex("empresa"))+")");
        }
        c.close();
        return origens;
    }

    public List<String> buscaFiltrado(String Origem, String Destino, String Empresa){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM dados where origem = '"+Origem+"' and destino ='"+Destino+"' and empresa = '"+Empresa+"';";
        Cursor c = db.rawQuery(sql, null);

        List<String> origens = new ArrayList<>();
        while (c.moveToNext()) {
            origens.add(c.getString(c.getColumnIndex("origem"))+" -> "+c.getString(c.getColumnIndex("destino"))+
            "\n"+c.getString(c.getColumnIndex("horas"))+"    R$ "+c.getString(c.getColumnIndex("valor")));
        }
        c.close();
        return origens;
    }

    public String foto(String id){
        String camilho = null;
        try {


            SQLiteDatabase db = getWritableDatabase();
            String sql = "SELECT * FROM dados where id = " + id + ";";
            Cursor c = db.rawQuery(sql, null);



            while (c.moveToNext()) {
                camilho = c.getString(6);
                break;
            }
            c.close();
            return camilho;
        }catch (SQLiteAbortException e){
            return camilho = "1"+e;
        }catch (SQLException e){
            return camilho = "2"+e;
        }catch (Exception e){
            return camilho = "3"+e;
        }
    }
}
