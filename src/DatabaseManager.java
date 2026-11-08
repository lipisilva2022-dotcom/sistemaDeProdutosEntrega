import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;
import java.util.ArrayList;

public class DatabaseManager implements AutoCloseable {

    private final Connection connection;

    public DatabaseManager() throws SQLException {
        this.connection = Conexao.conectar();
    }

    // ==========================================
    // CLIENTES
    // ==========================================

    public void registerCustomer(Cliente cliente) throws SQLException {

        String sql = """
                INSERT INTO clientes
                (nome, cpf_cnpj, endereco, bairro, cidade, cep)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCpfCnpj());
            statement.setString(3, cliente.getEndereco());
            statement.setString(4, cliente.getBairro());
            statement.setString(5, cliente.getCidade());
            statement.setString(6, cliente.getCep());

            statement.executeUpdate();
        }
    }

    public Cliente findCustomer(String cpfCnpj) throws SQLException {

        String sql = "SELECT * FROM clientes WHERE cpf_cnpj = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpfCnpj);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Cliente cliente = new Cliente(
                            result.getString("nome"),
                            result.getString("cpf_cnpj"),
                            result.getString("endereco"),
                            result.getString("bairro"),
                            result.getString("cidade"),
                            result.getString("cep")
                    );

                    cliente.setId(result.getInt("id"));

                    return cliente;
                }
            }
        }

        return null;
    }

    public void updateCustomer(Cliente cliente) throws SQLException {

        String sql = """
                UPDATE clientes
                SET nome = ?, endereco = ?, bairro = ?, cidade = ?, cep = ?
                WHERE cpf_cnpj = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEndereco());
            statement.setString(3, cliente.getBairro());
            statement.setString(4, cliente.getCidade());
            statement.setString(5, cliente.getCep());
            statement.setString(6, cliente.getCpfCnpj());

            statement.executeUpdate();
        }
    }

    // ==========================================
    // PEDIDOS
    // ==========================================

    public int registerOrder(Order order) throws SQLException {

        String sql = """
                INSERT INTO pedidos
                (cliente_id, data_compra, data_retirada, status)
                VALUES (?, ?, ?, ?)
                """;

        boolean originalAutoCommit = connection.getAutoCommit();

        try {

            connection.setAutoCommit(false);

            int orderId;

            try (PreparedStatement statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, order.getCliente().getId());

                statement.setDate(
                        2,
                        java.sql.Date.valueOf(order.getPurchaseDate())
                );

                if (order.getPickupDate() != null) {

                    statement.setDate(
                            3,
                            java.sql.Date.valueOf(order.getPickupDate())
                    );

                } else {

                    statement.setNull(3, Types.DATE);
                }

                statement.setString(4, order.getStatus());

                statement.executeUpdate();

                try (ResultSet result = statement.getGeneratedKeys()) {

                    if (!result.next()) {
                        throw new SQLException("Não foi possível obter o ID do pedido.");
                    }

                    orderId = result.getInt(1);
                }
            }

            registerOrderItems(orderId, order.getItems());

            connection.commit();

            return orderId;

        } catch (SQLException e) {

            connection.rollback();

            throw e;

        } finally {

            connection.setAutoCommit(originalAutoCommit);
        }
    }

    public Order findOrder(int id) throws SQLException {

        String sql = """
                SELECT
                    p.id AS order_id,
                    p.data_compra,
                    p.data_retirada,
                    p.status,

                    c.id AS customer_id,
                    c.nome,
                    c.cpf_cnpj,
                    c.endereco,
                    c.bairro,
                    c.cidade,
                    c.cep

                FROM pedidos p

                INNER JOIN clientes c
                ON p.cliente_id = c.id

                WHERE p.id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Cliente cliente = new Cliente(
                            result.getString("nome"),
                            result.getString("cpf_cnpj"),
                            result.getString("endereco"),
                            result.getString("bairro"),
                            result.getString("cidade"),
                            result.getString("cep")
                    );

                    cliente.setId(result.getInt("customer_id"));

                    Order order = new Order(
                            result.getInt("order_id"),
                            cliente,
                            result.getDate("data_compra").toLocalDate(),
                            result.getDate("data_retirada") != null
                                    ? result.getDate("data_retirada").toLocalDate()
                                    : null,
                            findOrderItems(id)
                    );

                    order.setStatus(result.getString("status"));

                    return order;
                }
            }
        }

        return null;
    }

    public ArrayList<Order> findAllOrders() throws SQLException {

        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT id FROM pedidos ORDER BY id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Order order = findOrder(result.getInt("id"));

                if (order != null) {
                    orders.add(order);
                }
            }
        }

        return orders;
    }

    public void updateOrder(Order order) throws SQLException {

        String sql = """
                UPDATE pedidos
                SET cliente_id = ?, data_compra = ?, data_retirada = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getCliente().getId());

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(order.getPurchaseDate())
            );

            if (order.getPickupDate() != null) {

                statement.setDate(
                        3,
                        java.sql.Date.valueOf(order.getPickupDate())
                );

            } else {

                statement.setNull(3, Types.DATE);
            }

            statement.setInt(4, order.getId());

            statement.executeUpdate();
        }
    }

    public void updateOrderStatus(int id, String status) throws SQLException {

        String sql = "UPDATE pedidos SET status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    // ==========================================
    // ITENS DOS PEDIDOS
    // ==========================================

    public void registerOrderItems(int orderId, ArrayList<OrderItem> items)
            throws SQLException {

        String sql = """
                INSERT INTO item_pedidos
                (pedido_id, produto, quantidade, valor)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (OrderItem item : items) {

                statement.setInt(1, orderId);
                statement.setString(2, item.getProduto());
                statement.setInt(3, item.getQuantidade());

                statement.setBigDecimal(
                        4,
                        BigDecimal.valueOf(item.getValor())
                );

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    public ArrayList<OrderItem> findOrderItems(int orderId) throws SQLException {

        ArrayList<OrderItem> items = new ArrayList<>();

        String sql = "SELECT * FROM item_pedidos WHERE pedido_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    OrderItem item = new OrderItem(
                            result.getString("produto"),
                            result.getInt("quantidade"),
                            result.getDouble("valor")
                    );

                    item.setId(result.getInt("id"));

                    items.add(item);
                }
            }
        }

        return items;
    }

    public void updateOrderItem(
            int itemId,
            String produto,
            int quantidade,
            double valor) throws SQLException {

        String sql = """
                UPDATE item_pedidos
                SET produto = ?, quantidade = ?, valor = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produto);
            statement.setInt(2, quantidade);

            statement.setBigDecimal(
                    3,
                    BigDecimal.valueOf(valor)
            );

            statement.setInt(4, itemId);

            statement.executeUpdate();
        }
    }

    public void deleteOrderItem(int itemId) throws SQLException {

        String sql = "DELETE FROM item_pedidos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);

            statement.executeUpdate();
        }
    }

    // ==========================================
    // CONEXÃO
    // ==========================================

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
