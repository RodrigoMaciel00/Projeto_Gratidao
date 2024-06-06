// EntradaGratidaoDAO.java
package dao;

import model.EntradaGratidao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaGratidaoDAO {
    private final Connection connection;

    public EntradaGratidaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(EntradaGratidao entrada) throws SQLException {
        String query = "INSERT INTO EntradaGratidao (texto, data, usuario_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entrada.getTexto());
            stmt.setDate(2, entrada.getData());
            stmt.setInt(3, entrada.getUsuarioId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entrada.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<EntradaGratidao> findByUsuarioId(int usuarioId) throws SQLException {
        String query = "SELECT * FROM EntradaGratidao WHERE usuario_id = ?";
        List<EntradaGratidao> entradas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entradas.add(new EntradaGratidao() {{
                        setId(rs.getInt("id"));
                        setTexto(rs.getString("texto"));
                        setData(rs.getDate("data"));
                        setUsuarioId(rs.getInt("usuario_id"));
                    }});
                }
            }
        }
        return entradas;
    }

    public List<EntradaGratidao> findAll() throws SQLException {
        String query = "SELECT * FROM EntradaGratidao";
        List<EntradaGratidao> entradas = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                entradas.add(new EntradaGratidao() {{
                    setId(rs.getInt("id"));
                    setTexto(rs.getString("texto"));
                    setData(rs.getDate("data"));
                    setUsuarioId(rs.getInt("usuario_id"));
                }});
            }
        }
        return entradas;
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM EntradaGratidao WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
