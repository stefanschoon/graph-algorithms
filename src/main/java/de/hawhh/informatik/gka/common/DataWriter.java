package de.hawhh.informatik.gka.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {

    private static final String OUTPUT_DIR = "files/";

    /**
     * Writes adjacencies to a GKA-file.
     *
     * @throws IOException
     */
    public static void writeToFile(List<String> data, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(OUTPUT_DIR + fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String s : data) {
            try {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bufferedWriter.close();
    }
}
