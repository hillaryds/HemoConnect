package hospital;

import doador.Doador;
import doador.DoadorController;
import java.util.List;
import java.util.ArrayList;

public class Hospital {
    private Long id; 
    private String nome;
    private String cep;
    private String cidade;
    private List<Doador> doadores;

    // Para objetos vindos do banco
    public Hospital(Long id, String nome, String cep, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
        this.cidade = cidade;
        this.doadores = new ArrayList<>();
    }

    public Hospital(String nome, String cep, String cidade) {
        this.nome = nome;
        this.cep = cep;
        this.cidade = cidade;
        this.doadores = new ArrayList<>();
    }

  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Doador> getDoadores() {
        return doadores;
    }

    public void setDoadores(List<Doador> doadores) {
        this.doadores = doadores != null ? doadores : new ArrayList<>();
    }

    public String getNomeDoHospital() {
        return nome;
    }

    public void setNomeDoHospital(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return id != null ? id.intValue() : 0;
    }

    public void setCodigo(int codigo) {
        this.id = (long) codigo;
    }

    /**
     * Consulta todos os doadores vinculados a este hospital
     * @return Lista de doadores vinculados ao hospital
     */
    public List<Doador> consultarDoadoresVinculados() {
        if (this.id == null) {
            return new ArrayList<>();
        }
        
        List<Doador> doadoresVinculados = DoadorController.listarDoadoresPorHospital(this.id);
        this.doadores = doadoresVinculados; 
        return doadoresVinculados;
    }

    /**
     * Valida se os dados do hospital são válidos
     * @return true se válido, false caso contrário
     */
    public boolean validarDados() {
        return nome != null && !nome.trim().isEmpty() &&
               cep != null && !cep.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty();
    }

    /**
     * Valida CEP brasileiro básico
     * @param cep CEP a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean validarCep(String cep) {
        if (cep == null) return false;
        cep = cep.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        return cep.length() == 8;
    }

    /**
     * Valida nome do hospital
     * @param nome Nome a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 3;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}