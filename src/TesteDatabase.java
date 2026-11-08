import java.sql.SQLException;
import java.util.ArrayList;

public class TesteDatabase {

    public static void main(String[] args) {

        try (DatabaseManager db = new DatabaseManager()) {

            executar(db);

        } catch (SQLException e) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("             ROTACENTER - INICIO");
            System.out.println("==============================================");
            System.out.println("[ERRO] Falha na conexão com o banco de dados!");
            System.out.println("Detalhes: " + e.getMessage());
            System.out.println("Verifique se o MySQL está funcionando.");

        }
    }

    public static void executar(DatabaseManager db) throws SQLException {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("             ROTACENTER - INICIO");
        System.out.println("==============================================");

        System.out.println("\n[OK] Conexão com o banco de dados estabelecida!");

        ArrayList<Order> pedidos = db.findAllOrders();

        System.out.println("\n              ÚLTIMOS PEDIDOS");
        System.out.println("--------------------------------------------------------------");

        System.out.printf(
                "%-6s %-22s %-14s %-14s %-12s%n",
                "ID", "CLIENTE", "COMPRA", "RETIRADA", "STATUS"
        );

        System.out.println("--------------------------------------------------------------");

        if (pedidos.isEmpty()) {

            System.out.println("Nenhum pedido encontrado.");

        } else {

            int inicio = Math.max(0, pedidos.size() - 5);

            for (int i = pedidos.size() - 1; i >= inicio; i--) {

                Order pedido = pedidos.get(i);

                String retirada = pedido.getPickupDate() != null
                        ? pedido.getPickupDate().toString()
                        : "Não definida";

                System.out.printf(
                        "%-6d %-22s %-14s %-14s %-12s%n",
                        pedido.getId(),
                        pedido.getCliente().getNome(),
                        pedido.getPurchaseDate(),
                        retirada,
                        pedido.getStatus()
                );
            }
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("             SISTEMA PRONTO PARA INICIAR");
        System.out.println("==============================================");
        System.out.println();
    }
}