package com.example.taskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // Variável dentro da classe (sem o 'final')
    private static String URL = "jdbc:sqlite:tarefas.db";

    // Método para alternar entre o arquivo físico e a memória RAM para o JUnit
    public static void setBancoEmMemoria(boolean emMemoria) {
        // Usamos um banco nomeado com cache compartilhado para que não seja destruído
        URL = emMemoria ? "jdbc:sqlite:file:teste?mode=memory&cache=shared" : "jdbc:sqlite:tarefas.db";
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBaseDados() {
        String sql = "CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo VARCHAR(100), " +
                "descricao TEXT, " +
                "concluida BOOLEAN" +
                ");";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar base de dados: " + e.getMessage());
        }
    }
}