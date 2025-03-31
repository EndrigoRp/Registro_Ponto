package registroponto.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import registroponto.model.Usuario;
import registroponto.service.UsuarioService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblErro;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void handleLogin() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Preencha todos os campos!");
            lblErro.setVisible(true);
            return;
        }

        try {
            Usuario usuarioLogado = usuarioService.autenticar(usuario, senha);

            if (usuarioLogado != null) {
                abrirTelaPrincipal(usuarioLogado);
            } else {
                lblErro.setText("Usuário ou senha inválidos!");
                lblErro.setVisible(true);
            }
        } catch (SQLException e) {
            lblErro.setText("Erro ao conectar com o banco de dados.");
            lblErro.setVisible(true);
            e.printStackTrace();
        }
    }

    private void abrirTelaPrincipal(Usuario usuarioLogado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
            Parent root = loader.load();

            PrincipalController controller = loader.getController();
            controller.setUsuario(usuarioLogado);

            Stage stage = (Stage) txtUsuario.getScene().getWindow(); // mesma janela
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro de Ponto - Judô");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Cadastro.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtUsuario.getScene().getWindow(); // mesma janela
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Cadastro de Usuário");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
