package administrador;

public class Administrador {
    private Long id; 
    private String cargoHospital;
    private String nomeAdministrador;
    private String login;
    private String senha;
    private Long idHospital; // Referência ao hospital associado

    // para objetos vindos do banco
    public Administrador(Long id, String cargoHospital, String nomeAdministrador, String login, String senha, Long idHospital) {
        this.id = id;
        this.cargoHospital = cargoHospital;
        this.nomeAdministrador = nomeAdministrador;
        this.login = login;
        this.senha = senha;
        this.idHospital = idHospital;
    }

    public Administrador(String cargoHospital, String nomeAdministrador, String login, String senha, Long idHospital) {
        this.cargoHospital = cargoHospital;
        this.nomeAdministrador = nomeAdministrador;
        this.login = login;
        this.senha = senha;
        this.idHospital = idHospital;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoHospital() {
        return cargoHospital;
    }

    public void setCargoHospital(String cargoHospital) {
        this.cargoHospital = cargoHospital;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(Long idHospital) {
        this.idHospital = idHospital;
    }

    /**
     * Valida se os dados do administrador são válidos
     * @return true se válido, false caso contrário
     */
    public boolean validarDados() {
        return nomeAdministrador != null && !nomeAdministrador.trim().isEmpty() &&
               login != null && !login.trim().isEmpty() &&
               senha != null && !senha.trim().isEmpty() &&
               cargoHospital != null && !cargoHospital.trim().isEmpty() &&
               idHospital != null && idHospital > 0;
    }

    /**
     * Verifica se o login é único
     * @param login Login a ser verificado
     * @return true se válido, false caso contrário
     */
    public static boolean validarLogin(String login) {
        return login != null && login.trim().length() >= 3;
    }

    /**
     * Verifica se a senha atende aos critérios mínimos
     * @param senha Senha a ser verificada
     * @return true se válida, false caso contrário
     */
    public static boolean validarSenha(String senha) {
        return senha != null && senha.trim().length() >= 4;
    }

    /**
     * Verifica se o ID do hospital é válido
     * @param idHospital ID do hospital a ser verificado
     * @return true se válido, false caso contrário
     */
    public static boolean validarIdHospital(Long idHospital) {
        return idHospital != null && idHospital > 0;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", cargoHospital='" + cargoHospital + '\'' +
                ", nomeAdministrador='" + nomeAdministrador + '\'' +
                ", login='" + login + '\'' +
                ", idHospital=" + idHospital +
                '}';
    }
}