package src.doacao;

import java.sql.Date;
import java.sql.Time;
import src.triagem.Triagem;
import src.doador.Doador;

public class Doacao {
    private Date data;
    private Time hora;
    private double volume;
    private Triagem triagem;
    private Doador doador;

    Doacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        this.data = data;
        this.hora = hora;
        this.volume = volume;
        this.triagem = triagem;
        this.doador = doador;
    }

    public Date getData() { return data; }
    public Time getHora() { return hora; }
    public double getVolume() { return volume; }
    public Triagem getTriagem() { return triagem; }
    public Doador getDoador() { return doador; }

    public static void cadastrarDoacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        if (!triagem.isStatus()) {
            throw new IllegalArgumentException("Triagem não aprovada. Doação cancelada.");
        }
        new Doacao(data, hora, volume, triagem, doador);
        System.out.println("Doação cadastrada com sucesso!");
    }
}
