import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Menu {

    public static void main(String[] args) {

        // construtor/ entrada de dados
        Scanner sc = new Scanner(System.in);

        ArrayList<Pedido> pedidos = new ArrayList<>();

        while (true) {

            // Exibe o menu principal e recebe a opção do usuário
            System.out.println("====ROTACENTER====");
            System.out.println("1 - Cadatrar pedido");
            System.out.println("2 - Consultar pedido");
            System.out.println("3 - Atualizar pedido");
            System.out.println("4 -Alterar status do pedido");
            System.out.println("5 - voltar");
            System.out.println("6 - Sair");

            // Variável para a escolha
            int opcao = sc.nextInt();
            sc.nextLine();

            // Verifica se a opção escolhida é válida ou não válida
            if (opcao == 1) {

                System.out.println("===============================");
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

                System.out.println("===============================");

                Pedido pedido1 =
                        new Pedido(nome, produto, quantidade, dataDaCompra);

                pedidos.add(pedido1);

                System.out.println("=====PEDIDO COMPUTADO=====");

            } else if (opcao == 2) {

                System.out.println("OPÇÃO FUNCIONANDO");
                System.out.println("");

            } else if (opcao == 3) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 4) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 5) {

                System.out.println("OPÇÃO FUNCIONANDO");

            } else if (opcao == 6) {

                System.out.println("Saindo...");
                break;

            } else {

                // Caso a opção escolhida não seja válida
                System.out.println("OPÇÃO INVÁLIDA, ENCERRANDO...");

            }
        }

        sc.close();
    }
}