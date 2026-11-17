package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

/**
 * Substitui o ClienteCSV. Em vez de ler/gravar o arquivo inteiro,
 * cada operação conversa direto com a tabela "cliente".
 */
public class ClienteDAO {

    private static final String COLUNAS = "codigo, nome, telefone, email, senha";

    public List<Cliente> listar() throws SQLException {

        String sql = "SELECT " + COLUNAS + " FROM tabela_cliente ORDER BY codigo";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection con = ConexaoMySQL.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(montarCliente(rs));
            }
        }

        return clientes;
    }

    public Cliente buscarPorId(int codigo) throws SQLException {

        String sql = "SELECT " + COLUNAS + " FROM tabela_cliente WHERE codigo = ?";

        try (Connection con = ConexaoMySQL.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montarCliente(rs) : null;
            }
        }
    }

    // A comparação de e-mail ignora maiúsculas/minúsculas por causa do COLLATE utf8mb4_unicode_ci.
    public Cliente buscarPorEmail(String email) throws SQLException {

        String sql = "SELECT " + COLUNAS + " FROM tabela_cliente WHERE email = ?";

        try (Connection con = ConexaoMySQL.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montarCliente(rs) : null;
            }
        }
    }

    // Insere e devolve o código gerado pelo AUTO_INCREMENT dentro do próprio objeto.
    public void inserir(Cliente cliente) throws SQLException {

        String sql = "INSERT INTO tabela_cliente (nome, telefone, email, senha) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoMySQL.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getSenha());

            ps.executeUpdate();

            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    cliente.setCodigo(chaves.getInt(1));
                }
            }
        }
    }

    // Converte a linha atual do ResultSet em um objeto Cliente.
    private Cliente montarCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("codigo"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha")
        );
    }
}
