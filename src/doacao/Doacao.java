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
        setData(data);
        setHora(hora);
        setVolume(volume);
        this.triagemId = triagemId;
        this.doadorId = doadorId;
    }

    /**
     * Construtor sem ID (para novos objetos)
     */
    public Doacao(Date data, Time hora, double volume, Long triagemId, Long doadorId) {
        setData(data);
        setHora(hora);
        setVolume(volume);
        this.triagemId = triagemId;
        this.doadorId = doadorId;
    }

    /**
     * Construtor com objetos relacionados (para facilitar uso)
     */
    public Doacao(Date data, Time hora, double volume, Triagem triagem, Doador doador) {
        setData(data);
        setHora(hora);
        setVolume(volume);
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
     * Validação de data - não pode ser futura
     */
    private void setData(Date data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }
        Date hoje = new Date(System.currentTimeMillis());
        if (data.after(hoje)) {
            throw new IllegalArgumentException("Data da doação não pode ser futura.");
        }
        this.data = data;
    }

    /**
     * Validação de hora - não pode ser nula
     */
    private void setHora(Time hora) {
        if (hora == null) {
            throw new IllegalArgumentException("Hora não pode ser nula.");
        }
        this.hora = hora;
    }

    /**
     * Validação de volume - deve estar dentro dos limites permitidos
     */
    private void setVolume(double volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume deve ser maior que zero.");
        }
        if (volume > 500.0) { // Volume máximo típico para doação de sangue
            throw new IllegalArgumentException("Volume excede o máximo permitido (500ml).");
        }
        if (volume < 350.0) { // Volume mínimo típico para doação de sangue
            throw new IllegalArgumentException("Volume abaixo do mínimo recomendado (350ml).");
        }
        this.volume = volume;
    }

    /**
     * Verifica se a doação pode ser realizada baseada na triagem
     */
    public static boolean podeRealizar(Triagem triagem) {
        if (triagem == null) {
            throw new IllegalArgumentException("Triagem não pode ser nula.");
        }
        return triagem.isStatus(); // Triagem deve estar aprovada
    }

    /**
     * Calcula o tempo desde a última doação em dias
     */
    public static long diasDesdeUltimaDoacao(Date ultimaDoacao) {
        if (ultimaDoacao == null) {
            return Long.MAX_VALUE; // Nunca doou antes
        }
        Date hoje = new Date(System.currentTimeMillis());
        return (hoje.getTime() - ultimaDoacao.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * Verifica se o doador pode doar novamente (intervalo mínimo)
     */
    public static boolean podeDoarNovamente(String sexoDoador, Date ultimaDoacao) {
        long diasDesdeUltima = diasDesdeUltimaDoacao(ultimaDoacao);

        // Intervalos mínimos: Homens 60 dias, Mulheres 90 dias
        if ("M".equalsIgnoreCase(sexoDoador) || "MASCULINO".equalsIgnoreCase(sexoDoador)) {
            return diasDesdeUltima >= 60;
        } else if ("F".equalsIgnoreCase(sexoDoador) || "FEMININO".equalsIgnoreCase(sexoDoador)) {
            return diasDesdeUltima >= 90;
        }

        return false; // Sexo não identificado
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