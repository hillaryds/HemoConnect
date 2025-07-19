import java.sql.Date;

public class Doador {
    private Long id;
    private String nome;
    private Long cpf;
    private String sexo;
    private String tipoSanguineo;
    private Date dataNascimento;
    private Long telefone;
    private String bairro;
    private String nacionalidade;
    private String cidade;
    private Date ultimaDoacao;

    // Construtor com ID (para objetos vindos do banco)
    public Doador(Long id, String nome, Long cpf, String sexo, String tipoSanguineo, 
                  Date dataNascimento, Long telefone, String bairro, String nacionalidade, 
                  String cidade, Date ultimaDoacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.tipoSanguineo = tipoSanguineo;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.bairro = bairro;
        this.nacionalidade = nacionalidade;
        this.cidade = cidade;
        this.ultimaDoacao = ultimaDoacao;
    }

    // Construtor sem ID
    public Doador(String nome, Long cpf, String sexo, String tipoSanguineo, 
                  Date dataNascimento, Long telefone, String bairro, String nacionalidade, 
                  String cidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.tipoSanguineo = tipoSanguineo;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.bairro = bairro;
        this.nacionalidade = nacionalidade;
        this.cidade = cidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }
    
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public String getTipoSanguineo() { return tipoSanguineo; }
    public void setTipoSanguineo(String tipoSanguineo) { this.tipoSanguineo = tipoSanguineo; }
    
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public Long getTelefone() { return telefone; }
    public void setTelefone(Long telefone) { this.telefone = telefone; }
    
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    
    public Date getUltimaDoacao() { return ultimaDoacao; }
    public void setUltimaDoacao(Date ultimaDoacao) { this.ultimaDoacao = ultimaDoacao; }

    public boolean validarDados() {
        return nome != null && !nome.trim().isEmpty() &&
               cpf != null && cpf > 0 &&
               sexo != null && !sexo.trim().isEmpty() &&
               tipoSanguineo != null && !tipoSanguineo.trim().isEmpty() &&
               dataNascimento != null &&
               telefone != null && telefone > 0 &&
               bairro != null && !bairro.trim().isEmpty() &&
               nacionalidade != null && !nacionalidade.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty();
    }

    public static boolean validarCpf(Long cpf) {
        if (cpf == null) return false;
        String cpfStr = cpf.toString();
        return cpfStr.length() == 11;
    }

    public static boolean validarTipoSanguineo(String tipoSanguineo) {
        if (tipoSanguineo == null) return false;
        String[] tiposValidos = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String tipo : tiposValidos) {
            if (tipo.equals(tipoSanguineo)) {
                return true;
            }
        }
        return false;
    }

    public int calcularIdade() {
        if (dataNascimento == null) return 0;
        long diffInMillies = System.currentTimeMillis() - dataNascimento.getTime();
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
        return (int) (diffInDays / 365);
    }

    public boolean podeDoar() {
        int idade = calcularIdade();
        return idade >= 16 && idade <= 69;
    }
}