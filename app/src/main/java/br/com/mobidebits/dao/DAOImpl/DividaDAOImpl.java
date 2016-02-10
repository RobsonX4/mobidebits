package br.com.mobidebits.dao.DAOImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.dao.DBHelper;
import br.com.mobidebits.dao.DividaDAO;

/**
 * Created by Robson on 23/12/2015.
 */
public class DividaDAOImpl implements DividaDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DividaDAOImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public boolean inserir(Divida divida) {
        if (divida != null) {
            ContentValues valores = new ContentValues();

            db = dbHelper.getWritableDatabase();

            valores.put(divida.ID, divida.getId());
            valores.put(divida.ID_USUARIO, divida.getIdUsuario());
            valores.put(divida.DESCRICAO, divida.getDescricao());
            valores.put(divida.VALOR, divida.getValor());
            valores.put(divida.ABERTURA, divida.getAbertura());
            valores.put(divida.VENCIMENTO, divida.getVencimento());
            valores.put(divida.TIPO, divida.getTipo());
            valores.put(divida.IS_PARCELADO, divida.isParcelado());
            valores.put(divida.STATUS, divida.getStatus());

            long resultado = db.insert(divida.TABELA, null, valores);
            db.close();

            if (resultado == -1)
                return false;
            return true;
        } else
            return false;
    }

    @Override
    public List<Divida> listar(int idUsuario, int mes) {
        Divida divida = new Divida();
        List<Divida> dividas = new ArrayList<Divida>();
        db = dbHelper.getWritableDatabase();

        String where = divida.ID_USUARIO + " = " + idUsuario;
        Cursor cursor = db.query(divida.TABELA, null, where, null, null, null, null);

        while (cursor.moveToNext()) {
            divida = new Divida();
            divida.setId(cursor.getInt(cursor.getColumnIndex(divida.ID)));
            divida.setIdUsuario(cursor.getInt(cursor.getColumnIndex(divida.ID_USUARIO)));
            divida.setDescricao(cursor.getString(cursor.getColumnIndex(divida.DESCRICAO)));
            divida.setValor(cursor.getDouble(cursor.getColumnIndex(divida.VALOR)));
            divida.setAbertura(cursor.getString(cursor.getColumnIndex(divida.ABERTURA)));
            divida.setVencimento(cursor.getString(cursor.getColumnIndex(divida.VENCIMENTO)));
            divida.setTipo(cursor.getString(cursor.getColumnIndex(divida.TIPO)));
            //divida.setParcelado(cursor.getColumnIndex(divida.IS_PARCELADO));
            divida.setStatus(cursor.getInt(cursor.getColumnIndex(divida.STATUS)));
            dividas.add(divida);
        }
        if (dividas != null)
            return dividas;
        return null;
    }
}
