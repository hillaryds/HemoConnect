package main;

import administrador.AdministradorMain;
import administrador.AdministradorController;
import doador.DoadorMain;
import hospital.HospitalMain;
import triagem.TriagemMain;
import doacao.DoacaoMain;
import java.util.Scanner;

public class MainSystem {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static administrador.Administrador administradorLogado = null; // Armazena o admin logado
    
    public static void main(String[] args) {
        executarSistemaPrincipal();
    }
    
    public static void executarSistemaPrincipal() {
        // Primeiro é necessário fazer login como administrador
        if (!realizarLoginAdministrador()) {
            System.out.println("Acesso negado. Sistema finalizado.");
            return;
        }
        
        int opcao;
        
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            
            processarOpcao(opcao);
            
            if (opcao != 0) {
                pausar();
            }
            
        } while (opcao != 0);
        
        exibirDespedida();
    }
    
    private static boolean realizarLoginAdministrador() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║           SISTEMA HEMOCONNECT       ║");
        System.out.println("║         LOGIN DE ADMINISTRADOR      ║");
        System.out.println("╚═════════════════════════════════════╝");
        System.out.println("\nPor favor, faça login para acessar o sistema:");
        
        System.out.print("Login: ");
        String login = scanner.nextLine();
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        try {
            boolean loginValido = AdministradorController.realizarLogin(login, senha) != null;
            
            if (loginValido) {
                administradorLogado = AdministradorController.realizarLogin(login, senha); // Armazena o admin logado
                System.out.println("\nLogin realizado com sucesso!");
                System.out.println("Bem-vindo ao Sistema HemoConnect!");
            } else {
                System.out.println("\nCredenciais inválidas!");
            }
            
            return loginValido;
        } catch (Exception e) {
            System.out.println("\nErro ao validar credenciais: " + e.getMessage());
            return false;
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║           SISTEMA HEMOCONNECT       ║");
        System.out.println("║         GESTÃO DE BANCO DE SANGUE   ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1.  Administradores                 ║");
        System.out.println("║ 2.  Hospitais                       ║");
        System.out.println("║ 3.  Doadores                        ║");
        System.out.println("║ 4.  Triagens                        ║");
        System.out.println("║ 5.  Doações                         ║");
        System.out.println("║ 0.  Sair do Sistema                 ║");
        System.out.println("╚═════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                acessarModuloAdministradores();
                break;
            case 2:
                acessarModuloHospitais();
                break;
            case 3:
                acessarModuloDoadores();
                break;
            case 4:
                acessarModuloTriagens();
                break;
            case 5:
                acessarModuloDoacoes();
                break;
            case 0:
                System.out.println("\nFinalizando sistema...");
                break;
            default:
                System.out.println("\nOpção inválida! Tente novamente.");
        }
    }

    private static void acessarModuloAdministradores() {
        System.out.println("\nIniciando Módulo de Administradores...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        AdministradorMain.executarMenuPrincipal(administradorLogado);
    }
    
    /**
     * Acessa o módulo de hospitais
     */
    private static void acessarModuloHospitais() {
        System.out.println("\nIniciando Módulo de Hospitais...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        HospitalMain.executarMenuPrincipal();
    }
    
    private static void acessarModuloDoadores() {
        System.out.println("\nIniciando Módulo de Doadores...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        DoadorMain.executarMenuPrincipal();
    }
    
    private static void acessarModuloTriagens() {
        System.out.println("\nIniciando Módulo de Triagens...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        TriagemMain.executarMenuPrincipal();
    }
    
    private static void acessarModuloDoacoes() {
        System.out.println("\nIniciando Módulo de Doações...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        DoacaoMain.executarMenuPrincipal();
    }

    private static void exibirDespedida() {
        System.out.println("\nFinalizando o Sistema...");
    }
    
    private static int lerOpcao() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void pausar() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Retornando ao menu principal...");
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}
