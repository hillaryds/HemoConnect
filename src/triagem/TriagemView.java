package src.triagem;

import java.sql.Date;
import java.util.List;


/**
 * View - Classe TriagemView
 * Responsável pela interface com o usuário, exibição de dados e formatação de saídas
 */
public class TriagemView {
    
    /**
      * Exibe as informações detalhadas de uma triagem
     **/
    public static void exibirTriagem(Triagem triagem) {
        System.out.println("=== DADOS DA TRIAGEM ===");
        System.out.println("ID: " + (triagem.getId() != null ? triagem.getId() : "N/A"));
        System.out.println("Data: " + triagem.getDate());
        System.out.println("Batimentos por minuto: " + triagem.getBatimentosPorMinuto() + " bpm");
        System.out.println("Pressão arterial: " + triagem.getPressaoArterial() + " mmHg");
        System.out.println("Temperatura: " + triagem.getTemperatura() + "°C");
        System.out.println("Peso: " + triagem.getPeso() + " kg");
        System.out.println("Status: " + (triagem.isStatus() ? "APROVADO" : "REPROVADO"));
        System.out.println("Descrição: " + triagem.getDescricaoTriagem());
        System.out.println("========================");
    }
    
    /**
     * Exibe uma lista de triagens
     */
    public static void exibirListaTriagens(List<Triagem> triagens) {
        if (triagens.isEmpty()) {
            System.out.println("Nenhuma triagem encontrada.");
            return;
        }
        
        System.out.println("=== LISTA DE TRIAGENS ===");
        System.out.println("Total: " + triagens.size() + " triagens");
        System.out.println("------------------------");
        
        for (int i = 0; i < triagens.size(); i++) {
            Triagem triagem = triagens.get(i);
            System.out.println((i + 1) + ". " + triagem.getDate() + " - " + 
                             (triagem.isStatus() ? "APROVADO" : "REPROVADO"));
        }
        System.out.println("========================");
    }
    
    /**
     * Exibe todas as triagens realizadas no dia especificado
     */
    public static void exibirTriagensDodia(Date data, List<Triagem> triagens) {
        System.out.println("=== TRIAGENS REALIZADAS NO DIA ===");
        System.out.println("Data: " + data);
        System.out.println("Total de triagens: " + triagens.size());
        
        if (triagens.isEmpty()) {
            System.out.println("Nenhuma triagem encontrada para esta data.");
        } else {
            int aprovadas = 0, reprovadas = 0;
            
            System.out.println("\n--- LISTA DE TRIAGENS ---");
            for (int i = 0; i < triagens.size(); i++) {
                Triagem triagem = triagens.get(i);
                System.out.println((i + 1) + ". ID: " + (triagem.getId() != null ? triagem.getId() : "N/A") + 
                                 " | Status: " + (triagem.isStatus() ? "APROVADO" : "REPROVADO") +
                                 " | BPM: " + triagem.getBatimentosPorMinuto() +
                                 " | Pressão: " + triagem.getPressaoArterial() +
                                 " | Temp: " + triagem.getTemperatura() + "°C" +
                                 " | Peso: " + triagem.getPeso() + "kg");
                
                if (triagem.isStatus()) {
                    aprovadas++;
                } else {
                    reprovadas++;
                }
            }
            
            System.out.println("\n--- RESUMO ---");
            System.out.println("Triagens aprovadas: " + aprovadas);
            System.out.println("Triagens reprovadas: " + reprovadas);
            
            if (triagens.size() > 0) {
                double percentualAprovacao = (double) aprovadas / triagens.size() * 100;
                System.out.println("Percentual de aprovação: " + String.format("%.1f", percentualAprovacao) + "%");
            }
        }
        
        System.out.println("=================================");
    }
    
    /**
     * Exibe todas as triagens realizadas no mês especificado
     */
    public static void exibirTriagensDoMes(int mes, int ano, List<Triagem> triagens) {
        System.out.println("=== TRIAGENS REALIZADAS NO MÊS ===");
        System.out.println("Mês/Ano: " + mes + "/" + ano);
        System.out.println("Total de triagens: " + triagens.size());
        
        if (triagens.isEmpty()) {
            System.out.println("Nenhuma triagem encontrada para este mês.");
        } else {
            int aprovadas = 0, reprovadas = 0;
            
            System.out.println("\n--- LISTA DE TRIAGENS ---");
            for (int i = 0; i < triagens.size(); i++) {
                Triagem triagem = triagens.get(i);
                System.out.println((i + 1) + ". Data: " + triagem.getDate() + 
                                 " | ID: " + (triagem.getId() != null ? triagem.getId() : "N/A") + 
                                 " | Status: " + (triagem.isStatus() ? "APROVADO" : "REPROVADO") +
                                 " | BPM: " + triagem.getBatimentosPorMinuto() +
                                 " | Pressão: " + triagem.getPressaoArterial() +
                                 " | Temp: " + triagem.getTemperatura() + "°C" +
                                 " | Peso: " + triagem.getPeso() + "kg");
                
                if (triagem.isStatus()) {
                    aprovadas++;
                } else {
                    reprovadas++;
                }
            }
            
            System.out.println("\n--- RESUMO ---");
            System.out.println("Triagens aprovadas: " + aprovadas);
            System.out.println("Triagens reprovadas: " + reprovadas);
            
            if (triagens.size() > 0) {
                double percentualAprovacao = (double) aprovadas / triagens.size() * 100;
                System.out.println("Percentual de aprovação: " + String.format("%.1f", percentualAprovacao) + "%");
            }
        }
        
        System.out.println("=================================");
    }
    
    /**
     * Exibe mensagem de sucesso para criação de triagem
     */
    public static void exibirMensagemTriagemCriada(boolean status) {
        System.out.println("Triagem criada com sucesso!");
        System.out.println("Status: " + (status ? "APROVADO" : "REPROVADO"));
    }
    
    /**
     * Exibe mensagem de sucesso para atualização de triagem
     */
    public static void exibirMensagemTriagemAtualizada(boolean novoStatus) {
        System.out.println("Triagem atualizada com sucesso!");
        System.out.println("Novo status: " + (novoStatus ? "APROVADO" : "REPROVADO"));
    }
    
    /**
     * Exibe mensagem de sucesso para remoção de triagem
     */
    public static void exibirMensagemTriagemRemovida(boolean sucesso) {
        if (sucesso) {
            System.out.println("Triagem removida com sucesso!");
        } else {
            System.out.println("Erro: Triagem não encontrada ou não pôde ser removida.");
        }
    }
    
    /**
     * Exibe mensagem de erro
     */
    public static void exibirMensagemErro(String mensagem) {
        System.err.println("ERRO: " + mensagem);
    }
    
    /**
     * Exibe mensagem de aviso
     */
    public static void exibirMensagemAviso(String mensagem) {
        System.out.println("AVISO: " + mensagem);
    }
    
    /**
     * Exibe menu principal do sistema de triagem
     */
    public static void exibirMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE TRIAGEM HEMOCONNECT ===");
        System.out.println("1. Criar nova triagem");
        System.out.println("2. Listar triagens por data");
        System.out.println("3. Exibir triagens do dia atual");
        System.out.println("4. Exibir triagens do mês atual");
        System.out.println("5. Atualizar triagem");
        System.out.println("6. Remover triagem");
        System.out.println("7. Listar todas as triagens");
        System.out.println("8. Buscar triagem por ID");
        System.out.println("9. Exibir estatísticas");
        System.out.println("0. Sair");
        System.out.println("=====================================");
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Exibe resumo de uma triagem (formato compacto)
     */
    public static void exibirResumoTriagem(Triagem triagem) {
        System.out.println("ID: " + (triagem.getId() != null ? triagem.getId() : "N/A") + 
                         " | Data: " + triagem.getDate() + 
                         " | Status: " + (triagem.isStatus() ? "APROVADO" : "REPROVADO") +
                         " | BPM: " + triagem.getBatimentosPorMinuto() +
                         " | Temp: " + triagem.getTemperatura() + "°C");
    }
    
    /**
     * Exibe cabeçalho do sistema
     */
    public static void exibirCabecalho() {
        System.out.println("*******************************************");
        System.out.println("*        SISTEMA HEMOCONNECT             *");
        System.out.println("*             Triagens                   *");
        System.out.println("*******************************************");
    }
}
