package src.doacao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import src.triagem.Triagem;
import src.doador.Doador;

public class Doacao {
    
    private static long idCounter = 1;                 
    private static List<Doacao> doacoes = new ArrayList<>();  
  

    private long id;
    private Date data;
    private Time hora;
    private double volume;
    private Triagem triagem;
    private Doador doador;

    public Doacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        this.id = idCounter++;      
        setData(data);              
        setVolume(volume);          
        this.triagem = triagem;
        this.doador = doador;
    }

    public long getId() { return id; }
    public Date getData() { return data; }
    public Time getHora() { return hora; }
    public double getVolume() { return volume; }
    public Triagem getTriagem() { return triagem; }
    public Doador getDoador() { return doador; }

    // * Validação de Parâmetros: data e volume *
    private void setData(Date data) {
        Date hoje = new Date(System.currentTimeMillis());
        if (data.after(hoje)) {
            throw new IllegalArgumentException("Data da doação não pode ser futura.");
        }
        this.data = data;
    }

    private void setHora(Time hora) {
        this.hora = hora;
    }

    private void setVolume(double volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume deve ser maior que zero.");
        }
        this.volume = volume;
    }

    public static Doacao cadastrarDoacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        if (!triagem.isStatus()) {
            throw new IllegalArgumentException("Triagem não aprovada. Doação cancelada.");
        }
        Doacao doacao = new Doacao(data, hora, volume, triagem, doador);
        doacoes.add(doacao);
        System.out.println("Doação cadastrada com sucesso! ID: " + doacao.getId());
        return doacao;
    }

    
    public static List<Doacao> listarDoacoes() {
        return new ArrayList<>(doacoes);
    }

    
    public static void atualizarDoacao(long id, Date novaData, Time novaHora, double novoVolume) {
        for (Doacao d : doacoes) {
            if (d.getId() == id) {
                d.setData(novaData);
                d.setHora(novaHora);
                d.setVolume(novoVolume);
                System.out.println("Doação atualizada com sucesso! ID: " + id);
                return;
            }
        }
        throw new IllegalArgumentException("Doação com ID " + id + " não encontrada.");
    }

    public static void removerDoacao(long id) {
        Doacao toRemove = null;
        for (Doacao d : doacoes) {
            if (d.getId() == id) {
                toRemove = d;
                break;
            }
        }
        if (toRemove != null) {
            doacoes.remove(toRemove);
            System.out.println("Doação removida com sucesso! ID: " + id);
        } else {
            throw new IllegalArgumentException("Doação com ID " + id + " não encontrada.");
        }
    }
    
}