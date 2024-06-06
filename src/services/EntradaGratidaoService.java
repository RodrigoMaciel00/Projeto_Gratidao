// EntradaGratidaoService.java
package service;

import dao.EntradaGratidaoDAO;
import model.EntradaGratidao;

import java.sql.SQLException;
import java.util.List;

public class EntradaGratidaoService {
    private EntradaGratidaoDAO entradaGratidaoDAO;

    public EntradaGratidaoService(EntradaGratidaoDAO entradaGratidaoDAO) {
        this.entradaGratidaoDAO = entradaGratidaoDAO;
    }

    public void addEntradaGratidao(EntradaGratidao entrada) throws SQLException {
        entradaGratidaoDAO.save(entrada);
    }

    public List<EntradaGratidao> getEntradasByUsuarioId(int usuarioId) throws SQLException {
        return entradaGratidaoDAO.findByUsuarioId(usuarioId);
    }

    public List<EntradaGratidao> getAllEntradas() throws SQLException {
        return entradaGratidaoDAO.findAll();
    }

    public void removeEntradaGratidao(int id) throws SQLException {
        entradaGratidaoDAO.deleteById(id);
    }
}
