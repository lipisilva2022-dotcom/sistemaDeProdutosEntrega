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
                System.out.println("⚠️ Digite apenas um número inteiro!");
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
                System.out.println("⚠️ Digite apenas um número!");
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

            System.out.println("⚠️ Digite alguma coisa!");
        }

    }
}