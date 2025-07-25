package doador;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface de usuário do módulo de doadores.
 * 
 * <p>Esta classe implementa o padrão MVC (Model-View-Controller) e é responsável
 * por toda a interação com o usuário no contexto de doadores, incluindo:</p>
 * 
 * <ul>
 *   <li>Exibição de dados de doadores</li>
 *   <li>Coleta de dados do usuário</li>
 *   <li>Apresentação de menus e opções</li>
 *   <li>Exibição de mensagens de status e erro</li>
 *   <li>Formatação de listagens e relatórios</li>
 * </ul>
 * 
 * <p>A classe utiliza Scanner para entrada de dados e System.out para saída,
 * proporcionando uma interface de linha de comando amigável e funcional.</p>
 * 
 * @author Sistema HemoConnect
 * @version 1.0
 * @since 1.0
 * @see Doador
 * @see DoadorController
 */
public class DoadorView {
    
    /** Scanner compartilhado para entrada de dados do usuário */
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Exibe os dados completos de um doador de forma formatada.
     * 
     * <p>Apresenta todas as informações do doador incluindo dados pessoais,
     * informações médicas, histórico de doações e status atual de elegibilidade.</p>
     * 
     * @param doador Doador cujos dados serão exibidos
     */
    public static void exibirDoador(Doador doador) {
        System.out.println("=== DADOS DO DOADOR ===");
        System.out.println("ID: " + (doador.getId() != null ? doador.getId() : "N/A"));
        System.out.println("Nome: " + doador.getNome());
        System.out.println("CPF: " + doador.getCpf());
        System.out.println("Sexo: " + doador.getSexo());
        System.out.println("Tipo Sanguíneo: " + doador.getTipoSanguineo());
        System.out.println("Data de Nascimento: " + doador.getDataNascimento());
        System.out.println("Idade: " + doador.calcularIdade() + " anos");
        System.out.println("Telefone: " + doador.getTelefone());
        System.out.println("Bairro: " + doador.getBairro());
        System.out.println("Nacionalidade: " + doador.getNacionalidade());
        System.out.println("Cidade: " + doador.getCidade());
        System.out.println("ID Hospital: " + (doador.getIdHospital() != null ? doador.getIdHospital() : "N/A"));
        System.out.println("Última Doação: " + (doador.getUltimaDoacao() != null ? doador.getUltimaDoacao() : "Nunca doou"));
        System.out.println("Pode Doar: " + (doador.podeDoar() ? "SIM" : "NÃO"));
        System.out.println("======================");
    }
    
    /**
     * Exibe uma lista de doadores em formato tabular.
     * 
     * <p>Apresenta os doadores em uma tabela organizada com as principais
     * informações: ID, Nome, CPF, Tipo Sanguíneo, Idade, Cidade e Hospital.</p>
     * 
     * @param doadores Lista de doadores a ser exibida
     */
    public static void exibirListaDoadores(List<Doador> doadores) {
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador encontrado.");
            return;
        }
        
        System.out.println("=== LISTA DE DOADORES ===");
        System.out.println("Total: " + doadores.size() + " doadores");
        System.out.println(String.format("|%-5s | %-25s | %-12s | %-5s | %-5s | %-20s | %-8s|", 
            "ID", "Nome", "CPF", "Tipo", "Idade", "Cidade", "Hospital"));
        System.out.println(String.format("|%s|%s|%s|%s|%s|%s|%s|", 
            "-".repeat(6), "-".repeat(26), "-".repeat(13), "-".repeat(6), "-".repeat(6), "-".repeat(21), "-".repeat(9)));
        
        for (Doador doador : doadores) {
            System.out.println(String.format("|%-5s | %-25s | %-12s | %-5s | %-5s | %-20s | %-8s|", 
                doador.getId() != null ? doador.getId() : "N/A",
                doador.getNome().length() > 25 ? doador.getNome().substring(0, 22) + "..." : doador.getNome(),
                doador.getCpf(),
                doador.getTipoSanguineo(),
                doador.calcularIdade(),
                doador.getCidade().length() > 20 ? doador.getCidade().substring(0, 17) + "..." : doador.getCidade(),
                doador.getIdHospital() != null ? doador.getIdHospital() : "N/A"));
        }
        
        System.out.println("=========================");
    }
    
    public static void exibirMensagemDoadorCriado(Doador doador) {
        System.out.println("=== DOADOR CRIADO COM SUCESSO ===");
        System.out.println("ID: " + doador.getId());
        System.out.println("Nome: " + doador.getNome());
        System.out.println("CPF: " + doador.getCpf());
        System.out.println("Tipo Sanguíneo: " + doador.getTipoSanguineo());
        System.out.println("Cidade: " + doador.getCidade());
        System.out.println("================================");
    }
    
    public static void exibirMensagemRemocaoSucesso(Long cpf) {
        System.out.println("=== DOADOR REMOVIDO ===");
        System.out.println("Doador com CPF " + cpf + " foi removido com sucesso.");
        System.out.println("======================");
    }
    
    public static void exibirMensagemRemocaoFalha(Long cpf) {
        System.out.println("=== ERRO NA REMOÇÃO ===");
        System.out.println("Não foi possível remover o doador com CPF " + cpf + ".");
        System.out.println("Verifique se o CPF existe no sistema.");
        System.out.println("======================");
    }
    
    public static void exibirMensagemErro(String mensagem) {
        System.out.println("=== ERRO ===");
        System.out.println(mensagem);
        System.out.println("============");
    }
    
    public static Long solicitarCpfParaRemocao() {
        System.out.println("=== REMOVER DOADOR ===");
        System.out.print("Digite o CPF do doador a ser removido (apenas números): ");
        try {
            String input = scanner.nextLine();
            Long cpf = Long.parseLong(input);
            System.out.println("======================");
            return cpf;
        } catch (NumberFormatException e) {
            System.out.println("CPF inválido. Digite apenas números.");
            System.out.println("======================");
            return null;
        }
    }
    
    public static Object[] solicitarDadosNovoDoador() {
        System.out.println("=== CRIAR NOVO DOADOR ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        if (nome.trim().isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            return null;
        }
        
        System.out.print("CPF (apenas números): ");
        Long cpf;
        try {
            cpf = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("CPF inválido.");
            return null;
        }
        
        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();
        if (sexo.trim().isEmpty()) {
            System.out.println("Sexo não pode ser vazio.");
            return null;
        }
        
        System.out.print("Tipo Sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
        String tipoSanguineo = scanner.nextLine();
        
        System.out.print("Data de Nascimento (YYYY-MM-DD): ");
        Date dataNascimento;
        try {
            dataNascimento = Date.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Data inválida. Use formato YYYY-MM-DD.");
            return null;
        }
        
        System.out.print("Telefone (apenas números): ");
        Long telefone;
        try {
            telefone = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Telefone inválido.");
            return null;
        }
        
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        if (bairro.trim().isEmpty()) {
            System.out.println("Bairro não pode ser vazio.");
            return null;
        }
        
        System.out.print("Nacionalidade: ");
        String nacionalidade = scanner.nextLine();
        if (nacionalidade.trim().isEmpty()) {
            System.out.println("Nacionalidade não pode ser vazia.");
            return null;
        }
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        if (cidade.trim().isEmpty()) {
            System.out.println("Cidade não pode ser vazia.");
            return null;
        }
        
        // Solicitar ID do Hospital
        System.out.print("ID do Hospital (exemplo: 1, 2, 3...): ");
        Long idHospital;
        try {
            idHospital = Long.parseLong(scanner.nextLine());
            if (idHospital <= 0) {
                System.out.println("ID do hospital deve ser maior que zero.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID do hospital inválido. Digite apenas números.");
            return null;
        }
        
        System.out.println("==========================");
        
        return new Object[]{nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital};
    }
    
    public static void exibirMenuDoador() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║           MENU DE DOADORES          ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1.  Listar Todos os Doadores        ║");
        System.out.println("║ 2.  Criar Doador                    ║");
        System.out.println("║ 3.  Buscar Doador por CPF           ║");
        System.out.println("║ 4.  Listar Doadores por Hospital    ║");
        System.out.println("║ 5.  Atualizar Doador                ║");
        System.out.println("║ 6.  Remover Doador                  ║");
        System.out.println("║ 0.  Voltar ao Menu Principal        ║");
        System.out.println("╚═════════════════════════════════════╝");
        System.out.print("Escolha uma opção: "); 
    }
    
    public static Long solicitarCpf() {
        System.out.print("Digite o CPF (apenas números): ");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("CPF inválido.");
            return null;
        }
    }
    
    public static Long solicitarIdHospital() {
        System.out.print("Digite o ID do hospital: ");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return null;
        }
    }
    
    public static void exibirDoadoresPorHospital(Long idHospital, List<Doador> doadores) {
        System.out.println("=== DOADORES DO HOSPITAL ID: " + idHospital + " ===");
        
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador encontrado neste hospital.");
        } else {
            System.out.println("Total: " + doadores.size() + " doadores");
            
            int aptos = 0;
            for (Doador doador : doadores) {
                if (doador.podeDoar()) aptos++;
            }
            
            System.out.println("Aptos para doação: " + aptos);
            System.out.println();
            
            exibirListaDoadores(doadores);
        }
        
        System.out.println("=======================================");
    }
    
    public static Object[] solicitarDadosAtualizacao(Doador doadorAtual) {
        System.out.println("=== ATUALIZAR DOADOR ===");
        System.out.println("Dados atuais:");
        exibirDoador(doadorAtual);
        System.out.println("\nDigite os novos dados (deixe vazio para manter o valor atual):");
        
        System.out.print("Nome [" + doadorAtual.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (nome.trim().isEmpty()) {
            nome = doadorAtual.getNome();
        }
        
        System.out.print("Sexo [" + doadorAtual.getSexo() + "]: ");
        String sexo = scanner.nextLine();
        if (sexo.trim().isEmpty()) {
            sexo = doadorAtual.getSexo();
        }
        
        System.out.print("Tipo Sanguíneo [" + doadorAtual.getTipoSanguineo() + "]: ");
        String tipoSanguineo = scanner.nextLine();
        if (tipoSanguineo.trim().isEmpty()) {
            tipoSanguineo = doadorAtual.getTipoSanguineo();
        }
        
        System.out.print("Data de Nascimento [" + doadorAtual.getDataNascimento() + "] (YYYY-MM-DD): ");
        String dataInput = scanner.nextLine();
        Date dataNascimento = doadorAtual.getDataNascimento();
        if (!dataInput.trim().isEmpty()) {
            try {
                dataNascimento = Date.valueOf(dataInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Data inválida. Mantendo valor atual.");
            }
        }
        
        System.out.print("Telefone [" + doadorAtual.getTelefone() + "]: ");
        String telefoneInput = scanner.nextLine();
        Long telefone = doadorAtual.getTelefone();
        if (!telefoneInput.trim().isEmpty()) {
            try {
                telefone = Long.parseLong(telefoneInput);
            } catch (NumberFormatException e) {
                System.out.println("Telefone inválido. Mantendo valor atual.");
            }
        }
        
        System.out.print("Bairro [" + doadorAtual.getBairro() + "]: ");
        String bairro = scanner.nextLine();
        if (bairro.trim().isEmpty()) {
            bairro = doadorAtual.getBairro();
        }
        
        System.out.print("Nacionalidade [" + doadorAtual.getNacionalidade() + "]: ");
        String nacionalidade = scanner.nextLine();
        if (nacionalidade.trim().isEmpty()) {
            nacionalidade = doadorAtual.getNacionalidade();
        }
        
        System.out.print("Cidade [" + doadorAtual.getCidade() + "]: ");
        String cidade = scanner.nextLine();
        if (cidade.trim().isEmpty()) {
            cidade = doadorAtual.getCidade();
        }
        
        System.out.print("ID do Hospital [" + doadorAtual.getIdHospital() + "]: ");
        String hospitalInput = scanner.nextLine();
        Long idHospital = doadorAtual.getIdHospital();
        if (!hospitalInput.trim().isEmpty()) {
            try {
                idHospital = Long.parseLong(hospitalInput);
                if (idHospital <= 0) {
                    System.out.println("ID do hospital inválido. Mantendo valor atual.");
                    idHospital = doadorAtual.getIdHospital();
                }
            } catch (NumberFormatException e) {
                System.out.println("ID do hospital inválido. Mantendo valor atual.");
            }
        }
        
        System.out.println("==========================");
        
        return new Object[]{doadorAtual.getId(), nome, doadorAtual.getCpf(), sexo, tipoSanguineo, 
                           dataNascimento, telefone, bairro, nacionalidade, cidade, 
                           doadorAtual.getUltimaDoacao(), idHospital};
    }
    
    public static void exibirMensagemAtualizacaoSucesso(Doador doador) {
        System.out.println("=== DOADOR ATUALIZADO COM SUCESSO ===");
        System.out.println("ID: " + doador.getId());
        System.out.println("Nome: " + doador.getNome());
        System.out.println("CPF: " + doador.getCpf());
        System.out.println("Tipo Sanguíneo: " + doador.getTipoSanguineo());
        System.out.println("Cidade: " + doador.getCidade());
        System.out.println("===================================");
    }
    
    public static void exibirMensagemAtualizacaoFalha(Long cpf) {
        System.out.println("=== ERRO NA ATUALIZAÇÃO ===");
        System.out.println("Não foi possível atualizar o doador com CPF " + cpf + ".");
        System.out.println("Verifique se os dados estão corretos.");
        System.out.println("===========================");
    }
}