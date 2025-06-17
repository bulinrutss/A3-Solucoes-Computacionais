/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.projeto.controleestoque;
import com.projeto.controleestoque.view.MenuPrincipal;
import javax.swing.SwingUtilities;

/**
 * Classe principal do sistema Controle de Estoque.
 * Responsável por inicializar a aplicação e exibir a interface gráfica principal.
 * 
 * O método main utiliza SwingUtilities para garantir que a interface seja criada na thread de eventos do Swing.
 * 
 * @author Marcos Antonio Gasperin
 */
public class ControleEstoque {

    /**
     * Método principal que inicia a aplicação.
     * Cria e exibe a janela do MenuPrincipal.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
