package br.com.mobidebits.beans.enuns;

import br.com.mobidebits.R;

/**
 * Created by Robson on 26/12/2015.
 */
public enum MES {

    JANEIRO(1),
    FEVEREIRO(2),
    MARCO(3),
    ABRIL(4),
    MAIO(5),
    JUNHO(6),
    JULHO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DEZEMBRO(12);

    private final int MES;

    MES(int mes) {
        this.MES = mes;
    }

    public int getInt(){
        return MES;
    }

    public String getDescricao(){
        switch (MES){
            case 1:{
                return "Janeiro";
            }case 2:{
                return "Fevereiro";
            }case 3:{
                return "Março";
            }case 4:{
                return "Abril";
            }case 5:{
                return "Maio";
            }case 6:{
                return "Junho";
            }case 7:{
                return "Julho";
            }case 8:{
                return "Agosto";
            }case 9:{
                return "Setembro";
            }case 10:{
                return "Outubro";
            }case 11:{
                return "Novembro";
            }case 12:{
                return "Dezembro";
            }default:
                return null;
        }
    }
}
