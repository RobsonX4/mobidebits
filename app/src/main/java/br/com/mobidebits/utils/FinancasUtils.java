package br.com.mobidebits.utils;

import android.content.Intent;

import java.text.NumberFormat;
import java.util.Currency;

import br.com.mobidebits.activities.CadastrarUsuarioActivity;
import br.com.mobidebits.activities.LoginActivity;

/**
 * Created by Robson on 28/12/2015.
 */
public class FinancasUtils {

    //Transforma o double em um valor monetario
    public String convertToMoney(double valor, String moeda) {
        Currency currency = Currency.getInstance(moeda);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setCurrency(currency);

        return nf.format(valor);
    }
}
