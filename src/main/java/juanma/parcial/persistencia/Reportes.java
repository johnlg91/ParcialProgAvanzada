package juanma.parcial.persistencia;

import juanma.parcial.objetos.Operacion;
import juanma.parcial.objetos.Tienda;
import juanma.parcial.objetos.TipoOperacion;
import juanma.parcial.objetos.Ubicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Reportes {


    public static List<Operacion> operacionesPorTipo(TipoOperacion tipo) {
        return DataBase.getInstance()
                .getOperaciones()
                .stream()
                .filter(operacion -> operacion.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    public static List<Operacion> destinosConMasDeTres(LocalDate fecha) {
        Map<Ubicacion, List<Operacion>> operacionesPorUbicacion = DataBase.getInstance()
                // Tomo todas las operaciones
                .getOperaciones()
                .stream()
                // Filtro las que corrresponden a la fecha pedida
                .filter(operacion -> operacion.getFecha().equals(fecha))
                // Este groupingBy me las Agrupa por origen y me queda un mapa con cada origen
                // y la lista de operaciones para ese origen
                .collect(groupingBy(Operacion::getOrigen));

        List<Operacion> resultado = new ArrayList<>();
        operacionesPorUbicacion
                // Del mapa tomo las operaciones para cada ubicacion (Una lista)
                .values().stream()
                // Filtro las que listas que tengan 3 o mas elementos
                .filter(ops -> ops.size() >= 3)
                // Las recorro y las voy agregando a mi lista resultado
                .forEach(resultado::addAll);
        //Ordena de menor a mayor con las fechas
        resultado.sort(Comparator.comparing(Operacion::getFecha));
        return resultado;
    }

    public static List<Operacion> operacionesPorTiendaEntreFechas(Tienda tienda, LocalDate desde, LocalDate hasta) {
        return DataBase.getInstance()
                .getOperaciones()
                .stream()
                //Filtra las operaciones de las fechas pedidas
                .filter(operacion -> operacion.getFecha().isAfter(desde.minusDays(1)) && operacion.getFecha().isBefore(hasta.plusDays(1)))
                //Filtra las operaciones q vienen de una tienda
                .filter(operacion -> operacion.getOrigen().equals(tienda) || operacion.getDestino().equals(tienda))
                .limit(10)
                //Lo pasa a List
                .collect(Collectors.toList());
    }
}
