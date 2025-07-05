public class Doador {
    private String nome;
    private int cpf;
    private String sexo;
    private String tipoSanguineo;
    private int telefone;
    private String bairro;
    private String nacionalidade;
    private String cidade;
    
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
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (cpf <= 0) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        if (tipoSanguineo == null || tipoSanguineo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo sanguíneo não pode ser vazio");
        }
        
        new Doador(nome, cpf, sexo, tipoSanguineo, telefone, bairro, nacionalidade, cidade);
        
        System.out.println("Doador cadastrado com sucesso!");
    }
}