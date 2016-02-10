package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mobidebits.R;
import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.control.DividaControl;

/**
 * Created by Robson on 12/12/2015.
 */
public class CadastrarDividaActivity extends Activity {

    private DividaControl dividaControl;

    private EditText descricaoEditText;
    private EditText valorEditText;
    private EditText aberturaEditText;
    private EditText vencimetoEditText;
    private EditText tipoEditText;
    private Switch parceladoSwitch;
    private TextView statusPage;

    private int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_divida);

        Intent parametros = getIntent();
        Bundle bundle = new Bundle();
        bundle = parametros.getBundleExtra("parametros");

        idUsuario = bundle.getInt("idUsuario");
        dividaControl = new DividaControl(getBaseContext());

        statusPage = (TextView) findViewById(R.id.statusCadastro);
        descricaoEditText = (EditText) findViewById(R.id.descricao);
        valorEditText = (EditText) findViewById(R.id.valor);
        aberturaEditText = (EditText) findViewById(R.id.abertura);
        vencimetoEditText = (EditText) findViewById(R.id.vencimento);
        tipoEditText = (EditText) findViewById(R.id.tipo);
        parceladoSwitch = (Switch) findViewById(R.id.parcelado);
    }

    public void cadastrar(View view) {
        Divida divida = new Divida();

        divida.setIdUsuario(idUsuario);
        divida.setDescricao(descricaoEditText.getText().toString());
        divida.setValor(Double.valueOf(valorEditText.getText().toString()));
        divida.setAbertura(aberturaEditText.getText().toString());
        divida.setVencimento(vencimetoEditText.getText().toString());
        divida.setTipo(tipoEditText.getText().toString());
        divida.setParcelado(true);
        divida.setStatus(2);

        if (dividaControl.inserir(divida)) {
            Toast.makeText(this, "Item cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            Intent dividas = new Intent(this, ListarDividasActivity.class);
            Bundle parametros = new Bundle();
            parametros.putInt("idUsuario", idUsuario);
            parametros.putInt("mes", 1);
            dividas.putExtra("parametros", parametros);
            startActivity(dividas);
        } else
            statusPage.setText("Ops. Erro ao cadastrar.");
    }

    public void voltar(View view) {
        Intent home = new Intent(this, HomeActivity.class);
        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);
        parametros.putInt("mes", 1);

        home.putExtra("parametros", parametros);
        startActivity(home);
    }
}