package com.example.taskmanager.db;

import java.util.List;

public interface IGenericDAO<T> {
    void inserir(T entidade);
    void atualizar(T entidade);
    void apagar(int id);
    List<T> listarTodos();
}