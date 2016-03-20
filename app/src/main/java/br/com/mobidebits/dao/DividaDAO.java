package br.com.mobidebits.dao;

import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.beans.Parcela;

/**
 * Created by Robson on 13/12/2015.
 */
public interface DividaDAO {

    Long inserirDivida(Divida divida);

    Long inserirParcela(Parcela parcela, Long idDivida);

    List<Divida> listarDividas(int idUsuario,int mes, String ano);
}