package Essentials;

import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class delete {
    
    
    public delete() {
        // Constructor
    }

    // Method to remove a row by value in a specific column (GUI + CSV)
    public void removeRowByValue(DefaultTableModel model, String value, int columnIndex,String FILE_PATH) {
        boolean found = false;

        // **Step 1: Remove from GUI Table Model**
        for (int i = model.getRowCount() - 1; i >= 0; i--) { // Iterate in reverse to avoid index shifting
            if (model.getValueAt(i, columnIndex).toString().equals(value)) {
                model.removeRow(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Value not found in table: " + value);
            return;
        }

        // **Step 2: Remove from CSV File**
        List<String[]> updatedRows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > columnIndex && rowData[columnIndex].equals(value)) {
                    continue; // Skip the matching row (delete it)
                }
                updatedRows.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // **Step 3: Write Updated Data Back to CSV**
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : updatedRows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Row deleted successfully from file and table.");
    }
    

}
