package br.com.mobidebits.beans;

import java.util.List;

import br.com.mobidebits.dao.Tabela;

/**
 * Created by Robson on 21/12/2015.
 */
public class Divida implements Tabela {

    private Long id;
    private Integer idUsuario;
    private String descricao;
    private Double valor;
    private String abertura;
    private String vencimento;
    private String tipo;
    private boolean parcelado;
    private Integer nParcelas;
    private Integer status;
    private List<Parcela> parcelas;
    private boolean pago;

    //Metodos Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getAbertura() {
        return abertura;
    }
    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public String getVencimento() {
        return vencimento;
    }
    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isParcelado() {
        return parcelado;
    }
    public void setParcelado(boolean parcelado) {
        this.parcelado = parcelado;
    }

    public Integer getNParcelas() {
        return nParcelas;
    }
    public void setNParcelas(Integer nParcelas) {
        this.nParcelas = nParcelas;
    }

    public boolean isPago() {
        return pago;
    }
    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }
    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    //TABELA BANCO DE DADOS
    public static final String TABELA       = "dividas";
    public static final String ID           = "id";
    public static final String ID_USUARIO   = "id_usuario";
    public static final String DESCRICAO    = "descricao";
    public static final String VALOR        = "valor";
    public static final String ABERTURA     = "abertura";
    public static final String VENCIMENTO   = "vencimento";
    public static final String TIPO         = "tipo";
    public static final String IS_PARCELADO = "is_parcelado";
    public static final String N_PARCELAS   = "nParcelas";
    public static final String STATUS       = "status";

    @Override
    public String getTabela() {
        String tabela = (
                "CREATE TABLE " + TABELA + " ("
                    + ID            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ID_USUARIO    + " INTEGER NOT NULL,"
                    + DESCRICAO     + " TEXT NOT NULL,"
                    + VALOR         + " TEXT NOT NULL,"
                    + ABERTURA      + " TEXT NOT NULL,"
                    + VENCIMENTO    + " TEXT NOT NULL,"
                    + TIPO          + " TEXT NOT NULL,"
                    + IS_PARCELADO  + " INTEGER NOT NULL,"
                    + N_PARCELAS    + " INTEGER NOT NULL,"
                    + STATUS        + " INTEGER NOT NULL,"
                    + "FOREIGN KEY("+ ID_USUARIO +") REFERENCES "+ Usuario.TABELA +"("+ Usuario.ID +")"
                    + ")"
        );
        return tabela;
    }
}