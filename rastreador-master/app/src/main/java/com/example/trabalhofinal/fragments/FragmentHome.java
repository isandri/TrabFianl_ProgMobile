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

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;

public class FragmentHome extends Fragment {

    private FragmentManager fragmentManager;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = requireActivity().getSupportFragmentManager();
        return inflater.inflate(R.layout.fragmento_home, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Button loginButton = view.findViewById(R.id.btnlogin);
        Button rastreioButton = view.findViewById(R.id.btnrastreio);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(new FragmentLogin(), fragmentManager.beginTransaction());
            }
        });

        rastreioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(new FragmentRastreio(), fragmentManager.beginTransaction());
            }
        });
    }
}