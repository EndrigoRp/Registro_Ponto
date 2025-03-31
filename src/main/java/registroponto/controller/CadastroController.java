package registroponto.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import registroponto.model.Usuario;
import registroponto.service.UsuarioService;
import registroponto.util.Notificacao;

import java.io.IOException;
import java.sql.SQLException;

public class CadastroController {

    @FXML private TextField txtNome;
    @FXML private TextField txtLogin;
    @FXML private PasswordField txtSenha;
    @FXML private ComboBox<String> cbPerfil;
    @FXML private Label lblErro;
    @FXML private ImageView imgFundo;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        cbPerfil.getItems().addAll("ADMIN", "USUARIO");
        cbPerfil.getSelectionModel().select("USUARIO");
        lblErro.setVisible(false);

        try {
            imgFundo.setImage(new Image(getClass().getResource("/images/judo_background.jpg").toExternalForm()));
        } catch (Exception e) {
            System.err.println("Imagem de fundo não encontrada.");
        }
    }

    @FXML
    private void handleCadastrar() {
        String nome = txtNome.getText();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        String perfil = cbPerfil.getValue();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || perfil == null) {
            mostrarErro("Todos os campos são obrigatórios.");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(nome, login, senha, perfil);
            usuarioService.cadastrarUsuario(novoUsuario);
            Notificacao.sucesso("Usuário cadastrado com sucesso!");
            voltarParaLogin();
        } catch (SQLException e) {
            mostrarErro("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void mostrarErro(String msg) {
        lblErro.setText(msg);
        lblErro.setVisible(true);
    }

    private void voltarParaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            Stage stage = (Stage) txtLogin.getScene().getWindow(); // mesma janela
            stage.setScene(scene);
            stage.setTitle("Login");
        } catch (IOException e) {
            Notificacao.erro("Erro ao voltar para o login.");
        }
    }

    @FXML
    private void handleVoltar() {
        voltarParaLogin();
    }
}
