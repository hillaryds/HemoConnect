package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gerenciador de conexão com PostgreSQL
 * Implementa padrão Singleton para reutilizar conexões
 */
public class DatabaseConnection {
    
    // Configurações do banco de dados
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/HemoConnect";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234"; 
    
    private static Connection connection;
    
    // Construtor privado para implementar Singleton
    private DatabaseConnection() {}
    
    /**
     * Obtém a conexão com o banco de dados
     * @return Connection ativa com PostgreSQL
     * @throws SQLException se houver erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carregar o driver PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // Propriedades da conexão
                Properties props = new Properties();
                props.setProperty("user", DB_USER);
                props.setProperty("password", DB_PASSWORD);
                
                // Criar conexão
                connection = DriverManager.getConnection(DB_URL, props);
                
                System.out.println("Conexão com PostgreSQL estabelecida com sucesso!");
                
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL não encontrado: " + e.getMessage());
            } catch (SQLException e) {
                throw new SQLException("Erro ao conectar com PostgreSQL: " + e.getMessage());
            }
        }
        
        return connection;
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com PostgreSQL fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
