package hospital;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//TODO: adicionar exeções mais específicas(banco)
public class Hospital{
    private String nomeDoHospital;
    private String cep;
    private String cidade;
    private int codigo;
    // TODO: Adicionar os Administradores
    // TODO: Adicionar os doadores

    public Hospital(String nomeDoHospital, String cep,String cidade){
        this.nomeDoHospital = nomeDoHospital;
        this.cep = cep;
        this.cidade = cidade;
    }

    public String getNomeDoHospital() {
        return nomeDoHospital;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNomeDoHospital(String novoNomeDoHospital) {
        this.nomeDoHospital = novoNomeDoHospital;
    }

    public void setCep(String novoCep) {
        this.cep = novoCep;
    }

    public void setCidade(String novaCidade) {
        this.cidade = novaCidade;
    }

    public void setCodigo(int novoCodigo) {
        this.codigo = novoCodigo;
    }

    public void criarHospital(String nomeDoHospital, String cep,String cidade, String url_banco, String usuario_banco, String senha_banco){
        // TODO: Adicionar tratativa de possíveis entradas erradas
        // TODO: adicionar tratativa de duplicatas
        try {
            Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);

            String sql = "INSERT INTO hospital(nome, cep, cidade) VALUES (?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nomeDoHospital);
            ps.setString(2, cep);
            ps.setString(3, cidade);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }

    //TODO: Adicionar o método consultarDoadoresVinculados que retorna List<Doador>

    public void exibeHospital(){
        System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", "id", "Nome", "CEP", "Cidade"));
        System.out.println(String.format("|%s|%s|%s|%s|", "-".repeat(6), "-".repeat(31), "-".repeat(11), "-".repeat(31)));
        System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", this.codigo, this.nomeDoHospital, this.cep, this.cidade));
    }

    public List<Hospital> listarHospitais(String url_banco, String usuario_banco, String senha_banco){
        List<Hospital> listaDeHospitais = new ArrayList<>();
        try {
            Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);

            String sql = "SELECT * FROM hospital;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", "id", "Nome", "CEP", "Cidade"));
            System.out.println(String.format("|%s|%s|%s|%s|", "-".repeat(6), "-".repeat(31), "-".repeat(11), "-".repeat(31)));
            while (rs.next()) {
                String codigo = rs.getString("id");
                String nome = rs.getString("nome");
                String cep = rs.getString("cep");
                String cidade = rs.getString("cidade");

                Hospital hosp = new Hospital(nome, cep, cidade);
                //TODO: Passar código como inteiro
                // hosp.setCodigo(codigo);

                listaDeHospitais.add(hosp);
                System.out.println(String.format("|%-5s | %-30s| %-10s| %-30s|", codigo, nome, cep, cidade));
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
        
        return listaDeHospitais;
    }

    public void removerHospital(String url_banco, String usuario_banco, String senha_banco){
        Scanner leitor = new Scanner(System.in);

        System.out.print("Digite o código do Hospital a ser removido: ");
        String hospitalARemover = leitor.nextLine();
        try {
            Connection conexao = DriverManager.getConnection(url_banco, usuario_banco, senha_banco);
            String sql = "DELETE FROM hospital WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(hospitalARemover.trim()));

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}