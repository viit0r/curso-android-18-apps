package com.vitor.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {
    public static String dataAtual(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String dataEscolhida (String data) {
        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];
        return mes + ano;

    }
}
