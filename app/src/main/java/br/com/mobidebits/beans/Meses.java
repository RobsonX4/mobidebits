package br.com.mobidebits.beans;

/**
 * Created by Robson on 16/03/2016.
 */
public class Meses {
    public static enum MES {
        JANEIRO(1),     FEVEREIRO(2),
        MARCO(3),       ABRIL(4),
        MAIO(5),        JUNHO(6),
        JULHO(7),       AGOSTO(8),
        SETEMBRO(9),    OUTUBRO(10),
        NOVEMBRO(11),   DEZEMBRO(12);

        private final int MES;

        MES(int mes) {
            this.MES = mes;
        }

        public String getDescricao() {
            switch(this) {
                case JANEIRO:   return "Janeiro";
                case FEVEREIRO: return "Fevereiro";
                case MARCO:     return "Março";
                case ABRIL:     return "Abril";
                case MAIO:      return "Maio";
                case JUNHO:     return "Junho";
                case JULHO:     return "Julho";
                case AGOSTO:    return "Agosto";
                case SETEMBRO:  return "Setembro";
                case OUTUBRO:   return "Outubro";
                case NOVEMBRO:  return "Novembro";
                case DEZEMBRO:  return "Dezembro";
                default :       return "";
            }
        }
    }
}
