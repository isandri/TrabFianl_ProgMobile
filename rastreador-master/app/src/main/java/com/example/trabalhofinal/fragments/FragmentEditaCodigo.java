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

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.dao.EncomendaDao;
import com.example.trabalhofinal.database.AppDatabase;
import com.example.trabalhofinal.entities.Encomenda;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class FragmentEditaCodigo extends Fragment {
    private static final String ARG_PARAM1 = "encomenda-id";
    private static final String ARG_PARAM2 = "encomenda-codigo";
    private static final String ARG_PARAM3 = "encomenda-descricao";
    private static final String ARG_PARAM4 = "encomenda-userId";

    private long encomendaId;
    private String encomendaCodigo;
    private String encomendaDescricao;
    private long encomendaUserId;

    private FragmentManager fragmentManager;
    private EncomendaDao encomendaDao;

    public FragmentEditaCodigo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            encomendaId = getArguments().getLong(ARG_PARAM1);
            encomendaCodigo = getArguments().getString(ARG_PARAM2);
            encomendaDescricao = getArguments().getString(ARG_PARAM3);
            encomendaUserId = getArguments().getLong(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = requireActivity().getSupportFragmentManager();
        return inflater.inflate(R.layout.fragment_edita_codigo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        encomendaDao = AppDatabase.getInstance(requireContext()).encomendaDao();

        ((TextInputEditText) view.findViewById(R.id.codrastreioedt)).setText(encomendaCodigo);
        ((TextInputEditText) view.findViewById(R.id.descrastreioedt)).setText(encomendaDescricao);

        ((Button) view.findViewById(R.id.btnrastreioedt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentRastreio fragmentRastreio = new FragmentRastreio();
                Bundle args = new Bundle();

                args.putString(ARG_PARAM2, encomendaCodigo);
                fragmentRastreio.setArguments(args);

                MainActivity.replaceFragment(fragmentRastreio, fragmentManager.beginTransaction());
            }
        });

        ((Button) view.findViewById(R.id.btnsalvaredt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Encomenda encomenda = new Encomenda();

                encomendaCodigo = ((TextInputEditText) view.findViewById(R.id.codrastreioedt)).getText().toString();
                encomendaDescricao = ((TextInputEditText) view.findViewById(R.id.descrastreioedt)).getText().toString();

                encomenda.setId(encomendaId);
                encomenda.setCodigo(encomendaCodigo);
                encomenda.setDescricao(encomendaDescricao);
                encomenda.setUserId(encomendaUserId);

                Executors.newSingleThreadExecutor().execute(() -> {
                    int updatedRows = encomendaDao.updateEncomenda(encomenda);

                    requireActivity().runOnUiThread(() -> {
                        if (updatedRows > 0) {
                            Toast.makeText(requireContext(), "Encomenda atualizada com sucesso", Toast.LENGTH_SHORT).show();
                            fragmentManager.popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Não foi possível atualizar a encomenda", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });

        ((Button) view.findViewById(R.id.btnapagaredt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Encomenda encomenda = new Encomenda();

                encomenda.setId(encomendaId);
                encomenda.setCodigo(encomendaCodigo);
                encomenda.setDescricao(encomendaDescricao);
                encomenda.setUserId(encomendaUserId);

                Executors.newSingleThreadExecutor().execute(() -> {
                    int deletedRows = encomendaDao.deleteEncomenda(encomenda);

                    requireActivity().runOnUiThread(() -> {
                       if (deletedRows > 0) {
                           Toast.makeText(requireContext(), "Encomenda removida com sucesso", Toast.LENGTH_SHORT).show();
                           fragmentManager.popBackStack();
                       } else {
                           Toast.makeText(requireContext(), "Não foi possível remover a encomenda", Toast.LENGTH_SHORT).show();
                       }
                    });
                });
            }
        });
    }
}