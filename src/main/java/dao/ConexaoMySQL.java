package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza os dados de conexão com o MySQL.
 * Todos os DAOs chamam obterConexao() em vez de repetir a URL e a senha.
 */
public class ConexaoMySQL {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:mysql://localhost:3306/studio_animal"
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo");

    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "root");
    private static final String SENHA = System.getenv().getOrDefault("DB_PASSWORD", "RS930wst#@");

    private ConexaoMySQL() {
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
