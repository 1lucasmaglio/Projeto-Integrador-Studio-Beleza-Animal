package dao;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) throws Exception {
        try (Connection con = ConexaoMySQL.obterConexao()) {
            System.out.println("Conectado em: " + con.getMetaData().getURL());
        }
    }
}