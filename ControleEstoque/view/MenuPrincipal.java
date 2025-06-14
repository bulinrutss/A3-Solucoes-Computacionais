/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.controleestoque.view;

/**
 * Tela principal do sistema de Controle de Estoque.
 * Exibe o menu lateral com as opções de cadastro, movimentação, histórico e relatórios.
 * Ao clicar em cada botão, o painel de conteúdo é atualizado com a respectiva tela.
 * 
 * Também fornece método utilitário para verificar se uma categoria possui produtos vinculados.
 * 
 * @author Marcos Antonio Gasperin
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.projeto.controleestoque.dao.Conexao;

public class MenuPrincipal extends JFrame {

    private JPanel painelConteudo;

    /**
     * Construtor da tela principal.
     * Inicializa os componentes gráficos, menu lateral e listeners dos botões.
     */
    public MenuPrincipal() {
        setTitle("Sistema de Controle de Estoque");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new GridLayout(0, 1, 5, 5));
        painelMenu.setPreferredSize(new Dimension(200, 0));

        JButton btnCategoria = new JButton("Categorias");
        JButton btnProduto = new JButton("Produtos");
        JButton btnMovimentacao = new JButton("Movimentar Estoque");
        JButton btnHistorico = new JButton("Histórico de Movimentações");
        JButton btnRel1 = new JButton("Relatório: Lista de Preços");
        JButton btnRel2 = new JButton("Relatório: Balanço Financeiro");
        JButton btnRel3 = new JButton("Relatório: Abaixo do Mínimo");
        JButton btnRel4 = new JButton("Relatório: Acima do Máximo");
        JButton btnRel5 = new JButton("Relatório: Por Categoria");

        painelMenu.add(btnCategoria);
        painelMenu.add(btnProduto);
        painelMenu.add(btnMovimentacao);
        painelMenu.add(btnHistorico);
        painelMenu.add(btnRel1);
        painelMenu.add(btnRel2);
        painelMenu.add(btnRel3);
        painelMenu.add(btnRel4);
        painelMenu.add(btnRel5);

        painelConteudo = new JPanel();
        painelConteudo.setLayout(new BorderLayout());

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelMenu, painelConteudo);
        split.setDividerLocation(200);
        split.setEnabled(false);

        add(split);

        btnCategoria.addActionListener(e -> mostrarPainel(new CategoriaView().getContentPanel()));
        btnProduto.addActionListener(e -> mostrarPainel(new ProdutoView().getContentPanel()));
        btnMovimentacao.addActionListener(e -> mostrarPainel(new MovimentacaoView().getContentPanel()));
        btnHistorico.addActionListener(e -> mostrarPainel(new HistoricoMovimentacaoView().getContentPanel()));
        btnRel1.addActionListener(e -> mostrarPainel(new RelatorioListaDePrecosView().getContentPanel()));
        btnRel2.addActionListener(e -> mostrarPainel(new RelatorioBalancoFinanceiroView().getContentPanel()));
        btnRel3.addActionListener(e -> mostrarPainel(new RelatorioProdutosAbaixoMinimoView().getContentPanel()));
        btnRel4.addActionListener(e -> mostrarPainel(new RelatorioProdutosAcimaMaximoView().getContentPanel()));
        btnRel5.addActionListener(e -> mostrarPainel(new RelatorioProdutosPorCategoriaView().getContentPanel()));
    }

    /**
     * Troca o painel de conteúdo exibido na área principal da tela.
     * 
     * @param painel Novo painel a ser exibido.
     */
    private void mostrarPainel(JPanel painel) {
        painelConteudo.removeAll();
        painelConteudo.add(painel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    /**
     * Verifica se uma categoria possui produtos vinculados.
     * 
     * @param categoriaId ID da categoria a ser verificada.
     * @return true se houver produtos vinculados, false caso contrário.
     */
    public static boolean categoriaTemProdutos(int categoriaId) {
        String sql = "SELECT COUNT(*) FROM produto WHERE categoria_id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método principal para iniciar a aplicação.
     * Cria e exibe a tela principal do sistema.
     * 
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
