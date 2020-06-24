package juanma.parcial.persistencia;

import juanma.parcial.exceptions.ReadFailedException;
import juanma.parcial.exceptions.WriteFailedException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;

@SuppressWarnings("unchecked")
public class TextPersistence {

    public static final String SEPARATOR = "=========";

    static public <T> void writeElements(File file, List<T> elements) {
        try (PrintWriter writer = new PrintWriter(file)) {  //declaro el writer y le paso el file
            writer.println(elements.size());    //imprime el tamaño de la lista
            if (elements.size() == 0) return;
            writer.println(elements.get(0).getClass().getName()); //imprime el nombre de la clase
            writer.println(SEPARATOR);
            for (T e : elements) { //pide todos los fields de la clase e imprime su nombre y su valor por cada objeto de la lista
                Class<T> c = (Class<T>) e.getClass();
                for (Field field : getAllFields(c).values()) {
                    String name = field.getName();
                    Object value = field.get(e);
                    writer.println(name + ":" + value);
                }
                writer.println(SEPARATOR);
            }

        } catch (Exception e) {
            throw new WriteFailedException(file.getAbsolutePath(), e);
        }

    }

    //devuelve los fields de las superclases, ya q sino pueden llegar a quedar vacios los fields
    private static Map<String, Field> getAllFields(Class<?> clase) {
        Map<String, Field> fields = new TreeMap<>();
        for (Class<?> c = clase; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                fields.put(f.getName(), f);
            }
        }
        return fields;
    }

    static public <T> List<T> readElements(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int n = parseInt(reader.readLine());
            if (n == 0) return emptyList();

            List<T> list = new ArrayList<>(n);

            Class<T> clase = (Class<T>) Class.forName(reader.readLine());
            reader.readLine(); // Saltear SEPARATOR

            for (int i = 0; i < n; i++) {
                list.add(readElement(clase, reader));
            }
            return list;
        } catch (FileNotFoundException e) {
            return emptyList();
        } catch (Exception e) {
            throw new ReadFailedException(file.getAbsolutePath(), e);
        }
    }

    private static <T> T readElement(Class<T> clase, BufferedReader reader) throws Exception {
        T element = clase.newInstance(); //usa un constructor vacio para la instancia
        Map<String, Field> fields = getAllFields(clase);
        String line;
        while ((line = reader.readLine()) != null && !line.equals(SEPARATOR)) {
            int dosPuntos = line.indexOf(':'); //devuelve la posicion de ':'
            String fieldName = line.substring(0, dosPuntos);
            String fieldValue = line.substring(dosPuntos + 1);
            Field field = fields.get(fieldName);
            if (field == null) throw new RuntimeException("Illegal Field Name: " + fieldName + " in class " + clase.getName());
            setFieldValue(element, fieldValue, field);
        }
        return element;
    }

    @SuppressWarnings("rawtypes")
    //convierte los strings del archivo al tipo q pide la clase
    private static <T> void setFieldValue(T element, String fieldValue, Field field) throws Exception {
        Class<?> fieldType = field.getType();
        if (fieldType == Double.TYPE)
            field.setDouble(element, Double.parseDouble(fieldValue));
        else if (fieldType == Integer.TYPE)
            field.setInt(element, Integer.parseInt(fieldValue));
        else if (fieldType.isEnum()) {
            field.set(element, Enum.valueOf((Class<Enum>) fieldType, fieldValue));
        } else field.set(element, fieldValue);
    }

}

