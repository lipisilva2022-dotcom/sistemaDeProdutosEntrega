public class Cliente {

    //atributos
    private Pedido pedido;
    private String nome;
    private String cpfCnpj;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String clienteJaCadastrado;

    // Construtor
    public Cliente(String nome, String cpfCnpj, String endereco,
                   String bairro, String cidade, String cep) {

        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;

    }

    // Getters e Setters
    public Pedido getPedido () {
        return pedido; }

    public String getNome () {return nome;}
    public void setNome(String nome) {
        this.nome = nome;}

    public String getCpfCnpj () {
        return cpfCnpj;}

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;}

    public String getEndereco () {
        return endereco;}

    public void setEndereco(String endereco) {
        this.endereco = endereco;}

    public String getBairro () {
        return bairro;}

    public void setBairro(String bairro) {
        this.bairro = bairro;}

    public String getCidade () {
        return cidade;}

    public void setCidade(String cidade) {
        this.cidade = cidade;}

    public String getCep () {
        return cep;}

    public void setCep(String cep) {
        this.cep = cep;}
}


