package br.com.mobidebits.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.R;
import br.com.mobidebits.utils.FinancasUtils;

/**
 * Created by Robson on 23/12/2015.
 */
public class DividaAdapter extends BaseAdapter {

    private final List<Divida> dividas;
    private final Context context;
    private LayoutInflater inflater;
    private FinancasUtils dividaUtils;

    public DividaAdapter(List<Divida> lista, Context context) {
        super();
        this.dividas = lista;
        this.context = context;
        inflater = LayoutInflater.from(context);
        dividaUtils = new FinancasUtils();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.divida_item, viewGroup, false);

        Divida divida = dividas.get(i);
        TextView descricao = (TextView) view.findViewById(R.id.divida_descricao);
        TextView valor = (TextView) view.findViewById(R.id.divida_valor);
        TextView vencimento = (TextView) view.findViewById(R.id.divida_vencimento);

        descricao.setText(divida.getDescricao());
        valor.setText(dividaUtils.convertToMoney(divida.getValor(),"BRL").toString());
        vencimento.setText(new String(divida.getVencimento().substring(0,5)));

        return view;
    }

    @Override
    public int getCount() {
        return dividas.size();
    }

    @Override
    public Object getItem(int i) {
        return dividas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}