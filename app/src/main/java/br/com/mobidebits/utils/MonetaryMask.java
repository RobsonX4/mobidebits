package br.com.mobidebits.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by Robson on 11/02/2016.
 */
public class MonetaryMask implements TextWatcher {

    private EditText campoMonetario;
    private boolean isUpdating = false;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public MonetaryMask(EditText campoMonetario){
        this.campoMonetario = campoMonetario;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        // Evita que o método seja executado varias vezes.
        // Se tirar ele entre em loop
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = s.toString();
        // Verifica se já existe a máscara no texto.
        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1)
                && (str.indexOf(".") > -1 || str.indexOf(",") > -1));
        // Verificamos se existe máscara
        if (hasMask) {
            // Retiramos a máscara.
            str = str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
        }
        try {
            // Transformamos o número que está escrito no EditText em valor monetário
            str = nf.format(Double.parseDouble(str) / 100);
            campoMonetario.setText(str);
            campoMonetario.setSelection(campoMonetario.getText().length());
        } catch (NumberFormatException e) {
            s = "";
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void afterTextChanged(Editable s) {}
}
