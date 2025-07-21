package triagem;

import java.sql.Date;

/**
 * Model - Classe Triagem
 * Representa a entidade Triagem com dados e regras de negócio
 */
public class Triagem {
    /** ID único da triagem (chave primária no banco de dados) */
    private Long id;
    
    /** Batimentos cardíacos por minuto (critério: 60-100 bpm) */
    private int batimentosPorMinuto;
    
    /** Pressão arterial no formato "sistólica/diastólica" (critério: 120/80-129/84) */
    private String pressaoArterial;
    
    /** Temperatura corporal em graus Celsius (critério: 36.0°-37.2°) */
    private double temperatura;
    
    /** Peso do doador em quilogramas (critério mínimo: 50kg) */
    private double peso;
    
    /** Status da triagem - true se aprovada, false se reprovada */
    private boolean status;
    
    /** Data de realização da triagem */
    private Date date;

    /**
     * Construtor completo para triagem com ID (para objetos vindos do banco de dados)
     * 
     * @param id ID único da triagem
     * @param bPM Batimentos por minuto (60-100 bpm)
     * @param pressaoArt Pressão arterial no formato "sistólica/diastólica" 
     * @param temperatura Temperatura corporal em °C (36.0-37.2)
     * @param peso Peso em kg (mínimo 50kg)
     * @param status Status da triagem (true=aprovada, false=reprovada)
     * @param date Data de realização da triagem
     */
    public Triagem(Long id, int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.id = id;
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }
    
    /**
     * Construtor sem ID para novas triagens (antes de salvar no banco)
     * 
     * @param bPM Batimentos por minuto (60-100 bpm)
     * @param pressaoArt Pressão arterial no formato "sistólica/diastólica"
     * @param temperatura Temperatura corporal em °C (36.0-37.2)
     * @param peso Peso em kg (mínimo 50kg)
     * @param status Status da triagem (true=aprovada, false=reprovada)
     * @param date Data de realização da triagem
     */
    public Triagem(int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }

    /**
     * Obtém o ID único da triagem
     * @return ID da triagem ou null se ainda não foi salva no banco
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Define o ID da triagem (usado pelo DAO após inserção no banco)
     * @param id ID único da triagem
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém os batimentos cardíacos por minuto
     * @return Batimentos por minuto
     */
    public int getBatimentosPorMinuto() {
        return batimentosPorMinuto;
    }
    
    /**
     * Define os batimentos cardíacos por minuto
     * @param batimentosPorMinuto Batimentos por minuto (critério: 60-100 bpm)
     */
    public void setBatimentosPorMinuto(int batimentosPorMinuto) {
        this.batimentosPorMinuto = batimentosPorMinuto;
    }
    
    /**
     * Obtém a pressão arterial
     * @return Pressão arterial no formato "sistólica/diastólica"
     */
    public String getPressaoArterial() {
        return pressaoArterial;
    }
    
    /**
     * Define a pressão arterial
     * @param pressaoArterial Pressão no formato "sistólica/diastólica" (ex: "120/80")
     */
    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }
    
    /**
     * Obtém a temperatura corporal
     * @return Temperatura em graus Celsius
     */
    public double getTemperatura() {
        return temperatura;
    }
    
    /**
     * Define a temperatura corporal
     * @param temperatura Temperatura em °C (critério: 36.0-37.2)
     */
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
    
    /**
     * Obtém o peso do doador
     * @return Peso em quilogramas
     */
    public double getPeso() {
        return peso;
    }
    
    /**
     * Define o peso do doador
     * @param peso Peso em kg (critério mínimo: 50kg)
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    /**
     * Verifica se a triagem foi aprovada
     * @return true se aprovada, false se reprovada
     */
    public boolean isStatus() {
        return status;
    }
    
    /**
     * Define o status da triagem
     * @param status true para aprovada, false para reprovada
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    /**
     * Obtém a data de realização da triagem
     * @return Data da triagem
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Define a data de realização da triagem
     * @param date Data da triagem
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Verifica se os critérios médicos para triagem são atendidos
     * @param batimentosPorMinuto Frequência cardíaca do doador
     * @param pressaoArterial Pressão arterial no formato "sistólica/diastólica"
     * @param temperatura Temperatura corporal em graus Celsius
     * @param peso Peso do doador em quilogramas
     * @return true se todos os critérios forem atendidos, false caso contrário
     */
    public static boolean verificarCriteriosTriagem(int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso) {   
        if (batimentosPorMinuto < 60 || batimentosPorMinuto > 100) {
            return false;
        }
        
        if (temperatura < 36.0 || temperatura > 37.2) {
            return false;
        }
        
        if (peso < 50.0) {
            return false;
        }
        
        if (!validarPressaoArterial(pressaoArterial)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Valida se a pressão arterial está dentro dos parâmetros aceitáveis
     * @param pressaoArterial String no formato "sistólica/diastólica" (ex: "120/80")
     * @return true se a pressão estiver entre 120/80 e 129/84, false caso contrário
     */
    private static boolean validarPressaoArterial(String pressaoArterial) {
        try {
            String[] partes = pressaoArterial.split("/");
            if (partes.length != 2) {
                return false;
            }
            
            int sistolica = Integer.parseInt(partes[0].trim());
            int diastolica = Integer.parseInt(partes[1].trim());
            
            return sistolica >= 120 && sistolica <= 129 && diastolica >= 80 && diastolica <= 84;
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    /**
     * Gera uma descrição detalhada do resultado da triagem
     * @return String descritiva do resultado da triagem com detalhes dos critérios
     */
    public String getDescricaoTriagem() {
        if (this.status) {
            return "Triagem APROVADA - Doador apto para doação";
        } else {
            StringBuilder problemas = new StringBuilder("Triagem REPROVADA - Critérios não atendidos: ");
            
            if (batimentosPorMinuto < 60 || batimentosPorMinuto > 100) {
                problemas.append("Batimentos por minuto fora do intervalo (60-100); ");
            }
            
            if (temperatura < 36.0 || temperatura > 37.2) {
                problemas.append("Temperatura fora do intervalo (36.0°-37.2°); ");
            }
            
            if (peso < 50.0) {
                problemas.append("Peso abaixo do mínimo (50kg); ");
            }
            
            if (!validarPressaoArterial(pressaoArterial)) {
                problemas.append("Pressão arterial fora do intervalo (120/80-129/84); ");
            }
            
            return problemas.toString();
        }
    }
}