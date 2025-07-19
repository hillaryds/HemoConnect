package hospital;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {
    
    // Queries SQL preparadas
    private static final String INSERT_HOSPITAL = 
        "INSERT INTO hospital (nome, cep, cidade) VALUES (?, ?, ?)";
    
    private static final String SELECT_ALL_HOSPITAIS = 
        "SELECT id, nome, cep, cidade FROM hospital ORDER BY nome";
    
    private static final String SELECT_HOSPITAL_BY_ID = 
        "SELECT id, nome, cep, cidade FROM hospital WHERE id = ?";
    
    private static final String SELECT_HOSPITAL_BY_NOME = 
        "SELECT id, nome, cep, cidade FROM hospital WHERE nome ILIKE ?";
    
    private static final String SELECT_HOSPITAIS_BY_CIDADE = 
        "SELECT id, nome, cep, cidade FROM hospital WHERE cidade ILIKE ? ORDER BY nome";
    
    private static final String UPDATE_HOSPITAL = 
        "UPDATE hospital SET nome = ?, cep = ?, cidade = ? WHERE id = ?";
    
    private static final String DELETE_HOSPITAL = 
        "DELETE FROM hospital WHERE id = ?";
    
    private static final String CHECK_NOME_EXISTS = 
        "SELECT COUNT(*) FROM hospital WHERE nome ILIKE ? AND id != ?";

    /**
     * Insere um novo hospital no banco de dados
     * @param hospital Hospital a ser inserido
     * @return Hospital com ID gerado pelo banco
     * @throws SQLException se houver erro na inserção
     */
    public static Hospital inserir(Hospital hospital) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_HOSPITAL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hospital.getNome());
            stmt.setString(2, hospital.getCep());
            stmt.setString(3, hospital.getCidade());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
               
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hospital.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return hospital;
        }
    }
    
    /**
     * Busca todos os hospitais
     * @return Lista de todos os hospitais
     * @throws SQLException se houver erro na consulta
     */
    public static List<Hospital> buscarTodos() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Hospital> hospitais = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_HOSPITAIS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                hospitais.add(mapResultSetToHospital(rs));
            }
        }
        
        return hospitais;
    }
    
    /**
     * Busca hospital por ID
     * @param id ID do hospital
     * @return Hospital encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public static Hospital buscarPorId(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_HOSPITAL_BY_ID)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHospital(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Busca hospital por nome (busca parcial, case-insensitive)
     * @param nome Nome do hospital
     * @return Hospital encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public static Hospital buscarPorNome(String nome) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_HOSPITAL_BY_NOME)) {
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHospital(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Busca hospitais por cidade
     * @param cidade Cidade dos hospitais
     * @return Lista de hospitais da cidade
     * @throws SQLException se houver erro na consulta
     */
    public static List<Hospital> buscarPorCidade(String cidade) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Hospital> hospitais = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_HOSPITAIS_BY_CIDADE)) {
            stmt.setString(1, "%" + cidade + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hospitais.add(mapResultSetToHospital(rs));
                }
            }
        }
        
        return hospitais;
    }
    
    /**
     * Verifica se um nome de hospital já existe no banco
     * @param nome Nome a ser verificado
     * @param excludeId ID a ser excluído da verificação (útil para updates)
     * @return true se nome já existe, false caso contrário
     * @throws SQLException se houver erro na consulta
     */
    public static boolean nomeExiste(String nome, Long excludeId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(CHECK_NOME_EXISTS)) {
            stmt.setString(1, nome);
            stmt.setLong(2, excludeId != null ? excludeId : 0);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Atualiza um hospital existente
     * @param hospital Hospital com dados atualizados
     * @return true se atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na atualização
     */
    public static boolean atualizar(Hospital hospital) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_HOSPITAL)) {
            stmt.setString(1, hospital.getNome());
            stmt.setString(2, hospital.getCep());
            stmt.setString(3, hospital.getCidade());
            stmt.setLong(4, hospital.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Remove um hospital pelo ID
     * @param id ID do hospital a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na remoção
     */
    public static boolean remover(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_HOSPITAL)) {
            stmt.setLong(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Mapeia um ResultSet para um objeto Hospital
     * @param rs ResultSet com dados do hospital
     * @return Hospital criado a partir do ResultSet
     * @throws SQLException se houver erro ao ler dados
     */
    private static Hospital mapResultSetToHospital(ResultSet rs) throws SQLException {
        return new Hospital(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("cep"),
            rs.getString("cidade")
        );
    }
}