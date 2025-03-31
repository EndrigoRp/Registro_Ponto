package registroponto.service;

import registroponto.dao.UsuarioDAO;
import registroponto.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String login, String senhaDigitada) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorLogin(login);
        if (usuario != null && BCrypt.checkpw(senhaDigitada, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }


    public void cadastrarUsuario(Usuario usuario) throws SQLException {
        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaCriptografada);
        usuarioDAO.inserirUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String novaSenhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(novaSenhaCriptografada);
        usuarioDAO.atualizarUsuario(usuario);
    }

    public List<Usuario> listarTodosUsuarios() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    public void excluirUsuario(String login) throws SQLException {
        usuarioDAO.excluirUsuario(login);
    }

    public Usuario buscarPorLogin(String login) throws SQLException {
        return usuarioDAO.buscarPorLogin(login);
    }
}
