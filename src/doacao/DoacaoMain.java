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
        // Exibe cabeçalho
        DoacaoView.exibirCabecalho();

        // Testa conexão com banco
        if (!testarConexao()) {
            System.err.println(" ERRO: Banco não conectado!");
            System.err.println("Verifique: PostgreSQL rodando, DatabaseConnection configurado");
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
                System.out.println(" DatabaseConnection funcionando!");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erro DatabaseConnection: " + e.getMessage());
        }
        return false;
    }
}
