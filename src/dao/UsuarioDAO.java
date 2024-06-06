// UsuarioDAO.java
package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Usuario findById(int id) throws SQLException {
        String query = "SELECT * FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario() {{
                        setId(rs.getInt("id"));
                        setNome(rs.getString("nome"));
                        setEmail(rs.getString("email"));
                        setSenha(rs.getString("senha"));
                    }};
                }
            }
        }
        return null;
    }

    public List<Usuario> findAll() throws SQLException {
        String query = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                usuarios.add(new Usuario() {{
                    setId(rs.getInt("id"));
                    setNome(rs.getString("nome"));
                    setEmail(rs.getString("email"));
                    setSenha(rs.getString("senha"));
                }});
            }
        }
        return usuarios;
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM Usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
