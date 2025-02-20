package Essentials;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class update {
    
public update(int modelRow, int columnIndex, String newValue) {
    String csvFile = "students.csv";
    List<String[]> csvData = new ArrayList<>();

    // Step 1: Read the CSV into memory
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] rowData = line.split(",");
            csvData.add(rowData);
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    // Step 2: Modify the specific cell
    if (modelRow >= 0 && modelRow < csvData.size()) {
        String[] row = csvData.get(modelRow);
        if (columnIndex >= 0 && columnIndex < row.length) {
            row[columnIndex] = newValue;
        } else {
            System.out.println("Error: Invalid column index.");
            return;
        }
    } else {
        System.out.println("Error: Row index out of bounds.");
        return;
    }

    // Step 3: Write the updated data back to the CSV file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
        for (String[] row : csvData) {
            bw.write(String.join(",", row));
            bw.newLine();
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    System.out.println("CSV file updated successfully for cell update.");
}
}
