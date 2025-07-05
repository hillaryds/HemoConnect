package src;

import java.sql.Date;

public class Triagem{
    private int batimentosPorMinuto;
    private double pressaoArterial;
    private double temperatura;
    private double peso;
    private boolean status;
    private Date date;

    Triagem(int bPM, double pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }

    public int getBatimentosPorMinuto() {
        return batimentosPorMinuto;
    }
    
    public double getPressaoArterial() {
        return pressaoArterial;
    }
    
    public double getTemperatura() {
        return temperatura;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public Date getDate() {
        return date;
    }

    public static void criarTriagem(int batimentosPorMinuto, double pressaoArterial, double temperatura, double peso, boolean status, Date date){
        // ? Pressão arterial: sistólica entre 120-129 mmHg (assumindo que pressaoArterial é a sistólica)
        if( batimentosPorMinuto >= 60 && batimentosPorMinuto <= 100 
        && pressaoArterial >= 120 && pressaoArterial <= 129 
        && temperatura >= 36.0 && temperatura <= 37.2 
        && peso > 49){
            status = true;
        }else{
            status = false;
        }

        Triagem triagem = new Triagem(batimentosPorMinuto, pressaoArterial, temperatura, peso, status, date);
        //TODO: esta faltando a logica aqui e integrar com o banco
    }
    
    
}