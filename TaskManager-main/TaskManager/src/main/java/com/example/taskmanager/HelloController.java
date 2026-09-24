package com.example.taskmanager;

import com.example.taskmanager.db.DatabaseHelper;
import com.example.taskmanager.db.RelatorioUtil;
import com.example.taskmanager.db.TarefaDAO;
import com.example.taskmanager.model.Tarefa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class HelloController {

    // Componentes da Interface (ligados pelo fx:id do FXML)
    @FXML
    private TableView<Tarefa> tabelaTarefas;
    @FXML
    private TableColumn<Tarefa, Integer> colunaId;
    @FXML
    private TableColumn<Tarefa, String> colunaTitulo;
    @FXML
    private TableColumn<Tarefa, String> colunaDescricao;
    @FXML
    private TableColumn<Tarefa, Boolean> colunaConcluida;
    @FXML
    private TextField campoTitulo;
    @FXML
    private TextField campoDescricao;

    private TarefaDAO tarefaDAO;

    @FXML
    public void initialize() {
        // Garante que a base de dados física está criada ao iniciar
        DatabaseHelper.setBancoEmMemoria(false);
        DatabaseHelper.inicializarBaseDados();
        tarefaDAO = new TarefaDAO();

        // Configura as colunas da tabela para ler os atributos da classe Tarefa
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaConcluida.setCellValueFactory(new PropertyValueFactory<>("concluida"));

        // Formatação visual da coluna de status usando CSS
        colunaConcluida.setCellFactory(coluna -> new TableCell<Tarefa, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                // O JavaFX recicla as células, por isso limpamos as classes CSS antigas primeiro
                getStyleClass().removeAll("status-concluido", "status-pendente");

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Concluído" : "Pendente");

                    // Adiciona a classe CSS correta vinda do styles.css
                    if (item) {
                        getStyleClass().add("status-concluido");
                    } else {
                        getStyleClass().add("status-pendente");
                    }
                }
            }
        });

        carregarTarefas();
    }

    private void carregarTarefas() {
        List<Tarefa> tarefasBanco = tarefaDAO.listarTodos();

        // Chamando o método com curinga (?) para imprimir no console
        RelatorioUtil.imprimirListaNoConsole(tarefasBanco);

        ObservableList<Tarefa> tarefasObservableList = FXCollections.observableArrayList(tarefasBanco);
        tabelaTarefas.setItems(tarefasObservableList);
    }

    @FXML
    protected void adicionarTarefa(ActionEvent event) {
        String titulo = campoTitulo.getText();
        String descricao = campoDescricao.getText();

        if (titulo != null && !titulo.trim().isEmpty()) {
            Tarefa novaTarefa = new Tarefa(titulo, descricao, false);
            tarefaDAO.inserir(novaTarefa);

            campoTitulo.clear();
            campoDescricao.clear();
            carregarTarefas(); // Atualiza a tabela
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("O título da tarefa não pode estar vazio!");
            alert.showAndWait();
        }
    }

    @FXML
    protected void marcarConcluida(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (tarefaSelecionada != null) {
            // Inverte o status atual: se está true vira false, se está false vira true
            tarefaSelecionada.setConcluida(!tarefaSelecionada.isConcluida());

            tarefaDAO.atualizar(tarefaSelecionada); // Atualiza no banco
            carregarTarefas(); // Atualiza a tabela
        }
    }

    @FXML
    protected void removerTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (tarefaSelecionada != null) {
            tarefaDAO.apagar(tarefaSelecionada.getId()); // Remove pelo ID
            carregarTarefas(); // Atualiza a tabela
        }
    }
}