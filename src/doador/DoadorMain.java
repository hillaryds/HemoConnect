import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class DoadorMain {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        executarMenuPrincipal();
    }

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
                    listarPorTipoSanguineo();
                    break;
                case 5:
                    listarPorCidade();
                    break;
                case 6:
                    removerDoador();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
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
            
            DoadorController.criarDoadorComMensagem(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade);
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
    
    private static void listarPorTipoSanguineo() {
        System.out.println("\n=== LISTAR POR TIPO SANGUÍNEO ===");
        
        String tipoSanguineo = DoadorView.solicitarTipoSanguineo();
        if (tipoSanguineo != null && !tipoSanguineo.isEmpty()) {
            List<Doador> doadores = DoadorController.listarDoadoresPorTipoSanguineo(tipoSanguineo);
            DoadorView.exibirDoadoresPorTipoSanguineo(tipoSanguineo, doadores);
        }
    }
    
    private static void listarPorCidade() {
        System.out.println("\n=== LISTAR POR CIDADE ===");
        
        String cidade = DoadorView.solicitarCidade();
        if (cidade != null && !cidade.isEmpty()) {
            List<Doador> doadores = DoadorController.listarDoadoresPorCidade(cidade);
            
            if (doadores.isEmpty()) {
                System.out.println("Nenhum doador encontrado na cidade: " + cidade);
            } else {
                System.out.println("Doadores encontrados na cidade: " + cidade);
                DoadorView.exibirListaDoadores(doadores);
            }
        }
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