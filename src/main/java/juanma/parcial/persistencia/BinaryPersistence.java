package juanma.parcial.persistencia;

import java.io.*;
import java.util.List;

public class BinaryPersistence {
    static public <T> void writeElements(List<T> elements, String fileName) {
        try (
                FileOutputStream outputStream = new FileOutputStream(fileName); //declaro el stream y le paso el file
                ObjectOutputStream datos = new ObjectOutputStream(outputStream); //declaro el stream de object q le pasara al stream file
        ) {
            datos.writeObject(elements); //pasa los juanma.parcial.objetos
            // el close file es redundante al declarar en los parentesis del try

        } catch (IOException e) {
        }

    }

    static public <T> List<T> readElements(String fileName) {
        try (
                FileInputStream in = new FileInputStream(fileName);
                ObjectInputStream datos = new ObjectInputStream(in);
        ) {
            return (List<T>)datos.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new ReadFailedException(fileName, e);
        }
    }

}

