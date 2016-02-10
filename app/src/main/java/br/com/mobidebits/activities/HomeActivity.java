package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.mobidebits.R;

/**
 * Created by Robson on 13/12/2015.
 */
public class HomeActivity extends Activity {

    private int idUsuario;
    private EditText salario;
    private TextView anoSelecionado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Recuperar parametros
        Intent parametros = getIntent();
        Bundle bundle = new Bundle();
        bundle = parametros.getBundleExtra("parametros");
        idUsuario = bundle.getInt("idUsuario");

        anoSelecionado = (TextView) findViewById(R.id.anoSelecionado);

        //Trabalhando com mascara automatica no campo monetário
        salario = (EditText) findViewById(R.id.salario);
        salario.setInputType(InputType.TYPE_CLASS_NUMBER);
        salario.addTextChangedListener(new TextWatcher() {

            private boolean isUpdating = false;
            // Pega a formatacao do sistema, se for brasil R$ se EUA US$
            private NumberFormat nf = NumberFormat.getCurrencyInstance();

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
                    // Transformamos o número que está escrito no EditText em
                    // monetário.
                    str = nf.format(Double.parseDouble(str) / 100);
                    salario.setText(str);
                    salario.setSelection(salario.getText().length());
                } catch (NumberFormatException e) {
                    s = "";
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não utilizamos
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Não utilizamos
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_cadastrar: {
                Bundle parametros = new Bundle();
                parametros.putInt("idUsuario", idUsuario);

                Intent cadastrarDivida = new Intent(this, CadastrarDividaActivity.class);
                cadastrarDivida.putExtra("parametros", parametros);
                startActivity(cadastrarDivida);
                return true;
            }
            case R.id.option_consultar: {
                //Código
                return true;
            }
            case R.id.option_editar: {
                //Código
                return true;
            }
            case R.id.logout: {
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void listarDividas(View view) {
        Button btnMes = (Button) findViewById(view.getId());
        int mes = 0;

        if (btnMes.getText().equals(getString(R.string.janeiro))) {
            mes = 1;
        } else if (btnMes.getText().equals(getString(R.string.fevereiro))) {
            mes = 2;
        } else if (btnMes.getText().equals(getString(R.string.marco))) {
            mes = 3;
        } else if (btnMes.getText().equals(getString(R.string.abril))) {
            mes = 4;
        } else if (btnMes.getText().equals(getString(R.string.maio))) {
            mes = 5;
        } else if (btnMes.getText().equals(getString(R.string.junho))) {
            mes = 6;
        } else if (btnMes.getText().equals(getString(R.string.julho))) {
            mes = 7;
        } else if (btnMes.getText().equals(getString(R.string.agosto))) {
            mes = 8;
        } else if (btnMes.getText().equals(getString(R.string.setembro))) {
            mes = 9;
        } else if (btnMes.getText().equals(getString(R.string.outubro))) {
            mes = 10;
        } else if (btnMes.getText().equals(getString(R.string.novembro))) {
            mes = 11;
        } else if (btnMes.getText().equals(getString(R.string.dezembro))) {
            mes = 12;
        }

        String salarioText = salario.getText().toString();
        int casasDecimais = salarioText.length();
        String salarioDouble = salarioText.substring(2, casasDecimais--).replace(".","").replace(",", ".");

        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);
        parametros.putInt("mes", mes);
        parametros.putInt("anoSelecionado", Integer.parseInt((String) anoSelecionado.getText()));
        parametros.putDouble("salario", Double.valueOf(salarioDouble));
        parametros.putString("mesText", String.valueOf(btnMes.getText()));

        Intent dividas = new Intent(this, ListarDividasActivity.class);
        dividas.putExtra("parametros", parametros);
        startActivity(dividas);
    }

    public void previewYear(View view){
        ImageButton btnPreview = (ImageButton) findViewById(R.id.btnPreviewYear);
        TextView year = (TextView)findViewById(R.id.anoSelecionado);
        int ano = Integer.parseInt(year.getText().toString());
        String anoText = String.valueOf( ano -=1);
        year.setText(anoText);
    }
    public void nextYear(View view){
        ImageButton btnNext = (ImageButton) findViewById(R.id.btnNextYear);
        TextView year = (TextView)findViewById(R.id.anoSelecionado);
        int ano = Integer.parseInt(year.getText().toString());
        String anoText = String.valueOf(ano += 1);
        year.setText(anoText);
    }
}