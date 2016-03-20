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
import java.util.Currency;
import java.util.List;

import br.com.mobidebits.R;
import br.com.mobidebits.utils.FinancasUtils;
import br.com.mobidebits.utils.MonetaryMask;
import br.com.mobidebits.utils.Validator;

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
        salario.addTextChangedListener(new MonetaryMask(salario));
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
        String mes = "";

        String [] meses = {
                getString(R.string.janeiro), getString(R.string.fevereiro),
                getString(R.string.marco),   getString(R.string.abril),
                getString(R.string.maio),    getString(R.string.junho),
                getString(R.string.julho),   getString(R.string.agosto),
                getString(R.string.setembro),getString(R.string.outubro),
                getString(R.string.novembro),getString(R.string.dezembro)};

        for(int i = 0; i < meses.length; i++){
            if(btnMes.getText().equals(meses[i])){
                if(i < 10)
                    mes = "0";
                mes += i+1 +"";
                break;
            }
        }

        Double teste = Validator.monetaryToDouble(salario.getText().toString(), Currency.getInstance("BRL"));

        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);
        parametros.putString("mes", mes);
        parametros.putString("anoSelecionado", String.valueOf(anoSelecionado.getText()));
        parametros.putDouble("salario", Validator.monetaryToDouble(salario.getText().toString(), Currency.getInstance("BRL")));
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