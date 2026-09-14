import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {

    // Atributos
    private int id;
    private String nome;
    private LocalDate dataDaCompra;
    private LocalDate dataDeRetirada;
    private String Status;

    private ArrayList<ItemPedido> itens;

    // Construtor
    public Pedido(
            int id,
            String nome,
            LocalDate dataDaCompra,
            LocalDate dataDeRetirada,
            ArrayList<ItemPedido> itens) {

        this.id = id;
        this.nome = nome;
        this.dataDaCompra = dataDaCompra;
        this.dataDeRetirada = dataDeRetirada;
        this.itens = itens;
        this.Status = "PENDENTE";
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }
}