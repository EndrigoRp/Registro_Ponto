package registroponto.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {
    private static Connection conexao;

    private static final String CONFIG_FILE = "/database.properties";

    private ConexaoBanco() {}

    public static Connection conectar() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try (InputStream input = ConexaoBanco.class.getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    throw new IOException("Arquivo de configuração não encontrado: " + CONFIG_FILE);
                }

                Properties prop = new Properties();
                prop.load(input);

                String url = prop.getProperty("db.url");
                String usuario = prop.getProperty("db.user");
                String senha = prop.getProperty("db.password");
                String driver = prop.getProperty("db.driver");

                Class.forName(driver);
                conexao = DriverManager.getConnection(url, usuario, senha);
            } catch (IOException | ClassNotFoundException e) {
                throw new SQLException("Erro ao carregar configuração do banco de dados.", e);
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com o banco: " + e.getMessage());
        }
    }
}
