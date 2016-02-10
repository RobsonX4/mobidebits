package br.com.mobidebits.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.dao.DAOImpl.DividaDAOImpl;
import br.com.mobidebits.dao.DividaDAO;

/**
 * Created by Robson on 23/12/2015.
 */
public class DividaControl {

    private DividaDAO dividaDAO;
    private List<Divida> dividas;

    public DividaControl(Context context) {
        this.dividaDAO = new DividaDAOImpl(context);
    }

    public boolean inserir(Divida divida) {

        if (dividaDAO.inserir(divida))
            return true;

        return false;
    }

    public List<Divida> listarDividas(int idUsuario, int mes) {
        //consultar no banco
        this.dividas = new ArrayList<Divida>();
        this.dividas = dividaDAO.listar(idUsuario, mes);

        if (dividas != null)
            return dividas;

        return null;
    }
}