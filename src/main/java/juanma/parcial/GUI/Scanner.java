package juanma.parcial.GUI;

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

    public static String getString(String message) {
        System.out.print(message);
        final String result = scanner.nextLine().trim();
        if (result.isEmpty()) {
            System.out.println("Por favor ingrese un texto.");
            return getString(message);
        }
        return result;
    }
}
