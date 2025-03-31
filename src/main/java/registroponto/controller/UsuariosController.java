package registroponto.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import registroponto.model.Usuario;
import registroponto.service.UsuarioService;
import registroponto.util.Notificacao;

import java.sql.SQLException;
import java.util.List;

public class UsuariosController {

    @FXML private TextField txtNome;
    @FXML private TextField txtLogin;
    @FXML private PasswordField txtSenha;
    @FXML private ComboBox<String> cbPerfil;
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colNome;
    @FXML private TableColumn<Usuario, String> colLogin;
    @FXML private TableColumn<Usuario, String> colPerfil;

    private final UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioSelecionado = null;

    @FXML
    public void initialize() {
        cbPerfil.setItems(FXCollections.observableArrayList("ADMIN", "USUARIO"));

        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colLogin.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLogin()));
        colPerfil.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPerfil()));

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
            tblUsuarios.setItems(FXCollections.observableArrayList(usuarios));
        } catch (SQLException e) {
            Notificacao.erro("Erro ao carregar usuários.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalvar() {
        String nome = txtNome.getText();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        String perfil = cbPerfil.getValue();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || perfil == null) {
            Notificacao.alerta("Todos os campos são obrigatórios.");
            return;
        }

        try {
            Usuario usuario = new Usuario(nome, login, senha, perfil);

            if (usuarioSelecionado == null) {
                usuarioService.cadastrarUsuario(usuario);
                Notificacao.sucesso("Usuário cadastrado com sucesso.");
            } else {
                usuarioService.atualizarUsuario(usuario);
                Notificacao.sucesso("Usuário atualizado com sucesso.");
            }

            limparCampos();
            carregarUsuarios();
        } catch (SQLException e) {
            Notificacao.erro("Erro ao salvar usuário.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExcluir() {
        Usuario selecionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Notificacao.alerta("Selecione um usuário para excluir.");
            return;
        }

        try {
            usuarioService.excluirUsuario(selecionado.getLogin());
            Notificacao.sucesso("Usuário excluído com sucesso.");
            carregarUsuarios();
        } catch (SQLException e) {
            Notificacao.erro("Erro ao excluir usuário.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditar() {
        usuarioSelecionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado != null) {
            txtNome.setText(usuarioSelecionado.getNome());
            txtLogin.setText(usuarioSelecionado.getLogin());
            cbPerfil.setValue(usuarioSelecionado.getPerfil());
        }
    }

    @FXML
    private void handleNovo() {
        limparCampos();
        usuarioSelecionado = null;
    }

    @FXML
    private void handleCancelar() {
        limparCampos();
        usuarioSelecionado = null;
    }

    private void limparCampos() {
        txtNome.clear();
        txtLogin.clear();
        txtSenha.clear();
        cbPerfil.getSelectionModel().clearSelection();
    }
}
