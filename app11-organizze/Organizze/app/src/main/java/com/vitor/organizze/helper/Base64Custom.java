package com.vitor.organizze.helper;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Base64Custom {

    public static String toBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.NO_WRAP).replaceAll("(\\n|\\r)","");
    }

    public static String getBase64(String textoCodificado){
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
