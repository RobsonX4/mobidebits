package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import br.com.mobidebits.R;
import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.beans.Parcela;
import br.com.mobidebits.control.DividaControl;
import br.com.mobidebits.utils.FinancasUtils;
import br.com.mobidebits.utils.MonetaryMask;
import br.com.mobidebits.utils.Validator;

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
    private EditText qntParcelaEditText;
    private EditText valorParcelaEditText;
    private TextView statusPage;
    private TableRow rowQuantParcela;
    private TableRow rowValorParcela;

    private int idUsuario = 0;
    private boolean isParcelado = false;

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
        qntParcelaEditText = (EditText) findViewById(R.id.quantidadeParcela);
        valorParcelaEditText = (EditText) findViewById(R.id.valorParcela);

        rowValorParcela = (TableRow) findViewById(R.id.rowValorParcela);
        rowQuantParcela = (TableRow) findViewById(R.id.rowQuantParcela);

        valorEditText.addTextChangedListener(new MonetaryMask(valorEditText));
        valorParcelaEditText.addTextChangedListener(new MonetaryMask(valorParcelaEditText));

        parceladoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                rowValorParcela.setVisibility(View.VISIBLE);
                rowQuantParcela.setVisibility(View.VISIBLE);
                isParcelado = true;
            } else {
                rowValorParcela.setVisibility(View.GONE);
                rowQuantParcela.setVisibility(View.GONE);
                isParcelado = false;
            }
            }
        });

        qntParcelaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String qntParcela = String.valueOf(qntParcelaEditText.getText());
                Double valorParcela = 0.0;
                if (!qntParcela.equals("0") && !qntParcela.isEmpty() ) {
                    valorParcela = (Validator.monetaryToDouble(String.valueOf(valorEditText.getText()),Currency.getInstance("BRL"))
                            / Integer.parseInt(qntParcela));
                }
                valorParcelaEditText.setText(Validator.convertToMoney(valorParcela, Currency.getInstance("BRL")));
            }
        });
    }

    public void cadastrar(View view) {
        Divida divida = new Divida();

        divida.setIdUsuario(idUsuario);

        divida.setDescricao(descricaoEditText.getText().toString());
        divida.setValor(Validator.monetaryToDouble(valorEditText.getText().toString(), Currency.getInstance("BRL")));
        divida.setAbertura(aberturaEditText.getText().toString());
        divida.setVencimento(vencimetoEditText.getText().toString());
        divida.setTipo(tipoEditText.getText().toString());
        divida.setParcelado(isParcelado);
        String nParcelas = qntParcelaEditText.getText().toString();
        if(Validator.isNotBlank(nParcelas))
            divida.setNParcelas(Integer.valueOf(nParcelas));
        else
            divida.setNParcelas(0);
        divida.setStatus(0);

        if(isParcelado){
            List<Parcela> parcelas = new ArrayList<Parcela>();
            int quantParc = Integer.parseInt(String.valueOf(qntParcelaEditText.getText()));

            String mes = String.valueOf(vencimetoEditText.getText()).split("/")[1];
            int ano = Integer.parseInt(String.valueOf(aberturaEditText.getText()).split("/")[2]);
            int iteratorMes = Integer.valueOf(mes);
            for(int i = 0; i < quantParc; i++){
                if(iteratorMes > 12) {
                    iteratorMes = 1;
                    ano += 1;
                }
                String proxMes = "";
                if(iteratorMes < 10)
                    proxMes = "0";
                if(i != 0) {
                    proxMes += iteratorMes + "";
                    mes = proxMes;
                }
                iteratorMes++;
                Parcela parcela = new Parcela();
                parcela.setNumero(i + 1);
                parcela.setValor(Validator.monetaryToDouble(
                        String.valueOf(valorParcelaEditText.getText()), Currency.getInstance("BRL")));
                parcela.setVencimento(String.valueOf(vencimetoEditText.getText()).split("/")[0] + "/" + mes + "/" + ano);
                parcela.setStatus(0);
                parcelas.add(parcela);
            }
            divida.setParcelas(parcelas);
        }

        Long idDivida = dividaControl.inserirDivida(divida);
        if (idDivida != null) {
            if(divida.isParcelado())
                for(Parcela parcela : divida.getParcelas())
                    dividaControl.inserirParcela(parcela, idDivida);

            Toast.makeText(this, "Item cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            Intent listarDividas = new Intent(this, ListarDividasActivity.class);
            Bundle parametros = new Bundle();
            parametros.putInt("idUsuario", idUsuario);
            parametros.putString("mes", divida.getVencimento().split("/")[1]);
            parametros.putString("anoSelecionado", divida.getVencimento().split("/")[2]);
            listarDividas.putExtra("parametros", parametros);
            startActivity(listarDividas);
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