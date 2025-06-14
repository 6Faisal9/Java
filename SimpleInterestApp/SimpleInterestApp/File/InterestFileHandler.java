package File;

import Entity.Interest;
import java.io.*;
import java.util.ArrayList;

public class InterestFileHandler {
    private static final String FILE_NAME = "records.dat";

    public static void saveRecords(ArrayList<Interest> records) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(records);
        }
    }

public static ArrayList<Interest> loadRecords() {
    ArrayList<Interest> records = new ArrayList<>();
    File file = new File("records.dat");

    // Check if the file exists and is not empty
    if (!file.exists() || file.length() == 0) {
        return records; // Return an empty list if there's nothing to load
    }

    // Try to read the object from the file
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        records = (ArrayList<Interest>) ois.readObject();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return records;
}
    public static void clearSavedFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
        writer.print("");
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
