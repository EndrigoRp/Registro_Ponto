package registroponto.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notificacao {

    private static Image carregarIcone(String nomeArquivo) {
        return new Image(Notificacao.class.getResource("/icons/" + nomeArquivo).toExternalForm(), 32, 32, true, true);
    }

    public static void sucesso(String mensagem) {
        Notifications.create()
                .title("Sucesso")
                .text(mensagem)
                .graphic(new ImageView(carregarIcone("success.png")))
                .hideAfter(Duration.seconds(3))
                .show();
    }

    public static void erro(String mensagem) {
        Notifications.create()
                .title("Erro")
                .text(mensagem)
                .graphic(new ImageView(carregarIcone("error.png")))
                .hideAfter(Duration.seconds(4))
                .showError();
    }

    public static void alerta(String mensagem) {
        Notifications.create()
                .title("Aviso")
                .text(mensagem)
                .graphic(new ImageView(carregarIcone("warning.png")))
                .hideAfter(Duration.seconds(4))
                .showWarning();
    }
}
