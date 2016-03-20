package br.com.mobidebits.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import br.com.mobidebits.beans.Divida;
import br.com.mobidebits.adapters.ItemAdapter;
import br.com.mobidebits.beans.DividaItem;
import br.com.mobidebits.beans.Meses;
import br.com.mobidebits.beans.Parcela;
import br.com.mobidebits.control.DividaControl;
import br.com.mobidebits.R;
import br.com.mobidebits.utils.Validator;

/**
 * Created by Robson on 23/12/2015.
 */
public class ListarDividasActivity extends Activity {

    private static final int OPTION_EDITAR = Menu.FIRST;
    private static final int OPTION_REMOVER = Menu.FIRST + 1;
    private static final int OPTION_DETALHES = Menu.FIRST + 2;

    private ItemAdapter itemAdapter;
    private DividaControl dividaControl;
    private int idUsuario;
    private String mes;
    private String anoSelecionado;
    private String mesText;
    private ListView lista;

    private EditText totalEditText;
    private EditText saldoEditText;
    private TextView mesSelecionado;
    private Switch isPago;
    private double salario;
    List<Divida> dividas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_dividas);

        dividaControl = new DividaControl(getBaseContext());
        dividas = new ArrayList<Divida>();

        mesSelecionado = (TextView) findViewById(R.id.mesSelecionado);
        totalEditText = (EditText) findViewById(R.id.total);
        saldoEditText = (EditText) findViewById(R.id.saldo);
        lista = (ListView) findViewById(R.id.lista_dividas);

        Intent parametros = getIntent();
        Bundle bundle = new Bundle();
        bundle = parametros.getBundleExtra("parametros");

        idUsuario = bundle.getInt("idUsuario");
        mes = bundle.getString("mes");
        anoSelecionado = bundle.getString("anoSelecionado");
        mesText = bundle.getString("mesText");
        salario = bundle.getDouble("salario");
        mesSelecionado.setText(mesText);

        listarDividas(this.idUsuario, Integer.valueOf(this.mes), this.anoSelecionado);

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

    public void nextMonth(View view){
        int nextMes = getMesInt(mesSelecionado.getText().toString())+1;
        if(nextMes > 12) {
            nextMes = 1;
            this.anoSelecionado = "" + (Integer.valueOf(this.anoSelecionado) + 1);
        }
        listarDividas(idUsuario, nextMes, this.anoSelecionado);
    }

    public void previewMonth(View view){
        int previewMes = getMesInt(mesSelecionado.getText().toString())-1;
        if(previewMes < 1) {
            previewMes = 12;
            this.anoSelecionado = "" + (Integer.valueOf(this.anoSelecionado) - 1);
        }
        listarDividas(idUsuario, previewMes, this.anoSelecionado);
    }

    private void listarDividas(int idUsuario, int mes, String anoSelecionado){
        saldoEditText.setTextColor(Color.BLACK);
        double total = 0.0;
        mesSelecionado.setText(getMes(mes));

        dividas = dividaControl.listarDividas(idUsuario, mes, anoSelecionado);
        List<DividaItem> itens = new ArrayList<DividaItem>();
        if (dividas != null) {
            for(Divida divida : dividas){
                DividaItem item = new DividaItem();
                item.setIdDivida(divida.getId());
                item.setDescricao(divida.getDescricao());
                if(divida.isParcelado())
                    for(Parcela parcela : divida.getParcelas()){
                        item.setIdParcela(parcela.getId());
                        item.setDescricao(divida.getDescricao()
                            +"("+ parcela.getNumero() +"/"+ divida.getNParcelas() +")");
                        item.setValor(parcela.getValor());
                        item.setVencimento(parcela.getVencimento());
                        item.setPago(parcela.getStatus() == 0 ? false:true);
                    }
                else{
                    item.setValor(divida.getValor());
                    item.setVencimento(divida.getVencimento());
                    item.setPago(divida.getStatus() == 0 ? false:true);
                }
                itens.add(item);
            }
            itemAdapter = new ItemAdapter(itens, this);
            lista.setAdapter(itemAdapter);

            for (DividaItem item : itens)
                total += item.getValor();

            if(total > salario)
                saldoEditText.setTextColor(Color.rgb(230,40,30));
        } else{
            lista.setAdapter(null);
            Toast.makeText(this, "Lista vazia.", Toast.LENGTH_SHORT).show();
        }
        totalEditText.setText(Validator.convertToMoney(total, Currency.getInstance("BRL")));
        saldoEditText.setText(Validator.convertToMoney(salario - total, Currency.getInstance("BRL")));
    }

    public void cadastrarNovo(View view) {
        Bundle parametros = new Bundle();
        parametros.putInt("idUsuario", idUsuario);

        Intent cadastroDivida = new Intent(this, CadastrarDividaActivity.class);
        cadastroDivida.putExtra("parametros", parametros);
        startActivity(cadastroDivida);
    }

    // Sempre que o switch é alterado
    public void realizarPagamento(View view) {
        Toast.makeText(this, "Item pago.", Toast.LENGTH_SHORT).show();
    }

    public String getMes(int mes){
        switch (mes){
            case 1: return "Janeiro";
            case 2: return "Fevereiro";
            case 3: return "Março";
            case 4: return "Abril";
            case 5: return "Maio";
            case 6: return "Junho";
            case 7: return "Julho";
            case 8: return "Agosto";
            case 9: return "Setembro";
            case 10:return "Outubro";
            case 11:return "Novembro";
            case 12:return "Dezembro";
            default:return null;
        }
    }

    public int getMesInt(String mes){
        switch (mes){
            case "Janeiro":     return 1;
            case "Fevereiro":   return 2;
            case "Março":       return 3;
            case "Abril":       return 4;
            case "Maio":        return 5;
            case "Junho":       return 6;
            case "Julho":       return 7;
            case "Agosto":      return 8;
            case  "Setembro":   return 9;
            case "Outubro":     return 10;
            case "Novembro":    return 11;
            case "Dezembro":    return 12;
            default:return 0;
        }
    }
}