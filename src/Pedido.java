import java.time.LocalDate;

import java.time.LocalDate;

public class Pedido {

    // Atributos
    private String nome;
    private String produto;
    private int quantidade;
    private LocalDate dataDaCompra;


    // Construtor
    public Pedido(String nome, String produto, int quantidade, LocalDate dataDaCompra) {

        this.nome = nome;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dataDaCompra = dataDaCompra;

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
}