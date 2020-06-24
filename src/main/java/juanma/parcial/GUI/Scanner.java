package juanma.parcial.GUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Scanner {

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);

    private Scanner() {
    }


    static public void clear() {
        System.out.print("\033[2J\033[H");
    }

    public static int getInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un numero entero.");
            return getInt(message);
        }
    }

    public static char getChar(String message) {
        return getString(message).charAt(0);
    }

    public static void enter() {
        System.out.print("Enter para continuar.");
        scanner.nextLine();
    }


    public static String getString(String message) {
        System.out.print(message);
        final String result = scanner.nextLine().trim();
        if (result.isEmpty()) {
            System.out.println("Por favor ingrese un texto.");
            return getString(message);
        }
        return result;
    }

    public static LocalDate getDate(String message) {
        System.out.print(message);
        try {
            String s = scanner.nextLine();
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Fecha Invalida.");
            return getDate(message);
        }
    }
}
