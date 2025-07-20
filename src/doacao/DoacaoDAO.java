package doacao;

import database.DatabaseConnection;
import triagem.Triagem;
import triagem.TriagemDAO;
import doador.Doador;
import doador.DoadorDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Doacao
 * Responsável por todas as operações de banco de dados relacionadas às doações
 * INTEGRADO com Triagem e Doador
 */
public class DoacaoDAO {

    // Queries SQL preparadas
    private static final String INSERT_DOACAO = "INSERT INTO doacao (data, hora, volume, triagem_id, doador_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_DOACOES = "SELECT d.id, d.data, d.hora, d.volume, d.triagem_id, d.doador_id, d.created_at, d.updated_at "
            +
            "FROM doacao d ORDER BY d.data DESC, d.hora DESC";

    private static final String SELECT_DOACAO_BY_ID = "SELECT d.id, d.data, d.hora, d.volume, d.triagem_id, d.doador_id, d.created_at, d.updated_at "
            +
            "FROM doacao d WHERE d.id = ?";

    private static final String SELECT_DOACOES_BY_DATE = "SELECT d.id, d.data, d.hora, d.volume, d.triagem_id, d.doador_id, d.created_at, d.updated_at "
            +
            "FROM doacao d WHERE d.data = ? ORDER BY d.hora DESC";

    private static final String SELECT_DOACOES_BY_DOADOR = "SELECT d.id, d.data, d.hora, d.volume, d.triagem_id, d.doador_id, d.created_at, d.updated_at "
            +
            "FROM doacao d WHERE d.doador_id = ? ORDER BY d.data DESC";

    private static final String SELECT_DOACOES_BY_MES = "SELECT d.id, d.data, d.hora, d.volume, d.triagem_id, d.doador_id, d.created_at, d.updated_at "
            +
            "FROM doacao d WHERE EXTRACT(MONTH FROM d.data) = ? AND EXTRACT(YEAR FROM d.data) = ? " +
            "ORDER BY d.data DESC, d.hora DESC";

    private static final String COUNT_DOACOES_BY_DATE = "SELECT COUNT(*) FROM doacao WHERE data = ?";

    private static final String COUNT_DOACOES_BY_MES = "SELECT COUNT(*) FROM doacao WHERE EXTRACT(MONTH FROM data) = ? AND EXTRACT(YEAR FROM data) = ?";

    private static final String SELECT_VOLUME_TOTAL_BY_DATE = "SELECT SUM(volume) FROM doacao WHERE data = ?";

    private static final String SELECT_VOLUME_TOTAL_BY_MES = "SELECT SUM(volume) FROM doacao WHERE EXTRACT(MONTH FROM data) = ? AND EXTRACT(YEAR FROM data) = ?";

    private static final String UPDATE_DOACAO = "UPDATE doacao SET data = ?, hora = ?, volume = ? WHERE id = ?";

    private static final String DELETE_DOACAO = "DELETE FROM doacao WHERE id = ?";

    /**
     * Insere uma nova doação no banco de dados
     * 
     * @param doacao Objeto doação a ser inserido
     * @return Doacao com ID gerado ou null se erro
     * @throws SQLException se houver erro na operação
     */
    public static Doacao inserir(Doacao doacao) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_DOACAO, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, doacao.getData());
            stmt.setTime(2, doacao.getHora());
            stmt.setDouble(3, doacao.getVolume());
            stmt.setLong(4, doacao.getTriagemId());
            stmt.setLong(5, doacao.getDoadorId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    Long novoId = keys.getLong(1);

                    // Retornar nova instância com ID
                    return new Doacao(novoId, doacao.getData(), doacao.getHora(),
                            doacao.getVolume(), doacao.getTriagemId(), doacao.getDoadorId());
                }
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir doação: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca todas as doações
     * 
     * @return Lista de todas as doações
     * @throws SQLException se houver erro na operação
     */
    public static List<Doacao> buscarTodas() throws SQLException {
        List<Doacao> doacoes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_DOACOES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doacao doacao = criarDoacaoFromResultSet(rs);
                doacoes.add(doacao);
            }
        }

        return doacoes;
    }

    /**
     * Busca doação por ID
     * 
     * @param id ID da doação
     * @return Doacao encontrada ou null
     * @throws SQLException se houver erro na operação
     */
    public static Doacao buscarPorId(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_DOACAO_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return criarDoacaoFromResultSet(rs);
            }
        }

        return null;
    }

    /**
     * Busca doações por data específica
     * 
     * @param data Data das doações
     * @return Lista de doações da data
     * @throws SQLException se houver erro na operação
     */
    public static List<Doacao> buscarPorData(Date data) throws SQLException {
        List<Doacao> doacoes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_DOACOES_BY_DATE)) {

            stmt.setDate(1, data);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Doacao doacao = criarDoacaoFromResultSet(rs);
                doacoes.add(doacao);
            }
        }

        return doacoes;
    }

    /**
     * Busca doações por doador
     * 
     * @param doadorId ID do doador
     * @return Lista de doações do doador
     * @throws SQLException se houver erro na operação
     */
    public static List<Doacao> buscarPorDoador(Long doadorId) throws SQLException {
        List<Doacao> doacoes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_DOACOES_BY_DOADOR)) {

            stmt.setLong(1, doadorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Doacao doacao = criarDoacaoFromResultSet(rs);
                doacoes.add(doacao);
            }
        }

        return doacoes;
    }

    /**
     * Busca doações por mês e ano
     * 
     * @param mes Mês (1-12)
     * @param ano Ano
     * @return Lista de doações do mês
     * @throws SQLException se houver erro na operação
     */
    public static List<Doacao> buscarPorMes(int mes, int ano) throws SQLException {
        List<Doacao> doacoes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_DOACOES_BY_MES)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Doacao doacao = criarDoacaoFromResultSet(rs);
                doacoes.add(doacao);
            }
        }

        return doacoes;
    }

    /**
     * Conta doações por data
     * 
     * @param data Data para contagem
     * @return Número de doações na data
     * @throws SQLException se houver erro na operação
     */
    public static int contarDoacoesPorData(Date data) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(COUNT_DOACOES_BY_DATE)) {

            stmt.setDate(1, data);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    /**
     * Conta doações por mês
     * 
     * @param mes Mês (1-12)
     * @param ano Ano
     * @return Número de doações no mês
     * @throws SQLException se houver erro na operação
     */
    public static int contarDoacoesPorMes(int mes, int ano) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(COUNT_DOACOES_BY_MES)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    /**
     * Calcula volume total por data
     * 
     * @param data Data para cálculo
     * @return Volume total em ml
     * @throws SQLException se houver erro na operação
     */
    public static double calcularVolumeTotalPorData(Date data) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_VOLUME_TOTAL_BY_DATE)) {

            stmt.setDate(1, data);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }

        return 0.0;
    }

    /**
     * Calcula volume total por mês
     * 
     * @param mes Mês (1-12)
     * @param ano Ano
     * @return Volume total em ml
     * @throws SQLException se houver erro na operação
     */
    public static double calcularVolumeTotalPorMes(int mes, int ano) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_VOLUME_TOTAL_BY_MES)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }

        return 0.0;
    }

    /**
     * Atualiza uma doação existente
     * 
     * @param id     ID da doação
     * @param data   Nova data
     * @param hora   Nova hora
     * @param volume Novo volume
     * @return true se atualizada com sucesso
     * @throws SQLException se houver erro na operação
     */
    public static boolean atualizarDoacao(Long id, Date data, Time hora, double volume) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE_DOACAO)) {

            stmt.setDate(1, data);
            stmt.setTime(2, hora);
            stmt.setDouble(3, volume);
            stmt.setLong(4, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    /**
     * Remove uma doação
     * 
     * @param id ID da doação
     * @return true se removida com sucesso
     * @throws SQLException se houver erro na operação
     */
    public static boolean removerDoacao(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(DELETE_DOACAO)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    /**
     * Atualiza uma doação existente
     * 
     * @param doacao Doação com novos dados
     * @return true se atualizada com sucesso
     * @throws SQLException se houver erro na operação
     */
    public static boolean atualizar(Doacao doacao) throws SQLException {
        return atualizarDoacao(doacao.getId(), doacao.getData(), doacao.getHora(), doacao.getVolume());
    }

    /**
     * Deleta uma doação
     * 
     * @param id ID da doação
     * @return true se removida com sucesso
     * @throws SQLException se houver erro na operação
     */
    public static boolean deletar(Long id) throws SQLException {
        return removerDoacao(id);
    }

    /**
     * Carrega dados relacionados (Triagem e Doador) para uma doação
     * 
     * @param doacao Doação para carregar dados
     * @return Doacao com dados relacionados carregados
     */
    public static Doacao carregarDadosRelacionados(Doacao doacao) {
        try {
            // Carregar triagem
            if (doacao.getTriagemId() != null) {
                Triagem triagem = TriagemDAO.buscarPorId(doacao.getTriagemId());
                doacao.setTriagem(triagem);
            }

            // Carregar doador
            if (doacao.getDoadorId() != null) {
                Doador doador = DoadorDAO.buscarPorId(doacao.getDoadorId());
                doacao.setDoador(doador);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados relacionados: " + e.getMessage());
        }

        return doacao;
    }

    /**
     * Método auxiliar para criar Doacao a partir do ResultSet
     * 
     * @param rs ResultSet com dados da doação
     * @return Objeto Doacao
     * @throws SQLException se houver erro ao acessar ResultSet
     */
    private static Doacao criarDoacaoFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Date data = rs.getDate("data");
        Time hora = rs.getTime("hora");
        double volume = rs.getDouble("volume");
        Long triagemId = rs.getLong("triagem_id");
        Long doadorId = rs.getLong("doador_id");

        return new Doacao(id, data, hora, volume, triagemId, doadorId);
    }

    /**
     * Obtém estatísticas das doações de um dia específico
     */
    public static java.util.Map<String, Object> obterEstatisticasDia(Date data) throws SQLException {
        java.util.Map<String, Object> resultado = new java.util.HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Total e volume do dia
            String sql = "SELECT COUNT(*) as total, COALESCE(SUM(volume), 0) as volume_total FROM doacao WHERE data = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, data);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    resultado.put("total", rs.getInt("total"));
                    resultado.put("volumeTotal", rs.getDouble("volume_total"));
                }
            }
        }

        return resultado;
    }

    /**
     * Obtém estatísticas das doações de um mês específico
     */
    public static java.util.Map<String, Object> obterEstatisticasMes(int mes, int ano) throws SQLException {
        java.util.Map<String, Object> resultado = new java.util.HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT COUNT(*) as total, COALESCE(SUM(volume), 0) as volume_total " +
                    "FROM doacao WHERE EXTRACT(MONTH FROM data) = ? AND EXTRACT(YEAR FROM data) = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, mes);
                stmt.setInt(2, ano);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    resultado.put("total", rs.getInt("total"));
                    resultado.put("volumeTotal", rs.getDouble("volume_total"));
                }
            }
        }

        return resultado;
    }

    /**
     * Obtém estatísticas gerais do sistema
     */
    public static java.util.Map<String, Object> obterEstatisticasGerais() throws SQLException {
        java.util.Map<String, Object> resultado = new java.util.HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Total geral
            String sql1 = "SELECT COUNT(*) as total, COALESCE(SUM(volume), 0) as volume_total FROM doacao";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                ResultSet rs1 = stmt1.executeQuery();
                if (rs1.next()) {
                    resultado.put("totalGeral", rs1.getInt("total"));
                    resultado.put("volumeGeral", rs1.getDouble("volume_total"));
                }
            }

            // Doações hoje
            String sql2 = "SELECT COUNT(*) as hoje FROM doacao WHERE data = CURRENT_DATE";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    resultado.put("hoje", rs2.getInt("hoje"));
                }
            }

            // Doações este mês
            String sql3 = "SELECT COUNT(*) as mes FROM doacao WHERE EXTRACT(MONTH FROM data) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM data) = EXTRACT(YEAR FROM CURRENT_DATE)";
            try (PreparedStatement stmt3 = conn.prepareStatement(sql3)) {
                ResultSet rs3 = stmt3.executeQuery();
                if (rs3.next()) {
                    resultado.put("esteMes", rs3.getInt("mes"));
                }
            }
        }

        return resultado;
    }
}
