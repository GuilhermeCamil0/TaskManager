package com.example.taskmanager.model;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private boolean concluida;

    // Construtor vazio
    public Tarefa() {
    }

    // Construtor completo (usado ao ler da base de dados)
    public Tarefa(int id, String titulo, String descricao, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    // Construtor sem ID (usado ao criar uma nova tarefa, pois a base de dados gera o ID)
    public Tarefa(String titulo, String descricao, boolean concluida) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return this.titulo + (this.concluida ? " (Concluída)" : " (Pendente)");
    }
}
