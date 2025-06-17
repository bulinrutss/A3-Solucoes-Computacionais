package dao;

/**
 *
 * @author João Vitor Cardoso de Jesus
 */

import modelo.Categoria;
import modelo.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    /**
     * Insere um novo produto no banco de dados.
     *
     * @param produto Objeto Produto a ser inserido.
     */
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco_unitario, unidade, quantidade_estoque, quantidade_minima, quantidade_maxima, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoria().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os dados de um produto existente no banco de dados.
     *
     * @param produto Objeto Produto com os dados atualizados.
     */
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome=?, preco_unitario=?, unidade=?, quantidade_estoque=?, quantidade_minima=?, quantidade_maxima=?, categoria_id=? WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setInt(7, produto.getCategoria().getId());
            stmt.setInt(8, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exclui um produto do banco de dados pelo seu ID.
     *
     * @param id Identificador do produto a ser excluído.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE id=?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os produtos cadastrados no banco de dados, incluindo informações da categoria relacionada.
     *
     * @return Lista de objetos Produto com os dados dos produtos e suas categorias.
     */
    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cid, c.nome AS cnome, c.tamanho, c.embalagem "
           + "FROM produto p "
           + "JOIN categoria c ON p.categoria_id = c.id "
           + "ORDER BY p.nome";

        try (Connection conn = Conexao.getConexao(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Categoria cat = new Categoria(
                    rs.getInt("cid"),
                    rs.getString("cnome"),
                    rs.getString("tamanho"),
                    rs.getString("embalagem")
                );

                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco_unitario"),
                    rs.getString("unidade"),
                    rs.getInt("quantidade_estoque"),
                    rs.getInt("quantidade_minima"),
                    rs.getInt("quantidade_maxima"),
                    cat
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

