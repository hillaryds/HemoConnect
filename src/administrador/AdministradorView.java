package administrador;

import java.util.List;
import java.util.Scanner;

public class AdministradorView {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Exibe as informações detalhadas de um administrador
     * @param administrador Administrador a ser exibido
     */
    public static void exibirAdministrador(Administrador administrador) {
        System.out.println("=== DADOS DO ADMINISTRADOR ===");
        System.out.println("ID: " + (administrador.getId() != null ? administrador.getId() : "N/A"));
        System.out.println("Nome: " + administrador.getNomeAdministrador());
        System.out.println("Login: " + administrador.getLogin());
        System.out.println("Cargo: " + administrador.getCargoHospital());
        System.out.println("ID Hospital: " + administrador.getIdHospital());
        System.out.println("=============================");
    }
    
    /**
     * Exibe uma lista de administradores em formato de tabela
     * @param administradores Lista de administradores
     */
    public static void exibirListaAdministradores(List<Administrador> administradores) {
        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador encontrado.");
            return;
        }
        
        System.out.println("=== LISTA DE ADMINISTRADORES ===");
        System.out.println("Total: " + administradores.size() + " administradores");
        System.out.println(String.format("|%-5s | %-20s | %-30s | %-20s | %-12s|", "ID", "Cargo", "Nome", "Login", "ID Hospital"));
        System.out.println(String.format("|%s|%s|%s|%s|%s|", 
            "-".repeat(6), "-".repeat(21), "-".repeat(31), "-".repeat(21), "-".repeat(13)));
        
        for (Administrador adm : administradores) {
            System.out.println(String.format("|%-5s | %-20s | %-30s | %-20s | %-12s|", 
                adm.getId() != null ? adm.getId() : "N/A",
                adm.getCargoHospital(),
                adm.getNomeAdministrador(),
                adm.getLogin(),
                adm.getIdHospital()));
        }
        
        System.out.println("===============================");
    }
    
    /**
     * Exibe lista de administradores
     * @param administradores Lista de administradores
     */
    public static void exibirListaAdministradoresSimples(List<Administrador> administradores) {
        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador encontrado.");
            return;
        }
        
        System.out.println(String.format("|%-20s | %-100s|", "Cargo", "Nome"));
        System.out.println(String.format("|%s|%s|", "-".repeat(21), "-".repeat(101)));
        
        for (Administrador adm : administradores) {
            System.out.println(String.format("|%-20s | %-100s|", 
                adm.getCargoHospital(),
                adm.getNomeAdministrador()));
        }
    }
    
    /**
     * Exibe mensagem de administrador criado com sucesso
     * @param administrador Administrador criado
     */
    public static void exibirMensagemAdministradorCriado(Administrador administrador) {
        System.out.println("=== ADMINISTRADOR CRIADO COM SUCESSO ===");
        System.out.println("ID: " + administrador.getId());
        System.out.println("Nome: " + administrador.getNomeAdministrador());
        System.out.println("Login: " + administrador.getLogin());
        System.out.println("Cargo: " + administrador.getCargoHospital());
        System.out.println("ID Hospital: " + administrador.getIdHospital());
        System.out.println("======================================");
    }
    
    /**
     * Exibe mensagem de login bem-sucedido
     * @param administrador Administrador que fez login
     */
    public static void exibirMensagemLoginSucesso(Administrador administrador) {
        System.out.println("=== LOGIN REALIZADO COM SUCESSO ===");
        System.out.println("Bem-vindo(a), " + administrador.getNomeAdministrador() + "!");
        System.out.println("Cargo: " + administrador.getCargoHospital());
        System.out.println("==================================");
    }
    
   
    public static void exibirMensagemLoginFalha() {
        System.out.println("=== ERRO NO LOGIN ===");
        System.out.println("Login ou senha incorretos.");
        System.out.println("==================");
    }
    
    /**
     * Exibe mensagem de administrador removido com sucesso
     * @param login Login do administrador removido
     */
    public static void exibirMensagemRemocaoSucesso(String login) {
        System.out.println("=== ADMINISTRADOR REMOVIDO ===");
        System.out.println("Administrador com login '" + login + "' foi removido com sucesso.");
        System.out.println("=============================");
    }
    
    /**
     * Exibe mensagem de falha na remoção
     * @param login Login do administrador que não pôde ser removido
     */
    public static void exibirMensagemRemocaoFalha(String login) {
        System.out.println("=== ERRO NA REMOÇÃO ===");
        System.out.println("Não foi possível remover o administrador com login '" + login + "'.");
        System.out.println("Verifique se o login existe no sistema.");
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
    
    /**
     * Solicita o login para remoção de administrador
     * @return Login digitado pelo usuário
     */
    public static String solicitarLoginParaRemocao() {
        System.out.println("=== REMOVER ADMINISTRADOR ===");
        System.out.print("Digite o login do administrador a ser removido: ");
        String login = scanner.nextLine();
        System.out.println("============================");
        return login;
    }
    
    /**
     * Solicita dados para criar um novo administrador
     * @return Array com [nome, login, senha, idHospital] ou null se cancelado
     */
    public static String[] solicitarDadosNovoAdministrador() {
        System.out.println("=== CRIAR NOVO ADMINISTRADOR ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            return null;
        }
        
        System.out.print("Login: ");
        String login = scanner.nextLine();
        if (login.trim().isEmpty()) {
            System.out.println("Login não pode ser vazio.");
            return null;
        }
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        if (senha.trim().isEmpty()) {
            System.out.println("Senha não pode ser vazia.");
            return null;
        }
        
        System.out.print("ID do Hospital: ");
        String idHospitalStr = scanner.nextLine();
        if (idHospitalStr.trim().isEmpty()) {
            System.out.println("ID do Hospital não pode ser vazio.");
            return null;
        }
        
        System.out.println("===============================");
        return new String[]{nome, login, senha, idHospitalStr};
    }
    
    /**
     * Solicita credenciais para login
     * @return Array com [login, senha] ou null se cancelado
     */
    public static String[] solicitarCredenciais() {
        System.out.println("=== LOGIN DO ADMINISTRADOR ===");
        
        System.out.print("Login: ");
        String login = scanner.nextLine();
        if (login.trim().isEmpty()) {
            System.out.println("Login não pode ser vazio.");
            return null;
        }
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        if (senha.trim().isEmpty()) {
            System.out.println("Senha não pode ser vazia.");
            return null;
        }
        
        System.out.println("=============================");
        return new String[]{login, senha};
    }
    
    
    public static void exibirMenuAdministrador() {
        System.out.println("=== MENU DO ADMINISTRADOR ===");
        System.out.println("1. Listar administradores");
        System.out.println("2. Criar administrador");
        System.out.println("3. Remover administrador");
        System.out.println("4. Fazer login");
        System.out.println("0. Sair");
        System.out.println("============================");
        System.out.print("Escolha uma opção: ");
    }
}