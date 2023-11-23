package com.example.trabalhofinal.placeholder;

import com.example.trabalhofinal.entities.Encomenda;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderContent {
    public static final List<Encomenda> ITEMS = new ArrayList<Encomenda>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Encomenda item) {
        ITEMS.add(item);
    }

    private static Encomenda createPlaceholderItem(int position) {
        Encomenda encomenda = new Encomenda();

        encomenda.setId(Long.parseLong(String.valueOf(position)));
        encomenda.setDescricao("Encomenda: " + position);

        return encomenda;
    }
}