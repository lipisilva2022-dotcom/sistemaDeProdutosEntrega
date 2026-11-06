public class OrderItem {

    // Atributos
    private int id;
    private String produto;
    private int quantidade;
    private double valor;

    // Construtor
    public OrderItem(String produto, int quantidade, double valor) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}