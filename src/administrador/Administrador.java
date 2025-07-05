public class Administrador{
    private String cargoHospital;
    private String nomeAdministrador;
    private String login;
    private String senha;

    Administrador(String nome, String login, String senha){
        this.nome = nomeAdministrador;
        this.login = login;
        this.senha = senha;
        this.cargoHospital = "Administrador";
    }

    //TODO: Adicionar listarAdminitrador que retorna List<Administrador>

    public void criarAdministrador(String nome, String login, String senha){
        // TODO: adicionar as tratativas
        Administrador(nome, login, senha);
    }

    public void realizarLogin(String login, String senha){ //? No documento, tinha "nomeDeUsuario" como parâmetro, entretanto, não faz sentindo ter um nomeDeUsuário para fazer login
        // TODO: implementar com o banco de dados
    }

    public void removerAdministrador(){
        // TODO: implementar com o banco de dados  
    }
    
}