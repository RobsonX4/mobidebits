package br.com.mobidebits.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.InputMismatchException;

/**
 * Created by Robson on 13/03/2016.
 */
public final class Validator {

    /**
     * Converte double para valor monetário.
     * @author Robson Souza
     * @param valorMonetario
     * @param currency
     * @return String
     */
    public static String convertToMoney(double valorMonetario, Currency currency) {
        if (currency != null) {
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            nf.setCurrency(currency);
            return nf.format(valorMonetario);
        }
        return null;
    }

    /**
     * Converte valor monetário para double.
     * @author Robson Souza
     * @param valorMonetario
     * @param currency
     * @return Double
     */
    public static Double monetaryToDouble(String valorMonetario, Currency currency) {
        if (currency != null && valorMonetario != null	&& !valorMonetario.isEmpty()) {
            int lenght = currency.getSymbol().length();
            int casasDecimais = valorMonetario.length();
            return Double.valueOf(valorMonetario
                    .substring(lenght, casasDecimais--)
                    .replace(".", "")
                    .replace(",", "."));
        }
        return null;
    }

    /**
     * Verifica se a String está vazia.
     * @author Robson Souza
     * @param valor
     * @return false se o parametro for IGUAL á null OU "" OU " "
     */
    public static boolean isNotBlank(String valor) {
        if (valor == null || valor.isEmpty() || valor.equals("") || valor.equals(" "))
            return false;
        return true;
    }

    public static boolean isCPF(String CPF) {
        if (CPF == null || CPF.length() != 11
                || CPF.matches("^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|9{11}|9{11})$"))
            return false;

        char dig10, dig11;
        int sm, i, r,num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char)(r + 48);

            sm = 0; peso = 11;
            for(i = 0; i < 10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);

            return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    /**
     * Insere mascara para CPF
     * @param CPF
     * @return String CPF
     */
    public static String maskCPF(String CPF) {
        if(CPF.length() == 11)
            return(CPF.substring(0, 3)
                    +"."+ CPF.substring(3, 6)
                    + "."+ CPF.substring(6, 9)
                    +"-"+ CPF.substring(9, 11));
        return null;
    }

    public static boolean isCNPJ(String CNPJ) {
        if (CNPJ == null || CNPJ.length() != 14
                || CNPJ.matches("^(0{14}|1{14}|2{14}|3{14}|4{14}|5{14}|6{14}|7{14}|9{14}|9{14})$"))
            return false;

        char dig13, dig14;
        int sm, i, r, num, peso;

        try{
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char)((11-r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char)((11-r) + 48);

            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return(true);

            return(false);
        } catch (InputMismatchException erro){
            return(false);
        }
    }

    /**
     * Insere mascara para CNPJ
     * @param CNPJ
     * @return String CNPJ
     */
    public static String maskCNPJ(String CNPJ) {
        if(CNPJ.length() == 14)
            return(CNPJ.substring(0, 2)
                    +"."+ CNPJ.substring(2, 5)
                    +"."+ CNPJ.substring(5, 8)
                    +"/"+ CNPJ.substring(8, 12)
                    +"-"+ CNPJ.substring(12, 14));
        return null;
    }
}
