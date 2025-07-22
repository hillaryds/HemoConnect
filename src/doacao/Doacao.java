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

    /** ID único da doação (chave primária no banco de dados) */
    private Long id;
    
    /** Data da doação (deve ser do dia atual) */
    private Date data;
    
    /** Hora da doação no formato HH:MM:SS */
    private Time hora;
    
    /** Volume de sangue coletado em mililitros (350-500ml) */
    private double volume;
    
    /** ID da triagem aprovada associada à doação */
    private Long triagemId;
    
    /** ID do doador que realizou a doação */
    private Long doadorId;

    // Objetos relacionados (para consultas e exibição)
    /** Objeto Triagem associado (carregado quando necessário) */
    private Triagem triagem;
    
    /** Objeto Doador associado (carregado quando necessário) */
    private Doador doador;

    /**
     * Construtor completo com ID para doações vindas do banco de dados
     * 
     * @param id ID único da doação (chave primária)
     * @param data Data da doação (deve ser do dia atual)
     * @param hora Hora da doação no formato HH:MM:SS
     * @param volume Volume coletado em ml (350-500ml)
     * @param triagemId ID da triagem aprovada associada
     * @param doadorId ID do doador que fez a doação
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
     * Construtor sem ID para novas doações (antes de salvar no banco)
     * 
     * @param data Data da doação (deve ser do dia atual)
     * @param hora Hora da doação no formato HH:MM:SS
     * @param volume Volume coletado em ml (350-500ml)
     * @param triagemId ID da triagem aprovada associada
     * @param doadorId ID do doador que fez a doação
     */
    public Doacao(Date data, Time hora, double volume, Long triagemId, Long doadorId) {
        this.data = data;
        this.hora = hora;
        this.volume = volume;
        this.triagemId = triagemId;
        this.doadorId = doadorId;
    }

    /**
     * Construtor com objetos relacionados para facilitar o uso
     * Automaticamente extrai os IDs dos objetos fornecidos
     * 
     * @param data Data da doação (deve ser do dia atual)
     * @param hora Hora da doação no formato HH:MM:SS
     * @param volume Volume coletado em ml (350-500ml)
     * @param triagem Objeto Triagem aprovada associada
     * @param doador Objeto Doador que fez a doação
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

    
    /**
     * Obtém o ID único da doação
     * @return ID da doação ou null se ainda não foi salva no banco
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da doação (usado pelo DAO após inserção)
     * @param id ID único da doação
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém a data da doação
     * @return Data da doação
     */
    public Date getData() {
        return data;
    }

    /**
     * Obtém o horário da doação
     * @return Hora da doação no formato HH:MM:SS
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Obtém o volume de sangue coletado
     * @return Volume em mililitros
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Obtém o ID da triagem associada
     * @return ID da triagem aprovada
     */
    public Long getTriagemId() {
        return triagemId;
    }

    /**
     * Obtém o ID do doador
     * @return ID do doador que fez a doação
     */
    public Long getDoadorId() {
        return doadorId;
    }

    /**
     * Obtém o objeto Triagem associado
     * @return Objeto Triagem ou null se não carregado
     */
    public Triagem getTriagem() {
        return triagem;
    }

    /**
     * Obtém o objeto Doador associado
     * @return Objeto Doador ou null se não carregado
     */
    public Doador getDoador() {
        return doador;
    }

    /**
     * Define a triagem associada e atualiza o ID
     * @param triagem Objeto Triagem aprovada
     */
    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
        this.triagemId = triagem != null ? triagem.getId() : null;
    }

    /**
     * Define o doador associado e atualiza o ID
     * @param doador Objeto Doador
     */
    public void setDoador(Doador doador) {
        this.doador = doador;
        this.doadorId = doador != null ? doador.getId() : null;
    }

    /**
     * Define a data da doação
     * @param data Data da doação (deve ser do dia atual)
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Define o horário da doação  
     * @param hora Hora no formato HH:MM:SS
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * Define o volume de sangue coletado
     * @param volume Volume em ml (deve estar entre 350-500ml)
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Valida se os dados básicos da doação estão preenchidos
     * @return true se todos os campos obrigatórios estão preenchidos
     */
    public boolean validarDados() {
        return data != null && 
               hora != null && 
               volume > 0 && 
               triagemId != null && 
               doadorId != null;
    }

    /**
     * Validação estática de data com regra restritiva
     * @param data Data a ser validada
     * @return true apenas se a data for exatamente a data atual
     */
    public static boolean validarData(Date data) {
        if (data == null) return false;
        
        Date hoje = new Date(System.currentTimeMillis());
        String hojeStr = hoje.toString(); // formato YYYY-MM-DD
        Date hojeNormalizada = Date.valueOf(hojeStr);
        
        return data.compareTo(hojeNormalizada) == 0;
    }

    /**
     * Validação estática de hora após conversão SQL
     * @param hora Objeto Time a ser validado
     * @return true se a hora estiver no formato válido (00:00:00-23:59:59)
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
     * Validação crítica de string de hora antes da conversão SQL
     
     * @param horaStr String no formato "HH:MM:SS" a ser validada
     * @return true apenas se todos os componentes estão nos intervalos válidos
     */
    public static boolean validarStringHora(String horaStr) {
        if (horaStr == null || horaStr.trim().isEmpty()) return false;
        
        String[] partes = horaStr.trim().split(":");
        
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
     * Validação de volume de sangue coletado
     * @param volume Volume em mililitros
     * @return true se o volume estiver entre 350ml e 500ml (inclusive)
     */
    public static boolean validarVolume(double volume) {
        return volume >= 350.0 && volume <= 500.0;
    }

    /**
     * Verifica se uma doação pode ser realizada com base no status da triagem
     * @param triagem Objeto Triagem a ser verificado
     * @return true se a triagem foi aprovada (status = true)
     * @throws IllegalArgumentException se a triagem for null
     */
    public static boolean podeRealizar(Triagem triagem) {
        if (triagem == null) {
            throw new IllegalArgumentException("Triagem não pode ser nula.");
        }
        return triagem.isStatus();
    }

    /**
     * Calcula quantos dias se passaram desde a última doação
     * @param ultimaDoacao Data da última doação ou null se primeira doação
     * @return Número de dias desde a última doação ou Long.MAX_VALUE se nunca doou
     */
    public static long diasDesdeUltimaDoacao(Date ultimaDoacao) {
        if (ultimaDoacao == null) {
            return Long.MAX_VALUE; // Nunca doou antes
        }
        Date hoje = new Date(System.currentTimeMillis());
        return (hoje.getTime() - ultimaDoacao.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * Gera uma descrição completa e formatada da doação
     * @return String com descrição completa da doação
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