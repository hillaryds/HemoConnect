package hospital;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class HospitalController {
    
    /**
     * Cria um novo hospital
     * @param nome Nome do hospital
     * @param cep CEP do hospital
     * @param cidade Cidade do hospital
     * @return Hospital criado com ID gerado pelo banco ou null se houve erro
     */
    public static Hospital criarHospital(String nome, String cep, String cidade) {
        try {
            if (!validarDadosEntrada(nome, cep, cidade)) {
                return null;
            }
            
      
            if (HospitalDAO.nomeExiste(nome, null)) {
                System.err.println("Nome do hospital já existe no sistema");
                return null;
            }
            
            Hospital hospital = new Hospital(nome, cep, cidade);
            
      
            if (!hospital.validarDados()) {
                System.err.println("Dados do hospital inválidos");
                return null;
            }
            
         
            return HospitalDAO.inserir(hospital);
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar hospital: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cria um novo hospital e exibe mensagem de resultado
     * @param nome Nome do hospital
     * @param cep CEP do hospital
     * @param cidade Cidade do hospital
     */
    public static void criarHospitalComMensagem(String nome, String cep, String cidade) {
        Hospital hospital = criarHospital(nome, cep, cidade);
        
        if (hospital != null) {
            HospitalView.exibirMensagemHospitalCriado(hospital);
        } else {
            HospitalView.exibirMensagemErro("Erro ao criar hospital");
        }
    }
    
    /**
     * Lista todos os hospitais
     * @return Lista de hospitais ou lista vazia se houve erro
     */
    public static List<Hospital> listarTodosHospitais() {
        try {
            return HospitalDAO.buscarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar hospitais: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static void exibirTodosHospitais() {
        try {
            List<Hospital> hospitais = HospitalDAO.buscarTodos();
            HospitalView.exibirListaHospitais(hospitais);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospitais: " + e.getMessage());
            HospitalView.exibirMensagemErro("Erro ao buscar hospitais: " + e.getMessage());
        }
    }
    
    /**
     * Busca hospital por ID
     * @param id ID do hospital
     * @return Hospital encontrado ou null se não existir
     */
    public static Hospital buscarHospitalPorId(Long id) {
        try {
            return HospitalDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospital por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca hospital por nome
     * @param nome Nome do hospital
     * @return Hospital encontrado ou null se não existir
     */
    public static Hospital buscarHospitalPorNome(String nome) {
        try {
            return HospitalDAO.buscarPorNome(nome);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospital por nome: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca hospitais por cidade
     * @param cidade Cidade dos hospitais
     * @return Lista de hospitais da cidade
     */
    public static List<Hospital> buscarHospitaisPorCidade(String cidade) {
        try {
            return HospitalDAO.buscarPorCidade(cidade);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospitais por cidade: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Exibe hospitais de uma cidade específica
     * @param cidade Cidade dos hospitais
     */
    public static void exibirHospitaisPorCidade(String cidade) {
        try {
            List<Hospital> hospitais = HospitalDAO.buscarPorCidade(cidade);
            HospitalView.exibirHospitaisPorCidade(cidade, hospitais);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospitais por cidade: " + e.getMessage());
            HospitalView.exibirMensagemErro("Erro ao buscar hospitais por cidade: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza dados de um hospital
     * @param hospital Hospital com dados atualizados
     * @return true se atualização foi bem-sucedida, false caso contrário
     */
    public static boolean atualizarHospital(Hospital hospital) {
        try {
            if (hospital.getId() == null) {
                return false;
            }
            
            // Validar dados
            if (!hospital.validarDados()) {
                System.err.println("Dados do hospital inválidos");
                return false;
            }
            
            // Verificar se nome já existe (excluindo o próprio hospital)
            if (HospitalDAO.nomeExiste(hospital.getNome(), hospital.getId())) {
                System.err.println("Nome do hospital já existe no sistema");
                return false;
            }
            
            return HospitalDAO.atualizar(hospital);
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar hospital: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um hospital pelo ID
     * @param id ID do hospital a ser removido
     * @return true se remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerHospital(Long id) {
        try {
            if (id == null) {
                return false;
            }
            
            return HospitalDAO.remover(id);
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover hospital: " + e.getMessage());
            return false;
        }
    }
    
    public static void removerHospitalInterativo() {
        Long id = HospitalView.solicitarIdParaRemocao();
        
        if (id != null) {
            
            Hospital hospital = buscarHospitalPorId(id);
            if (hospital != null) {
                HospitalView.exibirHospital(hospital);
                
                if (HospitalView.solicitarConfirmacao("Tem certeza que deseja remover este hospital?")) {
                    boolean sucesso = removerHospital(id);
                    
                    if (sucesso) {
                        HospitalView.exibirMensagemRemocaoSucesso(hospital.getNome());
                    } else {
                        HospitalView.exibirMensagemRemocaoFalha(hospital.getNome());
                    }
                }
            } else {
                HospitalView.exibirMensagemErro("Hospital não encontrado com ID: " + id);
            }
        }
    }
    
    /**
     * Valida dados de entrada básicos
     * @param nome Nome do hospital
     * @param cep CEP do hospital
     * @param cidade Cidade do hospital
     * @return true se válidos, false caso contrário
     */
    private static boolean validarDadosEntrada(String nome, String cep, String cidade) {
        if (!Hospital.validarNome(nome)) {
            System.err.println("Nome deve ter pelo menos 3 caracteres");
            return false;
        }
        
        if (!Hospital.validarCep(cep)) {
            System.err.println("CEP deve ter 8 dígitos");
            return false;
        }
        
        if (cidade == null || cidade.trim().isEmpty()) {
            System.err.println("Cidade não pode ser vazia");
            return false;
        }
        
        return true;
    }
    
    /**
     * Consulta doadores vinculados a um hospital específico
     * @param hospital Hospital para consultar os doadores
     * @return Lista de doadores vinculados ao hospital
     */
    public static List<doador.Doador> consultarDoadoresVinculados(Hospital hospital) {
        if (hospital == null) {
            System.err.println("Hospital não pode ser nulo");
            return new ArrayList<>();
        }
        
        return hospital.consultarDoadoresVinculados();
    }
    
    /**
     * Consulta doadores vinculados a um hospital por ID
     * @param idHospital ID do hospital
     * @return Lista de doadores vinculados ao hospital
     */
    public static List<doador.Doador> consultarDoadoresVinculadosPorId(Long idHospital) {
        try {
            Hospital hospital = HospitalDAO.buscarPorId(idHospital);
            if (hospital == null) {
                System.err.println("Hospital não encontrado");
                return new ArrayList<>();
            }
            
            return hospital.consultarDoadoresVinculados();
        } catch (SQLException e) {
            System.err.println("Erro ao consultar doadores vinculados: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}