package doador;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoadorDAO {
    private static final String INSERT_DOADOR = 
        "INSERT INTO doador (nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_DOADORES = 
        "SELECT id, nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade, ultima_doacao FROM doador ORDER BY nome";
    
    private static final String SELECT_DOADOR_BY_ID = 
        "SELECT id, nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade, ultima_doacao FROM doador WHERE id = ?";
    
    private static final String SELECT_DOADOR_BY_CPF = 
        "SELECT id, nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade, ultima_doacao FROM doador WHERE cpf = ?";
    
    private static final String SELECT_DOADORES_BY_TIPO_SANGUINEO = 
        "SELECT id, nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade, ultima_doacao FROM doador WHERE tipo_sanguineo = ? ORDER BY nome";
    
    private static final String SELECT_DOADORES_BY_CIDADE = 
        "SELECT id, nome, cpf, sexo, tipo_sanguineo, data_nascimento, telefone, bairro, nacionalidade, cidade, ultima_doacao FROM doador WHERE cidade ILIKE ? ORDER BY nome";
    
    private static final String UPDATE_DOADOR = 
        "UPDATE doador SET nome = ?, cpf = ?, sexo = ?, tipo_sanguineo = ?, data_nascimento = ?, telefone = ?, bairro = ?, nacionalidade = ?, cidade = ? WHERE id = ?";
    
    private static final String UPDATE_ULTIMA_DOACAO = 
        "UPDATE doador SET ultima_doacao = ? WHERE id = ?";
    
    private static final String DELETE_DOADOR = 
        "DELETE FROM doador WHERE id = ?";
    
    private static final String DELETE_DOADOR_BY_CPF = 
        "DELETE FROM doador WHERE cpf = ?";
    
    private static final String CHECK_CPF_EXISTS = 
        "SELECT COUNT(*) FROM doador WHERE cpf = ? AND id != ?";

    /**
     * Insere um novo doador no banco de dados
     * @param doador Doador a ser inserido
     * @return Doador com ID gerado pelo banco
     * @throws SQLException se houver erro na inserção
     */
    public static Doador inserir(Doador doador) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_DOADOR, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, doador.getNome());
            stmt.setLong(2, doador.getCpf());
            stmt.setString(3, doador.getSexo());
            stmt.setString(4, doador.getTipoSanguineo());
            stmt.setDate(5, doador.getDataNascimento());
            stmt.setLong(6, doador.getTelefone());
            stmt.setString(7, doador.getBairro());
            stmt.setString(8, doador.getNacionalidade());
            stmt.setString(9, doador.getCidade());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obter ID gerado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doador.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return doador;
        }
    }
    
    /**
     * Busca todos os doadores
     * @return Lista de todos os doadores
     * @throws SQLException se houver erro na consulta
     */
    public static List<Doador> buscarTodos() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Doador> doadores = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_DOADORES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                doadores.add(mapResultSetToDoador(rs));
            }
        }
        
        return doadores;
    }
    
    /**
     * Busca doador por ID
     * @param id ID do doador
     * @return Doador encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public static Doador buscarPorId(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DOADOR_BY_ID)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoador(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Busca doador por CPF
     * @param cpf CPF do doador
     * @return Doador encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public static Doador buscarPorCpf(Long cpf) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DOADOR_BY_CPF)) {
            stmt.setLong(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoador(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Busca doadores por tipo sanguíneo
     * @param tipoSanguineo Tipo sanguíneo
     * @return Lista de doadores com o tipo sanguíneo especificado
     * @throws SQLException se houver erro na consulta
     */
    public static List<Doador> buscarPorTipoSanguineo(String tipoSanguineo) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Doador> doadores = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DOADORES_BY_TIPO_SANGUINEO)) {
            stmt.setString(1, tipoSanguineo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doadores.add(mapResultSetToDoador(rs));
                }
            }
        }
        
        return doadores;
    }
    
    /**
     * Busca doadores por cidade
     * @param cidade Cidade dos doadores
     * @return Lista de doadores da cidade
     * @throws SQLException se houver erro na consulta
     */
    public static List<Doador> buscarPorCidade(String cidade) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        List<Doador> doadores = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DOADORES_BY_CIDADE)) {
            stmt.setString(1, "%" + cidade + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doadores.add(mapResultSetToDoador(rs));
                }
            }
        }
        
        return doadores;
    }
    
    /**
     * Verifica se um CPF já existe no banco
     * @param cpf CPF a ser verificado
     * @param excludeId ID a ser excluído da verificação (útil para updates)
     * @return true se CPF já existe, false caso contrário
     * @throws SQLException se houver erro na consulta
     */
    public static boolean cpfExiste(Long cpf, Long excludeId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(CHECK_CPF_EXISTS)) {
            stmt.setLong(1, cpf);
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
     * Atualiza um doador existente
     * @param doador Doador com dados atualizados
     * @return true se atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na atualização
     */
    public static boolean atualizar(Doador doador) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_DOADOR)) {
            stmt.setString(1, doador.getNome());
            stmt.setLong(2, doador.getCpf());
            stmt.setString(3, doador.getSexo());
            stmt.setString(4, doador.getTipoSanguineo());
            stmt.setDate(5, doador.getDataNascimento());
            stmt.setLong(6, doador.getTelefone());
            stmt.setString(7, doador.getBairro());
            stmt.setString(8, doador.getNacionalidade());
            stmt.setString(9, doador.getCidade());
            stmt.setLong(10, doador.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Atualiza a data da última doação
     * @param doadorId ID do doador
     * @param dataDoacao Data da doação
     * @return true se atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na atualização
     */
    public static boolean atualizarUltimaDoacao(Long doadorId, Date dataDoacao) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_ULTIMA_DOACAO)) {
            stmt.setDate(1, dataDoacao);
            stmt.setLong(2, doadorId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Remove um doador pelo ID
     * @param id ID do doador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na remoção
     */
    public static boolean remover(Long id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_DOADOR)) {
            stmt.setLong(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Remove um doador pelo CPF
     * @param cpf CPF do doador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se houver erro na remoção
     */
    public static boolean removerPorCpf(Long cpf) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_DOADOR_BY_CPF)) {
            stmt.setLong(1, cpf);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Mapeia um ResultSet para um objeto Doador
     * @param rs ResultSet com dados do doador
     * @return Doador criado a partir do ResultSet
     * @throws SQLException se houver erro ao ler dados
     */
    private static Doador mapResultSetToDoador(ResultSet rs) throws SQLException {
        return new Doador(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getLong("cpf"),
            rs.getString("sexo"),
            rs.getString("tipo_sanguineo"),
            rs.getDate("data_nascimento"),
            rs.getLong("telefone"),
            rs.getString("bairro"),
            rs.getString("nacionalidade"),
            rs.getString("cidade"),
            rs.getDate("ultima_doacao")
        );
    }
}