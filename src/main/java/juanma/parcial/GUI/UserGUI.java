package juanma.parcial.GUI;

import juanma.parcial.objetos.Deposito;
import juanma.parcial.objetos.Producto;
import juanma.parcial.objetos.Tienda;
import juanma.parcial.objetos.Usuario;

import static java.lang.System.out;

@SuppressWarnings("SpellCheckingInspection")
public class UserGUI {


    private final Controller controller;
    private Usuario usuario;

    public UserGUI(String dataDirectory) {
        this.controller = new Controller(dataDirectory);
    }

    public Usuario pedirDatosUsuario() {
        String dni = Scanner.getString("Ingrese su numero de DNI: ");
        String nombre = Scanner.getString("Ingrese su Nombre: ");
        String apellido = Scanner.getString("Ingrese su Apellido: ");
        return new Usuario(dni, nombre, apellido);
    }

    void menu() {
        char cmd;
        do {
            printMenu();
            cmd = Scanner.getChar("Ingrese el comando o 'q' para salir\n");
            Scanner.clear();
            switch (cmd) {
                case '1': {
                    Producto p = pedirProducto();
                    Deposito origen = pedirDeposito("Origen: ");
                    Deposito destino = pedirDeposito("Destino: ");
                    int cantidad = pedirCantidad();
                    controller.transferir(p, origen, destino, usuario, cantidad);
                    break;
                }
                case '2': {
                    Producto p = pedirProducto();
                    Deposito origen = pedirDeposito("Origen: ");
                    Tienda destino = pedirTienda();
                    int cantidad = pedirCantidad();
                    controller.transferir(p, origen, destino, usuario, cantidad);
                    break;
                }
                case '3': {
                    Producto p = pedirProducto();
                    Tienda origen = pedirTienda();
                    Deposito destino = pedirDeposito("Destino: ");
                    int cantidad = pedirCantidad();
                    controller.transferir(p, origen, destino, usuario, cantidad);
                    break;
                }
                case '4': {
                    Producto p = pedirProducto();
                    Tienda tienda = pedirTienda();
                    int cantidad = pedirCantidad();
                    controller.vender(p, tienda, usuario, cantidad);
                    break;
                }
                case '5': {
                    Producto p = pedirProducto();
                    Deposito deposito = pedirDeposito("Destino: ");
                    int cantidad = pedirCantidad();
                    controller.comprar(p, deposito, usuario, cantidad);
                    break;
                }
                default:
                    out.println("Commando Invalido.\n");
            }
        } while (cmd != 'q');
    }

    private int pedirCantidad() {
        return Scanner.getInt("Ingrese la cantidad: ");
    }

    private Producto pedirProducto() {
        String productId = Scanner.getString("Producto a transferir: ");
        Producto p = controller.findProduct(productId);
        if (p == null) {
            out.println("\tProducto Invalido.");
            return pedirProducto();
        }
        out.println("\t"+ p.getNombre() + ": " + p.getDescripcion());
        return p;
    }
    private Deposito pedirDeposito(String titulo) {
        String id = Scanner.getString(titulo);
        Deposito deposito = controller.findDeposito(id);
        if (deposito == null) {
            out.println("\tDeposito Invalido.");
            return pedirDeposito(titulo);
        }
        out.println("\t"+ deposito.getNombre());
        return deposito;
    }
    private Tienda pedirTienda() {
        String idTienda = Scanner.getString("Ingrese la tienda: ");
        Tienda tienda = controller.findTienda(idTienda);
        if (tienda == null) {
            out.println("\tTienda Invalida.");
            return pedirTienda();
        }
        out.println("\t"+ tienda.getNombre());
        return tienda;
    }
    void printMenu() {
        Scanner.clear();
        out.println();
        out.println("==================================================");
        out.println("=================   MENU   =======================");
        out.println("==================================================");
        out.println("= 1 - Realizar una transferencia entre depositos =");
        out.println("= 2 - Realizar una transferencia a tienda        =");
        out.println("= 3 - Realizar una transferencia desde tienda    =");
        out.println("= 4 - Realizar una venta                         =");
        out.println("= 5 - Realizar una compra                        =");
        out.println("==================================================");
        out.println();
    }


    public void start() {
        controller.start();
        usuario = pedirDatosUsuario();
        try {
            menu();
        }
        finally {
            controller.stop();
        }
    }
}