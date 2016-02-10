package br.com.mobidebits.dao.DAOImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.mobidebits.beans.Usuario;
import br.com.mobidebits.dao.DBHelper;
import br.com.mobidebits.dao.UsuarioDAO;

/**
 * Created by Robson on 21/12/2015.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UsuarioDAOImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public Boolean inserir(Usuario usuario) {
        if(usuario != null) {
            //ContentValues: É quem realiza o mapeamento entre os dados da tabela e o Enum.
            ContentValues valores = new ContentValues();

            //getWritableDatabase: Diz ao android que o banco será utilizado escrita e leitura de dados
            db = dbHelper.getWritableDatabase();

            valores.put(usuario.NOME,   usuario.getNome());
            valores.put(usuario.EMAIL,  usuario.getEmail());
            valores.put(usuario.USUARIO,usuario.getUsuario());
            valores.put(usuario.SENHA,  usuario.getSenha());
            valores.put(usuario.STATUS, usuario.getStatus());

            long resultado = db.insert(usuario.TABELA, null, valores);
            db.close();

            if (resultado != -1)
                return true;
        }
        return false;
    }

    @Override
    public Usuario consultar(String user, String senha) {
        Usuario usuario = new Usuario();

        db =  dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+ usuario.TABELA +" WHERE "+ usuario.USUARIO +" =? AND "+ usuario.SENHA +" = ?";
        String[] selectionArgs = new String[] {user, senha};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        cursor.moveToFirst();
        return montarUsuario(cursor);
    }

    @Override
    public Usuario montarUsuario(Cursor cursor) {
        if (cursor.getCount() == 0)
            return null;

        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(cursor.getColumnIndex(usuario.ID)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(usuario.NOME)));
        usuario.setUsuario(cursor.getString(cursor.getColumnIndex(usuario.USUARIO)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(usuario.SENHA)));
        usuario.setStatus(cursor.getInt(cursor.getColumnIndex(usuario.STATUS)));
        return usuario;
    }

    @Override
    public void alterar() {

    }

    @Override
    public boolean remover(int id) {
        String where = Usuario.ID +" = ?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        int resultado = db.delete(Usuario.TABELA, where, whereArgs);

        if (resultado >= 0)
            return true;
        return false;
    }

    @Override
    public Usuario consultarPorId(int id) {
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+ Usuario.TABELA +" WHERE "+ Usuario.ID +" = ?";

        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        cursor.moveToFirst();
        return montarUsuario(cursor);
    }
}
