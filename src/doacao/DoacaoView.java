package doacao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

/**
 * View - Classe DoacaoView
 * Responsável pela interface com o usuário, exibição de dados e entrada de
 * dados
 */
public class DoacaoView {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Exibe o menu principal do sistema de doações
     */
    public static int exibirMenuPrincipal() {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║           MENU DE DOAÇÕES           ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1.  Registrar Nova Doação           ║");
        System.out.println("║ 2.  Listar Doações do Dia           ║");
        System.out.println("║ 3.  Total Doações do Dia            ║");
        System.out.println("║ 4.  Total Doações do Mês            ║");
        System.out.println("║ 5.  Buscar Doação por ID            ║");
        System.out.println("║ 6.  Listar TODAS as Doações         ║");
        System.out.println("║ 7.   Atualizar Doação               ║");
        System.out.println("║ 8.   Remover Doação                 ║");
        System.out.println("║ 9.  Estatísticas Gerais             ║");
        System.out.println("║ 0.  Sair                            ║");
        System.out.println("╚═════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Coleta dados para nova doação
     */
    public static Doacao coletarDadosNovaDoacao() {
        System.out.println("\n═══ REGISTRAR NOVA DOAÇÃO ═══");

        try {
            System.out.print("Data (YYYY-MM-DD): ");
            Date data = Date.valueOf(scanner.nextLine());

            System.out.print("Hora (HH:MM:SS): ");
            Time hora = Time.valueOf(scanner.nextLine());

            System.out.print("Volume (ml): ");
            double volume = Double.parseDouble(scanner.nextLine());

            if (volume < 350 || volume > 500) {
                System.out.println(" Volume deve estar entre 350ml e 500ml");
                return null;
            }

            System.out.print("ID Triagem: ");
            Long triagemId = Long.parseLong(scanner.nextLine());

            System.out.print("ID Doador: ");
            Long doadorId = Long.parseLong(scanner.nextLine());

            return new Doacao(data, hora, volume, triagemId, doadorId);

        } catch (Exception e) {
            System.out.println(" Erro ao coletar dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Coleta data para busca
     */
    public static Date coletarData() {
        try {
            System.out.print("Data (YYYY-MM-DD): ");
            return Date.valueOf(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(" Erro: data inválida");
            return null;
        }
    }

    /**
     * Coleta mês e ano para busca
     */
    public static int[] coletarMesAno() {
        try {
            System.out.print("Mês (1-12): ");
            int mes = Integer.parseInt(scanner.nextLine());
            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            return new int[] { mes, ano };
        } catch (Exception e) {
            System.out.println(" Erro: mês/ano inválido");
            return null;
        }
    }

    /**
     * Coleta ID para busca
     */
    public static Long coletarId() {
        try {
            System.out.print("ID da doação: ");
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(" Erro: ID inválido");
            return null;
        }
    }

    /**
     * Exibe detalhes de uma doação
     */
    public static void exibirDoacao(Doacao doacao) {
        if (doacao == null) {
            System.out.println(" Doação não encontrada");
            return;
        }

        System.out.println("\n DOAÇÃO ENCONTRADA:");
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║         DADOS DA DOAÇÃO            ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ ID: " + String.format("%-27d", doacao.getId()) + "║");
        System.out.println("║ Data: " + String.format("%-25s", doacao.getData()) + "║");
        System.out.println("║ Hora: " + String.format("%-25s", doacao.getHora()) + "║");
        System.out.println("║ Volume: " + String.format("%-21.0f", doacao.getVolume()) + "ml ║");
        System.out.println("║ Triagem: " + String.format("%-22d", doacao.getTriagemId()) + "║");
        System.out.println("║ Doador: " + String.format("%-23d", doacao.getDoadorId()) + "║");
        System.out.println("╚════════════════════════════════════╝");
    }

    /**
     * Exibe lista de doações em formato tabela
     */
    public static void exibirListaDoacoes(List<Doacao> doacoes, String titulo) {
        if (doacoes == null || doacoes.isEmpty()) {
            System.out.println(" Nenhuma doação encontrada");
            return;
        }

        System.out.println("\n " + titulo.toUpperCase() + ":");
        System.out.printf("%-5s %-12s %-10s %-8s %-8s %-8s%n",
                "ID", "DATA", "HORA", "VOLUME", "TRIAGEM", "DOADOR");
        System.out.println("-".repeat(55));

        for (Doacao doacao : doacoes) {
            System.out.printf("%-5d %-12s %-10s %-8.0fml %-8d %-8d%n",
                    doacao.getId(),
                    doacao.getData(),
                    doacao.getHora(),
                    doacao.getVolume(),
                    doacao.getTriagemId(),
                    doacao.getDoadorId());
        }

        System.out.println("-".repeat(55));
        System.out.println("Total: " + doacoes.size() + " doações");
    }

    /**
     * Exibe estatísticas do dia
     */
    public static void exibirEstatisticasDia(int total, double volumeTotal, Date data) {
        double media = total > 0 ? volumeTotal / total : 0;

        System.out.println("\n RELATÓRIO DIÁRIO:");
        System.out.println("╔═════════════════════════════════╗");
        System.out.println("║       RESUMO DO DIA             ║");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║ Data: " + String.format("%-23s", data) + "║");
        System.out.println("║ Total doações: " + String.format("%-14d", total) + "║");
        System.out.println("║ Volume total: " + String.format("%-13.0f", volumeTotal) + "ml ║");
        System.out.println("║ Volume médio: " + String.format("%-13.1f", media) + "ml ║");
        System.out.println("╚═════════════════════════════════╝");
    }

    /**
     * Exibe estatísticas do mês
     */
    public static void exibirEstatisticasMes(int total, double volumeTotal, int mes, int ano) {
        double media = total > 0 ? volumeTotal / total : 0;

        System.out.println("\n RELATÓRIO MENSAL:");
        System.out.println("Período: " + mes + "/" + ano);
        System.out.println("Total doações: " + total);
        System.out.println("Volume total: " + volumeTotal + "ml");
        System.out.println("Volume médio: " + media + "ml");
    }

    /**
     * Exibe estatísticas gerais
     */
    public static void exibirEstatisticasGerais(int totalGeral, double volumeGeral, int hoje, int esteMes) {
        System.out.println("\n ESTATÍSTICAS DO SISTEMA:");
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║        DADOS CONSOLIDADOS         ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("║ Total de doações: " + String.format("%-11d", totalGeral) + "║");
        System.out.println("║ Volume total: " + String.format("%-13.0f", volumeGeral) + "ml ║");
        System.out.println(
                "║ Volume médio: " + String.format("%-13.1f", totalGeral > 0 ? volumeGeral / totalGeral : 0) + "ml ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.println("\n Doações hoje: " + hoje);
        System.out.println(" Doações este mês: " + esteMes);
    }

    /**
     * Coleta dados para atualização de doação
     */
    public static Doacao coletarDadosAtualizacao(Doacao doacaoAtual) {
        System.out.println("\nDados atuais:");
        System.out.println("Data: " + doacaoAtual.getData());
        System.out.println("Hora: " + doacaoAtual.getHora());
        System.out.println("Volume: " + doacaoAtual.getVolume() + "ml");

        try {
            System.out.print("\nNova data (YYYY-MM-DD) [Enter = manter]: ");
            String novaDataStr = scanner.nextLine();
            Date novaData = novaDataStr.trim().isEmpty() ? doacaoAtual.getData() : Date.valueOf(novaDataStr);

            System.out.print("Nova hora (HH:MM:SS) [Enter = manter]: ");
            String novaHoraStr = scanner.nextLine();
            Time novaHora = novaHoraStr.trim().isEmpty() ? doacaoAtual.getHora() : Time.valueOf(novaHoraStr);

            System.out.print("Novo volume (ml) [Enter = manter]: ");
            String novoVolumeStr = scanner.nextLine();
            double novoVolume = novoVolumeStr.trim().isEmpty() ? doacaoAtual.getVolume()
                    : Double.parseDouble(novoVolumeStr);

            return new Doacao(doacaoAtual.getId(), novaData, novaHora, novoVolume,
                    doacaoAtual.getTriagemId(), doacaoAtual.getDoadorId());

        } catch (Exception e) {
            System.out.println(" Erro ao coletar dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Confirma remoção
     */
    public static boolean confirmarRemocao(Doacao doacao) {
        System.out.println("\nDoação a ser removida:");
        System.out.println("ID: " + doacao.getId());
        System.out.println("Data: " + doacao.getData());
        System.out.println("Hora: " + doacao.getHora());
        System.out.println("Volume: " + doacao.getVolume() + "ml");

        System.out.print("\nConfirma remoção? (s/N): ");
        String confirmacao = scanner.nextLine();
        return "s".equalsIgnoreCase(confirmacao);
    }

    /**
     * Exibe mensagens de sucesso
     */
    public static void exibirMensagemSucesso(String operacao, Doacao doacao) {
        System.out.println("\n " + operacao.toUpperCase() + " COM SUCESSO!");
        if (doacao != null && doacao.getId() != null) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║         OPERAÇÃO REALIZADA         ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ ID: " + String.format("%-27d", doacao.getId()) + "║");
            System.out.println("║ Data: " + String.format("%-25s", doacao.getData()) + "║");
            System.out.println("║ Hora: " + String.format("%-25s", doacao.getHora()) + "║");
            System.out.println("║ Volume: " + String.format("%-21.0f", doacao.getVolume()) + "ml ║");
            System.out.println("║ Triagem: " + String.format("%-22d", doacao.getTriagemId()) + "║");
            System.out.println("║ Doador: " + String.format("%-23d", doacao.getDoadorId()) + "║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.println("Operação realizada com sucesso");
        }
    }

    /**
     * Exibe mensagens de erro
     */
    public static void exibirMensagemErro(String mensagem) {
        System.out.println("Erro:  " + mensagem);
    }

    /**
     * Pausa para aguardar Enter
     */
    public static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Exibe cabeçalho do sistema
     */
    public static void exibirCabecalho() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         HEMOCONNECT v2.1             ║");
        System.out.println("║             Doações      ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Exibe mensagem de conexão bem-sucedida
     */
    public static void exibirConexaoSucesso() {
        System.out.println("Conectado ao banco de dados PostgreSQL");
        System.out.println("Sistema inicializado com sucesso");
    }
}
