package juanma.parcial.GUI;

import juanma.parcial.Acciones;
import juanma.parcial.DataBase;
import juanma.parcial.objetos.Usuario;

public class userGUI {


    static public Usuario pedirDatosUsuario() {
        String dni = Scanner.getString("Ingrese su numero de DNI: ");
        String nombre = Scanner.getString("Ingrese su Nombre: ");
        String apellido = Scanner.getString("Ingrese su Apellido: ");
        return new Usuario(dni, nombre, apellido);
    }

    static void Menu() {
        char cmd;
        do {
            printMenu();
            cmd = Scanner.getChar("Ingrese el comando o 'q' para salir\n");
            switch (cmd) {
                case 't':
                    controller.transferir();
                    break;
                case 'v':
                    acciones.vender();
                    break;
                case 'c':
                    acciones.comprar();
                    break;
                case 'q':
                    DataBase.getInstance().saveData();
                    break;
                default:
                    System.out.println("Commando Invalido\n");
            }
        } while (cmd != 'q');
    }

    static void printMenu() {
        System.out.println("\n==================================");
        System.out.println("============   MENU   ===========");
        System.out.println("==================================");
        System.out.println("= t - Realizar una transferencia =");
        System.out.println("= v - Realizar una venta         =");
        System.out.println("= c - Realizar una compra        =");
        System.out.println("===================================\n");
    }


    public static void main(String[] args) {
        Scanner.clear();

    }
}
