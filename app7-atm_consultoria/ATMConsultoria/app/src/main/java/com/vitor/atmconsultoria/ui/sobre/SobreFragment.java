package com.vitor.atmconsultoria.ui.sobre;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitor.atmconsultoria.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreFragment extends Fragment {
    public SobreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sobre, container, false);
        String descricao = "A ATM Consultoria tem como missão apoiar organizações que desejam " +
                "alcançar o sucesso atráves da excelência em gestão e da busca pela qualidade";

        Element versao = new Element();
        versao.setTitle("Versão 1.0");

        return new AboutPage(getActivity())
                .setImage(R.drawable.logo)
                .setDescription(descricao)
                .addGroup("Entre em contato")
                .addEmail("dev.vitor1@gmail.com", "Envie um email")
                .addWebsite("https://www.linktr.ee/viit0r", "Acesse nosso site")

                .addGroup("Redes Sociais")
                .addGitHub("viit0r", "Github")
                .addItem(versao)
                .create();

    }
}