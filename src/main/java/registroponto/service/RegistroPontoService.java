package registroponto.service;

import registroponto.dao.RegistroPontoDAO;
import registroponto.model.RegistroPonto;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class RegistroPontoService {
    private final RegistroPontoDAO registroPontoDAO = new RegistroPontoDAO();

    public String registrarEntrada(String nomeUsuario) throws SQLException {
        if (temEntradaAberta(nomeUsuario)) {
            return "Já existe um registro de entrada aberto.";
        }

        RegistroPonto registro = new RegistroPonto(nomeUsuario, LocalDateTime.now());
        registroPontoDAO.inserirRegistro(registro);

        return "Entrada registrada com sucesso!";
    }

    public String registrarSaida(String nomeUsuario) throws SQLException {
        int idRegistro = registroPontoDAO.buscarRegistroAberto(nomeUsuario);
        if (idRegistro <= 0) {
            return "Nenhuma entrada aberta para registrar saída.";
        }

        RegistroPonto registro = new RegistroPonto(idRegistro, nomeUsuario, null, LocalDateTime.now());
        registroPontoDAO.atualizarRegistro(registro);

        return "Saída registrada com sucesso!";
    }

    public boolean temEntradaAberta(String nomeUsuario) throws SQLException {
        return registroPontoDAO.buscarRegistroAberto(nomeUsuario) != 0;
    }

    public List<RegistroPonto> listarRegistros(String nomeUsuario) throws SQLException {
        return registroPontoDAO.listarRegistros(nomeUsuario);
    }
}
