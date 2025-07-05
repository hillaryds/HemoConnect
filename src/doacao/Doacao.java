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

    public static void criarDoacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        if (!triagem.isStatus()) {
            System.out.println("Triagem não aprovada. Doação cancelada.");
            return;
        }
        Doacao doacao = new Doacao(data, hora, volume, triagem, doador);
        // TODO: implementar lógica de gravação
        System.out.println("Doação criada com sucesso: " + doacao);
    }
}
