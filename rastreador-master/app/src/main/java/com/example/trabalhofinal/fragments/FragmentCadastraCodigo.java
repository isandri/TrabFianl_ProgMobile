package com.example.trabalhofinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.dao.EncomendaDao;
import com.example.trabalhofinal.database.AppDatabase;
import com.example.trabalhofinal.entities.Encomenda;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class FragmentCadastraCodigo extends Fragment {
    private static final String ARG_PARAM1 = "user-id";

    private FragmentManager fragmentManager;
    private EncomendaDao encomendaDao;
    private long userId;

    public FragmentCadastraCodigo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = requireActivity().getSupportFragmentManager();
        return inflater.inflate(R.layout.fragment_cadastra_codigo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        encomendaDao = AppDatabase.getInstance(requireContext()).encomendaDao();

        ((Button) view.findViewById(R.id.btnsalvaradd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encomendaCodigo = ((TextInputEditText) view.findViewById(R.id.codrastreioadd)).getText().toString();
                String encomendaDescricao = ((TextInputEditText) view.findViewById(R.id.descrastreioadd)).getText().toString();

                Encomenda encomenda = new Encomenda();

                encomenda.setCodigo(encomendaCodigo);
                encomenda.setDescricao(encomendaDescricao);
                encomenda.setUserId(userId);

                Executors.newSingleThreadExecutor().execute(() -> {
                    long insertedId = encomendaDao.insertEncomenda(encomenda);

                    requireActivity().runOnUiThread(() -> {
                       if (insertedId > 0) {
                           Toast.makeText(requireContext(), "Encomenda cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                           fragmentManager.popBackStack();
                       } else {
                           Toast.makeText(requireContext(), "Não foi possível inserir a encomenda", Toast.LENGTH_SHORT).show();
                       }
                    });
                });
            }
        });

        ((Button) view.findViewById(R.id.btncancelaadd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });
    }
}