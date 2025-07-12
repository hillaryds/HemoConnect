package administrador;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


//TODO:funcao de exibir administrador 
//TODO: adicionar exeções mais específicas(banco)
public class Administrador{
    private String cargoHospital;
    private String nomeAdministrador;
    private String login;
    private String senha;

    public Administrador(String nome, String login, String senha){
        this.nomeAdministrador = nome;
        this.login = login;
        this.senha = senha;
        this.cargoHospital = "Administrador";
    }

    public String getNomeAdministrador(){
        return this.nomeAdministrador;
    }

    public void setNomeAdministrador(String novoNome){
        this.nomeAdministrador = novoNome;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String novoLogin){
        this.login = novoLogin;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String novaSenha){
        this.senha = novaSenha;
    }

    public String getCargoHospital(){
        return this.cargoHospital;
    }

    public void exibeAdministrador(){
        System.out.println(String.format("|%-20s | %-100s|", "Cargo", "Nome"));
        System.out.println(String.format("|%s|%s|", "-".repeat(21), "-".repeat(101)));
        System.out.println(String.format("|%-20s | %-100s|", this.cargoHospital, this.nomeAdministrador));
    }

    public List<Administrador> listarAdminitrador(String url_banco, String usuario_banco, String senha_banco){
        List<Administrador> listaDeAdms = new ArrayList<>();
        try {
            Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);

            String sql = "SELECT * FROM administrador;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            System.out.println(String.format("|%-20s | %-100s|", "Cargo", "Nome"));
            System.out.println(String.format("|%s|%s|", "-".repeat(21), "-".repeat(101)));
            while (rs.next()) {
                String cargo = rs.getString("cargo_hospital");
                String nome = rs.getString("nome_administrador");
                String login = rs.getString("login");
                String senhaDb = rs.getString("senha");

                Administrador adm = new Administrador(nome, login, senhaDb);

                listaDeAdms.add(adm);
                System.out.println(String.format("|%-20s | %-100s|", cargo, nome));
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
        
        return listaDeAdms;
    }

    public void criarAdministrador(String nome, String login, String senha, String url_banco, String usuario_banco, String senha_banco){
            try {
                Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);

                String sql = "INSERT INTO administrador (cargo_hospital, nome_administrador, login, senha) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setString(1, "Administrador");
                ps.setString(2, nome);
                ps.setString(3, login);
                ps.setString(4, senha);

                ps.executeUpdate();

                ps.close();
                conexao.close();

            } catch (SQLException e) {
                System.err.println("Erro ao conectar: " + e.getMessage());
            }
    }

    public void realizarLogin(String login, String senha){
        try {
            Connection conexao = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/projetoPOO",
                "postgres",
                "1234"
            );

            String sql = "SELECT nome_administrador FROM administrador WHERE login = ? AND senha = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome_administrador");
                System.out.println("Login realizado com sucesso. Bem-vindo(a), " + nome + "!");
                
            } else {
                System.out.println("Login ou senha incorretos.");
            }

            rs.close();
            ps.close();
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou consultar: " + e.getMessage());
        }
    }

    public void removerAdministrador(String url_banco, String usuario_banco, String senha_banco){
        Scanner leitor = new Scanner(System.in);

        System.out.print("Digite o login do Administrador a ser removido: ");
        String loginARemover = leitor.nextLine();
        try {
            Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);

            String sql = "DELETE FROM administrador WHERE login = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, loginARemover.trim());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
    
}
