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

                System.out.println("================================");
                System.out.println("CADASTRO DE PEDIDOS SELECIONADO");
                System.out.println();

                System.out.println("Digite o nome do cliente:");
                String nome = sc.nextLine();

                System.out.println("");

                System.out.println("Digite o produto:");
                String produto = sc.nextLine();

                System.out.println("Digite a quantidade de sacas:");
                int quantidade = sc.nextInt();
                sc.nextLine();

                System.out.println("Digite a data da compra:");
                String dataTexto = sc.nextLine();

                DateTimeFormatter formato =
                        DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate dataDaCompra =
                        LocalDate.parse(dataTexto, formato);

                // Geração do número único do pedido
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

                Pedido pedido1 =
                        new Pedido(
                                numeroPedido,
                                nome,
                                produto,
                                quantidade,
                                dataDaCompra
                        );

                pedidos.add(pedido1);

                System.out.println("========PEDIDO COMPUTADO========");

            } else if (opcao == 2) {

                System.out.println("--------------------------------");
                System.out.println("        CADASTRA CLIENTE        ");
                System.out.println("--------------------------------");
                System.out.println();

                // Dados do cliente

                System.out.println("Digite o nome cliente:");
                String nome = sc.nextLine();

                System.out.println("Digite o cpf/cnpj:");
                String cpfCnpj = sc.nextLine();

                System.out.println("Digite o endereço:");
                String endereco = sc.nextLine();

                System.out.println("Digite o bairro:");
                String bairro = sc.nextLine();

                System.out.println("Digite a cidade:");
                String cidade = sc.nextLine();

                System.out.println("Digite o cep:");
                String cep = sc.nextLine();

                Cliente cliente =
                        new Cliente(nome, cpfCnpj, endereco, bairro, cidade, cep);

                clientes.add(cliente);

                System.out.println("========CLIENTE CADASTRADO========");

            } else if (opcao == 3) {

                System.out.println("-------------------------------");
                System.out.println("      PEDIDOS CADASTRADOS");
                System.out.println("-------------------------------");

                for (Pedido pedido : pedidos) {

                    System.out.println(
                            "Número do pedido: " + pedido.getId());

                    System.out.println(
                            "Cliente: " + pedido.getNome());

                    System.out.println(
                            "Produto: " + pedido.getProduto());

                    System.out.println(
                            "Quantidade: " + pedido.getQuantidade());

                    System.out.println(
                            "Data prevista da entrega: "
                                    + pedido.getDataDaCompra().plusDays(15));

                    System.out.println(
                            "Status: " + pedido.getStatus());

                    System.out.println("-------------------------------");
                }

            } else if (opcao == 4) {

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

                        }
                    }
                }

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
}