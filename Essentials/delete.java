package Essentials;

import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class delete {

    public delete() {
        
    }

  
    public void removeRowByValue(DefaultTableModel model, String value, int columnIndex, String FILE_PATH) {
        boolean found = false;
        value = value.trim();

       
        for (int i = model.getRowCount() - 1; i >= 0; i--) { 
            if (model.getValueAt(i, columnIndex).toString().trim().equals(value)) {
                model.removeRow(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Value not found in table: " + value);
            return;
        }

      
        List<String[]> updatedRows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > columnIndex && rowData[columnIndex].trim().equals(value)) {
                    System.out.println("Skipping row (deleting): " + Arrays.toString(rowData)); 
                    continue;
                }
                updatedRows.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) { 
            for (String[] row : updatedRows) {
                if (row.length == 0 || row[0].trim().isEmpty()) continue; 
                bw.write(String.join(",", row).trim());
                bw.newLine();
            }
            bw.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Row successfully deleted and CSV updated.");
    }
    public void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
