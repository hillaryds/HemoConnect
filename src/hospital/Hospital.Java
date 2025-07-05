public class Hospital{
    private String nomeDoHospital;
    private String cep;
    private String cidade;
    private int codigo;
    // TODO: Adicionar os Administradores
    // TODO: Adicionar os doadores

    Hospital(String nomeDoHospital, String cep,String cidade,String codigo){
        this.nomeDoHospital = nomeDoHospital;
        this.cep = cep;
        this.cidade = cidade;
        this.codigo = codigo;
    }

    public void criarHospital(String nomeDoHospital, String cep,String cidade,String codigoHospital){
        // TODO: Adicionar tratativa de possíveis entradas erradas
        Hospital(nomeDoHospital, cep, cidade, codigoHospital);
    }

    //TODO: Adicionar o método consultarDoadoresVinculados que retorna List<Doador>

    public void exibeHospital(){
        System.out.println(String.format("Nome do hospital %s", this.nomeDoHospital));
        System.out.println(String.format("CEP: %s", this.cep));
        System.out.println(String.format("Cidade %s", this.cidade));
        System.out.println(String.format("Código do Hospital %d", this.nomeDoHospital));
    }

    //TODO: Listar hospitais que retorna List<Hospital>

    public void removerHospital(){
        // TODO: implementar com o banco de dados  
    }
}