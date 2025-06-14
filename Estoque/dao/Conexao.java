/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.controleestoque.dao;

/**
 * Classe responsável por fornecer a conexão com o banco de dados MySQL.
 * Utiliza os parâmetros definidos para URL, usuário e senha.
 * 
 * Métodos utilitários para obter uma instância de Connection.
 * 
 * @author Ruts
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Connection Conexão ativa com o banco de dados.
     * @throws SQLException Caso ocorra erro ao conectar.
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

