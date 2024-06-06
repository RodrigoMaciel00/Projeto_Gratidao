// UsuarioService.java
package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void addUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.save(usuario);
    }

    public Usuario getUsuarioById(int id) throws SQLException {
        return usuarioDAO.findById(id);
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        return usuarioDAO.findAll();
    }

    public void removeUsuario(int id) throws SQLException {
        usuarioDAO.deleteById(id);
    }
}
