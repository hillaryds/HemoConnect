package doacao;

import database.DatabaseConnection;
import java.sql.Connection;

/**
 * Main - Classe DoacaoMain
 * Ponto de entrada para o sistema de doações seguindo padrão MVC
 * Coordena View, Controller e DAO
 */
public class DoacaoMain {

    public static void main(String[] args) {
        executarMenuPrincipal();
    }
    
    /**
     * Executa o menu principal do sistema de doações
     * Método utilizado pelo MainSystem para integração
     */
    public static void executarMenuPrincipal() {
        // Exibe cabeçalho
        DoacaoView.exibirCabecalho();

        // Testa conexão com banco
        if (!testarConexao()) {
            System.err.println("ERRO: Falha na conexão com banco de dados!");
            System.err.println("Verifique se o PostgreSQL está em execução");
            return;
        }

        // Exibe confirmação de conexão
        DoacaoView.exibirConexaoSucesso();

        // Loop principal do sistema
        boolean sistemaAtivo = true;
        while (sistemaAtivo) {
            try {
                int opcao = DoacaoView.exibirMenuPrincipal();

                if (opcao == 0) {
                    sistemaAtivo = false;
                } else {
                    DoacaoController.processarOpcaoMenu(opcao);

                    if (sistemaAtivo && opcao != 0) {
                        DoacaoView.pausar();
                    }
                }

            } catch (Exception e) {
                DoacaoView.exibirMensagemErro("Erro: " + e.getMessage());
                DoacaoView.pausar();
            }
        }

        System.out.println("Sistema encerrado.");
    }

    /**
     * Testa conexão com PostgreSQL via DatabaseConnection
     */
    private static boolean testarConexao() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão estabelecida com sucesso");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
        return false;
    }
}
