package juanma.parcial.GUI;

import juanma.parcial.objetos.*;
import juanma.parcial.persistencia.Reportes;

import java.time.LocalDate;
import java.util.List;

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
                }case '6': {
                    reportMenu();
                    break;
                }
                default:
                    out.println("Commando Invalido.\n");
            }
        } while (cmd != 'q');
    }

    void reportMenu() {
        char cmd;
        do {
            printReportMenu();
            cmd = Scanner.getChar("Ingrese el comando o 'q' para salir\n");
            Scanner.clear();
            switch (cmd) {
                case '1': {
                    report(Reportes.operacionesPorTipo(getTypeMenu()));
                    break;
                }
                case '2': {
                    report(Reportes.destinosConMasDeTres(pedirFecha()));
                    break;
                }
                case '3': {
                    report(Reportes.operacionesPorTiendaEntreFechas(
                            pedirTienda(),
                            pedirFecha(),
                            pedirFecha()
                    ));
                    break;
                }
                default:
                    out.println("Commando Invalido.\n");
            }
        } while (cmd != 'q');
    }


    TipoOperacion getTypeMenu() {
        char cmd;
        do {
            printTypeMenu();
            cmd = Scanner.getChar("Ingrese el comando o 'q' para salir\n");
            Scanner.clear();
            switch (cmd) {
                case '1':
                    return TipoOperacion.TRANSFERENCIA_DEPOSITOS;
                case '2':
                    return TipoOperacion.TRANSFERENCIA_TIENDA;
                case '3':
                    return TipoOperacion.VENTA;
                case '4':
                    return TipoOperacion.COMPRA;
                default:
                    out.println("Commando Invalido.\n");
            }
        } while (cmd != 'q');
        return null;
    }


//pedidos, piden los datos como el ID de las ubicaciones y devuleve invalido

    private int pedirCantidad() {
        int n = Scanner.getInt("Ingrese la cantidad: ");
        if (n <= 0) {
            out.println("\tNumero Invalido.");
            return pedirCantidad();
        }
        return n;
    }

    private Producto pedirProducto() {
        String productId = Scanner.getString("Producto a transferir: ");
        Producto product = controller.findProduct(productId);
        if (product == null) {
            out.println("\tProducto Invalido.");
            return pedirProducto();
        }
        out.println("\t" + product.getNombre() + ": " + product.getDescripcion());
        return product;
    }

    private Deposito pedirDeposito(String titulo) {
        String idDeposito = Scanner.getString(titulo);
        Deposito deposito = controller.findDeposito(idDeposito);
        if (deposito == null) {
            out.println("\tDeposito Invalido.");
            return pedirDeposito(titulo);
        }
        out.println("\t" + deposito.getNombre());
        return deposito;
    }

    private Tienda pedirTienda() {
        String idTienda = Scanner.getString("Ingrese la tienda: ");
        Tienda tienda = controller.findTienda(idTienda);
        if (tienda == null) {
            out.println("\tTienda Invalida.");
            return pedirTienda();
        }
        out.println("\t" + tienda.getNombre());
        return tienda;
    }

    private LocalDate pedirFecha() {
        return LocalDate.of(Scanner.getInt("Introduzca el año"),
                Scanner.getInt("Introduzca el mes"),
                Scanner.getInt("Introduzca el día"));
    }

    private void report(List<Operacion> reporte) {
        for (Operacion o : reporte) {
            out.println(o.toString());
        }
    }

    void printMenu() {
        Scanner.clear();
        out.println();
        out.println("==================================================");
        out.println("==================   MENU   ======================");
        out.println("==================================================");
        out.println("= 1 - Realizar una transferencia entre depositos =");
        out.println("= 2 - Realizar una transferencia a tienda        =");
        out.println("= 3 - Realizar una transferencia desde tienda    =");
        out.println("= 4 - Realizar una venta                         =");
        out.println("= 5 - Realizar una compra                        =");
        out.println("= 6 - Rreportes                                  =");
        out.println("==================================================");
        out.println();
    }

    void printReportMenu() {
        Scanner.clear();
        out.println();
        out.println("========================================================================================");
        out.println("=============================   ELIJA QUE REPORTE QUIERE   =============================");
        out.println("========================================================================================");
        out.println("= 1 - Reportar las operaciones de dado tipo                                            =");
        out.println("= 2 - Reportar las operaciones repetidas 3 o más veces en el dia                       =");
        out.println("= 3 - Reportar movimientos entre las fechas dadas y devuelve los 10 primeros elementos =");
        out.println("========================================================================================");
        out.println();
    }

    void printTypeMenu() {
        out.println();
        out.println("==========================================");
        out.println("=====   ELIJA EL TIPO DE OPERACION   =====");
        out.println("==========================================");
        out.println("= 1 - Transferencia de depositos         =");
        out.println("= 2 - Transferencia de deposito a tienda =");
        out.println("= 3 - Venta                              =");
        out.println("= 4 - Compra                             =");
        out.println("==========================================");
        out.println();
    }


    public void start() {
        controller.start();
        usuario = pedirDatosUsuario();
        try {
            menu();
        } finally {
            controller.stop();
        }
    }
}