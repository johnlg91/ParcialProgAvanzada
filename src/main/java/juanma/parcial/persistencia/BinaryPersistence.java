package juanma.parcial.persistencia;

import juanma.parcial.exceptions.ReadFailedException;
import juanma.parcial.exceptions.WriteFailedException;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class BinaryPersistence {
    static public <T> void writeElements(File file, List<T> elements) {
        try (FileOutputStream outputStream = new FileOutputStream(file); //declaro el stream y le paso el file
             ObjectOutputStream datos = new ObjectOutputStream(outputStream); //declaro el stream de object q le pasara al stream file
        ) {
            datos.writeObject(elements); //pasa los juanma.parcial.objetos
            // el close file es redundante al declarar en los parentesis del try
        } catch (IOException e) {
            throw new WriteFailedException(file.getAbsolutePath(), e);
        }

    }

    @SuppressWarnings("unchecked")
    static public <T> List<T> readElements(File file) {
        try (
                FileInputStream in = new FileInputStream(file);
                ObjectInputStream datos = new ObjectInputStream(in);
        ) {
            return (List<T>) datos.readObject();
        }
        catch (FileNotFoundException f) {
            return Collections.emptyList();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new ReadFailedException(file.getAbsolutePath(), e);
        }
    }

}

