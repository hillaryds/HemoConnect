package triagem;

import java.sql.Date;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Sistema de Triagem HemoConnect - Menu Interativo
 * 
 * Executável principal do sistema de triagem seguindo o padrão MVC tradicional
 * Permite gerenciar triagens através de um menu intuitivo
 */
public class TriagemMain {
    
    private static Scanner scanner = new Scanner(System.in);
    private static boolean sistemaAtivo = true;
    
    public static void main(String[] args) {
        System.out.println("Inicializando Sistema HemoConnect...");
        
        // Verificar conexão com banco de dados
        if (!verificarConexaoBanco()) {
            System.err.println("Erro: Não foi possível conectar ao banco de dados PostgreSQL.");
            System.err.println("Verifique se o PostgreSQL está rodando e as configurações estão corretas.");
            return;
        }
        
        // Exibir cabeçalho do sistema
        TriagemView.exibirCabecalho();
        
        // Loop principal do menu
        while (sistemaAtivo) {
            try {
                TriagemView.exibirMenuPrincipal();
                int opcao = lerInteiro();
                
                processarOpcaoMenu(opcao);
                
            } catch (InputMismatchException e) {
                System.err.println("Erro: Digite apenas números!");
                scanner.nextLine(); // Limpar buffer
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("\nObrigado por usar o Sistema HemoConnect!");
        System.out.println("Sistema encerrado.");
        scanner.close();
    }
    
    /**
     * Processa a opção escolhida pelo usuário
     */
    private static void processarOpcaoMenu(int opcao) {
        switch (opcao) {
            case 1:
                criarNovaTriagem();
                break;
            case 2:
                listarTriagensPorData();
                break;
            case 3:
                exibirTriagensDodia();
                break;
            case 4:
                exibirTriagensDoMes();
                break;
            case 5:
                atualizarTriagem();
                break;
            case 6:
                removerTriagem();
                break;
            case 7:
                listarTodasTriagens();
                break;
            case 8:
                buscarTriagemPorId();
                break;
            case 9:
                exibirEstatisticas();
                break;
            case 0:
                sistemaAtivo = false;
                break;
            default:
                System.err.println("Opção inválida! Tente novamente.");
                break;
        }
        
        if (sistemaAtivo && opcao != 0) {
            pausarSistema();
        }
    }
    
    /**
     * Cria uma nova triagem
     */
    private static void criarNovaTriagem() {
        System.out.println("\n=== CRIAR NOVA TRIAGEM ===");
        
        try {
            System.out.print("Batimentos por minuto (60-100): ");
            int bpm = lerInteiro();
            
            System.out.print("Pressão arterial (formato: 120/80): ");
            String pressao = lerString();
            
            System.out.print("Temperatura corporal (°C): ");
            double temperatura = lerDouble();
            
            System.out.print("Peso (kg): ");
            double peso = lerDouble();
            
            Date hoje = new Date(System.currentTimeMillis());
            
            // Usar Controller para criar triagem
            TriagemController.criarTriagemComMensagem(bpm, pressao, temperatura, peso, hoje);
            
        } catch (Exception e) {
            TriagemView.exibirMensagemErro("Erro ao criar triagem: " + e.getMessage());
        }
    }
    
    /**
     * Lista triagens por data específica
     */
    private static void listarTriagensPorData() {
        System.out.println("\n=== TRIAGENS POR DATA ===");
        
        try {
            System.out.print("Digite a data (YYYY-MM-DD) ou pressione Enter para hoje: ");
            String dataInput = lerString();
            
            Date data;
            if (dataInput.trim().isEmpty()) {
                data = new Date(System.currentTimeMillis());
            } else {
                data = Date.valueOf(dataInput);
            }
            
            TriagemController.exibirTriagensDeData(data);
            
        } catch (Exception e) {
            TriagemView.exibirMensagemErro("Erro ao listar triagens: " + e.getMessage());
        }
    }
    
    /**
     * Exibe triagens do dia atual
     */
    private static void exibirTriagensDodia() {
        System.out.println("\n=== TRIAGENS DO DIA ATUAL ===");
        TriagemController.exibirTriagensDodia();
    }
    
    /**
     * Exibe triagens do mês atual
     */
    private static void exibirTriagensDoMes() {
        System.out.println("\n=== TRIAGENS DO MÊS ATUAL ===");
        TriagemController.exibirTriagensDoMes();
    }
    
    /**
     * Atualiza uma triagem existente
     */
    private static void atualizarTriagem() {
        System.out.println("\n=== ATUALIZAR TRIAGEM ===");
        
        try {
            System.out.print("Digite o ID da triagem: ");
            Long id = lerLong();
            
            Triagem triagem = TriagemController.buscarTriagemPorId(id);
            
            if (triagem == null) {
                TriagemView.exibirMensagemErro("Triagem não encontrada!");
                return;
            }
            
            System.out.println("Triagem encontrada:");
            TriagemView.exibirTriagem(triagem);
            
            System.out.print("Novos batimentos por minuto (atual: " + triagem.getBatimentosPorMinuto() + "): ");
            int bpm = lerInteiro();
            
            System.out.print("Nova pressão arterial (atual: " + triagem.getPressaoArterial() + "): ");
            String pressao = lerString();
            
            System.out.print("Nova temperatura (atual: " + triagem.getTemperatura() + "): ");
            double temperatura = lerDouble();
            
            System.out.print("Novo peso (atual: " + triagem.getPeso() + "): ");
            double peso = lerDouble();
            
            TriagemController.atualizarTriagemComMensagem(triagem, bpm, pressao, temperatura, peso);
            
        } catch (Exception e) {
            TriagemView.exibirMensagemErro("Erro ao atualizar triagem: " + e.getMessage());
        }
    }
    
    /**
     * Remove uma triagem
     */
    private static void removerTriagem() {
        System.out.println("\n=== REMOVER TRIAGEM ===");
        
        try {
            System.out.print("Digite o ID da triagem: ");
            Long id = lerLong();
            
            Triagem triagem = TriagemController.buscarTriagemPorId(id);
            
            if (triagem == null) {
                TriagemView.exibirMensagemErro("Triagem não encontrada!");
                return;
            }
            
            System.out.println("Triagem encontrada:");
            TriagemView.exibirTriagem(triagem);
            
            System.out.print("Tem certeza que deseja remover esta triagem? (s/N): ");
            String confirmacao = lerString().toLowerCase();
            
            if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                TriagemController.removerTriagemComMensagem(triagem);
            } else {
                System.out.println("Operação cancelada.");
            }
            
        } catch (Exception e) {
            TriagemView.exibirMensagemErro("Erro ao remover triagem: " + e.getMessage());
        }
    }
    
    /**
     * Lista todas as triagens
     */
    private static void listarTodasTriagens() {
        System.out.println("\n=== TODAS AS TRIAGENS ===");
        TriagemController.exibirTodasTriagens();
    }
    
    /**
     * Busca triagem por ID
     */
    private static void buscarTriagemPorId() {
        System.out.println("\n=== BUSCAR TRIAGEM POR ID ===");
        
        try {
            System.out.print("Digite o ID da triagem: ");
            Long id = lerLong();
            
            TriagemController.exibirTriagemPorId(id);
            
        } catch (Exception e) {
            TriagemView.exibirMensagemErro("Erro ao buscar triagem: " + e.getMessage());
        }
    }
    
    /**
     * Exibe estatísticas do sistema
     */
    private static void exibirEstatisticas() {
        System.out.println("\n=== ESTATÍSTICAS DO SISTEMA ===");
        
        var todasTriagens = TriagemController.obterTodasTriagens();
        int total = todasTriagens.size();
        
        if (total == 0) {
            System.out.println("Nenhuma triagem encontrada no sistema.");
            return;
        }
        
        long aprovadas = todasTriagens.stream().mapToLong(t -> t.isStatus() ? 1 : 0).sum();
        long reprovadas = total - aprovadas;
        
        System.out.println("Total de triagens: " + total);
        System.out.println("Triagens aprovadas: " + aprovadas);
        System.out.println("Triagens reprovadas: " + reprovadas);
        System.out.println("Taxa de aprovação: " + String.format("%.1f%%", (double) aprovadas / total * 100));
        
        // Estatísticas do dia atual
        Date hoje = new Date(System.currentTimeMillis());
        var triagensHoje = TriagemController.listarTriagemDate(hoje);
        System.out.println("\nTriagens realizadas hoje: " + triagensHoje.size());
    }
    
    /**
     * Verifica conexão com banco de dados
     */
    private static boolean verificarConexaoBanco() {
        try {
            database.DatabaseConnection.getConnection();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Pausa o sistema até o usuário pressionar Enter
     */
    private static void pausarSistema() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Métodos utilitários para leitura de dados
     */
    private static int lerInteiro() {
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        return valor;
    }
    
    private static long lerLong() {
        long valor = scanner.nextLong();
        scanner.nextLine(); // Limpar buffer
        return valor;
    }
    
    private static double lerDouble() {
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar buffer
        return valor;
    }
    
    private static String lerString() {
        return scanner.nextLine();
    }
}
