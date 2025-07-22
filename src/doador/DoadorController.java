package doador;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class DoadorController {
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
    
    public static void criarDoadorComMensagem(String nome, Long cpf, String sexo, String tipoSanguineo, Date dataNascimento, Long telefone, String bairro, String nacionalidade, String cidade, Long idHospital) {
        Doador doador = criarDoador(nome, cpf, sexo, tipoSanguineo, dataNascimento, telefone, bairro, nacionalidade, cidade, idHospital);
        
        if (doador != null) {
            DoadorView.exibirMensagemDoadorCriado(doador);
        } else {
            DoadorView.exibirMensagemErro("Erro ao criar doador");
        }
    }
    
    public static List<Doador> listarTodosDoadores() {
        try {
            return DoadorDAO.buscarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar doadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static void exibirTodosDoadores() {
        try {
            List<Doador> doadores = DoadorDAO.buscarTodos();
            DoadorView.exibirListaDoadores(doadores);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doadores: " + e.getMessage());
            DoadorView.exibirMensagemErro("Erro ao buscar doadores: " + e.getMessage());
        }
    }
    
    public static Doador buscarDoadorPorId(Long id) {
        try {
            return DoadorDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doador por ID: " + e.getMessage());
            return null;
        }
    }
    
    public static Doador buscarDoadorPorCpf(Long cpf) {
        try {
            return DoadorDAO.buscarPorCpf(cpf);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doador por CPF: " + e.getMessage());
            return null;
        }
    }
    
    public static List<Doador> listarDoadoresPorHospital(Long idHospital) {
        try {
            return DoadorDAO.buscarPorHospital(idHospital);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar doadores por hospital: " + e.getMessage());
            return new ArrayList<>();
        }
    }

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
    
    public static boolean atualizarUltimaDoacao(Long doadorId, Date dataDoacao) {
        try {
            return DoadorDAO.atualizarUltimaDoacao(doadorId, dataDoacao);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar última doação: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean verificarDisponibilidadeDoacao(Long doadorId) {
        try {
            if (doadorId == null) {
                return false;
            }
            
            Doador doador = DoadorDAO.buscarPorId(doadorId);
            if (doador == null) {
                return false;
            }
            
            // Verifica critérios básicos (idade)
            if (!doador.podeDoar()) {
                return false;
            }
            
            // Verifica intervalo de 60 dias desde última doação (para todos os doadores)
            if (doador.getUltimaDoacao() != null) {
                long diffInMillies = System.currentTimeMillis() - doador.getUltimaDoacao().getTime();
                long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
                return diffInDays >= 60;
            }
            
            return true; // Primeira doação
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar disponibilidade para doação: " + e.getMessage());
            return false;
        }
    }
    
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