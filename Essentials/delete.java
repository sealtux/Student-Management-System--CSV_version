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
                    found = true;
                    continue; 
                }

                updatedRows.add(rowData); 
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) { 
            for (String[] row : updatedRows) {
                String cleanRow = String.join(",", row).trim();
                if (!cleanRow.isEmpty()) { 
                    bw.write(cleanRow);
                    bw.newLine();
                }
            }
            bw.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

      
        if (!updatedRows.isEmpty()) {
            System.out.println("New first row: " + Arrays.toString(updatedRows.get(0)));
        }

   
        model.fireTableDataChanged();

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
