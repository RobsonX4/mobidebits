package br.com.mobidebits.dao;

import android.database.Cursor;

import br.com.mobidebits.beans.Usuario;

/**
 * Created by Robson on 13/12/2015.
 */
public interface UsuarioDAO {

    Boolean inserir(Usuario usuario);

    Usuario consultar(String usuario, String senha);

    Usuario montarUsuario(Cursor cursor);

    void alterar();

    boolean remover(int id);

    Usuario consultarPorId(int id);
}