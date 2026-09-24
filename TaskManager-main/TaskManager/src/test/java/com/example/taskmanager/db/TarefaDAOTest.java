package com.example.taskmanager.db;

import com.example.taskmanager.model.Tarefa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TarefaDAOTest {

    private TarefaDAO tarefaDAO;
    private Connection conexaoMestra; // Segura o banco em memória vivo

    @BeforeEach
    public void setUp() throws Exception {
        DatabaseHelper.setBancoEmMemoria(true);

        // Abre e segura essa conexão aberta para impedir que o SQLite destrua o banco!
        conexaoMestra = DatabaseHelper.conectar();

        DatabaseHelper.inicializarBaseDados();
        tarefaDAO = new TarefaDAO();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Fechar a conexão mestra apaga automaticamente o banco em memória
        if (conexaoMestra != null && !conexaoMestra.isClosed()) {
            conexaoMestra.close();
        }
        DatabaseHelper.setBancoEmMemoria(false);
    }

    @Test
    public void testCriacaoEListagemDeTarefa() {
        // Cria uma tarefa de teste
        Tarefa novaTarefa = new Tarefa("Testar JUnit", "Validar banco em memória", false);
        tarefaDAO.inserir(novaTarefa);

        // Busca as tarefas
        List<Tarefa> tarefas = tarefaDAO.listarTodos();

        // Verifica se funcionou
        assertFalse(tarefas.isEmpty(), "A lista de tarefas não deve estar vazia.");
        assertEquals(1, tarefas.size(), "Deve conter exatamente 1 tarefa.");
        assertEquals("Testar JUnit", tarefas.get(0).getTitulo(), "O título deve corresponder ao inserido.");
    }
}