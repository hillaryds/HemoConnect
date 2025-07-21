package hospital;

import java.util.List;
import java.util.Scanner;

public class HospitalMain {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        executarMenuPrincipal();
    }
    
    public static void executarMenuPrincipal() {
        int opcao;
        
        do {
            HospitalView.exibirMenuHospital();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    listarHospitais();
                    break;
                case 2:
                    criarHospital();
                    break;
                case 3:
                    buscarPorCidade();
                    break;
                case 4:
                    buscarPorNome();
                    break;
                case 5:
                    removerHospital();
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
    
    private static void listarHospitais() {
        System.out.println("\n=== LISTANDO HOSPITAIS ===");
        HospitalController.exibirTodosHospitais();
    }
    
  
    private static void criarHospital() {
        System.out.println("\n=== CRIANDO NOVO HOSPITAL ===");
        
        String[] dados = HospitalView.solicitarDadosNovoHospital();
        
        if (dados != null) {
            String nome = dados[0];
            String cep = dados[1];
            String cidade = dados[2];
            
            HospitalController.criarHospitalComMensagem(nome, cep, cidade);
        }
    }
    
    private static void buscarPorCidade() {
        System.out.println("\n=== BUSCANDO POR CIDADE ===");
        
        String cidade = HospitalView.solicitarCidadeParaBusca();
        
        if (cidade != null) {
            HospitalController.exibirHospitaisPorCidade(cidade);
        }
    }
    
    private static void buscarPorNome() {
        System.out.println("\n=== BUSCANDO POR NOME ===");
        
        String nome = HospitalView.solicitarNomeParaBusca();
        
        if (nome != null) {
            Hospital hospital = HospitalController.buscarHospitalPorNome(nome);
            
            if (hospital != null) {
                HospitalView.exibirHospital(hospital);
            } else {
                HospitalView.exibirMensagemErro("Hospital não encontrado com nome: " + nome);
            }
        }
    }
    
    
    private static void removerHospital() {
        System.out.println("\n=== REMOVENDO HOSPITAL ===");
        
        List<Hospital> hospitais = HospitalController.listarTodosHospitais();
        
        if (hospitais.isEmpty()) {
            System.out.println("Nenhum hospital encontrado para remoção.");
            return;
        }
        
        HospitalView.exibirListaHospitais(hospitais);
        
        HospitalController.removerHospitalInterativo();
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