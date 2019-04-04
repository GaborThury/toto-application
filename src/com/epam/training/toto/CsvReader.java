package com.epam.training.toto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public List<List<String>> readFile(String filename){
        List<List<String>> records = new ArrayList<>();
        File file = new File(filename);
        if (!(file.exists())) {
            System.out.println("Invalid file path!");
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(readSingleLine(scanner.nextLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    private List<String> readSingleLine(String line) {
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(";");
        List<String> values = new ArrayList<>();

        while (rowScanner.hasNext()) {
            values.add(rowScanner.next());
        }
        return values;
    }
}
