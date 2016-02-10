package br.com.mobidebits.dao;

import java.util.List;

import br.com.mobidebits.beans.Divida;

/**
 * Created by Robson on 13/12/2015.
 */
public interface DividaDAO {

    boolean inserir(Divida divida);

    List<Divida> listar(int idUsuario, int mes);
}