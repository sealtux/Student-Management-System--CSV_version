package Essentials.modifiedEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import Essentials.GUI;

public class modifyprogram {
    // CSV file path constant for programs (adjust as needed)
    public static final String PROGRAM_FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    
    private GUI maingui;
    private DefaultTableModel model;  // Table model for programs

    // Constructor: receives the main GUI instance and stores its program model
    public modifyprogram(GUI gui) {
        this.maingui = gui;
        this.model = maingui.getprogramModel();  // Assumes your GUI provides this method
        
        // Create a modal dialog that prompts for a Program Code
        JDialog modifyDialog = new JDialog((java.awt.Frame) null, "Modify Program", true);
        modifyDialog.setLayout(null);
        modifyDialog.setSize(300, 180);
        modifyDialog.setLocationRelativeTo(null);
        modifyDialog.setResizable(false);
        
        JTextField checkerField = new JTextField();
        JLabel programChecker = new JLabel("Enter Program Code:");
        JButton programCheckerSubmit = new JButton("MODIFY");
        
        programChecker.setBounds(30, 30, 180, 25);
        checkerField.setBounds(150, 30, 80, 25);
        programCheckerSubmit.setBounds(85, 80, 130, 25);
        
        modifyDialog.add(programChecker);
        modifyDialog.add(checkerField);
        modifyDialog.add(programCheckerSubmit);
        
        // Add action listener before showing the dialog
        programCheckerSubmit.addActionListener(e -> {
            String enteredCode = checkerField.getText().trim();
            if (enteredCode.isEmpty()) {
                JOptionPane.showMessageDialog(modifyDialog, "Please enter a Program Code.");
                return;
            }
            // Search for the record in the CSV file for programs
            String[] record = searchCSVForProgramByCode(enteredCode);
            if (record == null) {
                JOptionPane.showMessageDialog(modifyDialog, "Record with Program Code " + enteredCode + " not found.");
            } else {
                // Open the edit form with the record data
                modifiedFrame(record);
            }
            modifyDialog.dispose();
        });
        
        modifyDialog.setVisible(true);
    }
    
 
    // Allows the user to change the Program Code.
    private void modifiedFrame(String[] record) {
        // Store the original program code for lookup
        final String originalCode = record[0];
        
        // Create a modal dialog for the edit form
        JDialog editDialog = new JDialog((java.awt.Frame) null, "Edit Program", true);
        editDialog.setSize(350, 250);
        editDialog.setLayout(null);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setLocationRelativeTo(null);
        editDialog.setResizable(false);
        
   
        JLabel codeLabel = new JLabel("Program Code:");
        JTextField codeField = new JTextField(record[0]);
        

        JLabel nameLabel = new JLabel("Program Name:");
        JTextField nameField = new JTextField(record[1]);
        

        JLabel collegeCodeLabel = new JLabel("College Code:");
        String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegeCodeCombo = new JComboBox<>(collegeOptions);
   
        collegeCodeCombo.setSelectedItem(record[2]);
        

        codeLabel.setBounds(20, 20, 100, 25);
        codeField.setBounds(130, 20, 150, 25);
        nameLabel.setBounds(20, 60, 100, 25);
        nameField.setBounds(130, 60, 150, 25);
        collegeCodeLabel.setBounds(20, 100, 100, 25);
        collegeCodeCombo.setBounds(130, 100, 150, 25);
        
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(130, 150, 100, 30);
        
     
        editDialog.add(codeLabel);
        editDialog.add(codeField);
        editDialog.add(nameLabel);
        editDialog.add(nameField);
        editDialog.add(collegeCodeLabel);
        editDialog.add(collegeCodeCombo);
        editDialog.add(updateButton);
        
      
        updateButton.addActionListener(ae -> {
          
            String newCode = codeField.getText().trim();
            String newName = nameField.getText().trim();
            String newCollegeCode = (String) collegeCodeCombo.getSelectedItem();
            
         
            if (newCode.isEmpty() || newName.isEmpty() || newCollegeCode.isEmpty()) {
                JOptionPane.showMessageDialog(editDialog, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        
            if (!newCode.equals(originalCode)) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String existingCode = model.getValueAt(i, 0).toString().trim();
                    if (existingCode.equals(newCode)) {
                        JOptionPane.showMessageDialog(editDialog, "A record with Program Code " + newCode + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            
          
            String[] newRecord = { newCode, newName, newCollegeCode };
            
           
            int modelRow = findRowByProgramCode(originalCode);
            if (modelRow != -1) {
                model.setValueAt(newCode, modelRow, 0);
                model.setValueAt(newName, modelRow, 1);
                model.setValueAt(newCollegeCode, modelRow, 2);
                
                updateProgramCSVFile(modelRow, newRecord);
            } else {
                JOptionPane.showMessageDialog(editDialog, "Record not found in the table.");
            }
            editDialog.dispose();
        });
        
        editDialog.setVisible(true);
    }
    

    private int findRowByProgramCode(String code) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(code)) {
                return i;
            }
        }
        return -1;
    }
    
   
    private void updateProgramCSVFile(int modelRow, String[] newRecord) {
        List<String[]> csvData = new ArrayList<>();
        
    
        try (BufferedReader br = new BufferedReader(new FileReader(PROGRAM_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                csvData.add(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

        int csvRowIndex = modelRow + 1;
        if (csvRowIndex >= 1 && csvRowIndex < csvData.size()) {
            csvData.set(csvRowIndex, newRecord);
        } else {
            System.out.println("Error: CSV row index " + csvRowIndex + " out of bounds. CSV rows: " + csvData.size());
            return;
        }
        
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PROGRAM_FILE_PATH))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Program CSV file updated successfully.");
    }
    

    private String[] searchCSVForProgramByCode(String code) {
        try (BufferedReader br = new BufferedReader(new FileReader(PROGRAM_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > 0 && rowData[0].trim().equals(code)) {
                    return rowData;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
