package triagem;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Controller - Classe TriagemControllerDB
 * Responsável pela lógica de negócio, manipulação de dados e coordenação entre Model e View
 * Integrado com PostgreSQL através do DAO
 */
public class TriagemControllerDB {
    
    /**
     * Cria uma nova triagem e a persiste no PostgreSQL
     * @return A triagem criada com ID gerado pelo banco ou null se houve erro
     */
    public static Triagem criarTriagem(int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso, Date date) {
        try {
            boolean status = Triagem.verificarCriteriosTriagem(batimentosPorMinuto, pressaoArterial, temperatura, peso);
            
            Triagem triagem = new Triagem(batimentosPorMinuto, pressaoArterial, temperatura, peso, status, date);
            
            // Persiste no PostgreSQL através do DAO
            return TriagemDAO.inserir(triagem);
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar triagem: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cria uma nova triagem e exibe o resultado
     */
    public static void criarTriagemComMensagem(int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso, Date date) {
        Triagem triagem = criarTriagem(batimentosPorMinuto, pressaoArterial, temperatura, peso, date);
        
        if (triagem != null) {
            TriagemView.exibirMensagemTriagemCriada(triagem.isStatus());
        } else {
            TriagemView.exibirMensagemErro("Erro ao criar triagem");
        }
    }
    
    /**
     * Lista todas as triagens realizadas em uma data específica
     * @return Lista de triagens ou lista vazia se houve erro
     */
    public static List<Triagem> listarTriagemDate(Date date) {
        try {
            return TriagemDAO.buscarPorData(date);
        } catch (SQLException e) {
            System.err.println("Erro ao listar triagens por data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Exibe todas as triagens realizadas no dia atual
     */
    public static void exibirTriagensDodia() {
        try {
            Date hoje = new Date(System.currentTimeMillis());
            List<Triagem> triagensHoje = TriagemDAO.buscarPorData(hoje);
            
            TriagemView.exibirTriagensDodia(hoje, triagensHoje);
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar triagens do dia: " + e.getMessage());
            TriagemView.exibirMensagemErro("Erro ao buscar triagens do dia: " + e.getMessage());
        }
    }
    
    /**
     * Exibe todas as triagens realizadas no mês atual
     */
    public static void exibirTriagensDoMes() {
        try {
            Calendar cal = Calendar.getInstance();
            int mesAtual = cal.get(Calendar.MONTH) + 1; // Calendar usa 0-11, PostgreSQL usa 1-12
            int anoAtual = cal.get(Calendar.YEAR);
            
            List<Triagem> triagensDoMes = TriagemDAO.buscarPorMes(mesAtual, anoAtual);
            
            TriagemView.exibirTriagensDoMes(mesAtual, anoAtual, triagensDoMes);
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar triagens do mês: " + e.getMessage());
            TriagemView.exibirMensagemErro("Erro ao buscar triagens do mês: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza os dados de uma triagem existente
     * @return true se atualização foi bem-sucedida, false caso contrário
     */
    public static boolean atualizarTriagem(Triagem triagem, int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso) {
        try {
            triagem.setBatimentosPorMinuto(batimentosPorMinuto);
            triagem.setPressaoArterial(pressaoArterial);
            triagem.setTemperatura(temperatura);
            triagem.setPeso(peso);
            
            // Recalcula o status baseado nos novos dados
            boolean novoStatus = Triagem.verificarCriteriosTriagem(batimentosPorMinuto, pressaoArterial, temperatura, peso);
            triagem.setStatus(novoStatus);
            
            // Atualiza no PostgreSQL
            return TriagemDAO.atualizar(triagem);
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar triagem: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove uma triagem do sistema
     * @return true se remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerTriagem(Triagem triagem) {
        try {
            if (triagem.getId() == null) {
                return false;
            }
            
            return TriagemDAO.remover(triagem.getId());
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover triagem: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtém todas as triagens do sistema
     * @return Lista de todas as triagens ou lista vazia se houve erro
     */
    public static List<Triagem> obterTodasTriagens() {
        try {
            return TriagemDAO.buscarTodas();
        } catch (SQLException e) {
            System.err.println("Erro ao obter todas as triagens: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Busca uma triagem específica por ID
     * @return Triagem encontrada ou null se não existe ou houve erro
     */
    public static Triagem buscarTriagemPorId(Long id) {
        try {
            return TriagemDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar triagem por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Exibe todas as triagens realizadas em uma data específica
     */
    public static void exibirTriagensDeData(Date data) {
        try {
            List<Triagem> triagensData = TriagemDAO.buscarPorData(data);
            TriagemView.exibirTriagensDodia(data, triagensData);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar triagens da data: " + e.getMessage());
            TriagemView.exibirMensagemErro("Erro ao buscar triagens da data: " + e.getMessage());
        }
    }
    
    /**
     * Exibe todas as triagens realizadas em um mês específico
     */
    public static void exibirTriagensDeMes(int mes, int ano) {
        try {
            List<Triagem> triagensDoMes = TriagemDAO.buscarPorMes(mes, ano);
            TriagemView.exibirTriagensDoMes(mes, ano, triagensDoMes);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar triagens do mês: " + e.getMessage());
            TriagemView.exibirMensagemErro("Erro ao buscar triagens do mês: " + e.getMessage());
        }
    }
    
    /**
     * Exibe uma triagem específica
     */
    public static void exibirTriagem(Triagem triagem) {
        TriagemView.exibirTriagem(triagem);
    }
    
    /**
     * Exibe uma triagem específica por ID
     */
    public static void exibirTriagemPorId(Long id) {
        Triagem triagem = buscarTriagemPorId(id);
        
        if (triagem != null) {
            TriagemView.exibirTriagem(triagem);
        } else {
            TriagemView.exibirMensagemErro("Triagem não encontrada com ID: " + id);
        }
    }
    
    /**
     * Exibe todas as triagens do sistema
     */
    public static void exibirTodasTriagens() {
        List<Triagem> todasTriagens = obterTodasTriagens();
        TriagemView.exibirListaTriagens(todasTriagens);
    }
    
    /**
     * Atualiza uma triagem e exibe o resultado
     */
    public static void atualizarTriagemComMensagem(Triagem triagem, int batimentosPorMinuto, String pressaoArterial, double temperatura, double peso) {
        boolean sucesso = atualizarTriagem(triagem, batimentosPorMinuto, pressaoArterial, temperatura, peso);
        
        if (sucesso) {
            TriagemView.exibirMensagemTriagemAtualizada(triagem.isStatus());
        } else {
            TriagemView.exibirMensagemErro("Erro ao atualizar triagem");
        }
    }
    
    /**
     * Remove uma triagem e exibe o resultado
     */
    public static void removerTriagemComMensagem(Triagem triagem) {
        boolean sucesso = removerTriagem(triagem);
        TriagemView.exibirMensagemTriagemRemovida(sucesso);
    }
    
    // Métodos auxiliares para obter dados específicos
    
    /**
     * Exibe total de triagens do dia
     */
    public static void totalDeTriagemDia() {
        try {
            Date hoje = new Date(System.currentTimeMillis());
            List<Triagem> triagensHoje = TriagemDAO.buscarPorData(hoje);
            
            int total = triagensHoje.size();
            int aprovadas = (int) triagensHoje.stream().filter(Triagem::isStatus).count();
            int reprovadas = total - aprovadas;
            
            System.out.println("TOTAL DE TRIAGENS DO DIA - " + hoje);
            System.out.println("Total: " + total + " triagens");
            System.out.println("Aprovadas: " + aprovadas + " (" + String.format("%.1f", (aprovadas * 100.0 / Math.max(total, 1))) + "%)");
            System.out.println("Reprovadas: " + reprovadas + " (" + String.format("%.1f", (reprovadas * 100.0 / Math.max(total, 1))) + "%)");
            
        } catch (SQLException e) {
            System.err.println("Erro ao calcular total de triagens do dia: " + e.getMessage());
        }
    }
    
    /**
     * Exibe total de triagens do mês
     */
    public static void totalDeTriagemMes() {
        try {
            Calendar cal = Calendar.getInstance();
            int mesAtual = cal.get(Calendar.MONTH) + 1;
            int anoAtual = cal.get(Calendar.YEAR);
            
            List<Triagem> triagensDoMes = TriagemDAO.buscarPorMes(mesAtual, anoAtual);
            
            int total = triagensDoMes.size();
            int aprovadas = (int) triagensDoMes.stream().filter(Triagem::isStatus).count();
            int reprovadas = total - aprovadas;
            
            String[] nomesMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                                  "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            
            System.out.println("TOTAL DE TRIAGENS DO MES - " + nomesMeses[mesAtual-1] + "/" + anoAtual);
            System.out.println("Total: " + total + " triagens");
            System.out.println("Aprovadas: " + aprovadas + " (" + String.format("%.1f", (aprovadas * 100.0 / Math.max(total, 1))) + "%)");
            System.out.println("Reprovadas: " + reprovadas + " (" + String.format("%.1f", (reprovadas * 100.0 / Math.max(total, 1))) + "%)");
            System.out.println("Media diaria: " + String.format("%.1f", total / 30.0) + " triagens/dia");
            
        } catch (SQLException e) {
            System.err.println("Erro ao calcular total de triagens do mês: " + e.getMessage());
        }
    }
    
    /**
     * Obtém a data atual no formato SQL
     */
    public static Date obterDataAtual() {
        return new Date(System.currentTimeMillis());
    }
    
    /**
     * Obtém o mês e ano atuais
     * @return array [mes, ano] onde mes é 1-12
     */
    public static int[] obterMesAnoAtual() {
        Calendar cal = Calendar.getInstance();
        return new int[]{cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)};
    }
}
