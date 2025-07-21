package triagem;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Triagem
 * Responsável por todas as operações de banco de dados
 */
public class TriagemDAO {
    
    // Queries SQL preparadas
    private static final String INSERT_TRIAGEM = 
        "INSERT INTO triagem (batimentos_por_minuto, pressao_arterial, temperatura, peso, status, data) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_TRIAGENS = 
        "SELECT id, batimentos_por_minuto, pressao_arterial, temperatura, peso, status, data, created_at, updated_at FROM triagem ORDER BY data DESC, created_at DESC";
    
    private static final String SELECT_TRIAGEM_BY_ID = 
        "SELECT id, batimentos_por_minuto, pressao_arterial, temperatura, peso, status, data, created_at, updated_at FROM triagem WHERE id = ?";
    
    private static final String SELECT_TRIAGENS_BY_DATE = 
        "SELECT id, batimentos_por_minuto, pressao_arterial, temperatura, peso, status, data, created_at, updated_at FROM triagem WHERE data = ? ORDER BY created_at DESC";
    
    private static final String UPDATE_TRIAGEM = 
        "UPDATE triagem SET batimentos_por_minuto = ?, pressao_arterial = ?, temperatura = ?, peso = ?, status = ? WHERE id = ?";
    
    private static final String DELETE_TRIAGEM = 
        "DELETE FROM triagem WHERE id = ?";
    
    /**
     * Insere uma nova triagem no banco de dados
     * @param triagem Triagem a ser inserida
     * @return Triagem com ID gerado pelo banco
     * @throws SQLException se houver erro na inserção
     */
    public static Triagem inserir(Triagem triagem) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_TRIAGEM, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, triagem.getBatimentosPorMinuto());
            stmt.setString(2, triagem.getPressaoArterial());
            stmt.setDouble(3, triagem.getTemperatura());
            stmt.setDouble(4, triagem.getPeso());
            stmt.setBoolean(5, triagem.isStatus());
            stmt.setDate(6, triagem.getDate());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obter ID gerado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        triagem.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return triagem;
        }
    }
    
    /**
     * Busca todas as triagens
     * @return Lista de todas as triagens
     * @throws SQLException se houver erro na consulta
     */
    public static List<Triagem> buscarTodas() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Triagem> triagens = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TRIAGENS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                triagens.add(mapResultSetToTriagem(rs));
            }
        }
        
        return triagens;
    }
    
    /**
     * Busca triagem por ID
     * @param id ID da triagem
     * @return Triagem encontrada ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public static Triagem buscarPorId(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TRIAGEM_BY_ID)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTriagem(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Busca triagens por data
     * @param data Data a ser consultada
     * @return Lista de triagens da data especificada
     * @throws SQLException se houver erro na consulta
     */
    public static List<Triagem> buscarPorData(Date data) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Triagem> triagens = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TRIAGENS_BY_DATE)) {
            stmt.setDate(1, data);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    triagens.add(mapResultSetToTriagem(rs));
                }
            }
        }
        
        return triagens;
    }
    
    /**
     * Busca triagens por mês e ano
     */
    public static List<Triagem> buscarPorMes(int mes, int ano) throws SQLException {
        String sql = "SELECT * FROM triagem WHERE EXTRACT(MONTH FROM data) = ? AND EXTRACT(YEAR FROM data) = ? ORDER BY data DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Triagem> triagens = new ArrayList<>();
                while (rs.next()) {
                    triagens.add(mapResultSetToTriagem(rs));
                }
                return triagens;
            }
        }
    }
    
    /**
     * Busca estatísticas por data
     * @param data Data a ser consultada
     * @return Array com [total, aprovadas, reprovadas]
     * @throws SQLException se houver erro na consulta
     */
    public static int[] buscarEstatisticasPorData(Date data) throws SQLException {
        String sql = "SELECT COUNT(*) as total, " +
                    "SUM(CASE WHEN status = true THEN 1 ELSE 0 END) as aprovadas, " +
                    "SUM(CASE WHEN status = false THEN 1 ELSE 0 END) as reprovadas " +
                    "FROM triagem WHERE data = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, data);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new int[] {
                        rs.getInt("total"),
                        rs.getInt("aprovadas"),
                        rs.getInt("reprovadas")
                    };
                }
            }
        }
        
        return new int[] {0, 0, 0};
    }
    
    /**
     * Busca estatísticas por mês
     * @param mes Mês (1-12)
     * @param ano Ano
     * @return Array com [total, aprovadas, reprovadas]
     * @throws SQLException se houver erro na consulta
     */
    public static int[] buscarEstatisticasPorMes(int mes, int ano) throws SQLException {
        String sql = "SELECT COUNT(*) as total, " +
                    "SUM(CASE WHEN status = true THEN 1 ELSE 0 END) as aprovadas, " +
                    "SUM(CASE WHEN status = false THEN 1 ELSE 0 END) as reprovadas " +
                    "FROM triagem WHERE EXTRACT(MONTH FROM data) = ? AND EXTRACT(YEAR FROM data) = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new int[] {
                        rs.getInt("total"),
                        rs.getInt("aprovadas"),
                        rs.getInt("reprovadas")
                    };
                }
            }
        }
        
        return new int[] {0, 0, 0};
    }
    
    /**
     * Atualiza uma triagem existente
     * @param triagem Triagem com dados atualizados
     * @return true se a atualização foi bem-sucedida
     * @throws SQLException se houver erro na atualização
     */
    public static boolean atualizar(Triagem triagem) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_TRIAGEM)) {
            stmt.setInt(1, triagem.getBatimentosPorMinuto());
            stmt.setString(2, triagem.getPressaoArterial());
            stmt.setDouble(3, triagem.getTemperatura());
            stmt.setDouble(4, triagem.getPeso());
            stmt.setBoolean(5, triagem.isStatus());
            stmt.setLong(6, triagem.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Remove uma triagem e todas as doações associadas (CASCADE)
     * ESTE É O MÉTODO RECOMENDADO PARA DELETAR TRIAGENS
     * REGRA DE NEGÓCIO: Se uma triagem é deletada, suas doações dependentes também devem ser
     * @param id ID da triagem a ser removida
     * @return true se a remoção foi bem-sucedida
     * @throws SQLException se houver erro na remoção
     */
    public static boolean remover(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            conn.setAutoCommit(false);
            
            String countDoacoes = "SELECT COUNT(*) FROM doacao WHERE triagem_id = ?";
            int totalDoacoes = 0;
            
            try (PreparedStatement countStmt = conn.prepareStatement(countDoacoes)) {
                countStmt.setLong(1, id);
                try (ResultSet rs = countStmt.executeQuery()) {
                    if (rs.next()) {
                        totalDoacoes = rs.getInt(1);
                    }
                }
            }
            
            if (totalDoacoes > 0) {
                String deleteDoacoes = "DELETE FROM doacao WHERE triagem_id = ?";
                try (PreparedStatement stmtDoacoes = conn.prepareStatement(deleteDoacoes)) {
                    stmtDoacoes.setLong(1, id);
                    int doacoesDeletadas = stmtDoacoes.executeUpdate();
                    System.out.println("Doações removidas em cascata: " + doacoesDeletadas + " de " + totalDoacoes);
                }
            }
            
            try (PreparedStatement stmtTriagem = conn.prepareStatement(DELETE_TRIAGEM)) {
                stmtTriagem.setLong(1, id);
                int triagensDeletadas = stmtTriagem.executeUpdate();
                
                if (triagensDeletadas > 0) {
                    conn.commit(); // Confirmar transação
                    if (totalDoacoes > 0) {
                        System.out.println("Triagem ID " + id + " removida com " + totalDoacoes + " doação(ões) em cascata");
                    } else {
                        System.out.println("Triagem ID " + id + " removida (sem doações associadas)");
                    }
                    return true;
                } else {
                    conn.rollback(); // Reverter se triagem não foi encontrada
                    System.out.println("Triagem ID " + id + " não encontrada para remoção");
                    return false;
                }
            }
            
        } catch (SQLException e) {
            try {
                conn.rollback(); // Reverter em caso de erro
                System.err.println("Erro ao remover triagem ID " + id + ": " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Erro crítico no rollback: " + rollbackEx.getMessage());
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e) {
                System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
            }
        }
    }
    
    /**
     * Mapeia ResultSet para objeto Triagem
     * @param rs ResultSet do banco
     * @return Objeto Triagem mapeado
     * @throws SQLException se houver erro no mapeamento
     */
    private static Triagem mapResultSetToTriagem(ResultSet rs) throws SQLException {
        return new Triagem(
            rs.getLong("id"),
            rs.getInt("batimentos_por_minuto"),
            rs.getString("pressao_arterial"),
            rs.getDouble("temperatura"),
            rs.getDouble("peso"),
            rs.getBoolean("status"),
            rs.getDate("data")
        );
    }
}
