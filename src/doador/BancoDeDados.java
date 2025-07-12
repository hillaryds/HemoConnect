/*
    Classe temporaria para simular um banco de dados de doadores.
    Esta classe é usada apenas para testes e não deve ser considerada no projeto final.
 */
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private List<Doador> doadores;
    
    public BancoDeDados() {
        this.doadores = new ArrayList<>();
    }
    
    public void adicionarDoador(Doador doador) {
        doadores.add(doador);
    }
    
    public Doador buscarPorCpf(int cpf) {
        for (Doador doador : doadores) {
            if (doador.getCpf() == cpf) {
                return doador;
            }
        }
        return null; 
    }
    
    public List<Doador> listarTodos() {
        return new ArrayList<>(doadores);
    }
    
    public boolean cpfExiste(int cpf) {
        return buscarPorCpf(cpf) != null;
    }
    
    public boolean removerPorCpf(int cpf) {
        Doador doador = buscarPorCpf(cpf);
        if (doador != null) {
            doadores.remove(doador);
            return true;
        }
        return false;
    }

    public int getTotalDoadores() {
        return doadores.size();
    }
}
