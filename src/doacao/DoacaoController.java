package doacao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import triagem.Triagem;
import triagem.TriagemDAO;
import doador.DoadorController;

/**
 * Controller - Classe DoacaoController
 * Responsável pela lógica de negócio, manipulação de dados e coordenação entre
 * Model e View
 * Integrado com PostgreSQL através do DAO
 */
public class DoacaoController {

    /**
     * Registra uma nova doação no sistema com validação completa.
     * @param doacao Objeto Doacao com dados validados
     * @return Doacao persistida com ID gerado, ou null se validação falhar
     * @throws SQLException se houver erro na persistência
     * @see #validarDoacao(Doacao)
     */
    public static Doacao registrarDoacao(Doacao doacao) {
        try {
            if (!validarDoacao(doacao)) {
                return null;
            }

            // Inserir a doação no banco
            Doacao doacaoSalva = DoacaoDAO.inserir(doacao);
            
            if (doacaoSalva != null) {
                // CRÍTICO: Atualizar a data da última doação do doador
                boolean atualizouDoador = DoadorController.atualizarUltimaDoacao(
                    doacao.getDoadorId(), 
                    doacao.getData()
                );
                
                if (!atualizouDoador) {
                    DoacaoView.exibirMensagemErro("AVISO: Doação registrada, mas falha ao atualizar última doação do doador!");
                }
            }
            
            return doacaoSalva;

        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao registrar doação: " + e.getMessage());
            if (e.getMessage().contains("violates foreign key constraint")) {
                DoacaoView.exibirMensagemErro(" Verifique se triagem_id e doador_id existem!");
            }
            return null;
        }
    }

    /**
     * Lista doações de uma data específica
     */
    public static List<Doacao> listarDoacoesPorData(Date data) {
        try {
            return DoacaoDAO.buscarPorData(data);
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao buscar doações: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca doação por ID
     */
    public static Doacao buscarDoacaoPorId(Long id) {
        try {
            return DoacaoDAO.buscarPorId(id);
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao buscar doação: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todas as doações (limitado a 50)
     */
    public static List<Doacao> listarTodasDoacoes() {
        try {
            return DoacaoDAO.buscarTodas();
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao listar doações: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza uma doação existente
     */
    public static boolean atualizarDoacao(Doacao doacao) {
        try {
            if (!validarDoacao(doacao)) {
                return false;
            }

            return DoacaoDAO.atualizar(doacao);

        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao atualizar doação: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove uma doação do sistema
     */
    public static boolean removerDoacao(Long id) {
        try {
            return DoacaoDAO.deletar(id);
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao remover doação: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtém estatísticas do dia
     */
    public static Map<String, Object> obterEstatisticasDia(Date data) {
        try {
            return DoacaoDAO.obterEstatisticasDia(data);
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao obter estatísticas: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtém estatísticas do mês
     */
    public static Map<String, Object> obterEstatisticasMes(int mes, int ano) {
        try {
            return DoacaoDAO.obterEstatisticasMes(mes, ano);
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao obter estatísticas mensais: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtém estatísticas gerais do sistema
     */
    public static Map<String, Object> obterEstatisticasGerais() {
        try {
            return DoacaoDAO.obterEstatisticasGerais();
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao obter estatísticas gerais: " + e.getMessage());
            return null;
        }
    }

    /**
     * Processa a opção do menu selecionada
     */
    public static void processarOpcaoMenu(int opcao) {
        switch (opcao) {
            case 1:
                processarRegistroDoacao();
                break;
            case 2:
                processarListagemDia();
                break;
            case 3:
                processarEstatisticasDia();
                break;
            case 4:
                processarEstatisticasMes();
                break;
            case 5:
                processarBuscaPorId();
                break;
            case 6:
                processarListagemTodas();
                break;
            case 7:
                processarAtualizacao();
                break;
            case 8:
                processarRemocao();
                break;
            case 9:
                processarEstatisticasGerais();
                break;
            case 0:
                System.out.println("Saindo do sistema...");
                break;
            default:
                DoacaoView.exibirMensagemErro("Opção inválida!");
        }
    }

    /**
     * Processa registro de nova doação
     */
    private static void processarRegistroDoacao() {
        Doacao doacao = DoacaoView.coletarDadosNovaDoacao();
        if (doacao != null) {
            Doacao doacaoSalva = registrarDoacao(doacao);
            if (doacaoSalva != null) {
                DoacaoView.exibirMensagemSucesso("DOAÇÃO REGISTRADA", doacaoSalva);
            }
        }
    }

    /**
     * Processa listagem por dia
     */
    private static void processarListagemDia() {
        Date data = DoacaoView.coletarData();
        if (data != null) {
            List<Doacao> doacoes = listarDoacoesPorData(data);
            if (doacoes != null) {
                DoacaoView.exibirListaDoacoes(doacoes, "DOAÇÕES DE " + data);
            }
        }
    }

    /**
     * Processa estatísticas do dia
     */
    private static void processarEstatisticasDia() {
        Date data = DoacaoView.coletarData();
        if (data != null) {
            Map<String, Object> stats = obterEstatisticasDia(data);
            if (stats != null) {
                DoacaoView.exibirEstatisticasDia(
                        (Integer) stats.get("total"),
                        (Double) stats.get("volumeTotal"),
                        data);
            }
        }
    }

    /**
     * Processa estatísticas do mês
     */
    private static void processarEstatisticasMes() {
        int[] mesAno = DoacaoView.coletarMesAno();
        if (mesAno != null) {
            Map<String, Object> stats = obterEstatisticasMes(mesAno[0], mesAno[1]);
            if (stats != null) {
                DoacaoView.exibirEstatisticasMes(
                        (Integer) stats.get("total"),
                        (Double) stats.get("volumeTotal"),
                        mesAno[0],
                        mesAno[1]);
            }
        }
    }

    /**
     * Processa busca por ID
     */
    private static void processarBuscaPorId() {
        Long id = DoacaoView.coletarId();
        if (id != null) {
            Doacao doacao = buscarDoacaoPorId(id);
            DoacaoView.exibirDoacao(doacao);
        }
    }

    /**
     * Processa listagem de todas as doações
     */
    private static void processarListagemTodas() {
        List<Doacao> doacoes = listarTodasDoacoes();
        if (doacoes != null) {
            DoacaoView.exibirListaDoacoes(doacoes, "ÚLTIMAS 50 DOAÇÕES");
        }
    }

    /**
     * Processa atualização de doação
     */
    private static void processarAtualizacao() {
        Long id = DoacaoView.coletarId();
        if (id != null) {
            Doacao doacaoAtual = buscarDoacaoPorId(id);
            if (doacaoAtual != null) {
                DoacaoView.exibirDoacao(doacaoAtual);
                Doacao doacaoAtualizada = DoacaoView.coletarDadosAtualizacao(doacaoAtual);

                if (doacaoAtualizada != null) {
                    if (atualizarDoacao(doacaoAtualizada)) {
                        DoacaoView.exibirMensagemSucesso("DOAÇÃO ATUALIZADA", doacaoAtualizada);
                    }
                }
            } else {
                DoacaoView.exibirMensagemErro("Doação não encontrada");
            }
        }
    }

    /**
     * Processa remoção de doação
     */
    private static void processarRemocao() {
        Long id = DoacaoView.coletarId();
        if (id != null) {
            Doacao doacao = buscarDoacaoPorId(id);
            if (doacao != null) {
                if (DoacaoView.confirmarRemocao(doacao)) {
                    if (removerDoacao(id)) {
                        DoacaoView.exibirMensagemSucesso("DOAÇÃO REMOVIDA", null);
                    }
                } else {
                    System.out.println("Remoção cancelada");
                }
            } else {
                DoacaoView.exibirMensagemErro("Doação não encontrada");
            }
        }
    }

    /**
     * Processa estatísticas gerais
     */
    private static void processarEstatisticasGerais() {
        Map<String, Object> stats = obterEstatisticasGerais();
        if (stats != null) {
            DoacaoView.exibirEstatisticasGerais(
                    (Integer) stats.get("totalGeral"),
                    (Double) stats.get("volumeGeral"),
                    (Integer) stats.get("hoje"),
                    (Integer) stats.get("esteMes"));
        }
    }

    /**
     * Valida os dados de uma doação
     */
    private static boolean validarDoacao(Doacao doacao) {
        if (doacao == null) {
            DoacaoView.exibirMensagemErro("Dados da doação inválidos");
            return false;
        }

        if (!doacao.validarDados()) {
            DoacaoView.exibirMensagemErro("Dados básicos obrigatórios não informados");
            return false;
        }

        if (!Doacao.validarData(doacao.getData())) {
            DoacaoView.exibirMensagemErro("Data da doação não pode ser anterior à data atual");
            return false;
        }

        if (!Doacao.validarHora(doacao.getHora())) {
            DoacaoView.exibirMensagemErro("Formato de hora inválido. Use HH:MM:SS com valores válidos");
            return false;
        }

        if (!Doacao.validarVolume(doacao.getVolume())) {
            DoacaoView.exibirMensagemErro("Volume deve estar entre 350ml e 500ml");
            return false;
        }

        try {
            Triagem triagem = TriagemDAO.buscarPorId(doacao.getTriagemId());
            if (triagem == null) {
                DoacaoView.exibirMensagemErro("Triagem não encontrada com ID: " + doacao.getTriagemId());
                return false;
            }
            
            if (!triagem.isStatus()) {
                DoacaoView.exibirMensagemErro("Doação não pode ser realizada! Triagem REPROVADA (ID: " + doacao.getTriagemId() + ")");
                return false;
            }
            
            if (!Doacao.podeRealizar(triagem)) {
                DoacaoView.exibirMensagemErro("Triagem não está apta para doação");
                return false;
            }
            
            if (!validarDataTriagemDoacao(triagem.getDate(), doacao.getData())) {
                return false;
            }
        } catch (SQLException e) {
            DoacaoView.exibirMensagemErro("Erro ao validar triagem: " + e.getMessage());
            return false;
        }

        if (!validarDoador(doacao.getDoadorId())) {
            return false;
        }

        return true;
    }
    
    /**
     * Valida a compatibilidade de datas entre triagem e doação
     */
    private static boolean validarDataTriagemDoacao(Date dataTriagem, Date dataDoacao) {
        if (dataTriagem == null || dataDoacao == null) {
            DoacaoView.exibirMensagemErro("Erro interno: datas da triagem ou doação estão nulas");
            return false;
        }
        
        String dataTriagemStr = dataTriagem.toString();
        String dataDoacaoStr = dataDoacao.toString();
        
        if (!dataTriagemStr.equals(dataDoacaoStr)) {
            DoacaoView.exibirMensagemErro("ERRO - Triagem e doação devem ser realizadas no mesmo dia!");
            DoacaoView.exibirMensagemErro("Data da triagem: " + dataTriagem);
            DoacaoView.exibirMensagemErro("Data da doação: " + dataDoacao);
            DoacaoView.exibirMensagemErro("Realize uma nova triagem no dia da doação");
            return false;
        }
        return true;
    }
    
    private static boolean validarDoador(Long idDoador) {
        try {
            boolean podeDoar = DoadorController.verificarDisponibilidadeDoacao(idDoador);
            
            if (!podeDoar) {
                DoacaoView.exibirMensagemErro("ERRO - Este doador ainda não pode doar!");
                DoacaoView.exibirMensagemErro("Não passou o intervalo mínimo entre doações");
                DoacaoView.exibirMensagemErro("Intervalo mínimo: 60 dias entre doações");
                DoacaoView.exibirMensagemErro("Aguarde o período mínimo ou selecione outro doador");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            DoacaoView.exibirMensagemErro("ERRO: Falha ao verificar elegibilidade do doador: " + e.getMessage());
            return false;
        }
    }
}
