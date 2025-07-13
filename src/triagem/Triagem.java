package src.triagem;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Triagem{
    //! Lista estática temporária para armazenar triagens (será substituída por PostgreSQL)
    private static List<Triagem> triagens = new ArrayList<>();
    
    private Long id; // ID único da triagem (chave primária no banco)
    private int batimentosPorMinuto;
    private String pressaoArterial;
    private double temperatura;
    private double peso;
    private boolean status;
    private Date date;

    Triagem(Long id, int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
        this.id = id;
        this.batimentosPorMinuto = bPM;
        this.pressaoArterial = pressaoArt;
        this.temperatura = temperatura;
        this.peso = peso;
        this.status = status;
        this.date = date;
    }
    
    //?Construtor sem ID (para novas triagens antes da inserção no banco)
    Triagem(int bPM, String pressaoArt, double temperatura, double peso, boolean status, Date date){
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

    public static void criarTriagem(int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso, Date date){
        boolean status = verificarCriteriosTriagem(batimentosPorMinuto, pressaoArterial, temperatura, peso);
        
    
        Triagem triagem = new Triagem(batimentosPorMinuto, pressaoArterial, temperatura, peso, status, date);
        
        //!Temporário: Adiciona à lista (substituir por inserção no PostgreSQL)
        triagens.add(triagem);
        
        System.out.println("Triagem criada com sucesso!");
        
        // TODO: Implementar persistência no PostgreSQL
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


    public void exibirTriagem() {
        System.out.println("=== DADOS DA TRIAGEM ===");
        System.out.println("Data: " + this.date);
        System.out.println("Batimentos por minuto: " + this.batimentosPorMinuto + " bpm");
        System.out.println("Pressão arterial: " + this.pressaoArterial + " mmHg");
        System.out.println("Temperatura: " + this.temperatura + "°C");
        System.out.println("Peso: " + this.peso + " kg");
        System.out.println("Status: " + (this.status ? "APROVADO" : "REPROVADO"));
        System.out.println("Descrição: " + getDescricaoTriagem());
        System.out.println("========================");
    }

    
    public static List<Triagem> listarTriagemDate(Date date) {
        //! Temporário: substituir por consulta SQL
        List<Triagem> triagensData = new ArrayList<>();
        
        for (Triagem triagem : triagens) {
            if (triagem.getDate().equals(date)) {
                triagensData.add(triagem);
            }
        }
        
        return triagensData;
        
        // TODO: Implementar consulta no PostgreSQL
    }

    
    public static void totalDeTriagemDia() {
        Date hoje = new Date(System.currentTimeMillis());
        
        //! Temporário: substituir por consulta
        List<Triagem> triagensHoje = listarTriagemDate(hoje);
        
        System.out.println("=== TOTAL DE TRIAGENS DO DIA ===");
        System.out.println("Data: " + hoje);
        System.out.println("Total de triagens: " + triagensHoje.size());
        
        int aprovadas = 0;
        int reprovadas = 0;
        
        for (Triagem triagem : triagensHoje) {
            if (triagem.isStatus()) {
                aprovadas++;
            } else {
                reprovadas++;
            }
        }
        
        System.out.println("Triagens aprovadas: " + aprovadas);
        System.out.println("Triagens reprovadas: " + reprovadas);
        System.out.println("===============================");
        
        // TODO: Implementar consulta SQL otimizada no PostgreSQL
    }

    public static void totalDeTriagemMes() {
        Calendar cal = Calendar.getInstance();
        int mesAtual = cal.get(Calendar.MONTH);
        int anoAtual = cal.get(Calendar.YEAR);
        
        //! Temporário: substituir por consulta SQL
        int totalMes = 0;
        int aprovadas = 0;
        int reprovadas = 0;
        
        for (Triagem triagem : triagens) {
            cal.setTime(triagem.getDate());
            int mesTriagem = cal.get(Calendar.MONTH);
            int anoTriagem = cal.get(Calendar.YEAR);
            
            if (mesTriagem == mesAtual && anoTriagem == anoAtual) {
                totalMes++;
                if (triagem.isStatus()) {
                    aprovadas++;
                } else {
                    reprovadas++;
                }
            }
        }
        
        System.out.println("=== TOTAL DE TRIAGENS DO MÊS ===");
        System.out.println("Mês/Ano: " + (mesAtual + 1) + "/" + anoAtual);
        System.out.println("Total de triagens: " + totalMes);
        System.out.println("Triagens aprovadas: " + aprovadas);
        System.out.println("Triagens reprovadas: " + reprovadas);
        System.out.println("===============================");
        
        // TODO: Implementar consulta SQL otimizada no PostgreSQL
    }

    
    public void atualizarTriagem(int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso) {
        this.batimentosPorMinuto = batimentosPorMinuto;
        this.pressaoArterial = pressaoArterial;
        this.temperatura = temperatura;
        this.peso = peso;
        
        // Recalcula o status baseado nos novos dados
        this.status = verificarCriteriosTriagem(batimentosPorMinuto, pressaoArterial, temperatura, peso);
        
        System.out.println("Triagem atualizada com sucesso!");
        System.out.println("Novo status: " + (this.status ? "APROVADO" : "REPROVADO"));
        
        // TODO: Implementar atualização no PostgreSQL
    }

    
    public void removerTriagem() {
        //! Temporário: Remove da lista em memória (substituir por exclusão no PostgreSQL)
        if (triagens.remove(this)) {
            System.out.println("Triagem removida com sucesso!");
        } else {
            System.out.println("Erro: Triagem não encontrada na lista.");
        }
        
        // TODO: Implementar exclusão no PostgreSQL
    }


    public static List<Triagem> obterTodasTriagens() {
        //!Temporário
        return new ArrayList<>(triagens);
        
        // TODO: Implementar consulta no PostgreSQL
    }


}