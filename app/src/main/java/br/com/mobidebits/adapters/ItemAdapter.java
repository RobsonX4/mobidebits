package br.com.mobidebits.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.R;
import br.com.mobidebits.beans.DividaItem;
import br.com.mobidebits.utils.FinancasUtils;
import br.com.mobidebits.utils.Validator;

/**
 * Created by Robson on 23/12/2015.
 */
public class ItemAdapter extends BaseAdapter {

    private final List<DividaItem> itens;
    private final Context context;
    private LayoutInflater inflater;

    public ItemAdapter(List<DividaItem> lista, Context context) {
        super();
        this.itens = lista;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.divida_item, viewGroup, false);

        DividaItem item = itens.get(i);
        TextView descricao = (TextView) view.findViewById(R.id.divida_descricao);
        TextView valor = (TextView) view.findViewById(R.id.divida_valor);
        TextView vencimento = (TextView) view.findViewById(R.id.divida_vencimento);
        Switch isPago = (Switch) view.findViewById(R.id.divida_isPago);

        descricao.setText(item.getDescricao());
        valor.setText(Validator.convertToMoney(item.getValor(), Currency.getInstance("BRL")).toString());
        vencimento.setText(new String(item.getVencimento().substring(0,5)));
        isPago.setChecked(item.isPago());

        return view;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int i) {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}