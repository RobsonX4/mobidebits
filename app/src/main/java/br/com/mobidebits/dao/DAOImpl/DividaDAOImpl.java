package br.com.mobidebits.dao.DAOImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.beans.Parcela;
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
    public Long inserirDivida(Divida divida) {
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
            valores.put(divida.IS_PARCELADO, divida.isParcelado() ? 1 : 0);
            valores.put(divida.N_PARCELAS, divida.getNParcelas());
            valores.put(divida.STATUS, divida.getStatus());

            long resultado = db.insert(Divida.TABELA, null, valores);
            db.close();

            if (resultado == -1)
                return null;
            return resultado;
        } else
            return null;
    }

    @Override
    public Long inserirParcela(Parcela parcela,Long idDivida) {
        if (parcela != null) {
            ContentValues valores = new ContentValues();
            db = dbHelper.getWritableDatabase();

            valores.put(Parcela.ID, parcela.getId());
            valores.put(Parcela.ID_DIVIDA, idDivida);
            valores.put(Parcela.NUMERO, parcela.getNumero());
            valores.put(Parcela.VALOR, parcela.getValor());
            valores.put(Parcela.VENCIMENTO, parcela.getVencimento());
            valores.put(Parcela.STATUS, parcela.getStatus());

            long resultado = db.insert(Parcela.TABELA, null, valores);
            db.close();

            if (resultado == -1)
                return null;
            return resultado;
        } else
            return null;
    }

    @Override
    public List<Divida> listarDividas(int idUsuario,int mes, String ano) {
        Divida divida = new Divida();
        List<Divida> dividas = new ArrayList<Divida>();
        db = dbHelper.getWritableDatabase();

        String[] args = new String[2];
        args[0] = String.valueOf(idUsuario);
        args[1] = "%"+ mes +"/"+ ano;

        Cursor cursor = db.rawQuery(
                "SELECT d.*, p.*  FROM " + Divida.TABELA + " AS d"
                    + " INNER JOIN " + Parcela.TABELA + " AS p ON p." + Parcela.ID_DIVIDA + " = d." + Divida.ID
                        + " WHERE (d." + divida.ID_USUARIO + " = ?"
                            + " AND p." + Parcela.VENCIMENTO + " LIKE ?)", args);

        while (cursor.moveToNext()) {
            divida = new Divida();
            divida.setId(cursor.getLong(cursor.getColumnIndex(divida.ID)));
            divida.setIdUsuario(cursor.getInt(cursor.getColumnIndex(divida.ID_USUARIO)));
            divida.setDescricao(cursor.getString(cursor.getColumnIndex(divida.DESCRICAO)));
            divida.setValor(cursor.getDouble(cursor.getColumnIndex(divida.VALOR)));
            divida.setAbertura(cursor.getString(cursor.getColumnIndex(divida.ABERTURA)));
            divida.setVencimento(cursor.getString(cursor.getColumnIndex(divida.VENCIMENTO)));
            divida.setTipo(cursor.getString(cursor.getColumnIndex(divida.TIPO)));
            int isParcelado = cursor.getInt(cursor.getColumnIndex(divida.IS_PARCELADO));
            divida.setParcelado(isParcelado == 0 ? false : true);
            divida.setNParcelas(cursor.getInt(cursor.getColumnIndex(divida.N_PARCELAS)));
            divida.setStatus(cursor.getInt(cursor.getColumnIndex(divida.STATUS)));

            Parcela parcela = new Parcela();
            parcela.setId(cursor.getLong(cursor.getColumnIndex(Parcela.ID)));
            parcela.setIdDivida(cursor.getLong(cursor.getColumnIndex(Parcela.ID_DIVIDA)));
            parcela.setValor(cursor.getDouble(cursor.getColumnIndex(Parcela.VALOR)));
            parcela.setNumero(cursor.getInt(cursor.getColumnIndex(Parcela.NUMERO)));
            parcela.setVencimento(cursor.getString(cursor.getColumnIndex(Parcela.VENCIMENTO)));
            int teste = cursor.getInt(cursor.getColumnIndex(Parcela.STATUS));
            parcela.setStatus(cursor.getInt(cursor.getColumnIndex(Parcela.STATUS)));
            List<Parcela> parcelas = new ArrayList<Parcela>();

            parcelas.add(parcela);
            divida.setParcelas(parcelas);
            dividas.add(divida);
        }
        if (dividas != null && !dividas.isEmpty())
            return dividas;
        return null;
    }
}