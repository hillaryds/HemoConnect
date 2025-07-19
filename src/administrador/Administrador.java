package administrador;

public class Administrador {
    private Long id; 
    private String cargoHospital;
    private String nomeAdministrador;
    private String login;
    private String senha;

    // para objetos vindos do banco
    public Administrador(Long id, String cargoHospital, String nomeAdministrador, String login, String senha) {
        this.id = id;
        this.cargoHospital = cargoHospital;
        this.nomeAdministrador = nomeAdministrador;
        this.login = login;
        this.senha = senha;
    }

    public Administrador(String cargoHospital, String nomeAdministrador, String login, String senha) {
        this.cargoHospital = cargoHospital;
        this.nomeAdministrador = nomeAdministrador;
        this.login = login;
        this.senha = senha;
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

    /**
     * Valida se os dados do administrador são válidos
     * @return true se válido, false caso contrário
     */
    public boolean validarDados() {
        return nomeAdministrador != null && !nomeAdministrador.trim().isEmpty() &&
               login != null && !login.trim().isEmpty() &&
               senha != null && !senha.trim().isEmpty() &&
               cargoHospital != null && !cargoHospital.trim().isEmpty();
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

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", cargoHospital='" + cargoHospital + '\'' +
                ", nomeAdministrador='" + nomeAdministrador + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}