package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {

    /**
     * Writes adjacencies to a GKA-file.
     *
     * @throws IOException
     */
    public static void writeToFile(List<String> data, String fileName) throws IOException {
        FileWriter fw = new FileWriter("files/" + fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        data.forEach(a -> {
            try {
                bw.write(a);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw.close();
    }
}
