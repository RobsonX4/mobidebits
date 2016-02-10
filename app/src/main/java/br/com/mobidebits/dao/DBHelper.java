package br.com.mobidebits.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.beans.Parcela;
import br.com.mobidebits.beans.Usuario;

/**
 * Created by Robson on 13/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String BASE_DE_DADOS = "mobidebits.db";
    public static final Integer VERSAO = 1;

    public DBHelper(Context context) {
        super(context, BASE_DE_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Usuario usuario = new Usuario();
        Divida divida = new Divida();
        Parcela parcela = new Parcela();

        db.execSQL(usuario.getTabela());
        db.execSQL(divida.getTabela());
        db.execSQL(parcela.getTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int vAntiga, int vNova) {
        db.execSQL("DROP TABLE IF EXISTS " + Parcela.TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + Divida.TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + Usuario.TABELA);
        onCreate(db);
    }
}