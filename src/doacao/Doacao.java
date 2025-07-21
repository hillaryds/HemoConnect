package doacao;

import java.sql.Date;
import java.sql.Time;
import triagem.Triagem;
import doador.Doador;

/**
 * Model - Classe Doacao
 * Representa a entidade Doacao com dados e regras de negócio
 * Vinculada à triagem realizada e verificada aptidão para doação
 */
public class Doacao {

    private Long id; // ID único da doação (chave primária no banco)
    private Date data;
    private Time hora;
    private double volume;
    private Long triagemId; // Referência à triagem aprovada
    private Long doadorId; // Referência ao doador

    // Objetos relacionados (para consultas)
    private Triagem triagem;
    private Doador doador;

    /**
     * Construtor completo com ID (para objetos vindos do banco)
     */
    public Doacao(Long id, Date data, Time hora, double volume, Long triagemId, Long doadorId) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.volume = volume;
        this.triagemId = triagemId;
        this.doadorId = doadorId;
    }

    /**
     * Construtor sem ID (para novos objetos)
     */
    public Doacao(Date data, Time hora, double volume, Long triagemId, Long doadorId) {
        this.data = data;
        this.hora = hora;
        this.volume = volume;
        this.triagemId = triagemId;
        this.doadorId = doadorId;
    }

    /**
     * Construtor com objetos relacionados (para facilitar uso)
     */
    public Doacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        this.data = data;
        this.hora = hora;
        this.volume = volume;
        setTriagem(triagem);
        setDoador(doador);
        this.triagemId = triagem.getId();
        this.doadorId = doador.getId();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public Time getHora() {
        return hora;
    }

    public double getVolume() {
        return volume;
    }

    public Long getTriagemId() {
        return triagemId;
    }

    public Long getDoadorId() {
        return doadorId;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
        this.triagemId = triagem != null ? triagem.getId() : null;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
        this.doadorId = doador != null ? doador.getId() : null;
    }

    /**
     * Setter simples para data
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Setter simples para hora  
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * Setter simples para volume
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Valida se os dados básicos da doação são válidos
     */
    public boolean validarDados() {
        return data != null && 
               hora != null && 
               volume > 0 && 
               triagemId != null && 
               doadorId != null;
    }

    /**
     * Validação estática de data
     */
    public static boolean validarData(Date data) {
        if (data == null) return false;
        Date hoje = new Date(System.currentTimeMillis());
        return !data.before(hoje);
    }

    /**
     * Validação estática de hora
     */
    public static boolean validarHora(Time hora) {
        if (hora == null) return false;
        
        String horaStr = hora.toString();
        String[] partes = horaStr.split(":");
        
        if (partes.length != 3) return false;
        
        try {
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            int segundos = Integer.parseInt(partes[2]);
            
            return (horas >= 0 && horas <= 23) &&
                   (minutos >= 0 && minutos <= 59) &&
                   (segundos >= 0 && segundos <= 59);
            
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validação estática de volume
     */
    public static boolean validarVolume(double volume) {
        return volume >= 350.0 && volume <= 500.0;
    }

    /**
     * Verifica se a doação pode ser realizada baseada na triagem
     */
    public static boolean podeRealizar(Triagem triagem) {
        if (triagem == null) {
            throw new IllegalArgumentException("Triagem não pode ser nula.");
        }
        return triagem.isStatus();
    }

    /**
     * Calcula o tempo desde a última doação em dias
     */
    public static long diasDesdeUltimaDoacao(Date ultimaDoacao) {
        if (ultimaDoacao == null) {
            return Long.MAX_VALUE;
        }
        Date hoje = new Date(System.currentTimeMillis());
        return (hoje.getTime() - ultimaDoacao.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * Verifica se o doador pode doar novamente (intervalo mínimo)
     */
    public static boolean podeDoarNovamente(String sexoDoador, Date ultimaDoacao) {
        long diasDesdeUltima = diasDesdeUltimaDoacao(ultimaDoacao);

        if ("M".equalsIgnoreCase(sexoDoador) || "MASCULINO".equalsIgnoreCase(sexoDoador)) {
            return diasDesdeUltima >= 60;
        } else if ("F".equalsIgnoreCase(sexoDoador) || "FEMININO".equalsIgnoreCase(sexoDoador)) {
            return diasDesdeUltima >= 90;
        }

        return false;
    }

    /**
     * Retorna descrição completa da doação
     */
    public String getDescricaoCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append("Doação ID: ").append(id != null ? id : "N/A");
        desc.append(" | Data: ").append(data);
        desc.append(" | Hora: ").append(hora);
        desc.append(" | Volume: ").append(volume).append("ml");

        if (doador != null) {
            desc.append(" | Doador: ").append(doador.getNome());
            desc.append(" | Tipo Sanguíneo: ").append(doador.getTipoSanguineo());
        }

        if (triagem != null) {
            desc.append(" | Triagem: ").append(triagem.isStatus() ? "APROVADA" : "REPROVADA");
        }

        return desc.toString();
    }
}