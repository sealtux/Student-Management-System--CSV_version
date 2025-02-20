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
        
        // Create a simple modify dialog that prompts for a Program Code
        JTextField checkerField = new JTextField();
        JLabel programChecker = new JLabel("Enter Program Code:");
        JButton programCheckerSubmit = new JButton("MODIFY");
        JFrame modify = new JFrame("Modify Program");
        modify.setLayout(null);
        modify.setSize(300, 180);
        programChecker.setBounds(30, 30, 180, 25);
        checkerField.setBounds(150, 30, 80, 25);
        programCheckerSubmit.setBounds(85, 80, 130, 25);
        modify.add(programChecker);
        modify.add(checkerField);
        modify.add(programCheckerSubmit);
        modify.setVisible(true);
       
        programCheckerSubmit.addActionListener(e -> {
            String enteredCode = checkerField.getText().trim();
            if (enteredCode.isEmpty()) {
                JOptionPane.showMessageDialog(modify, "Please enter a Program Code.");
                return;
            }
            // Search for the record in the CSV file for programs
            String[] record = searchCSVForProgramByCode(enteredCode);
            if (record == null) {
                JOptionPane.showMessageDialog(modify, "Record with Program Code " + enteredCode + " not found.");
            } else {
                // Open the edit form with the record data
                modifiedFrame(record);
            }
            modify.dispose();
        });
    }
    
    // Opens an edit form pre-populated with the program's data.
    // Allows the user to change the Program Code.
    private void modifiedFrame(String[] record) {
    // Store the original program code for lookup
    final String originalCode = record[0];
    
    JFrame editFrame = new JFrame("Edit Program");
    editFrame.setSize(350, 250);
    editFrame.setLayout(null);
    editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // Program Code (editable text field)
    JLabel codeLabel = new JLabel("Program Code:");
    JTextField codeField = new JTextField(record[0]);
    
    // Program Name (editable text field)
    JLabel nameLabel = new JLabel("Program Name:");
    JTextField nameField = new JTextField(record[1]);
    
    // College Code (now a JComboBox)
    JLabel collegeCodeLabel = new JLabel("College Code:");
    String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
    JComboBox<String> collegeCodeCombo = new JComboBox<>(collegeOptions);
    // Set the selected item to the existing college code from the record
    collegeCodeCombo.setSelectedItem(record[2]);
    
    // Set bounds for components
    codeLabel.setBounds(20, 20, 100, 25);
    codeField.setBounds(130, 20, 150, 25);
    nameLabel.setBounds(20, 60, 100, 25);
    nameField.setBounds(130, 60, 150, 25);
    collegeCodeLabel.setBounds(20, 100, 100, 25);
    collegeCodeCombo.setBounds(130, 100, 150, 25);
    
    JButton updateButton = new JButton("Update");
    updateButton.setBounds(130, 150, 100, 30);
    
    // Add components to the frame
    editFrame.add(codeLabel);
    editFrame.add(codeField);
    editFrame.add(nameLabel);
    editFrame.add(nameField);
    editFrame.add(collegeCodeLabel);
    editFrame.add(collegeCodeCombo);
    editFrame.add(updateButton);
    
    editFrame.setVisible(true);
    
    updateButton.addActionListener(ae -> {
        // Get new values from fields
        String newCode = codeField.getText().trim();
        String newName = nameField.getText().trim();
        String newCollegeCode = (String) collegeCodeCombo.getSelectedItem();
        
        // Prepare the updated record array
        String[] newRecord = { newCode, newName, newCollegeCode };
        
        // Use the originalCode to find the table model row
        int modelRow = findRowByProgramCode(originalCode);
        if (modelRow != -1) {
            model.setValueAt(newCode, modelRow, 0);
            model.setValueAt(newName, modelRow, 1);
            model.setValueAt(newCollegeCode, modelRow, 2);
            
            updateProgramCSVFile(modelRow, newRecord);
        } else {
            JOptionPane.showMessageDialog(editFrame, "Record not found in the table.");
        }
        editFrame.dispose();
    });
}

    
    // Find a row in the program table model by comparing the Program Code (column 0)
    private int findRowByProgramCode(String code) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(code)) {
                return i;
            }
        }
        return -1;
    }
    
    // Update the program CSV file; assume the first row is a header.
    // When updating, the CSV row corresponding to the table model row is modelRow + 1.
    private void updateProgramCSVFile(int modelRow, String[] newRecord) {
        List<String[]> csvData = new ArrayList<>();
        
        // Step 1: Read all CSV rows into a list using PROGRAM_FILE_PATH
        try (BufferedReader br = new BufferedReader(new FileReader(PROGRAM_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                csvData.add(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // The table model does not include the header, so the corresponding CSV row is modelRow + 1.
        int csvRowIndex = modelRow + 1;
        if (csvRowIndex >= 1 && csvRowIndex < csvData.size()) {
            csvData.set(csvRowIndex, newRecord);
        } else {
            System.out.println("Error: CSV row index " + csvRowIndex + " out of bounds. CSV rows: " + csvData.size());
            return;
        }
        
        // Write the updated list back to the CSV file using PROGRAM_FILE_PATH
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
    
    // Search the program CSV file for a record by Program Code.
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
