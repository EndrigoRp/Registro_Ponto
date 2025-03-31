package registroponto.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import registroponto.model.RegistroPonto;
import registroponto.model.Usuario;
import registroponto.service.RegistroPontoService;
import registroponto.util.DataUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PrincipalController {

    @FXML private Label lblUsuario;
    @FXML private Label lblHoraData;
    @FXML private Label lblIndicador;
    @FXML private Button btnPonto;
    @FXML private TableView<RegistroPonto> tblRegistros;
    @FXML private TableColumn<RegistroPonto, String> colEntrada;
    @FXML private TableColumn<RegistroPonto, String> colSaida;
    @FXML private TableColumn<RegistroPonto, String> colDuracao;
    @FXML private ImageView imgFundo;

    private Usuario usuario;
    private final RegistroPontoService registroService = new RegistroPontoService();

    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEntrada() != null ? DataUtils.formatarDataHora(cellData.getValue().getEntrada()) : ""
                )
        );

        colSaida.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getSaida() != null ? DataUtils.formatarDataHora(cellData.getValue().getSaida()) : ""
                )
        );

        colDuracao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDuracao()
                )
        );
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUsuario.setText("Usuário: " + usuario.getNome());
        atualizarHora();
        atualizarRegistros();
    }

    @FXML
    private void handlePonto() {
        try {
            boolean temEntradaAberta = registroService.temEntradaAberta(usuario.getNome());
            String mensagem;

            if (temEntradaAberta) {
                mensagem = registroService.registrarSaida(usuario.getNome());
            } else {
                mensagem = registroService.registrarEntrada(usuario.getNome());
            }

            atualizarRegistros();
            atualizarHora();
            lblIndicador.setText(temEntradaAberta ? "Status: Fora" : "Status: Trabalhando");
            btnPonto.setText(temEntradaAberta ? "Registrar Entrada" : "Registrar Saída");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarHora() {
        lblHoraData.setText(DataUtils.formatarDataHora(java.time.LocalDateTime.now()));
    }

    private void atualizarRegistros() {
        try {
            List<RegistroPonto> registros = registroService.listarRegistros(usuario.getNome());
            tblRegistros.getItems().setAll(registros);

            boolean trabalhando = registroService.temEntradaAberta(usuario.getNome());
            lblIndicador.setText(trabalhando ? "Status: Trabalhando" : "Status: Fora");
            btnPonto.setText(trabalhando ? "Registrar Saída" : "Registrar Entrada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);

            Stage stage = (Stage) lblUsuario.getScene().getWindow(); // reusa a janela
            stage.setScene(scene);
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
