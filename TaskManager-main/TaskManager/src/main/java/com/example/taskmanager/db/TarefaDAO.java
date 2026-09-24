package com.example.taskmanager.db;

import com.example.taskmanager.model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements IGenericDAO<Tarefa> {

    @Override
    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas(titulo, descricao, concluida) VALUES(?,?,?)";
        try (Connection conn = DatabaseHelper.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tarefa.getTitulo());
            pstmt.setString(2, tarefa.getDescricao());
            pstmt.setBoolean(3, tarefa.isConcluida());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tarefa: " + e.getMessage());
        }
    }

    @Override
    public List<Tarefa> listarTodos() {
        String sql = "SELECT * FROM tarefas";
        List<Tarefa> listaTarefas = new ArrayList<>();

        try (Connection conn = DatabaseHelper.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarefa t = new Tarefa(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getBoolean("concluida")
                );
                listaTarefas.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return listaTarefas;
    }

    @Override
    public void atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, concluida = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tarefa.getTitulo());
            pstmt.setString(2, tarefa.getDescricao());
            pstmt.setBoolean(3, tarefa.isConcluida());
            pstmt.setInt(4, tarefa.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    @Override
    public void apagar(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = DatabaseHelper.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao apagar tarefa: " + e.getMessage());
        }
    }
}