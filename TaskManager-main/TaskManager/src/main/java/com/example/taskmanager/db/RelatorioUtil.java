package com.example.taskmanager.db;

import java.util.List;

public class RelatorioUtil {

    // Método que usa Generics com curinga (?) para aceitar listas de qualquer tipo
    public static void imprimirListaNoConsole(List<?> lista) {
        System.out.println("--- Atualização da Lista ---");
        for (Object item : lista) {
            System.out.println(item.toString());
        }
        System.out.println("----------------------------");
    }
}
