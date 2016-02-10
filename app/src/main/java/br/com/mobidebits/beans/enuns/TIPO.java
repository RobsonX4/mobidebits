package br.com.mobidebits.beans.enuns;

/**
 * Created by Robson on 21/12/2015.
 */
public enum TIPO {

    MENSAL(1),
    RESIDENCIAL(1),
    ALIMENTO(2),
    ENTRETENIMENTO(3),
    ESPORTE(4),
    SAUDE(5),
    POUPANCA(6),
    OUTROS(7);

    private final int valor;
    private final String descricao;

    TIPO(int valor) {
        this.valor = valor;

        switch (valor) {
            case 1:
                this.descricao = "Residencial";
                break;
            case 2:
                this.descricao = "Alimento";
                break;
            case 3:
                this.descricao = "Entretenimento";
                break;
            case 4:
                this.descricao = "Esporte";
                break;
            case 5:
                this.descricao = "Saúde";
                break;
            case 6:
                this.descricao = "Poupança";
                break;
            case 7:
                this.descricao = "Outros";
                break;
            default:
                this.descricao = "Outros";
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public int getValor() {
        return valor;
    }
}
