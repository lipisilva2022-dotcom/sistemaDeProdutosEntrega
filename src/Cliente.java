public class Cliente {

    private Pedido pedido;
    private String nome;
    private String cpfCnpj;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;

    public Cliente(String nome, String cpfCnpj, String endereco,
                   String bairro, String cidade, String cep) {

        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
    }



}
