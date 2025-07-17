package src.triagem;

import java.sql.Date;

/**
 * Model - Classe Triagem
 * Representa a entidade Triagem com dados e regras de negócio
 */
public class Triagem {
    private Long id; // ID único da triagem (chave primária no banco)
    private int batimentosPorMinuto;
    private String pressaoArterial;
    private double temperatura;
    private double peso;
    private boolean status;
    private Date date;

    public Triagem(Long id, int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.id = id;
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }
    
    public Triagem(int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public int getBatimentosPorMinuto() {
        return batimentosPorMinuto;
    }
    
    public void setBatimentosPorMinuto(int batimentosPorMinuto) {
        this.batimentosPorMinuto = batimentosPorMinuto;
    }
    
    public String getPressaoArterial() {
        return pressaoArterial;
    }
    
    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }
    
    public double getTemperatura() {
        return temperatura;
    }
    
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

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