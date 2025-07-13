import java.util.List;

public class Doador {
    private String nome;
    private int cpf;
    private String sexo;
    private String tipoSanguineo;
    private int telefone;
    private String bairro;
    private String nacionalidade;
    private String cidade;
    
    // Instancia do banco de dados (classe temporária para testes)
    private static BancoDeDados bancoDeDados = new BancoDeDados();
    
    public Doador(String nome, int cpf, String sexo, String tipoSanguineo, int telefone, String bairro, String nacionalidade, String cidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.tipoSanguineo = tipoSanguineo;
        this.telefone = telefone;
        this.bairro = bairro;
        this.nacionalidade = nacionalidade;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }
    
    public int getCpf() {
        return cpf;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public String getTipoSanguineo() {
        return tipoSanguineo;
    }
    
    public int getTelefone() {
        return telefone;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public String getNacionalidade() {
        return nacionalidade;
    }
    
    public String getCidade() {
        return cidade;
    }

    public static void cadastrarDoador(String nome, int cpf, String sexo, String tipoSanguineo, int telefone, String bairro, String nacionalidade, String cidade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo nome e obrigatorio.");
        }
        
        if (cpf <= 0) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        if (bancoDeDados.cpfExiste(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }
        
        if (tipoSanguineo == null || tipoSanguineo.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo tipo sanguineo e obrigatorio");
        }
        

        Doador novoDoador = new Doador(nome, cpf, sexo, tipoSanguineo, telefone, bairro, nacionalidade, cidade);
        
        bancoDeDados.adicionarDoador(novoDoador);
        
        System.out.println("Doador cadastrado com sucesso!");
    } 

    public static void exibirDoadorPorCpf(int cpf) {
        Doador doador = bancoDeDados.buscarPorCpf(cpf);

        System.out.println("Buscando doador com CPF: " + cpf);
        
        if (doador != null) {
            System.out.println("=== DADOS DO DOADOR ===");
            System.out.println("Nome: " + doador.getNome());
            System.out.println("CPF: " + doador.getCpf());
            System.out.println("Sexo: " + doador.getSexo());
            System.out.println("Tipo Sanguíneo: " + doador.getTipoSanguineo());
            System.out.println("Telefone: " + doador.getTelefone());
            System.out.println("Bairro: " + doador.getBairro());
            System.out.println("Nacionalidade: " + doador.getNacionalidade());
            System.out.println("Cidade: " + doador.getCidade());
            System.out.println("========================");
        } else {
            System.out.println("Doador nao encontrado.");
        }
    }

    public void listarDoadores() {
        List<Doador> doadores = bancoDeDados.listarTodos();
        
        if (doadores.isEmpty()) {
            System.out.println("Nenhum doador cadastrado.");
        } else {
            System.out.println("=== LISTA DE DOADORES ===");
            for (Doador doador : doadores) {
                System.out.println("Nome: " + doador.getNome() + ", CPF: " + doador.getCpf());
            }
            System.out.println("==========================");
        }
    }

    public void excluirDoador(int cpf) {
        if (bancoDeDados.removerPorCpf(cpf)) {
            System.out.println("Doador removido com sucesso!");
        } else {
            System.out.println("Doador não encontrado.");
        }
    }

}