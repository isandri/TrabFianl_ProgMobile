package com.example.trabalhofinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.requests.RastreioEncomenda;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentRastreio extends Fragment {
    private static final String ARG_PARAM1 = "encomenda-codigo";

    private String codigoDeRastreio;

    public FragmentRastreio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            codigoDeRastreio = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rastreio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextInputEditText) view.findViewById(R.id.inputcodigo)).setText(codigoDeRastreio);

        Button btnBuscar = view.findViewById(R.id.btnbuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoDeRastreio = ((TextInputEditText) view.findViewById(R.id.inputcodigo)).getText().toString();
                String urlRastreio = String.format("https://www.linkcorreios.com.br/?id=%s", codigoDeRastreio);

                String resultadoRastreamento = RastreioEncomenda.rastrearEncomenda(urlRastreio, requireContext());

                ((TextView) view.findViewById(R.id.resultadoRastreamento)).setText(resultadoRastreamento);
            }
        });
    }
}