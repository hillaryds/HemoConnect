package administrador;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do módulo Administrador
 * 
 * Regra de Negócio:
 * - Existe um usuário principal pré-cadastrado no banco
 * - Apenas administradores logados podem cadastrar outros administradores
 * - Menu inicial: apenas Login e Sair
 * - Um administrador deve estar associado a um hospital
 * - Um hospital pode ter um ou mais administradores
 */
public class AdministradorMain {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static Administrador administradorLogado = null; // Controle de sessão
    
    public static void main(String[] args) {
        executarMenuPrincipal();
    }
    
    public static void executarMenuPrincipal() {
        executarMenuPrincipal(null);
    }
    
    /**
     * Executa o menu principal com um administrador já logado
     * @param adminLogado Administrador já autenticado
     */
    public static void executarMenuPrincipal(Administrador adminLogado) {
        if (adminLogado != null) {
            administradorLogado = adminLogado;
            System.out.println("Bem-vindo ao módulo de administradores, " + adminLogado.getNomeAdministrador() + "!");
        }
        
        int opcao;
        
        do {
            exibirMenuLogado();
            
            opcao = lerOpcao();
            
            if (administradorLogado == null) {
                processarOpcaoNaoLogado(opcao);
            } else {
                processarOpcaoLogado(opcao);
            }
            
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcao != 0);
    }
    
    private static void exibirMenuLogado() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║        SISTEMA ADMINISTRADOR        ║");
        System.out.println("║            HEMOCONNECT              ║");
        System.out.println("╠═════════════════════════════════════╣");
        String nomeAdmin = administradorLogado.getNomeAdministrador();
        String linhaLogin = String.format("║ Logado como: %-23s║", nomeAdmin);
        System.out.println(linhaLogin);
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1.  Listar Administradores          ║");
        System.out.println("║ 2.  Criar Administrador             ║");
        System.out.println("║ 3.  Remover Administrador           ║");
        System.out.println("║ 4.  Fazer Logout                    ║");
        System.out.println("║ 0.  Sair                            ║");
        System.out.println("╚═════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void processarOpcaoNaoLogado(int opcao) {
        switch (opcao) {
            case 1:
                realizarLogin();
                break;
            case 0:
                System.out.println("Retornando ao Menu Principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    
    private static void processarOpcaoLogado(int opcao) {
        switch (opcao) {
            case 1:
                listarAdministradores();
                break;
            case 2:
                criarAdministrador();
                break;
            case 3:
                removerAdministrador();
                break;
            case 4:
                realizarLogout();
                break;
            case 0:
                System.out.println("Retornando ao Menu Principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    
    private static void listarAdministradores() {
        System.out.println("\n=== LISTANDO ADMINISTRADORES ===");
        AdministradorController.exibirTodosAdministradores();
    }
    
    private static void criarAdministrador() {
        System.out.println("\n=== CRIANDO NOVO ADMINISTRADOR ===");
        
        String[] dados = AdministradorView.solicitarDadosNovoAdministrador();
        
        if (dados != null) {
            String nome = dados[0];
            String login = dados[1];
            String senha = dados[2];
            String idHospitalStr = dados[3];
            
            try {
                Long idHospital = Long.parseLong(idHospitalStr);
                AdministradorController.criarAdministradorComMensagem(nome, login, senha, idHospital);
            } catch (NumberFormatException e) {
                System.out.println("ID do Hospital deve ser um número válido.");
            }
        }
    }
    
    private static void removerAdministrador() {
        System.out.println("\n=== REMOVENDO ADMINISTRADOR ===");
        
        // Primeiro, mostrar lista atual
        List<Administrador> administradores = AdministradorController.listarTodosAdministradores();
        
        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador encontrado para remoção.");
            return;
        }
        
        AdministradorView.exibirListaAdministradores(administradores);
        
        AdministradorController.removerAdministradorInterativo();
    }
    
    private static void realizarLogin() {
        System.out.println("\n=== REALIZANDO LOGIN ===");
        
        String[] credenciais = AdministradorView.solicitarCredenciais();
        
        if (credenciais != null) {
            String login = credenciais[0];
            String senha = credenciais[1];
            
            Administrador admin = AdministradorController.realizarLogin(login, senha);
            if (admin != null) {
                administradorLogado = admin;
                System.out.println("Login realizado com sucesso! Bem-vindo, " + admin.getNomeAdministrador());
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
            }
        }
    }
    
    private static void realizarLogout() {
        if (administradorLogado != null) {
            System.out.println("Logout realizado com sucesso. Até logo, " + administradorLogado.getNomeAdministrador() + "!");
            administradorLogado = null;
        }
    }
    
    /**
     * Lê uma opção do menu
     * @return Opção escolhida pelo usuário
     */
    private static int lerOpcao() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}