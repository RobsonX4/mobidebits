package br.com.mobidebits.beans;

import java.util.Calendar;

import br.com.mobidebits.dao.Tabela;

/**
 * Created by Robson on 21/12/2015.
 */
public class Parcela implements Tabela {

    private Long id;
    private Long idDivida;
    private Integer numero;
    private Double valor;
    private String vencimento;
    private Integer status;

    //Metodos Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDivida() {
        return idDivida;
    }

    public void setIdDivida(Long idDivida) {
        this.idDivida = idDivida;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //Para gerar tabela
    public static final String TABELA       = "parcelas";
    public static final String ID           = "id";
    public static final String ID_DIVIDA    = "id_divida";
    public static final String NUMERO       = "numero";
    public static final String VALOR        = "valor";
    public static final String VENCIMENTO   = "vencimento";
    public static final String STATUS       = "status";

    @Override
    public String getTabela() {
        String tabela = (
                "CREATE TABLE " + TABELA + " ("
                    + ID            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ID_DIVIDA     + " INTEGER NOT NULL,"
                    + NUMERO        + " TEXT NOT NULL,"
                    + VALOR         + " TEXT NOT NULL,"
                    + VENCIMENTO    + " TEXT NOT NULL,"
                    + STATUS        + " INTEGER NOT NULL,"
                    + "FOREIGN KEY("+ ID_DIVIDA +") REFERENCES "+ Divida.TABELA +"("+ Divida.ID +")"
                    + ")"
        );
        return tabela;
    }
}