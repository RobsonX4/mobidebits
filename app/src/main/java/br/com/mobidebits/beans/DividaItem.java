package br.com.mobidebits.beans;

/**
 * Created by Robson on 13/03/2016.
 */
public class DividaItem {

    private Long idDivida;
    private Long idParcela;
    private String descricao;
    private Double valor;
    private String vencimento;
    private boolean pago;

    //Metodos Getters & Setters
    public Long getIdDivida() {
        return idDivida;
    }
    public void setIdDivida(Long idDivida) {
        this.idDivida = idDivida;
    }

    public Long getIdParcela() {
        return idParcela;
    }
    public void setIdParcela(Long idParcela) {
        this.idParcela = idParcela;
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

    public String getVencimento() {
        return vencimento;
    }
    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public boolean isPago() {
        return pago;
    }
    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
