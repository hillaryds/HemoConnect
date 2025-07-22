package doador;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do módulo de doadores.
 * 
 * <p>Esta classe serve como ponto de entrada para o sistema de gerenciamento
 * de doadores, implementando o menu principal e coordenando as operações
 * disponíveis para gestão de doadores.</p>
 * 
 * <p>Funcionalidades disponíveis:</p>
 * <ul>
 *   <li>Listar todos os doadores</li>
 *   <li>Criar novo doador</li>
 *   <li>Buscar doador por CPF</li>
 *   <li>Listar doadores por hospital</li>
 *   <li>Atualizar dados de doador</li>
 *   <li>Remover doador</li>
 * </ul>
 * 
 * @author Sistema HemoConnect
 * @version 1.0
 * @since 1.0
 * @see DoadorController
 * @see DoadorView
 * @see Doador
 */
public class DoadorMain {
    
    /** Scanner para entrada de dados do usuário */
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Método principal de entrada do módulo.
     * 
     * @param args Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        executarMenuPrincipal();
    }

    /**
     * Executa o menu principal do módulo de doadores.
     * 
     * <p>Apresenta as opções disponíveis e processa a escolha do usuário
     * até que a opção de saída seja selecionada.</p>
     */
    public static void executarMenuPrincipal() {
        int opcao;
        
        do {
            DoadorView.exibirMenuDoador();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    listarDoadores();
                    break;
                case 2:
                    criarDoador();
                    break;
                case 3:
                    buscarDoadorPorCpf();
                    break;
                case 4:
                    listarPorHospital();
                    break;
                case 5:
                    atualizarDoador();
                    break;
                case 6:
                    removerDoador();
                    break;
                case 0:
                    System.out.println("Retornando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcao != 0);
    }
    
    private static void listarDoadores() {
        System.out.println("\n=== LISTANDO DOADORES ===");
        DoadorController.exibirTodosDoadores();
    }
    
    private static void criarDoador() {
        System.out.println("\n=== CRIANDO NOVO DOADOR ===");
        
        Object[] dados = DoadorView.solicitarDadosNovoDoador();
        
        if (dados != null) {
            String nome = (String) dados[0];
            Long cpf = (Long) dados[1];
            String sexo = (String) dados[2];
            String tipoSanguineo = (String) dados[3];
            Date dataNascimento = (Date) dados[4];
            Long telefone = (Long) dados[5];
            String bairro = (String) dados[6];
            String nacionalidade = (String) dados[7];
            String cidade = (String) dados[8];
            Long idHospital = (Long) dados[9];
            
            DoadorController.criarDoadorComMensagem(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital);
        }
    }
    
    private static void buscarDoadorPorCpf() {
        System.out.println("\n=== BUSCAR DOADOR POR CPF ===");
        
        Long cpf = DoadorView.solicitarCpf();
        if (cpf != null) {
            Doador doador = DoadorController.buscarDoadorPorCpf(cpf);
            
            if (doador != null) {
                DoadorView.exibirDoador(doador);
            } else {
                System.out.println("Doador não encontrado com o CPF: " + cpf);
            }
        }
    }
    
    private static void listarPorHospital() {
        System.out.println("\n=== LISTAR POR HOSPITAL ===");
        
        System.out.print("Digite o ID do hospital: ");
        try {
            Long idHospital = Long.parseLong(scanner.nextLine());
            List<Doador> doadores = DoadorController.listarDoadoresPorHospital(idHospital);
            
            if (doadores.isEmpty()) {
                System.out.println("Nenhum doador encontrado para o hospital ID: " + idHospital);
            } else {
                System.out.println("Doadores encontrados para o hospital ID: " + idHospital);
                DoadorView.exibirListaDoadores(doadores);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID do hospital inválido. Digite apenas números.");
        }
    }
    
    private static void atualizarDoador() {
        System.out.println("\n=== ATUALIZANDO DOADOR ===");
        
        List<Doador> doadores = DoadorController.listarTodosDoadores();
        
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador encontrado para atualização.");
            return;
        }
        
        DoadorView.exibirListaDoadores(doadores);
        System.out.println("\nSelecione o doador para atualizar:");
        
        DoadorController.atualizarDoadorInterativo();
    }
    
    private static void removerDoador() {
        System.out.println("\n=== REMOVENDO DOADOR ===");
        
        List<Doador> doadores = DoadorController.listarTodosDoadores();
        
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador encontrado para remoção.");
            return;
        }
        
        DoadorView.exibirListaDoadores(doadores);
        
        DoadorController.removerDoadorInterativo();
    }
    
    private static int lerOpcao() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}