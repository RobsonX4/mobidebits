package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.adapters.DividaAdapter;
import br.com.mobidebits.control.DividaControl;
import br.com.mobidebits.R;
import br.com.mobidebits.utils.FinancasUtils;

/**
 * Created by Robson on 23/12/2015.
 */
public class ListarDividasActivity extends Activity {

    private static final int OPTION_EDITAR = Menu.FIRST;
    private static final int OPTION_REMOVER = Menu.FIRST + 1;
    private static final int OPTION_DETALHES = Menu.FIRST + 2;

    private DividaAdapter dividaAdapter;
    private DividaControl dividaControl;
    private int idUsuario;
    private int mes;
    private int anoSelecionado;
    private String mesText;

    private EditText totalEditText;
    private EditText saldoEditText;
    private TextView mesSelecionado;
    private Switch isPago;
    private double salario;

    private FinancasUtils financasUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_dividas);

        mesSelecionado = (TextView) findViewById(R.id.mesSelecionado);
        dividaControl = new DividaControl(getBaseContext());
        financasUtils = new FinancasUtils();

        Intent parametros = getIntent();
        Bundle bundle = new Bundle();
        bundle = parametros.getBundleExtra("parametros");

        idUsuario = bundle.getInt("idUsuario");
        mes = bundle.getInt("mes");
        anoSelecionado = bundle.getInt("anoSelecionado");
        mesText = bundle.getString("mesText");
        salario = bundle.getDouble("salario");

        this.mesSelecionado.setText(mesText);

        listarDividas(this.anoSelecionado, this.mes, this.idUsuario);

        //Menu de Contexto - Exibe quando tem um clique longo sobre um item.
        ListView rowItem = (ListView) findViewById(R.id.lista_dividas);
        registerForContextMenu(rowItem);
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
            }case R.id.logout: {
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, OPTION_EDITAR, 0, "Editar");
        menu.add(0, OPTION_REMOVER, 0, "Remover");
        menu.add(0, OPTION_DETALHES, 0, "Detalhes");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case OPTION_EDITAR: {
                Bundle parametros = new Bundle();
                parametros.putInt("idUsuario", idUsuario);

                Intent cadastroDivida = new Intent(this, CadastrarDividaActivity.class);
                cadastroDivida.putExtra("parametros", parametros);
                startActivity(cadastroDivida);
                return true;
            }
            case OPTION_REMOVER: {
                Toast.makeText(this, "Item removido", Toast.LENGTH_SHORT).show();
                return true;
            }
            case OPTION_DETALHES: {
                Toast.makeText(this, "Detalhes", Toast.LENGTH_SHORT).show();
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void listarDividas(int ano, int mes, int idUsuario) {
        if (mes >= 1 && mes <= 12 && idUsuario != 0) {
            List<Divida> dividas = new ArrayList<Divida>();
            dividas = dividaControl.listarDividas(idUsuario, 1);

            if (dividas != null) {
                ListView lista = (ListView) findViewById(R.id.lista_dividas);
                dividaAdapter = new DividaAdapter(dividas, this);
                lista.setAdapter(dividaAdapter);

                double total = 0.0;
                for (Divida divida : dividas) {
                    total += divida.getValor();
                }

                totalEditText = (EditText) findViewById(R.id.total);
                saldoEditText = (EditText) findViewById(R.id.saldo);
                totalEditText.setText(financasUtils.convertToMoney(total, "BRL"));
                saldoEditText.setText(financasUtils.convertToMoney(salario - total, "BRL"));
            } else
                Toast.makeText(this, "Lista vazia.", Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrarNovo(View view) {
        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);

        Intent cadastroDivida = new Intent(this, CadastrarDividaActivity.class);
        cadastroDivida.putExtra("parametros", parametros);
        startActivity(cadastroDivida);
    }

    // Sempre que o switch é alterado
    public void realizarPagamento(View view){
        Toast.makeText(this, "Item pago.", Toast.LENGTH_SHORT).show();
    }

    public void voltar(View view) {
        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);

        Intent home = new Intent(this, HomeActivity.class);
        home.putExtra("parametros", parametros);
        startActivity(home);
    }
}