package doador;

import java.sql.Date;

/**
 * Classe modelo que representa um doador de sangue no sistema HemoConnect.
 * 
 * <p>Esta classe encapsula todas as informações pessoais e médicas necessárias
 * para o cadastro e gerenciamento de doadores, incluindo dados de contato,
 * informações médicas e histórico de doações.</p>
 * 
 * <p>A classe implementa validações básicas para garantir a integridade dos dados
 * e fornece métodos utilitários para verificação de elegibilidade para doação.</p>
 * 
 * @author Sistema HemoConnect
 * @version 1.0
 * @since 1.0
 */
public class Doador {
    
    /** Identificador único do doador na base de dados */
    private Long id;
    
    /** Nome completo do doador */
    private String nome;
    
    /** Número do CPF do doador (formato numérico) */
    private Long cpf;
    
    /** Sexo do doador (M para Masculino, F para Feminino) */
    private String sexo;
    
    /** Tipo sanguíneo do doador (A+, A-, B+, B-, AB+, AB-, O+, O-) */
    private String tipoSanguineo;
    
    /** Data de nascimento do doador */
    private Date dataNascimento;
    
    /** Número de telefone para contato */
    private Long telefone;
    
    /** Bairro de residência do doador */
    private String bairro;
    
    /** Nacionalidade do doador */
    private String nacionalidade;
    
    /** Cidade de residência do doador */
    private String cidade;
    
    /** Data da última doação realizada pelo doador */
    private Date ultimaDoacao;
    
    /** Identificador do hospital ao qual o doador está vinculado */
    private Long idHospital;

    /**
     * Construtor completo para objetos vindos da base de dados.
     * 
     * @param id Identificador único do doador
     * @param nome Nome completo do doador
     * @param cpf Número do CPF (11 dígitos)
     * @param sexo Sexo do doador (M/F)
     * @param tipoSanguineo Tipo sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-)
     * @param dataNascimento Data de nascimento
     * @param telefone Número de telefone para contato
     * @param bairro Bairro de residência
     * @param nacionalidade Nacionalidade do doador
     * @param cidade Cidade de residência
     * @param ultimaDoacao Data da última doação (pode ser null)
     * @param idHospital Identificador do hospital vinculado
     */
    public Doador(Long id, String nome, Long cpf, String sexo, String tipoSanguineo, 
                  Date dataNascimento, Long telefone, String bairro, String nacionalidade, 
                  String cidade, Date ultimaDoacao, Long idHospital) {
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
        this.idHospital = idHospital;
    }

    /**
     * Construtor para novos doadores (sem ID).
     * 
     * <p>Utilizado para criar novos objetos Doador antes da persistência
     * na base de dados. O ID será atribuído automaticamente pelo sistema.</p>
     * 
     * @param nome Nome completo do doador
     * @param cpf Número do CPF (11 dígitos)
     * @param sexo Sexo do doador (M/F)
     * @param tipoSanguineo Tipo sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-)
     * @param dataNascimento Data de nascimento
     * @param telefone Número de telefone para contato
     * @param bairro Bairro de residência
     * @param nacionalidade Nacionalidade do doador
     * @param cidade Cidade de residência
     * @param idHospital Identificador do hospital vinculado
     */
    public Doador(String nome, Long cpf, String sexo, String tipoSanguineo, 
                  Date dataNascimento, Long telefone, String bairro, String nacionalidade, 
                  String cidade, Long idHospital) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.tipoSanguineo = tipoSanguineo;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.bairro = bairro;
        this.nacionalidade = nacionalidade;
        this.cidade = cidade;
        this.idHospital = idHospital;
    }

    /**
     * Obtém o identificador único do doador.
     * 
     * @return ID do doador ou null se ainda não persistido
     */
    public Long getId() { return id; }
    
    /**
     * Define o identificador único do doador.
     * 
     * @param id Identificador único do doador
     */
    public void setId(Long id) { this.id = id; }
    
    /**
     * Obtém o nome completo do doador.
     * 
     * @return Nome completo do doador
     */
    public String getNome() { return nome; }
    
    /**
     * Define o nome completo do doador.
     * 
     * @param nome Nome completo do doador
     */
    public void setNome(String nome) { this.nome = nome; }
    
    /**
     * Obtém o número do CPF do doador.
     * 
     * @return Número do CPF (formato numérico)
     */
    public Long getCpf() { return cpf; }
    
    /**
     * Define o número do CPF do doador.
     * 
     * @param cpf Número do CPF (deve ter 11 dígitos)
     */
    public void setCpf(Long cpf) { this.cpf = cpf; }
    
    /**
     * Obtém o sexo do doador.
     * 
     * @return Sexo do doador (M para Masculino, F para Feminino)
     */
    public String getSexo() { return sexo; }
    
    /**
     * Define o sexo do doador.
     * 
     * @param sexo Sexo do doador (M para Masculino, F para Feminino)
     */
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    /**
     * Obtém o tipo sanguíneo do doador.
     * 
     * @return Tipo sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-)
     */
    public String getTipoSanguineo() { return tipoSanguineo; }
    
    /**
     * Define o tipo sanguíneo do doador.
     * 
     * @param tipoSanguineo Tipo sanguíneo válido (A+, A-, B+, B-, AB+, AB-, O+, O-)
     */
    public void setTipoSanguineo(String tipoSanguineo) { this.tipoSanguineo = tipoSanguineo; }
    
    /**
     * Obtém a data de nascimento do doador.
     * 
     * @return Data de nascimento
     */
    public Date getDataNascimento() { return dataNascimento; }
    
    /**
     * Define a data de nascimento do doador.
     * 
     * @param dataNascimento Data de nascimento
     */
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    
    /**
     * Obtém o número de telefone do doador.
     * 
     * @return Número de telefone para contato
     */
    public Long getTelefone() { return telefone; }
    
    /**
     * Define o número de telefone do doador.
     * 
     * @param telefone Número de telefone para contato
     */
    public void setTelefone(Long telefone) { this.telefone = telefone; }
    
    /**
     * Obtém o bairro de residência do doador.
     * 
     * @return Bairro de residência
     */
    public String getBairro() { return bairro; }
    
    /**
     * Define o bairro de residência do doador.
     * 
     * @param bairro Bairro de residência
     */
    public void setBairro(String bairro) { this.bairro = bairro; }
    
    /**
     * Obtém a nacionalidade do doador.
     * 
     * @return Nacionalidade do doador
     */
    public String getNacionalidade() { return nacionalidade; }
    
    /**
     * Define a nacionalidade do doador.
     * 
     * @param nacionalidade Nacionalidade do doador
     */
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    
    /**
     * Obtém a cidade de residência do doador.
     * 
     * @return Cidade de residência
     */
    public String getCidade() { return cidade; }
    
    /**
     * Define a cidade de residência do doador.
     * 
     * @param cidade Cidade de residência
     */
    public void setCidade(String cidade) { this.cidade = cidade; }
    
    /**
     * Obtém a data da última doação realizada.
     * 
     * @return Data da última doação ou null se nunca doou
     */
    public Date getUltimaDoacao() { return ultimaDoacao; }
    
    /**
     * Define a data da última doação realizada.
     * 
     * @param ultimaDoacao Data da última doação
     */
    public void setUltimaDoacao(Date ultimaDoacao) { this.ultimaDoacao = ultimaDoacao; }

    /**
     * Obtém o identificador do hospital vinculado.
     * 
     * @return ID do hospital ao qual o doador está vinculado
     */
    public Long getIdHospital() { return idHospital; }
    
    /**
     * Define o identificador do hospital vinculado.
     * 
     * @param idHospital ID do hospital ao qual o doador será vinculado
     */
    public void setIdHospital(Long idHospital) { this.idHospital = idHospital; }

    /**
     * Valida se todos os dados obrigatórios do doador estão preenchidos.
     * 
     * <p>Verifica se todos os campos obrigatórios possuem valores válidos,
     * garantindo a integridade básica dos dados antes da persistência.</p>
     * 
     * @return true se todos os dados obrigatórios são válidos, false caso contrário
     */
    public boolean validarDados() {
        return nome != null && !nome.trim().isEmpty() &&
               cpf != null && cpf > 0 &&
               sexo != null && !sexo.trim().isEmpty() &&
               tipoSanguineo != null && !tipoSanguineo.trim().isEmpty() &&
               dataNascimento != null &&
               telefone != null && telefone > 0 &&
               bairro != null && !bairro.trim().isEmpty() &&
               nacionalidade != null && !nacionalidade.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty() &&
               idHospital != null && idHospital > 0;
    }

    /**
     * Valida se um número de CPF possui o formato correto.
     * 
     * <p>Verifica se o CPF possui exatamente 11 dígitos numéricos.
     * Esta validação verifica apenas o formato, não a validade matemática do CPF.</p>
     * 
     * @param cpf Número do CPF a ser validado
     * @return true se o CPF possui 11 dígitos, false caso contrário
     */
    public static boolean validarCpf(Long cpf) {
        if (cpf == null) return false;
        String cpfStr = cpf.toString();
        return cpfStr.length() == 11;
    }

    /**
     * Valida se um tipo sanguíneo é válido.
     * 
     * <p>Verifica se o tipo sanguíneo informado está entre os tipos válidos
     * reconhecidos pelo sistema (A+, A-, B+, B-, AB+, AB-, O+, O-).</p>
     * 
     * @param tipoSanguineo Tipo sanguíneo a ser validado
     * @return true se é um tipo sanguíneo válido, false caso contrário
     */
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

    /**
     * Calcula a idade atual do doador em anos.
     * 
     * <p>Calcula a idade com base na data de nascimento e a data atual.
     * O cálculo é aproximado, baseado em dias divididos por 365.</p>
     * 
     * @return Idade do doador em anos, ou 0 se data de nascimento for null
     */
    public int calcularIdade() {
        if (dataNascimento == null) return 0;
        long diffInMillies = System.currentTimeMillis() - dataNascimento.getTime();
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
        return (int) (diffInDays / 365);
    }

    /**
     * Verifica se o doador pode doar sangue baseado na idade.
     * 
     * <p>Aplica as regras básicas de elegibilidade para doação de sangue,
     * verificando se a idade está entre 16 e 69 anos (inclusive).</p>
     * 
     * <p><strong>Nota:</strong> Esta verificação considera apenas a idade.
     * Para uma verificação completa de elegibilidade, incluindo intervalos
     * entre doações, utilize os métodos do módulo de doação.</p>
     * 
     * @return true se a idade permite doação (16-69 anos), false caso contrário
     */
    public boolean podeDoar() {
        int idade = calcularIdade();
        return idade >= 16 && idade <= 69;
    }
}