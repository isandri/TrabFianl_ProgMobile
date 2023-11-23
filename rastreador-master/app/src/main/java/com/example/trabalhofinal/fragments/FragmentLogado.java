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

import java.util.List;
import java.util.concurrent.Executors;

public class FragmentLogado extends Fragment {
    private static final String ARG_PARAM1 = "user-id";

    private FragmentManager fragmentManager;
    private EncomendaDao encomendaDao;
    private long userId;

    public FragmentLogado() {
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
        return inflater.inflate(R.layout.fragment_logado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Button) view.findViewById(R.id.btnListaEncomendas)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase db = AppDatabase.getInstance(requireContext());
                    encomendaDao = db.encomendaDao();

                    List<Encomenda> encomendasDoUsuario = encomendaDao.getEncomendasByUserId(userId);

                    FragmentLista fragmentLista = new FragmentLista(encomendasDoUsuario);
                    Bundle args = new Bundle();

                    args.putLong(ARG_PARAM1, userId);
                    fragmentLista.setArguments(args);

                    MainActivity.replaceFragment(fragmentLista, fragmentManager.beginTransaction());
                });
            }
        });

        ((Button) view.findViewById(R.id.btnRastrear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(new FragmentRastreio(), fragmentManager.beginTransaction());
            }
        });
    }
}