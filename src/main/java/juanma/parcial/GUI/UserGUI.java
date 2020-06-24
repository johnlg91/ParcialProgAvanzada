package juanma.parcial.GUI;

import juanma.parcial.objetos.*;
import juanma.parcial.persistencia.Reportes;

import java.util.List;

import static java.lang.System.out;

@SuppressWarnings("SpellCheckingInspection")
public class UserGUI {


    private final Controller controller;
    private Usuario usuario;

    public UserGUI(String dataDirectory) {
        this.controller = new Controller(dataDirectory);
    }


    //Pide el dni al usuario y revisa si ya esta registrado en la data base
    public Usuario pedirDatosUsuario() {
        String dni = Scanner.getString("Ingrese su numero de DNI: ");
        Usuario usuario = controller.findUsuario(dni);
        if (usuario != null) {
            out.println("\t" + usuario.getNombre() + ": " + usuario.getApellido());
            if (Scanner.getChar("s/n: ") != 'n') return usuario; //Pide la confirmacion del Usuario
        }
        String nombre = Scanner.getString("Ingrese su Nombre: ");
        String apellido = Scanner.getString("Ingrese su Apellido: ");
        usuario = new Usuario(dni, nombre, apellido);
        controller.addUsuario(usuario);
        return usuario;
    }

    //Basicamente un switch para recibir los comandos del usuario
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
                case '6': {
                    reportMenu();
                    break;
                }
                case 'q' : break;
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
                    report(Reportes.destinosConMasDeTres(Scanner.getDate("")));
                    break;
                }
                case '3': {
                    report(Reportes.operacionesPorTiendaEntreFechas(
                            pedirTienda(),
                            Scanner.getDate("Fecha desde: "),
                            Scanner.getDate("Fecha hasta: ")
                    ));
                    break;
                }
                case 'q' : break;
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
                case 'q' : break;
                default:
                    out.println("Commando Invalido.\n");
            }
        } while (cmd != 'q');
        return null;
    }


    //Pedidos, piden los datos al usuario como el ID de las ubicaciones o productos y cantidades
    //si el ID q dado es invalido se le imprimiran la lista de lo q hay de ese objeto

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
            out.println("\tProducto Invalido. Ingrese alguno de los indicados:");
            for (Producto p : controller.getProductos())
                out.println("\t\t" + p);
            out.println();
            return pedirProducto();
        }
        out.println("\t" + product.getNombre() + ": " + product.getDescripcion());
        return product;
    }

    private Deposito pedirDeposito(String titulo) {
        String idDeposito = Scanner.getString(titulo);
        Deposito deposito = controller.findDeposito(idDeposito);
        if (deposito == null) {
            out.println("\tDeposito Invalido. Ingrese alguno de los indicados:");
            for (Deposito d : controller.getDepositos())
                out.println("\t\t" + d);
            out.println();
            return pedirDeposito(titulo);
        }
        out.println("\t" + deposito.getNombre());
        return deposito;
    }

    private Tienda pedirTienda() {
        String idTienda = Scanner.getString("Ingrese la tienda: ");
        Tienda tienda = controller.findTienda(idTienda);
        if (tienda == null) {
            out.println("\tTienda Invalida. Ingrese alguna de las indicadas: ");
            for (Tienda t : controller.getTiendas())
                out.println("\t\t" + t);
            out.println();
            return pedirTienda();
        }
        out.println("\t" + tienda.getNombre());
        return tienda;
    }


    //Recibe la lista ya filtrada y la imprime en pantalla
    private void report(List<Operacion> reporte) {
        out.printf("%-10s %-20s %-20s %-20s %10s\n", "Fecha", "Producto", "Origen", "Destino", "Cantidad");
        out.println("====================================================================================");
        for (Operacion o : reporte) {
            out.printf("%10s %-20s %-20s %-20s %10d\n", o.getFecha(), o.getProducto(), o.getOrigen(), o.getDestino(), o.getCantidad());
        }
        out.println();
        Scanner.enter();
    }


    //Menus lindos para saber q hacer

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
        out.println("= 6 - Reportes                                   =");
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


    //Para q se llame desde el main y empiece el programa
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