/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package themis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Diego
 */
public class DataBaseConnection {

    String url = "jdbc:postgresql://localhost:5432/Themis";
    String username = "postgres";
    String password = "210198";

    ResultSet resultSet;

    public Connection PostgreSQLConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
        }
        return connection;
    }

    public void consultarDados(Connection connection, String tableName) {
        String query = String.format("SELECT * FROM Lembrete");
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int numLembrete = resultSet.getInt("numLembrete");
                int numProcesso = resultSet.getInt("numProcesso");
                int numAudiencia = resultSet.getInt("numAudiencia");
                String dataAudiencia = resultSet.getString("dataAudiencia");
                String tribunal = resultSet.getString("tribunal");
                String vara = resultSet.getString("vara");
                String nomeEmpregador = resultSet.getString("nomeEmpregador");
                String nomeAdvogado = resultSet.getString("nomeAdvogado");
                String categoria = resultSet.getString("categoria");
                String assunto = resultSet.getString("assunto");
                String status = resultSet.getString("status");

                System.out.println(String.format("numProcesso: %d | numAudiencia %d | Data Audiencia: %s| tribunal: %s | vara: %s | nomeEmpregador: %s | nomeAdvogado: %s | categoria: %s | assunto: %s | status: %s ", numLembrete, numAudiencia, dataAudiencia, tribunal, vara, nomeEmpregador, nomeAdvogado, categoria, assunto, status));

                System.out.println(
                        String.format("Seu proceso numero %d está marcada para data: %s", numAudiencia, dataAudiencia));
                System.out.println(String.format("O local da sua audiência no tribunal %s na vara %s no Órgão Judicial %s ",
                        tribunal, vara, tribunal));
                System.out.println(
                        String.format("O cliente %s será defendido pelo advogado %s", nomeEmpregador, nomeAdvogado));
                System.out.println(String.format("O status da audiência é %s", status));

            }
        } catch (SQLException e) {
            System.err.println("Erro ao ler dados: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
        }
    }

    public void inserirLembrete(Connection connection, int numProcesso, int numAudiencia, String dataAudiencia, String tribunal, String vara, String nomeEmpregador, String nomeAdvogado, String categoria, String assunto, String status) {
        String query = "INSERT INTO Lembrete (numProcesso, numAudiencia, dataAudiencia, tribunal, vara, nomeEmpregador, nomeAdvogado, categoria, assunto, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, numProcesso);
            preparedStatement.setInt(2, numAudiencia);
            preparedStatement.setString(3, dataAudiencia);
            preparedStatement.setString(4, tribunal);
            preparedStatement.setString(5, vara);
            preparedStatement.setString(6, nomeEmpregador);
            preparedStatement.setString(7, nomeAdvogado);
            preparedStatement.setString(8, categoria);
            preparedStatement.setString(9, assunto);
            preparedStatement.setString(10, status);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " linhas afetadas.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados: " + e.getMessage());
        }
    }

}
