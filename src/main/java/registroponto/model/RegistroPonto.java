package registroponto.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class RegistroPonto {
    private int id;
    private String nomeUsuario;
    private LocalDateTime entrada;
    private LocalDateTime saida;


    public RegistroPonto() {
    }

    public RegistroPonto(String nomeUsuario, LocalDateTime entrada) {
        this.nomeUsuario = nomeUsuario;
        this.entrada = entrada;
    }

    public RegistroPonto(int id, String nomeUsuario, LocalDateTime entrada, LocalDateTime saida) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.entrada = entrada;
        this.saida = saida;
    }

    public String getDuracao() {
        if (entrada != null && saida != null) {
            Duration duracao = Duration.between(entrada, saida);
            long horas = duracao.toHours();
            long minutos = duracao.toMinutes() % 60;
            long segundos = duracao.toSeconds() % 60;
            return String.format("%02dh %02dm %02ds", horas, minutos, segundos);
        }
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroPonto that = (RegistroPonto) o;
        return id == that.id && nomeUsuario.equals(that.nomeUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeUsuario);
    }

    @Override
    public String toString() {
        return "RegistroPonto{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", entrada=" + entrada +
                ", saida=" + (saida != null ? saida : "Ainda no expediente") +
                '}';
    }
}
