import java.util.InputMismatchException;
import java.util.Scanner;

public class Entradas {

    public static int lerInteiro(Scanner sc) {

        while (true) {

            try {

                int numero = sc.nextInt();
                sc.nextLine();

                return numero;

            } catch (InputMismatchException e) {

                System.out.println(
                        "⚠️ Digite apenas um número inteiro!");

                sc.nextLine();
            }
        }
    }


    public static double lerDouble(Scanner sc) {

        while (true) {

            try {

                double numero = sc.nextDouble();
                sc.nextLine();

                return numero;

            } catch (InputMismatchException e) {

                System.out.println(
                        "⚠️ Digite apenas um número!");

                sc.nextLine();
            }
        }
    }


    public static String lerTexto(Scanner sc) {

        while (true) {

            String texto = sc.nextLine();

            if (!texto.trim().isEmpty()) {

                return texto;
            }

            System.out.println(
                    "⚠️ Digite alguma coisa!");
        }
    }


    public static String lerCpfCnpj(Scanner sc) {

        while (true) {

            String cpfCnpj = sc.nextLine();

            cpfCnpj = cpfCnpj.replaceAll("\\D", "");

            // CPF

            if (cpfCnpj.length() == 11) {

                return cpfCnpj.substring(0, 3)
                        + "."
                        + cpfCnpj.substring(3, 6)
                        + "."
                        + cpfCnpj.substring(6, 9)
                        + "-"
                        + cpfCnpj.substring(9, 11);

                // CNPJ

            } else if (cpfCnpj.length() == 14) {

                return cpfCnpj.substring(0, 2)
                        + "."
                        + cpfCnpj.substring(2, 5)
                        + "."
                        + cpfCnpj.substring(5, 8)
                        + "/"
                        + cpfCnpj.substring(8, 12)
                        + "-"
                        + cpfCnpj.substring(12, 14);

            } else {

                System.out.println(
                        "⚠️ Digite um CPF com 11 dígitos "
                                + "ou CNPJ com 14 dígitos!");
            }
        }
    }


    public static String lerCep(Scanner sc) {

        while (true) {

            String cep = sc.nextLine();

            cep = cep.replaceAll("\\D", "");

            if (cep.length() == 8) {

                return cep.substring(0, 5)
                        + "-"
                        + cep.substring(5, 8);

            } else {

                System.out.println(
                        "⚠️ Digite um CEP com 8 dígitos!");
            }
        }
    }
}