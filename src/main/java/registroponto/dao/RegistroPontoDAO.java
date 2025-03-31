package registroponto.dao;

import registroponto.model.RegistroPonto;
import registroponto.util.ConexaoBanco;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroPontoDAO {

    public void inserirRegistro(RegistroPonto registro) throws SQLException {
        String sql = "INSERT INTO registros (nome_usuario, entrada) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, registro.getNomeUsuario());
            stmt.setTimestamp(2, Timestamp.valueOf(registro.getEntrada()));
            stmt.executeUpdate();
        }
    }

    public void atualizarRegistro(RegistroPonto registro) throws SQLException {
        String sql = "UPDATE registros SET saida = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(registro.getSaida()));
            stmt.setInt(2, registro.getId());
            stmt.executeUpdate();
        }
    }

    public int buscarRegistroAberto(String nomeUsuario) throws SQLException {
        String sql = "SELECT id FROM registros WHERE nome_usuario = ? AND saida IS NULL LIMIT 1";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return 0;
    }

    public List<RegistroPonto> listarRegistros(String nomeUsuario) throws SQLException {
        List<RegistroPonto> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros WHERE nome_usuario = ? ORDER BY entrada DESC";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                registros.add(new RegistroPonto(
                        rs.getInt("id"),
                        rs.getString("nome_usuario"),
                        rs.getTimestamp("entrada").toLocalDateTime(),
                        rs.getTimestamp("saida") != null ? rs.getTimestamp("saida").toLocalDateTime() : null
                ));
            }
        }
        return registros;
    }
}
