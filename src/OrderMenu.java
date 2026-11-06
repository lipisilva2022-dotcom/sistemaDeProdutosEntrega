import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderMenu {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ==========================================
    // INICIALIZAÇÃO
    // ==========================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (DatabaseManager database = new DatabaseManager()) {

            boolean continuar = true;

            while (continuar) {

                System.out.println("\n===== ROTACENTER =====");
                System.out.println("1 - Cadastrar pedido");
                System.out.println("2 - Cadastrar cliente");
                System.out.println("3 - Histórico e consulta de pedidos");
                System.out.println("4 - Atualizar status do pedido");
                System.out.println("5 - Atualizar pedido");
                System.out.println("6 - Emitir nota promissória");
                System.out.println("0 - Sair");

                int opcao = Entradas.lerInteiro(sc);

                try {

                    switch (opcao) {

                        case 1:
                            registerOrder(sc, database);
                            break;

                        case 2:
                            registerCustomer(sc, database);
                            break;

                        case 3:
                            consultOrders(sc, database);
                            break;

                        case 4:
                            updateOrderStatus(sc, database);
                            break;

                        case 5:
                            updateOrder(sc, database);
                            break;

                        case 6:
                            issuePromissoryNote(sc, database);
                            break;

                        case 0:
                            System.out.println("Saindo do ROTACENTER...");
                            continuar = false;
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }

                } catch (SQLException e) {

                    System.out.println("Erro ao acessar o banco de dados: "
                            + e.getMessage());
                }
            }

        } catch (SQLException e) {

            System.out.println("Não foi possível conectar ao MySQL: "
                    + e.getMessage());

        } finally {

            sc.close();
        }
    }

    // ==========================================
    // CLIENTES
    // ==========================================

    private static Cliente registerCustomer(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("\n===== CADASTRO DE CLIENTE =====");

        System.out.println("Digite o nome:");
        String nome = Entradas.lerTexto(sc);

        System.out.println("Digite o CPF/CNPJ:");
        String cpfCnpj = Entradas.lerCpfCnpj(sc);

        System.out.println("Digite o endereço:");
        String endereco = Entradas.lerTexto(sc);

        System.out.println("Digite o bairro:");
        String bairro = Entradas.lerTexto(sc);

        System.out.println("Digite a cidade:");
        String cidade = Entradas.lerTexto(sc);

        System.out.println("Digite o CEP:");
        String cep = Entradas.lerCep(sc);

        Cliente cliente = new Cliente(
                nome,
                cpfCnpj,
                endereco,
                bairro,
                cidade,
                cep
        );

        database.registerCustomer(cliente);

        Cliente clienteSalvo = database.findCustomer(cpfCnpj);

        System.out.println("\nCliente cadastrado com sucesso!");

        return clienteSalvo;
    }

    private static Cliente searchCustomer(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("Digite o CPF/CNPJ do cliente:");

        String cpfCnpj = sc.nextLine();

        return database.findCustomer(cpfCnpj);
    }

    // ==========================================
    // PEDIDOS
    // ==========================================

    private static void registerOrder(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("\n===== CADASTRO DE PEDIDO =====");

        System.out.println("Cliente já cadastrado?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int opcao = Entradas.lerInteiro(sc);

        Cliente cliente;

        if (opcao == 1) {

            cliente = searchCustomer(sc, database);

            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            System.out.println("Cliente encontrado: "
                    + cliente.getNome());

        } else if (opcao == 2) {

            cliente = registerCustomer(sc, database);

        } else {

            System.out.println("Opção inválida!");
            return;
        }

        ArrayList<OrderItem> items = new ArrayList<>();

        boolean adicionar = true;

        while (adicionar) {

            System.out.println("\nDigite o produto:");
            String produto = sc.nextLine();

            System.out.println("Digite a quantidade:");
            int quantidade = Entradas.lerInteiro(sc);

            System.out.println("Digite o valor unitário:");
            double valor = Entradas.lerDouble(sc);

            OrderItem item = new OrderItem(
                    produto,
                    quantidade,
                    valor
            );

            items.add(item);

            System.out.println("\nDeseja cadastrar mais produtos?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");

            int resposta = Entradas.lerInteiro(sc);

            while (resposta != 1 && resposta != 2) {
                System.out.println("Digite apenas 1 ou 2!");
                resposta = Entradas.lerInteiro(sc);
            }

            adicionar = resposta == 1;
        }

        System.out.println("\nDigite a data da compra (dd/MM/yyyy):");
        LocalDate purchaseDate = LocalDate.parse(
                sc.nextLine(), FORMAT
        );

        System.out.println("Digite a data de retirada (dd/MM/yyyy):");
        LocalDate pickupDate = LocalDate.parse(
                sc.nextLine(), FORMAT
        );

        Order order = new Order(
                0,
                cliente,
                purchaseDate,
                pickupDate,
                items
        );

        int orderId = database.registerOrder(order);

        System.out.println("\nPedido cadastrado com sucesso!");
        System.out.println("Número do pedido: " + orderId);
    }

    // ==========================================
    // CONSULTA DE PEDIDOS
    // ==========================================

    private static void consultOrders(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        boolean continuar = true;

        while (continuar) {

            System.out.println("\n===== HISTÓRICO DE PEDIDOS =====");

            ArrayList<Order> orders = database.findAllOrders();

            if (orders.isEmpty()) {
                System.out.println("Nenhum pedido cadastrado.");
                return;
            }

            for (Order order : orders) {

                System.out.println("Pedido: " + order.getId());

                System.out.println("Cliente: "
                        + order.getCliente().getNome());

                System.out.println("Data da compra: "
                        + order.getPurchaseDate());

                System.out.println("Status: "
                        + order.getStatus());

                System.out.println("-".repeat(45));
            }

            System.out.println("\nDeseja consultar algum pedido?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");

            int resposta = Entradas.lerInteiro(sc);

            if (resposta == 2) {
                return;
            }

            if (resposta != 1) {
                System.out.println("Opção inválida!");
                continue;
            }

            System.out.println("Digite o número do pedido:");

            int orderId = Entradas.lerInteiro(sc);

            Order order = database.findOrder(orderId);

            if (order == null) {
                System.out.println("Pedido não encontrado!");
                continue;
            }

            displayOrder(order);

            System.out.println("\nDeseja consultar outro pedido?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");

            continuar = Entradas.lerInteiro(sc) == 1;
        }
    }

    private static void displayOrder(Order order) {

        System.out.println("\n===== PEDIDO ENCONTRADO =====");

        System.out.println("Número: " + order.getId());

        System.out.println("Cliente: "
                + order.getCliente().getNome());

        System.out.println("\nPRODUTO             QUANTIDADE       VALOR");

        double total = 0;

        for (OrderItem item : order.getItems()) {

            double subtotal = item.getQuantidade() * item.getValor();

            System.out.printf(
                    "%-20s %-15d R$ %.2f%n",
                    item.getProduto(),
                    item.getQuantidade(),
                    item.getValor()
            );

            total += subtotal;
        }

        System.out.println("-".repeat(50));

        System.out.printf("TOTAL: R$ %.2f%n", total);

        System.out.println("Data da compra: "
                + order.getPurchaseDate());

        System.out.println("Data de retirada: "
                + order.getPickupDate());

        System.out.println("Status: " + order.getStatus());
    }

    // ==========================================
    // ATUALIZAR STATUS
    // ==========================================

    private static void updateOrderStatus(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("\nDigite o número do pedido:");

        int id = Entradas.lerInteiro(sc);

        Order order = database.findOrder(id);

        if (order == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        System.out.println("Pedido encontrado!");
        System.out.println("Status atual: " + order.getStatus());

        System.out.println("\n1 - Entregue");

        int opcao = Entradas.lerInteiro(sc);

        if (opcao == 1) {

            database.updateOrderStatus(id, "ENTREGUE");

            System.out.println("Status atualizado!");
        } else {

            System.out.println("Opção inválida!");
        }
    }

    // ==========================================
    // ATUALIZAR PEDIDO
    // ==========================================

    private static void updateOrder(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("\n===== ATUALIZAR PEDIDO =====");

        System.out.println("Digite o número do pedido:");

        int id = Entradas.lerInteiro(sc);

        Order order = database.findOrder(id);

        if (order == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        boolean continuar = true;

        while (continuar) {

            System.out.println("\n1 - Alterar data de retirada");
            System.out.println("2 - Alterar valor de um produto");
            System.out.println("3 - Alterar quantidade de um produto");
            System.out.println("4 - Adicionar produto");
            System.out.println("0 - Voltar");

            int opcao = Entradas.lerInteiro(sc);

            switch (opcao) {

                case 1:

                    System.out.println("Digite a nova data (dd/MM/yyyy):");

                    LocalDate novaData = LocalDate.parse(
                            sc.nextLine(), FORMAT
                    );

                    order.setPickupDate(novaData);

                    database.updateOrder(order);

                    System.out.println("Data atualizada!");

                    break;

                case 2:

                    OrderItem itemValor = selectOrderItem(sc, order);

                    if (itemValor != null) {

                        System.out.println("Digite o novo valor:");

                        double valor = Entradas.lerDouble(sc);

                        database.updateOrderItem(
                                itemValor.getId(),
                                itemValor.getProduto(),
                                itemValor.getQuantidade(),
                                valor
                        );

                        System.out.println("Valor atualizado!");
                    }

                    break;

                case 3:

                    OrderItem itemQuantidade = selectOrderItem(sc, order);

                    if (itemQuantidade != null) {

                        System.out.println("Digite a nova quantidade:");

                        int quantidade = Entradas.lerInteiro(sc);

                        database.updateOrderItem(
                                itemQuantidade.getId(),
                                itemQuantidade.getProduto(),
                                quantidade,
                                itemQuantidade.getValor()
                        );

                        System.out.println("Quantidade atualizada!");
                    }

                    break;

                case 4:

                    System.out.println("Digite o produto:");
                    String produto = sc.nextLine();

                    System.out.println("Digite a quantidade:");
                    int quantidade = Entradas.lerInteiro(sc);

                    System.out.println("Digite o valor:");
                    double valor = Entradas.lerDouble(sc);

                    ArrayList<OrderItem> newItems = new ArrayList<>();

                    newItems.add(new OrderItem(
                            produto,
                            quantidade,
                            valor
                    ));

                    database.registerOrderItems(id, newItems);

                    System.out.println("Produto adicionado!");

                    break;

                case 0:

                    continuar = false;

                    break;

                default:

                    System.out.println("Opção inválida!");
            }

            // Atualiza o objeto com os dados mais recentes do banco.
            if (continuar) {
                order = database.findOrder(id);
            }
        }
    }

    private static OrderItem selectOrderItem(
            Scanner sc,
            Order order) {

        if (order.getItems().isEmpty()) {
            System.out.println("Esse pedido não possui produtos.");
            return null;
        }

        System.out.println("\n===== PRODUTOS DO PEDIDO =====");

        for (OrderItem item : order.getItems()) {

            System.out.println(
                    "ID: " + item.getId()
                            + " | Produto: " + item.getProduto()
                            + " | Quantidade: " + item.getQuantidade()
                            + " | Valor: R$ " + item.getValor()
            );
        }

        System.out.println("\nDigite o ID do item que deseja alterar:");

        int itemId = Entradas.lerInteiro(sc);

        for (OrderItem item : order.getItems()) {

            if (item.getId() == itemId) {
                return item;
            }
        }

        System.out.println("Item não encontrado!");

        return null;
    }

    // ==========================================
    // NOTA PROMISSÓRIA
    // ==========================================

    private static void issuePromissoryNote(
            Scanner sc,
            DatabaseManager database) throws SQLException {

        System.out.println("\n===== NOTA PROMISSÓRIA =====");

        System.out.println("Digite o número do pedido:");

        int id = Entradas.lerInteiro(sc);

        Order order = database.findOrder(id);

        if (order == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        Cliente cliente = order.getCliente();

        System.out.println("\n" + "=".repeat(58));

        System.out.println("                 NOTA PROMISSÓRIA");

        System.out.println("=".repeat(58));

        System.out.println("NÚMERO DO PEDIDO: " + order.getId());

        System.out.println("DATA DA COMPRA: " + order.getPurchaseDate());

        System.out.println("-".repeat(58));

        System.out.println("CLIENTE: " + cliente.getNome());

        System.out.println("CPF/CNPJ: " + cliente.getCpfCnpj());

        System.out.println("ENDEREÇO: " + cliente.getEndereco());

        System.out.println("BAIRRO: " + cliente.getBairro());

        System.out.println("CIDADE: " + cliente.getCidade());

        System.out.println("CEP: " + cliente.getCep());

        System.out.println("-".repeat(58));

        System.out.printf(
                "%-20s %-10s %-13s %-13s%n",
                "PRODUTO",
                "QTD",
                "VL. UNIT.",
                "SUBTOTAL"
        );

        double total = 0;

        for (OrderItem item : order.getItems()) {

            double subtotal = item.getQuantidade() * item.getValor();

            System.out.printf(
                    "%-20s %-10d R$ %-10.2f R$ %.2f%n",
                    item.getProduto(),
                    item.getQuantidade(),
                    item.getValor(),
                    subtotal
            );

            total += subtotal;
        }

        System.out.println("-".repeat(58));

        System.out.printf("TOTAL: R$ %.2f%n", total);

        System.out.println();

        System.out.println("DATA DE RETIRADA: " + order.getPickupDate());

        System.out.println("STATUS: " + order.getStatus());

        System.out.println("=".repeat(58));
    }
}