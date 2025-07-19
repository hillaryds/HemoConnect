package hospital;

import java.util.List;
import java.util.Scanner;

public class HospitalView {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Exibe as informações detalhadas de um hospital
     * @param hospital Hospital a ser exibido
     */
    public static void exibirHospital(Hospital hospital) {
        System.out.println("=== DADOS DO HOSPITAL ===");
        System.out.println("ID: " + (hospital.getId() != null ? hospital.getId() : "N/A"));
        System.out.println("Nome: " + hospital.getNome());
        System.out.println("CEP: " + hospital.getCep());
        System.out.println("Cidade: " + hospital.getCidade());
        System.out.println("========================");
    }
    
    /**
     * Exibe uma lista de hospitais em formato de tabela
     * @param hospitais Lista de hospitais
     */
    public static void exibirListaHospitais(List<Hospital> hospitais) {
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado.");
            return;
        }
        
        System.out.println("=== LISTA DE HOSPITAIS ===");
        System.out.println("Total: " + hospitais.size() + " hospitais");
        System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", "ID", "Nome", "CEP", "Cidade"));
        System.out.println(String.format("|%s|%s|%s|%s|", 
            "-".repeat(6), "-".repeat(31), "-".repeat(11), "-".repeat(31)));
        
        for (Hospital hospital : hospitais) {
            System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", 
                hospital.getId() != null ? hospital.getId() : "N/A",
                hospital.getNome(),
                hospital.getCep(),
                hospital.getCidade()));
        }
        
        System.out.println("=========================");
    }
    
    /**
     * Exibe hospitais de uma cidade específica
     * @param cidade Nome da cidade
     * @param hospitais Lista de hospitais da cidade
     */
    public static void exibirHospitaisPorCidade(String cidade, List<Hospital> hospitais) {
        System.out.println("=== HOSPITAIS DE " + cidade.toUpperCase() + " ===");
        
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado nesta cidade.");
            return;
        }
        
        System.out.println("Total: " + hospitais.size() + " hospitais");
        System.out.println(String.format("|%-5s | %-30s| %-10s|", "ID", "Nome", "CEP"));
        System.out.println(String.format("|%s|%s|%s|", 
            "-".repeat(6), "-".repeat(31), "-".repeat(11)));
        
        for (Hospital hospital : hospitais) {
            System.out.println(String.format("|%-5s | %-30s| %-10s|", 
                hospital.getId() != null ? hospital.getId() : "N/A",
                hospital.getNome(),
                hospital.getCep()));
        }
        
        System.out.println("========================");
    }
    
    /**
     * Exibe mensagem de hospital criado com sucesso
     * @param hospital Hospital criado
     */
    public static void exibirMensagemHospitalCriado(Hospital hospital) {
        System.out.println("=== HOSPITAL CRIADO COM SUCESSO ===");
        System.out.println("ID: " + hospital.getId());
        System.out.println("Nome: " + hospital.getNome());
        System.out.println("CEP: " + hospital.getCep());
        System.out.println("Cidade: " + hospital.getCidade());
        System.out.println("==================================");
    }
    
    /**
     * Exibe mensagem de hospital removido com sucesso
     * @param nome Nome do hospital removido
     */
    public static void exibirMensagemRemocaoSucesso(String nome) {
        System.out.println("=== HOSPITAL REMOVIDO ===");
        System.out.println("Hospital '" + nome + "' foi removido com sucesso.");
        System.out.println("========================");
    }
    
    /**
     * Exibe mensagem de falha na remoção
     * @param nome Nome do hospital que não pôde ser removido
     */
    public static void exibirMensagemRemocaoFalha(String nome) {
        System.out.println("=== ERRO NA REMOÇÃO ===");
        System.out.println("Não foi possível remover o hospital '" + nome + "'.");
        System.out.println("======================");
    }
    
    /**
     * Exibe mensagem de erro genérica
     * @param mensagem Mensagem de erro
     */
    public static void exibirMensagemErro(String mensagem) {
        System.out.println("=== ERRO ===");
        System.out.println(mensagem);
        System.out.println("============");
    }
    
    public static Long solicitarIdParaRemocao() {
        System.out.println("=== REMOVER HOSPITAL ===");
        System.out.print("Digite o ID do hospital a ser removido: ");
        try {
            String input = scanner.nextLine();
            Long id = Long.parseLong(input.trim());
            System.out.println("=======================");
            return id;
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Deve ser um número.");
            System.out.println("=======================");
            return null;
        }
    }

    public static String[] solicitarDadosNovoHospital() {
        System.out.println("=== CRIAR NOVO HOSPITAL ===");
        
        System.out.print("Nome do hospital: ");
        String nome = scanner.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            return null;
        }
        
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        if (cep.trim().isEmpty()) {
            System.out.println("CEP não pode ser vazio.");
            return null;
        }
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        if (cidade.trim().isEmpty()) {
            System.out.println("Cidade não pode ser vazia.");
            return null;
        }
        
        System.out.println("==========================");
        return new String[]{nome, cep, cidade};
    }
    
    public static String solicitarCidadeParaBusca() {
        System.out.println("=== BUSCAR POR CIDADE ===");
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();
        
        if (cidade.trim().isEmpty()) {
            System.out.println("Cidade não pode ser vazia.");
            System.out.println("========================");
            return null;
        }
        
        System.out.println("========================");
        return cidade;
    }
    
    public static String solicitarNomeParaBusca() {
        System.out.println("=== BUSCAR POR NOME ===");
        System.out.print("Digite o nome do hospital: ");
        String nome = scanner.nextLine();
        
        if (nome.trim().isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            System.out.println("======================");
            return null;
        }
        
        System.out.println("======================");
        return nome;
    }
    
    public static void exibirMenuHospital() {
        System.out.println("=== MENU DO HOSPITAL ===");
        System.out.println("1. Listar hospitais");
        System.out.println("2. Criar hospital");
        System.out.println("3. Buscar por cidade");
        System.out.println("4. Buscar por nome");
        System.out.println("5. Remover hospital");
        System.out.println("0. Sair");
        System.out.println("=======================");
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Solicita confirmação do usuário
     * @param mensagem Mensagem de confirmação
     * @return true se confirmado, false caso contrário
     */
    public static boolean solicitarConfirmacao(String mensagem) {
        System.out.print(mensagem + " (s/n): ");
        String resposta = scanner.nextLine();
        return resposta.toLowerCase().startsWith("s");
    }
}