import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Pedido> pedidos = new ArrayList<>();

        Random random = new Random();

        while (true) {

            System.out.println("====ROTACENTER====");
            System.out.println("1 - Cadastrar pedido");
            System.out.println("2 - Consultar pedido");
            System.out.println("3 - Atualizar pedido");
            System.out.println("4 - Alterar status do pedido");
            System.out.println("5 - Voltar");
            System.out.println("0 - Sair");

            int opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {

                System.out.println("================================");
                System.out.println("CADASTRO DE PEDIDOS SELECIONADO");
                System.out.println();

                System.out.println("Digite o nome do cliente:");
                String nome = sc.nextLine();

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
                        new Pedido(numeroPedido, nome, produto,
                                quantidade, dataDaCompra);

                pedidos.add(pedido1);

                System.out.println("========PEDIDO COMPUTADO========");

            } else if (opcao == 2) {

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

                    System.out.println("-------------------------------");
                }

            } else if (opcao == 3) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 4) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 5) {

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