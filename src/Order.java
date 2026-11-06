import java.time.LocalDate;
import java.util.ArrayList;

public class Order {

    // Atributos
    private int id;
    private Cliente cliente;
    private LocalDate purchaseDate;
    private LocalDate pickupDate;
    private String status;

    private ArrayList<OrderItem> items;

    // Construtor
    public Order(
            int id,
            Cliente cliente,
            LocalDate purchaseDate,
            LocalDate pickupDate,
            ArrayList<OrderItem> items) {

        this.id = id;
        this.cliente = cliente;
        this.purchaseDate = purchaseDate;
        this.pickupDate = pickupDate;
        this.items = items;
        this.status = "PENDENTE";
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }
}