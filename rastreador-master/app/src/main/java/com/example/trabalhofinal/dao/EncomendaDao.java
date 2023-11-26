package com.example.trabalhofinal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.entities.Encomenda;

import java.util.List;

@Dao
public interface EncomendaDao {
    @Insert
    long insertEncomenda(Encomenda encomenda);

    @Delete
    int deleteEncomenda(Encomenda encomenda);

    @Update
    int updateEncomenda(Encomenda encomenda);

    @Query("SELECT * FROM encomendas")
    List<Encomenda> getAllEncomendas();

    @Query("SELECT * FROM encomendas WHERE userId = :userId")
    List<Encomenda> getEncomendasByUserId(long userId);


}
