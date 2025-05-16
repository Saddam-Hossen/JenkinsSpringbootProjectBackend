package com.example.userservice.service;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CsvToJsonTreeWriter {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\01957\\Downloads/csv.csv";   // Path to your CSV file
        String outputFilePath = "output.json";        // Output JSON file

        Map<String, Object> jsonTree = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String headerLine = br.readLine(); // Read and skip header
            if (headerLine == null) return;

            String[] headers = headerLine.split(",");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                Map<String, Object> currentLevel = jsonTree;

                for (int i = 0; i < values.length; i++) {
                    String key = values[i].trim();

                    if (!currentLevel.containsKey(key)) {
                        currentLevel.put(key, new LinkedHashMap<>());
                    }

                    currentLevel = (Map<String, Object>) currentLevel.get(key);
                }
            }

            // Convert the nested map to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(jsonTree);

            // Write to file
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(jsonOutput);
                System.out.println("JSON tree written to " + outputFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
