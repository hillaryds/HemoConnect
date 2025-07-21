package administrador;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AdministradorController {
    
    /**
     * Cria um novo administrador
     * @param nome Nome do administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @param idHospital ID do hospital ao qual o administrador será associado
     * @return Administrador criado com ID gerado pelo banco ou null se houve erro
     */
    public static Administrador criarAdministrador(String nome, String login, String senha, Long idHospital) {
        return criarAdministrador("Administrador", nome, login, senha, idHospital);
    }
    
    /**
     * Cria um novo administrador com cargo específico
     * @param cargo Cargo do administrador
     * @param nome Nome do administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @param idHospital ID do hospital ao qual o administrador será associado
     * @return Administrador criado com ID gerado pelo banco ou null se houve erro
     */
    public static Administrador criarAdministrador(String cargo, String nome, String login, String senha, Long idHospital) {
        try {
            if (!validarDadosEntrada(nome, login, senha, idHospital)) {
                return null;
            }
            
            if (AdministradorDAO.loginExiste(login, null)) {
                System.err.println("Login já existe no sistema");
                return null;
            }
            
            Administrador administrador = new Administrador(cargo, nome, login, senha, idHospital);
            
           
            if (!administrador.validarDados()) {
                System.err.println("Dados do administrador inválidos");
                return null;
            }
            
           
            return AdministradorDAO.inserir(administrador);
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar administrador: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cria um novo administrador e exibe mensagem de resultado
     * @param nome Nome do administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @param idHospital ID do hospital ao qual o administrador será associado
     */
    public static void criarAdministradorComMensagem(String nome, String login, String senha, Long idHospital) {
        criarAdministradorComMensagem("Administrador", nome, login, senha, idHospital);
    }
    
    /**
     * Cria um novo administrador e exibe mensagem de resultado
     * @param cargo Cargo do administrador
     * @param nome Nome do administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @param idHospital ID do hospital ao qual o administrador será associado
     */
    public static void criarAdministradorComMensagem(String cargo, String nome, String login, String senha, Long idHospital) {
        Administrador administrador = criarAdministrador(cargo, nome, login, senha, idHospital);
        
        if (administrador != null) {
            AdministradorView.exibirMensagemAdministradorCriado(administrador);
        } else {
            AdministradorView.exibirMensagemErro("Erro ao criar administrador");
        }
    }
    
    /**
     * Lista todos os administradores
     * @return Lista de administradores ou lista vazia se houve erro
     */
    public static List<Administrador> listarTodosAdministradores() {
        try {
            return AdministradorDAO.buscarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar administradores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static void exibirTodosAdministradores() {
        try {
            List<Administrador> administradores = AdministradorDAO.buscarTodos();
            AdministradorView.exibirListaAdministradores(administradores);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar administradores: " + e.getMessage());
            AdministradorView.exibirMensagemErro("Erro ao buscar administradores: " + e.getMessage());
        }
    }
    
    /**
     * Realiza login de um administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @return Administrador autenticado ou null se credenciais inválidas
     */
    public static Administrador realizarLogin(String login, String senha) {
        try {
            if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
                return null;
            }
            
            return AdministradorDAO.autenticar(login.trim(), senha);
            
        } catch (SQLException e) {
            System.err.println("Erro ao realizar login: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Realiza login e exibe mensagem de resultado
     * @param login Login do administrador
     * @param senha Senha do administrador
     */
    public static void realizarLoginComMensagem(String login, String senha) {
        Administrador administrador = realizarLogin(login, senha);
        
        if (administrador != null) {
            AdministradorView.exibirMensagemLoginSucesso(administrador);
        } else {
            AdministradorView.exibirMensagemLoginFalha();
        }
    }
    
    
    /**
     * Remove um administrador pelo ID
     * @param id ID do administrador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerAdministrador(Long id) {
        try {
            if (id == null) {
                return false;
            }
            
            return AdministradorDAO.remover(id);
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover administrador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um administrador pelo login
     * @param login Login do administrador a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerAdministradorPorLogin(String login) {
        try {
            if (login == null || login.trim().isEmpty()) {
                return false;
            }
            
            return AdministradorDAO.removerPorLogin(login.trim());
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover administrador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um administrador pelo login com interação do usuário
     */
    public static void removerAdministradorInterativo() {
        String login = AdministradorView.solicitarLoginParaRemocao();
        
        if (login != null && !login.trim().isEmpty()) {
            boolean sucesso = removerAdministradorPorLogin(login);
            
            if (sucesso) {
                AdministradorView.exibirMensagemRemocaoSucesso(login);
            } else {
                AdministradorView.exibirMensagemRemocaoFalha(login);
            }
        }
    }
    
    /**
     * Busca administradores por hospital
     * @param idHospital ID do hospital
     * @return Lista de administradores do hospital ou lista vazia se houve erro
     */
    public static List<Administrador> listarAdministradoresPorHospital(Long idHospital) {
        try {
            if (idHospital == null) {
                return new ArrayList<>();
            }
            return AdministradorDAO.buscarPorHospital(idHospital);
        } catch (SQLException e) {
            System.err.println("Erro ao listar administradores por hospital: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Valida dados de entrada básicos
     * @param nome Nome do administrador
     * @param login Login do administrador
     * @param senha Senha do administrador
     * @param idHospital ID do hospital
     * @return true se válidos, false caso contrário
     */
    private static boolean validarDadosEntrada(String nome, String login, String senha, Long idHospital) {
        if (nome == null || nome.trim().isEmpty()) {
            System.err.println("Nome não pode ser vazio");
            return false;
        }
        
        if (!Administrador.validarLogin(login)) {
            System.err.println("Login deve ter pelo menos 3 caracteres");
            return false;
        }
        
        if (!Administrador.validarSenha(senha)) {
            System.err.println("Senha deve ter pelo menos 4 caracteres");
            return false;
        }
        
        if (!Administrador.validarIdHospital(idHospital)) {
            System.err.println("ID do hospital deve ser válido");
            return false;
        }
        
        return true;
    }
}