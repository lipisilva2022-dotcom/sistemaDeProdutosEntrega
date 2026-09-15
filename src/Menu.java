import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();

        // ================= DADOS DE TESTE =================

        Cliente clienteTeste = new Cliente(
                "João Teste",
                "55501571870",
                "Rua Teste",
                "Centro",
                "Casa Branca",
                "13700000"
        );

        clientes.add(clienteTeste);

        Random random = new Random();

        boolean continuar = true;

        while (continuar) {

            System.out.println("====ROTACENTER====");
            System.out.println("1 - Cadastrar pedido");
            System.out.println("2 - Cadastrar cliente");
            System.out.println("3 - Consultar pedido");
            System.out.println("4 - Atualizar status do pedido");
            System.out.println("5 - Atualizar pedido");
            System.out.println("6 - Emitir nota promisoria");
            System.out.println("0 - Sair");

            int opcao = Entradas.lerInteiro(sc);

            switch (opcao) {

                case 1:
                    cadastrarPedido(sc, pedidos, clientes, random);
                    break;

                case 2:
                    cadastrarCliente(sc, clientes);
                    break;

                case 3:
                    consultarPedido(sc, pedidos);
                    break;

                case 4:
                    atualizarStatusPedido(sc, pedidos);
                    break;

                case 5:
                    atualizarPedido(sc, pedidos);
                    break;

                case 6:
                    emitirNotaPromissoria(sc, pedidos);
                    break;

                case 0:

                    System.out.println("Deseja realmente sair?");
                    System.out.println("1 - Sim");
                    System.out.println("2 - Não");

                    int confirmacao = Entradas.lerInteiro(sc);

                    if (confirmacao == 1) {
                        continuar = false;
                        System.out.println("Saindo...");

                    } else if (confirmacao == 2) {
                        System.out.println("Voltando para o menu...");

                    } else {
                        System.out.println("Opção inválida!");
                    }

                    break;

                default:
                    System.out.println("OPÇÃO INVÁLIDA!");
            }
        }

        sc.close();
    }

    public static void cadastrarPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos,
            ArrayList<Cliente> clientes,
            Random random) {

        System.out.println("===== CADASTRO DE PEDIDO =====");
        System.out.println();

        System.out.println("Cliente já cadastrado?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int clienteJaCadastrado = Entradas.lerInteiro(sc);

        Cliente clienteEncontrado;

        if (clienteJaCadastrado == 1) {

            System.out.println("Digite o CPF/CNPJ do cliente:");

            String cpfCnpj = sc.nextLine();

            clienteEncontrado = buscarCliente(clientes, cpfCnpj);

            if (clienteEncontrado == null) {

                System.out.println("Cliente não encontrado!");
                return;
            }

            System.out.println(
                    "Cliente encontrado: "
                            + clienteEncontrado.getNome());

        } else if (clienteJaCadastrado == 2) {

            clienteEncontrado = cadastrarCliente(sc, clientes);

        } else {

            System.out.println("Opção inválida!");
            return;
        }

        ArrayList<ItemPedido> itemPedidos = new ArrayList<>();

        while (true) {

            System.out.println("Digite o produto:");
            String produto = sc.nextLine();

            System.out.println("Digite a quantidade:");
            int quantidade = Entradas.lerInteiro(sc);

            System.out.println("Digite o valor:");
            double valor = Entradas.lerDouble(sc);

            ItemPedido item = new ItemPedido(
                    produto,
                    quantidade,
                    valor
            );

            itemPedidos.add(item);

            System.out.println("===== DESEJA CADASTRA MAIS PRODUTOS =====");
            System.out.println();
            System.out.println("1 - Sim");
            System.out.println("2 - Não");

            int itemPedido = Entradas.lerInteiro(sc);

            while (itemPedido != 1 && itemPedido != 2) {

                System.out.println("⚠️ Digite apenas 1 ou 2!");
                System.out.println();

                System.out.println("===== DESEJA CADASTRA MAIS PRODUTOS =====");
                System.out.println();
                System.out.println("1 - Sim");
                System.out.println("2 - Não");

                itemPedido = Entradas.lerInteiro(sc);
            }

            if (itemPedido == 2) {
                break;
            }
        }

        System.out.println("Digite a data da compra:");

        String dataTexto = sc.nextLine();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataDaCompra =
                LocalDate.parse(dataTexto, formato);

        System.out.println(
                "Digite a data que deseja retirar o pedido:");

        String dataRetiradaTexto = sc.nextLine();

        LocalDate dataDeRetirada =
                LocalDate.parse(dataRetiradaTexto, formato);

        // Geração do ID

        int numeroPedido;
        boolean existe;

        do {

            numeroPedido = random.nextInt(900000) + 100000;

            existe = false;

            for (Pedido pedido : pedidos) {

                if (pedido.getId() == numeroPedido) {
                    existe = true;
                }
            }

        } while (existe);

        Pedido pedido = new Pedido(
                numeroPedido,
                clienteEncontrado,
                dataDaCompra,
                dataDeRetirada,
                itemPedidos
        );

        pedidos.add(pedido);

        System.out.println("Pedido cadastrado!");
        System.out.println("Número do pedido: " + pedido.getId());
    }

    private static Cliente buscarCliente(
            ArrayList<Cliente> clientes,
            String cpfCnpj) {

        for (Cliente cliente : clientes) {

            if (cpfCnpj.equals(cliente.getCpfCnpj())) {

                return cliente;
            }
        }

        return null;
    }

    private static Cliente cadastrarCliente(
            Scanner sc,
            ArrayList<Cliente> clientes) {

        System.out.println("===== CADASTRO DE CLIENTE =====");
        System.out.println();

        System.out.println("Digite o nome do cliente");
        String nome = sc.nextLine();

        System.out.println("Digite o CPF/CNPJ do cliente");
        String cpfcnpj = sc.nextLine();

        System.out.println("Digite o endereço");
        String endereco = sc.nextLine();

        System.out.println("Digite o bairro");
        String bairro = sc.nextLine();

        System.out.println("Digite a cidade");
        String cidade = sc.nextLine();

        System.out.println("Digite o CEP");
        String cep = sc.nextLine();

        Cliente cliente = new Cliente(
                nome,
                cpfcnpj,
                endereco,
                bairro,
                cidade,
                cep
        );

        clientes.add(cliente);

        System.out.println("======== CLIENTE CADASTRADO ========");

        return cliente;
    }

    private static void consultarPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("======== CONSULTAR PEDIDO ========");
        System.out.println();

        System.out.println("-".repeat(31));

        System.out.println("Digite o número do pedido:");
        int numeroPedido = Entradas.lerInteiro(sc);

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                System.out.println("Pedido encontrado!");
                System.out.println();

                System.out.println(
                        "Número do pedido: "
                                + pedido.getId());

                System.out.println(
                        "Cliente: "
                                + pedido.getCliente().getNome());

                System.out.println();

                System.out.println(
                        "Produto           Quantidade        Valor");

                System.out.println("-".repeat(45));

                double total = 0;

                for (ItemPedido item : pedido.getItens()) {

                    double subtotal =
                            item.getQuantidade() * item.getValor();

                    System.out.printf(
                            "%-20s %-17d R$ %.2f un%n",
                            item.getProduto(),
                            item.getQuantidade(),
                            item.getValor()
                    );

                    total += subtotal;
                }

                System.out.println("-".repeat(45));

                System.out.printf(
                        "TOTAL:                                  R$ %.2f%n",
                        total
                );

                System.out.println();

                System.out.println(
                        "Data da compra: "
                                + pedido.getDataDaCompra());

                System.out.println(
                        "Data de retirada: "
                                + pedido.getDataDeRetirada());

                System.out.println(
                        "Status: "
                                + pedido.getStatus());

                System.out.println("-".repeat(31));

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }

    private static void atualizarStatusPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("Digite o número do pedido:");

        int numeroPedido = Entradas.lerInteiro(sc);

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                System.out.println("Pedido encontrado!");

                System.out.println("1 - Entregue");

                int opcaoStatus = Entradas.lerInteiro(sc);

                if (opcaoStatus == 1) {

                    pedido.setStatus("ENTREGUE");

                    System.out.println("Status atualizado!");
                }

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }

    private static void atualizarPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("======== ATUALIZAR PEDIDO ========");

        System.out.println("Digite o número do pedido:");

        int numeroPedido = Entradas.lerInteiro(sc);

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                boolean continuar = true;

                while (continuar) {

                    System.out.println("1 - Alterar valor");
                    System.out.println("2 - Alterar data de retirada");
                    System.out.println("3 - Alterar quantidade");
                    System.out.println("0 - Voltar");

                    System.out.println("Digite a opção:");

                    int opcao = Entradas.lerInteiro(sc);

                    switch (opcao) {

                        case 1:

                            System.out.println("Digite o novo valor:");

                            double novoValor =
                                    Entradas.lerDouble(sc);

                            pedido.getItens()
                                    .get(0)
                                    .setValor(novoValor);

                            System.out.println("Valor alterado!");

                            break;

                        case 2:

                            System.out.println(
                                    "Digite a nova data de retirada:");

                            String novaDataTexto =
                                    sc.nextLine();

                            DateTimeFormatter formato =
                                    DateTimeFormatter.ofPattern(
                                            "dd/MM/yyyy");

                            LocalDate novaData =
                                    LocalDate.parse(
                                            novaDataTexto,
                                            formato);

                            pedido.setDataDeRetirada(novaData);

                            System.out.println(
                                    "Data de retirada alterada!");

                            break;

                        case 3:

                            System.out.println(
                                    "Digite a nova quantidade:");

                            int novaQuantidade =
                                    Entradas.lerInteiro(sc);

                            pedido.getItens()
                                    .get(0)
                                    .setQuantidade(novaQuantidade);

                            System.out.println(
                                    "Quantidade alterada!");

                            break;

                        case 0:

                            System.out.println(
                                    "Deseja realmente voltar?");

                            System.out.println("1 - Sim");
                            System.out.println("2 - Não");

                            int confirmacao =
                                    Entradas.lerInteiro(sc);

                            if (confirmacao == 1) {

                                continuar = false;

                                System.out.println(
                                        "VOLTANDO PARA O MENU...");
                            }

                            break;

                        default:

                            System.out.println(
                                    "OPÇÃO INVÁLIDA!");
                    }
                }

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }

    private static void emitirNotaPromissoria(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("=".repeat(58));
        System.out.printf(
                "%17s%s%n",
                "",
                "NOTA PROMISSÓRIA");
        System.out.println("=".repeat(58));

        System.out.println("Digite o número do pedido:");

        int numeroPedido = Entradas.lerInteiro(sc);

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                System.out.println();
                System.out.println();

                System.out.println("=".repeat(58));

                System.out.println(
                        "NÚMERO DO PEDIDO: "
                                + pedido.getId());

                System.out.println(
                        "DATA DA COMPRA: "
                                + pedido.getDataDaCompra());

                System.out.println();

                System.out.println("-".repeat(58));

                System.out.println(
                        "CLIENTE: "
                                + pedido.getCliente().getNome());

                System.out.println(
                        "CPF/CNPJ: "
                                + pedido.getCliente().getCpfCnpj());

                System.out.println(
                        "ENDEREÇO: "
                                + pedido.getCliente().getEndereco());

                System.out.println(
                        "BAIRRO: "
                                + pedido.getCliente().getBairro());

                System.out.println(
                        "CIDADE: "
                                + pedido.getCliente().getCidade());

                System.out.println(
                        "CEP: "
                                + pedido.getCliente().getCep());

                System.out.println();

                System.out.println("-".repeat(58));

                System.out.printf(
                        "%-20s %-10s %-15s %-15s%n",
                        "PRODUTO",
                        "QTD",
                        "VL. UNIT.",
                        "SUBTOTAL");

                System.out.println("-".repeat(58));

                double total = 0;

                for (ItemPedido item : pedido.getItens()) {

                    double subtotal =
                            item.getQuantidade()
                                    * item.getValor();

                    System.out.printf(
                            "%-20s %-10d R$ %-11.2f R$ %.2f%n",
                            item.getProduto(),
                            item.getQuantidade(),
                            item.getValor(),
                            subtotal
                    );

                    total += subtotal;
                }

                System.out.println("-".repeat(58));

                System.out.printf(
                        "TOTAL R$:                              %.2f%n",
                        total);

                System.out.println();

                System.out.println("-".repeat(58));

                System.out.println(
                        "DATA DE RETIRADA: "
                                + pedido.getDataDeRetirada());

                System.out.println(
                        "STATUS: "
                                + pedido.getStatus());

                System.out.println("-".repeat(58));

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }
}