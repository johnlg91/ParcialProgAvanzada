package juanma.parcial.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;

@SuppressWarnings("unchecked")
public class TextPersistence {

    public static final String SEPARATOR = "=========";

    static public <T> void writeElements(List<T> elements, String fileName) {
        try (
                PrintWriter writer = new PrintWriter(fileName) //declaro el writer y le paso el file
        ) {
            writer.println(elements.size());
            if (elements.size() == 0) return;
            writer.println(elements.get(0).getClass().getName());
            writer.println(SEPARATOR);
            for (T e : elements) {
                Class<T> c = (Class<T>) e.getClass();
                for (Field field : c.getDeclaredFields()) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(e);
                    writer.println(name + ":" + value);
                }
                writer.println(SEPARATOR);
            }

        } catch (Exception e) {
            throw new WriteFailedException(fileName, e);

        }

    }

    static public <T> List<T> readElements(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int n = parseInt(reader.readLine());
            if (n == 0) return emptyList();

            List<T> list = new ArrayList<>(n);

            Class<T> clase = (Class<T>) Class.forName(reader.readLine());
            reader.readLine(); // Saltear SEPARATOR

            for (int i = 0; i < n; i++) {
                list.add(readElement(clase, reader));
            }
            return list;

        } catch (Exception e) {
            throw new ReadFailedException(fileName, e);
        }
    }

    private static <T> T readElement(Class<T> clase, BufferedReader reader) throws Exception {
        T element = clase.newInstance();
        String line;
        while ((line = reader.readLine()) != null && !line.equals(SEPARATOR)) {
            int dosPuntos = line.indexOf(':'); //devuelve la posicion de ':'
            String fieldName = line.substring(0, dosPuntos);
            String fieldValue = line.substring(dosPuntos + 1);
            Field field = clase.getDeclaredField(fieldName);
            field.setAccessible(true);
            setFieldValue(element, fieldValue, field);
        }
        return element;
    }

    private static <T> void setFieldValue(T element, String fieldValue, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == Double.TYPE)
            field.setDouble(element, Double.parseDouble(fieldValue));
        else if (type == Integer.TYPE)
            field.setDouble(element, Integer.parseInt(fieldValue));
        else field.set(element, fieldValue);
    }

}

