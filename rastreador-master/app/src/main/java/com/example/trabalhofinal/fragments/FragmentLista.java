package com.example.trabalhofinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.MyItemRecyclerViewAdapter;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.dao.EncomendaDao;
import com.example.trabalhofinal.database.AppDatabase;
import com.example.trabalhofinal.entities.Encomenda;
import com.example.trabalhofinal.interfaces.OnItemClickListener;

import java.util.List;
import java.util.concurrent.Executors;

public class FragmentLista extends Fragment {
    private static final String ARG_PARAM1 = "user-id";
    private static final String ARG_PARAM2 = "encomenda-id";
    private static final String ARG_PARAM3 = "encomenda-codigo";
    private static final String ARG_PARAM4 = "encomenda-descricao";
    private static final String ARG_PARAM5 = "encomenda-userId";

    private List<Encomenda> listaEncomendas;

    private MyItemRecyclerViewAdapter recyclerViewAdapter;
    private FragmentManager fragmentManager;
    private EncomendaDao encomendaDao;
    private long userId;
    private int mColumnCount = 1;

    public FragmentLista(List<Encomenda> listaEncomendas) {
        this.listaEncomendas = listaEncomendas;
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
        return inflater.inflate(R.layout.fragment_lista_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        encomendaDao = AppDatabase.getInstance(requireContext()).encomendaDao();

        recyclerViewAdapter = new MyItemRecyclerViewAdapter(listaEncomendas);

        recyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentEditaCodigo fragmentEditaCodigo = new FragmentEditaCodigo();
                Bundle args = new Bundle();

                args.putLong(ARG_PARAM2, listaEncomendas.get(position).getId());
                args.putString(ARG_PARAM3, listaEncomendas.get(position).getCodigo());
                args.putString(ARG_PARAM4, listaEncomendas.get(position).getDescricao());
                args.putLong(ARG_PARAM5, listaEncomendas.get(position).getUserId());
                fragmentEditaCodigo.setArguments(args);

                MainActivity.replaceFragment(fragmentEditaCodigo, fragmentManager.beginTransaction());
            }
        });

        RecyclerView listaEncomendas = view.findViewById(R.id.listaEncomendas);

        listaEncomendas.setLayoutManager(new LinearLayoutManager(requireContext()));
        listaEncomendas.setAdapter(recyclerViewAdapter);

        ((Button) view.findViewById(R.id.btnCadastrarEncomenda)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCadastraCodigo fragmentCadastraCodigo = new FragmentCadastraCodigo();
                Bundle args = new Bundle();

                args.putLong(ARG_PARAM1, userId);
                fragmentCadastraCodigo.setArguments(args);

                MainActivity.replaceFragment(fragmentCadastraCodigo, fragmentManager.beginTransaction());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Executors.newSingleThreadExecutor().execute(() -> {
            listaEncomendas = encomendaDao.getEncomendasByUserId(userId);

            recyclerViewAdapter.setEncomendas(listaEncomendas);
            recyclerViewAdapter.notifyDataSetChanged();
        });
    }
}