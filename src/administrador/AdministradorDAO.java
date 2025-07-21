package administrador;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Administrador
 * Responsável por todas as operações de banco de dados
 */
public class AdministradorDAO {
    
    // Queries SQL preparadas
    private static final String INSERT_ADMINISTRADOR = 
        "INSERT INTO administrador (cargo_hospital, nome_administrador, login, senha, id_hospital) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_ADMINISTRADORES = 
        "SELECT id, cargo_hospital, nome_administrador, login, senha, id_hospital FROM administrador ORDER BY nome_administrador";
    
    private static final String SELECT_ADMINISTRADOR_BY_ID = 
        "SELECT id, cargo_hospital, nome_administrador, login, senha, id_hospital FROM administrador WHERE id = ?";
    
    private static final String SELECT_ADMINISTRADOR_BY_LOGIN = 
        "SELECT id, cargo_hospital, nome_administrador, login, senha, id_hospital FROM administrador WHERE login = ?";
    
    private static final String SELECT_ADMINISTRADOR_BY_LOGIN_SENHA = 
        "SELECT id, cargo_hospital, nome_administrador, login, senha, id_hospital FROM administrador WHERE login = ? AND senha = ?";
    
    private static final String UPDATE_ADMINISTRADOR = 
        "UPDATE administrador SET cargo_hospital = ?, nome_administrador = ?, login = ?, senha = ?, id_hospital = ? WHERE id = ?";
    
    private static final String DELETE_ADMINISTRADOR = 
        "DELETE FROM administrador WHERE id = ?";
    
    private static final String DELETE_ADMINISTRADOR_BY_LOGIN = 
        "DELETE FROM administrador WHERE login = ?";
    
    private static final String CHECK_LOGIN_EXISTS = 
        "SELECT COUNT(*) FROM administrador WHERE login = ? AND id != ?";
    
    private static final String SELECT_ADMINISTRADORES_BY_HOSPITAL = 
        "SELECT id, cargo_hospital, nome_administrador, login, senha, id_hospital FROM administrador WHERE id_hospital = ? ORDER BY nome_administrador";

    /**
     * Insere um novo administrador no banco de dados
     * @param administrador Administrador a ser inserido
     * @return Administrador com ID gerado pelo banco
     * @throws SQLException se houver erro na inserção
     */
    public static Administrador inserir(Administrador administrador) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_ADMINISTRADOR, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, administrador.getCargoHospital());
            stmt.setString(2, administrador.getNomeAdministrador());
            stmt.setString(3, administrador.getLogin());
            stmt.setString(4, administrador.getSenha());
            stmt.setLong(5, administrador.getIdHospital());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obter ID gerado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        administrador.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return administrador;
        }
    }
    
    /**
     * Busca todos os administradores
     * @return Lista de todos os administradores
     * @throws SQLException se houver erro na consulta
     */
    public static List<Administrador> buscarTodos() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Administrador> administradores = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_ADMINISTRADORES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                administradores.add(mapResultSetToAdministrador(rs));
            }
        }
        
        return administradores;
    }
    
    /**
     * Autentica um administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @return Administrador autenticado ou null se credenciais inválidas
     * @throws SQLException se houver erro na consulta
     */
    public static Administrador autenticar(String login, String senha) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ADMINISTRADOR_BY_LOGIN_SENHA)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdministrador(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Verifica se um login já existe no banco
     * @param login Login a ser verificado
     * @param excludeId ID a ser excluído da verificação (útil para updates)
     * @return true se login já existe, false caso contrário
     * @throws SQLException se houver erro na consulta
     */
    public static boolean loginExiste(String login, Long excludeId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(CHECK_LOGIN_EXISTS)) {
            stmt.setString(1, login);
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
     * Remove um administrador pelo ID
     * @param id ID do administrador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na remoção
     */
    public static boolean remover(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_ADMINISTRADOR)) {
            stmt.setLong(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Remove um administrador pelo login
     * @param login Login do administrador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na remoção
     */
    public static boolean removerPorLogin(String login) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_ADMINISTRADOR_BY_LOGIN)) {
            stmt.setString(1, login);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Busca administradores por hospital
     * @param idHospital ID do hospital
     * @return Lista de administradores do hospital
     * @throws SQLException se houver erro na consulta
     */
    public static List<Administrador> buscarPorHospital(Long idHospital) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Administrador> administradores = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ADMINISTRADORES_BY_HOSPITAL)) {
            stmt.setLong(1, idHospital);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    administradores.add(mapResultSetToAdministrador(rs));
                }
            }
        }
        
        return administradores;
    }
    
    /**
     * Mapeia um ResultSet para um objeto Administrador
     * @param rs ResultSet com dados do administrador
     * @return Administrador criado a partir do ResultSet
     * @throws SQLException se houver erro ao ler dados
     */
    private static Administrador mapResultSetToAdministrador(ResultSet rs) throws SQLException {
        return new Administrador(
            rs.getLong("id"),
            rs.getString("cargo_hospital"),
            rs.getString("nome_administrador"),
            rs.getString("login"),
            rs.getString("senha"),
            rs.getLong("id_hospital")
        );
    }
}