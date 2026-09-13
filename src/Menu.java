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

        Random random = new Random();

        while (true) {

            System.out.println("====ROTACENTER====");
            System.out.println("1 - Cadastrar pedido");
            System.out.println("2 - Cadastrar cliente");
            System.out.println("3 - Consultar pedido");
            System.out.println("4 - Atualizar status do pedido");
            System.out.println("5 - Atualizar pedido");
            System.out.println("6 - Emitir nota provisoria");
            System.out.println("0 - Sair");

            int opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {

                cadastrarPedido(sc, pedidos, clientes, random);

            } else if (opcao == 2) {

                cadastrarCliente(sc, clientes);

            } else if (opcao == 3) {

                consultarPedido(sc, pedidos);

            } else if (opcao == 4) {

                atualizarStatusPedido(sc, pedidos);

            } else if (opcao == 5) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 6) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 0) {

                System.out.println("Saindo...");
                break;

            } else {

                System.out.println("OPÇÃO INVÁLIDA, ENCERRANDO...");
            }
        }

        sc.close();
    }

    /*
     * Método para cadastrar pedido
     */
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

        int clienteJaCadastrado = sc.nextInt();
        sc.nextLine();

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

        System.out.println("Digite o produto:");
        String produto = sc.nextLine();

        System.out.println("Digite a quantidade:");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o valor:");
        double valor = sc.nextDouble();
        sc.nextLine();

        double valorTotal = valor * quantidade;

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
                clienteEncontrado.getNome(),
                produto,
                quantidade,
                dataDaCompra,
                dataDeRetirada,
                valorTotal
        );

        pedidos.add(pedido);

        System.out.println("Pedido cadastrado!");
        System.out.println("Número do pedido: " + pedido.getId());
    }

    /*
     * Método para buscar cliente
     */
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

    /*
     * Método para cadastro de clientes
     */
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

    /*
     * Método para consulta de pedidos
     */
    private static void consultarPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("======== CONSULTAR PEDIDO ========");
        System.out.println();
        System.out.println("-------------------------------");

        System.out.println("Digite o número do pedido:");
        int numeroPedido = sc.nextInt();
        sc.nextLine();

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                System.out.println("Pedido encontrado!");
                System.out.println();

                System.out.println(
                        "Número do pedido: " + pedido.getId());

                System.out.println(
                        "Cliente: " + pedido.getNome());

                System.out.println(
                        "Produto: " + pedido.getProduto());

                System.out.println(
                        "Quantidade: " + pedido.getQuantidade());

                System.out.println(
                        "Valor total: R$ " + pedido.getValorTotal());

                System.out.println(
                        "Data da compra: " + pedido.getDataDaCompra());

                System.out.println(
                        "Data de retirada: " + pedido.getDataDeRetirada());

                System.out.println(
                        "Status: " + pedido.getStatus());

                System.out.println("-------------------------------");

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }

    /*
     * Método para mudança de status do pedido
     */
    private static void atualizarStatusPedido(
            Scanner sc,
            ArrayList<Pedido> pedidos) {

        System.out.println("Digite o número do pedido:");
        int numeroPedido = sc.nextInt();
        sc.nextLine();

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == numeroPedido) {

                System.out.println("Pedido encontrado!");

                System.out.println("1 - Entregue");

                int opcaoStatus = sc.nextInt();
                sc.nextLine();

                if (opcaoStatus == 1) {

                    pedido.setStatus("ENTREGUE");

                    System.out.println("Status atualizado!");
                }

                return;
            }
        }

        System.out.println("Pedido não encontrado!");
    }
}