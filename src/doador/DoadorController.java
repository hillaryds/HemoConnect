package doador;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Controlador responsável pela lógica de negócio relacionada aos doadores.
 * 
 * <p>Esta classe implementa o padrão MVC (Model-View-Controller) e serve como
 * intermediária entre a camada de apresentação (View) e a camada de dados (DAO).
 * Gerencia todas as operações CRUD (Create, Read, Update, Delete) dos doadores,
 * aplicando regras de negócio e validações necessárias.</p>
 * 
 * <p>Principais responsabilidades:</p>
 * <ul>
 *   <li>Criação e validação de novos doadores</li>
 *   <li>Busca e listagem de doadores</li>
 *   <li>Atualização de dados de doadores existentes</li>
 *   <li>Remoção de doadores do sistema</li>
 *   <li>Verificação de elegibilidade para doação</li>
 *   <li>Gerenciamento do histórico de doações</li>
 * </ul>
 * 
 * @author Sistema HemoConnect
 * @version 1.0
 * @since 1.0
 * @see Doador
 * @see DoadorDAO
 * @see DoadorView
 */
public class DoadorController {
    
    /**
     * Cria um novo doador no sistema.
     * 
     * <p>Realiza validação completa dos dados de entrada, verifica se o CPF
     * já existe no sistema e persiste o doador na base de dados.</p>
     * 
     * @param nome Nome completo do doador
     * @param cpf Número do CPF (11 dígitos)
     * @param sexo Sexo do doador (M/F)
     * @param tipoSanguineo Tipo sanguíneo válido
     * @param dataNascimento Data de nascimento
     * @param telefone Número de telefone para contato
     * @param bairro Bairro de residência
     * @param nacionalidade Nacionalidade do doador
     * @param cidade Cidade de residência
     * @param idHospital ID do hospital ao qual será vinculado
     * @return Doador criado com ID gerado ou null em caso de erro
     */
    public static Doador criarDoador(String nome, Long cpf, String sexo, String tipoSanguineo, Date dataNascimento, Long telefone, String bairro, String nacionalidade, String cidade, Long idHospital) {
        try {
            if (!validarDadosEntrada(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital)) {
                return null;
            }
            
            if (DoadorDAO.cpfExiste(cpf, null)) {
                System.err.println("CPF já existe no sistema");
                return null;
            }
            
            Doador doador = new Doador(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital);
            
            if (!doador.validarDados()) {
                System.err.println("Dados do doador inválidos");
                return null;
            }
            
            return DoadorDAO.inserir(doador);
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar doador: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cria um novo doador e exibe mensagem de resultado.
     * 
     * <p>Método conveniente que combina a criação do doador com a exibição
     * imediata do resultado da operação ao usuário.</p>
     * 
     * @param nome Nome completo do doador
     * @param cpf Número do CPF (11 dígitos)
     * @param sexo Sexo do doador (M/F)
     * @param tipoSanguineo Tipo sanguíneo válido
     * @param dataNascimento Data de nascimento
     * @param telefone Número de telefone para contato
     * @param bairro Bairro de residência
     * @param nacionalidade Nacionalidade do doador
     * @param cidade Cidade de residência
     * @param idHospital ID do hospital ao qual será vinculado
     */
    public static void criarDoadorComMensagem(String nome, Long cpf, String sexo, String tipoSanguineo, Date dataNascimento, Long telefone, String bairro, String nacionalidade, String cidade, Long idHospital) {
        Doador doador = criarDoador(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital);
        
        if (doador != null) {
            DoadorView.exibirMensagemDoadorCriado(doador);
        } else {
            DoadorView.exibirMensagemErro("Erro ao criar doador");
        }
    }
    
    /**
     * Lista todos os doadores cadastrados no sistema.
     * 
     * @return Lista de todos os doadores ou lista vazia em caso de erro
     */
    public static List<Doador> listarTodosDoadores() {
        try {
            return DoadorDAO.buscarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar doadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lista e exibe todos os doadores cadastrados.
     * 
     * <p>Método conveniente que busca todos os doadores e os exibe
     * através da camada de visualização.</p>
     */
    public static void exibirTodosDoadores() {
        try {
            List<Doador> doadores = DoadorDAO.buscarTodos();
            DoadorView.exibirListaDoadores(doadores);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doadores: " + e.getMessage());
            DoadorView.exibirMensagemErro("Erro ao buscar doadores: " + e.getMessage());
        }
    }
    
    /**
     * Busca um doador pelo seu identificador único.
     * 
     * @param id Identificador único do doador
     * @return Doador encontrado ou null se não existir ou ocorrer erro
     */
    public static Doador buscarDoadorPorId(Long id) {
        try {
            return DoadorDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doador por ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca um doador pelo número do CPF.
     * 
     * @param cpf Número do CPF do doador
     * @return Doador encontrado ou null se não existir ou ocorrer erro
     */
    public static Doador buscarDoadorPorCpf(Long cpf) {
        try {
            return DoadorDAO.buscarPorCpf(cpf);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doador por CPF: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lista todos os doadores vinculados a um hospital específico.
     * 
     * @param idHospital Identificador único do hospital
     * @return Lista de doadores vinculados ao hospital ou lista vazia em caso de erro
     */
    public static List<Doador> listarDoadoresPorHospital(Long idHospital) {
        try {
            return DoadorDAO.buscarPorHospital(idHospital);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doadores por hospital: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Atualiza os dados de um doador existente.
     * 
     * <p>Realiza validação completa dos dados e verifica se o CPF não está
     * sendo usado por outro doador antes de persistir as alterações.</p>
     * 
     * @param doador Objeto Doador com dados atualizados (deve conter ID válido)
     * @return true se a atualização foi bem-sucedida, false caso contrário
     */
    public static boolean atualizarDoador(Doador doador) {
        try {
            if (doador.getId() == null) {
                return false;
            }
            
            if (!doador.validarDados()) {
                System.err.println("Dados do doador inválidos");
                return false;
            }
            
            if (DoadorDAO.cpfExiste(doador.getCpf(), doador.getId())) {
                System.err.println("CPF já existe no sistema");
                return false;
            }
            
            return DoadorDAO.atualizar(doador);
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar doador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um doador pelo seu identificador único.
     * 
     * @param id Identificador único do doador a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerDoador(Long id) {
        try {
            if (id == null) {
                return false;
            }
            
            return DoadorDAO.remover(id);
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover doador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um doador pelo número do CPF.
     * 
     * @param cpf Número do CPF do doador a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     */
    public static boolean removerDoadorPorCpf(Long cpf) {
        try {
            if (cpf == null) {
                return false;
            }
            
            return DoadorDAO.removerPorCpf(cpf);
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover doador: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove um doador através de interação com o usuário.
     * 
     * <p>Solicita o CPF do doador ao usuário e executa a remoção,
     * exibindo mensagens de sucesso ou falha conforme o resultado.</p>
     */
    public static void removerDoadorInterativo() {
        Long cpf = DoadorView.solicitarCpfParaRemocao();
        
        if (cpf != null) {
            boolean sucesso = removerDoadorPorCpf(cpf);
            
            if (sucesso) {
                DoadorView.exibirMensagemRemocaoSucesso(cpf);
            } else {
                DoadorView.exibirMensagemRemocaoFalha(cpf);
            }
        }
    }
    
    /**
     * Atualiza um doador através de interação com o usuário.
     * 
     * <p>Solicita o CPF do doador, apresenta os dados atuais e permite
     * a edição campo por campo, mantendo valores atuais para campos vazios.</p>
     */
    public static void atualizarDoadorInterativo() {
        Long cpf = DoadorView.solicitarCpf();
        
        if (cpf != null) {
            Doador doadorAtual = buscarDoadorPorCpf(cpf);
            
            if (doadorAtual != null) {
                Object[] dados = DoadorView.solicitarDadosAtualizacao(doadorAtual);
                
                if (dados != null) {
                    Doador doadorAtualizado = new Doador(
                        (Long) dados[0],        // id
                        (String) dados[1],      // nome
                        (Long) dados[2],        // cpf
                        (String) dados[3],      // sexo
                        (String) dados[4],      // tipoSanguineo
                        (Date) dados[5],        // dataNascimento
                        (Long) dados[6],        // telefone
                        (String) dados[7],      // bairro
                        (String) dados[8],      // nacionalidade
                        (String) dados[9],      // cidade
                        (Date) dados[10],       // ultimaDoacao
                        (Long) dados[11]        // idHospital
                    );
                    
                    boolean sucesso = atualizarDoador(doadorAtualizado);
                    
                    if (sucesso) {
                        DoadorView.exibirMensagemAtualizacaoSucesso(doadorAtualizado);
                    } else {
                        DoadorView.exibirMensagemAtualizacaoFalha(cpf);
                    }
                }
            } else {
                DoadorView.exibirMensagemErro("Doador com CPF " + cpf + " não encontrado.");
            }
        }
    }
    
    /**
     * Atualiza a data da última doação de um doador.
     * 
     * <p>Método utilizado pelo sistema de doações para manter o histórico
     * atualizado após uma nova doação ser registrada.</p>
     * 
     * @param doadorId Identificador único do doador
     * @param dataDoacao Data da doação realizada
     * @return true se a atualização foi bem-sucedida, false caso contrário
     */
    public static boolean atualizarUltimaDoacao(Long doadorId, Date dataDoacao) {
        try {
            return DoadorDAO.atualizarUltimaDoacao(doadorId, dataDoacao);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar última doação: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica se um doador está apto para realizar uma nova doação.
     * 
     * <p>Realiza verificação completa considerando:</p>
     * <ul>
     *   <li>Critérios básicos de idade (16-69 anos)</li>
     *   <li>Intervalo mínimo de 60 dias desde a última doação</li>
     * </ul>
     * 
     * <p><strong>Nota:</strong> Este método utiliza intervalo fixo de 60 dias.
     * Para verificação com intervalos diferenciados por sexo, utilize os métodos
     * da classe Doacao.</p>
     * 
     * @param doadorId Identificador único do doador
     * @return true se o doador pode doar, false caso contrário
     */
    public static boolean verificarDisponibilidadeDoacao(Long doadorId) {
        try {
            if (doadorId == null) {
                return false;
            }
            
            Doador doador = DoadorDAO.buscarPorId(doadorId);
            if (doador == null) {
                return false;
            }
            
            if (!doador.podeDoar()) {
                return false;
            }
            
            if (doador.getUltimaDoacao() != null) {
                long diffInMillies = System.currentTimeMillis() - doador.getUltimaDoacao().getTime();
                long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
                return diffInDays >= 60;
            }
            
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar disponibilidade para doação: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Valida os dados de entrada antes da criação ou atualização de um doador.
     * 
     * <p>Realiza validação de todos os campos obrigatórios, verificando:</p>
     * <ul>
     *   <li>Campos não nulos e não vazios</li>
     *   <li>Formato do CPF (11 dígitos)</li>
     *   <li>Tipo sanguíneo válido</li>
     *   <li>Valores numéricos positivos</li>
     * </ul>
     * 
     * @param nome Nome completo do doador
     * @param cpf Número do CPF
     * @param sexo Sexo do doador
     * @param tipoSanguineo Tipo sanguíneo
     * @param dataNascimento Data de nascimento
     * @param telefone Número de telefone
     * @param bairro Bairro de residência
     * @param nacionalidade Nacionalidade
     * @param cidade Cidade de residência
     * @param idHospital ID do hospital vinculado
     * @return true se todos os dados são válidos, false caso contrário
     */
    private static boolean validarDadosEntrada(String nome, Long cpf, String sexo, String tipoSanguineo, Date dataNascimento, Long telefone, String bairro, String nacionalidade, String cidade, Long idHospital) {
        if (nome == null || nome.trim().isEmpty()) {
            System.err.println("Nome não pode ser vazio");
            return false;
        }
        
        if (!Doador.validarCpf(cpf)) {
            System.err.println("CPF deve ter 11 dígitos");
            return false;
        }
        
        if (sexo == null || sexo.trim().isEmpty()) {
            System.err.println("Sexo não pode ser vazio");
            return false;
        }
        
        if (!Doador.validarTipoSanguineo(tipoSanguineo)) {
            System.err.println("Tipo sanguíneo inválido");
            return false;
        }
        
        if (dataNascimento == null) {
            System.err.println("Data de nascimento não pode ser vazia");
            return false;
        }
        
        if (telefone == null || telefone <= 0) {
            System.err.println("Telefone inválido");
            return false;
        }
        
        if (bairro == null || bairro.trim().isEmpty()) {
            System.err.println("Bairro não pode ser vazio");
            return false;
        }
        
        if (nacionalidade == null || nacionalidade.trim().isEmpty()) {
            System.err.println("Nacionalidade não pode ser vazia");
            return false;
        }
        
        if (cidade == null || cidade.trim().isEmpty()) {
            System.err.println("Cidade não pode ser vazia");
            return false;
        }
        
        if (idHospital == null || idHospital <= 0) {
            System.err.println("ID do hospital inválido");
            return false;
        }
        
        return true;
    }
}