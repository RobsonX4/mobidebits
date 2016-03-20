package br.com.mobidebits.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.beans.Parcela;
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

    public Long inserirDivida(Divida divida) {
        Long idDivida = dividaDAO.inserirDivida(divida);
        if (idDivida != null)
            return idDivida;
        return null;
    }

    public Long inserirParcela(Parcela parcela,Long idDivida) {
        Long idParcela = dividaDAO.inserirParcela(parcela, idDivida);
        if (idParcela != null)
            return idParcela;
        return null;
    }

    public List<Divida> listarDividas(int idUsuario, int mes, String ano) {
        List<Divida> resultado = new ArrayList<Divida>();

        List<Divida> dividas = new ArrayList<Divida>();
        dividas = dividaDAO.listarDividas(idUsuario, ano);

        if(dividas == null || dividas.isEmpty())
            return null;

        for(Divida divida: dividas) {
            if(divida.isParcelado()) {
                List<Parcela> parcelas = dividaDAO.listarParcelas(divida.getId(), mes, ano);
                if(parcelas != null) {
                    divida.setParcelas(parcelas);
                    resultado.add(divida);
                }
            }else {
                String[] data = divida.getVencimento().split("/");

                String mesNovo = String.valueOf(mes);
                if(mes < 10)
                    mesNovo = "0"+ mes;

                if (data[1].equals(mesNovo) && data[2].equals(ano))
                    resultado.add(divida);
            }
        }

        if (resultado != null && !resultado.isEmpty())
            return resultado;
        return null;
    }
}