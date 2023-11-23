package com.example.trabalhofinal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trabalhofinal.dao.EncomendaDao;
import com.example.trabalhofinal.dao.UserDao;
import com.example.trabalhofinal.entities.Encomenda;
import com.example.trabalhofinal.entities.User;

@Database(entities = {User.class, Encomenda.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract EncomendaDao encomendaDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "localizador.db").build();
        }

        return instance;
    }
}
