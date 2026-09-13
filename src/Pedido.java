import java.time.LocalDate;

public class Pedido {

    // Atributos
    private int id;
    private String nome;
    private String produto;
    private int quantidade;
    private double valorTotal;
    private LocalDate dataDaCompra;
    private LocalDate dataDeRetirada;
    private String Status ;


    public int getId() {
        return id;
    }

    // Construtor
    public Pedido(int id, String nome, String produto, int quantidade, LocalDate dataDaCompra, LocalDate dataDeRetirada, double valorTotal ) {

        this.nome = nome;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.dataDaCompra = dataDaCompra;
        this.dataDeRetirada = dataDeRetirada;
        this.id = id;
        this.Status = "PENDENTE";
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }

    public void setDataDaCompra(LocalDate dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }

    public LocalDate getDataDeRetirada() {
        return dataDeRetirada;
    }

    public void setDataDeRetirada(LocalDate dataDeRetirada) {
        this.dataDeRetirada = dataDeRetirada;
    }

    public String getStatus(){
        return Status;
    }

    public void setStatus(String Status){
        this.Status = Status;
    }

    public double getvalorTotal() {
        return valorTotal;
    }

    public void setValor(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}