package com.example.trabalhofinal.requests;

import android.content.Context;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RastreioEncomenda {
    private RastreioEncomenda() {
        throw new IllegalStateException("Utility Class");
    }

    public static String rastrearEncomenda(String url, Context context) {
        Request request = new Request.Builder().url(url).build();

        Future<String> future = Executors.newSingleThreadExecutor().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Response response = new OkHttpClient.Builder().build().newCall(request).execute();

                    if (response.isSuccessful()) {
                        String HTML = response.body().string();

                        Document page = Jsoup.parse(HTML);
                        Element cardHeader = page.select("div.card-header").first();

                        return cardHeader.text();
                    } else {
                        // TODO - Tratar a Resposta Incorreta
                    }
                } catch (IOException e) {
                    Toast.makeText(context, "Houve um erro na requisição", Toast.LENGTH_SHORT).show();
                }

                return null;
            }
        });

        String resultadoRastreamento;

        try {
            resultadoRastreamento = future.get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO - Tratar a Exception
            resultadoRastreamento = null;
        }

        return resultadoRastreamento;
    }
}
