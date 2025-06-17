package com.projeto.controleestoque.dao;

import com.projeto.controleestoque.modelo.Movimentacao;
import com.projeto.controleestoque.modelo.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    /**
     * Registra uma movimentação de entrada ou saída de produto no banco de dados.
     * Atualiza também a quantidade em estoque do produto relacionado.
     *
     * @param mov Objeto Movimentacao contendo os dados da movimentação e do produto.
     */
    public void registrarMovimento(Movimentacao mov) {
        String sql = "INSERT INTO movimentacao (tipo, quantidade, produto_id) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mov.getTipo());
            stmt.setInt(2, mov.getQuantidade());
            stmt.setInt(3, mov.getProduto().getId());
            stmt.executeUpdate();
            
            int novaQtd = mov.getTipo().equals("ENTRADA") ?
                    mov.getProduto().getQuantidadeEstoque() + mov.getQuantidade() :
                    mov.getProduto().getQuantidadeEstoque() - mov.getQuantidade();

            String update = "UPDATE produto SET quantidade_estoque=? WHERE id=?";
            try (PreparedStatement stmt2 = conn.prepareStatement(update)) {
                stmt2.setInt(1, novaQtd);
                stmt2.setInt(2, mov.getProduto().getId());
                stmt2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todas as movimentações cadastradas no banco de dados, incluindo informações do produto relacionado.
     *
     * @return Lista de objetos Movimentacao com os dados das movimentações e produtos.
     */
    public List<Movimentacao> listarTodas() {
        List<Movimentacao> lista = new ArrayList<>();

        String sql = "SELECT m.id AS m_id, m.tipo, m.quantidade, m.data_movimento, "
                   + "p.id AS p_id, p.nome, p.preco_unitario, p.unidade, p.quantidade_estoque, "
                   + "p.quantidade_minima, p.quantidade_maxima "
                   + "FROM movimentacao m "
                   + "JOIN produto p ON m.produto_id = p.id "
                   + "ORDER BY m.data_movimento DESC";

        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("p_id"),
                    rs.getString("nome"),
                    rs.getDouble("preco_unitario"),
                    rs.getString("unidade"),
                    rs.getInt("quantidade_estoque"),
                    rs.getInt("quantidade_minima"),
                    rs.getInt("quantidade_maxima"),
                    null
                );

                Movimentacao mov = new Movimentacao(
                    rs.getInt("m_id"),
                    rs.getString("tipo"),
                    rs.getInt("quantidade"),
                    rs.getTimestamp("data_movimento").toLocalDateTime(),
                    produto
                );

                lista.add(mov);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
