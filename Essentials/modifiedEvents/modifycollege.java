package Essentials.modifiedEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import Essentials.AutoCompletion;
import Essentials.GUI;

public class modifycollege {

    // CSV file path constant for colleges (adjust as needed)
    public static final String COLLEGE_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\college.csv";
    
    private GUI mainGUI;
    private DefaultTableModel model;  // Table model for colleges

    // Constructor: receives the main GUI instance and stores its college model
    public modifycollege(GUI gui) {
        this.mainGUI = gui;
        this.model = mainGUI.getcollegeModel();  // Assumes your GUI provides this method
        
        // Create a simple modify dialog that prompts for a College Code using a JComboBox
        String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegeCombo = new JComboBox<>(collegeOptions);
        JLabel collegeChecker = new JLabel("Enter College Code:");
        JButton collegeCheckerSubmit = new JButton("MODIFY");
        
        JDialog modifyDialog = new JDialog((java.awt.Frame) null, "Modify College", true);
        modifyDialog.setLayout(null);
        modifyDialog.setSize(300, 180);
        collegeChecker.setBounds(30, 30, 180, 25);
        collegeCombo.setBounds(150, 30, 80, 25);
        collegeCheckerSubmit.setBounds(85, 80, 130, 25);
        modifyDialog.add(collegeChecker);
        modifyDialog.add(collegeCombo);
        modifyDialog.add(collegeCheckerSubmit);
        modifyDialog.setLocationRelativeTo(null);
        modifyDialog.setResizable(false);
        
        // Add action listener BEFORE showing the dialog
        collegeCheckerSubmit.addActionListener(e -> {
            String enteredCode = (String) collegeCombo.getSelectedItem();
            if (enteredCode.isEmpty()) {
                JOptionPane.showMessageDialog(modifyDialog, "Please enter a College Code.");
                return;
            }
            String[] record = searchCSVForCollegeByCode(enteredCode);
            if (record == null) {
                JOptionPane.showMessageDialog(modifyDialog, "Record with College Code " + enteredCode + " not found.");
            } else {
            
                modifiedFrame(record);
            }
            modifyDialog.dispose();
        });
        
     
        modifyDialog.setVisible(true);
    }
    

    private void modifiedFrame(String[] record) {
        // Store the original college code for lookup
        final String originalCode = record[0];
        
        // Create a modal edit dialog
        JDialog editDialog = new JDialog((java.awt.Frame) null, "Edit College", true);
        editDialog.setSize(350, 250);
        editDialog.setLayout(null);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // College Code label and combo box
        JLabel codeLabel = new JLabel("College Code:");
        String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> codeCombo = new JComboBox<>(collegeOptions);
        codeCombo.setSelectedItem(record[0]);  // pre-select the current code
        
        // College Name label and combo box
        JLabel nameLabel = new JLabel("College Name:");
        String[] collegeNameOptions = {
            "", "College of Computer Studies", "College of Economics and Business Administration",
            "College of Health Sciences", "College of Engineering",
            "College of Science and Mathematics", "College of Arts and Social Sciences",
            "College of Education"
        };
        JComboBox<String> nameCombo = new JComboBox<>(collegeNameOptions);
        nameCombo.setSelectedItem(record[1]);
        
        // Enable auto-completion if available
        AutoCompletion.enable(codeCombo);
        AutoCompletion.enable(nameCombo);
        
        // Set bounds for components
        codeLabel.setBounds(20, 20, 100, 25);
        codeCombo.setBounds(130, 20, 150, 25);
        nameLabel.setBounds(20, 60, 100, 25);
        nameCombo.setBounds(130, 60, 150, 25);
        
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(130, 120, 100, 30);
        
        // Add components to the edit dialog
        editDialog.add(codeLabel);
        editDialog.add(codeCombo);
        editDialog.add(nameLabel);
        editDialog.add(nameCombo);
        editDialog.add(updateButton);
        
        // Add action listener BEFORE making dialog visible
        updateButton.addActionListener(ae -> {
            String newCode = codeCombo.getSelectedItem().toString().trim();
            String newName = nameCombo.getSelectedItem().toString().trim();
            
         
            String[] newRecord = { newCode, newName };
            
            int modelRow = findRowByCollegeCode(originalCode);
            if (modelRow != -1) {
                model.setValueAt(newCode, modelRow, 0);
                model.setValueAt(newName, modelRow, 1);
                
                updateCollegeCSVFile(modelRow, newRecord);
            } else {
                JOptionPane.showMessageDialog(editDialog, "Record not found in the table.");
            }
            editDialog.dispose();
        });
        
        editDialog.setLocationRelativeTo(null);
        editDialog.setResizable(false);
        
        editDialog.setVisible(true);
    }
    
    private int findRowByCollegeCode(String code) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(code)) {
                return i;
            }
        }
        return -1;
    }
    
   
    private void updateCollegeCSVFile(int modelRow, String[] newRecord) {
        List<String[]> csvData = new ArrayList<>();
        
        // Step 1: Read all CSV rows into a list using COLLEGE_FILE
        try (BufferedReader br = new BufferedReader(new FileReader(COLLEGE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                csvData.add(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // Determine CSV row index: table model row 0 corresponds to CSV row 1 (header is row 0)
        int csvRowIndex = modelRow + 1;
        if (csvRowIndex >= 1 && csvRowIndex < csvData.size()) {
            csvData.set(csvRowIndex, newRecord);
        } else {
            System.out.println("Error: CSV row index " + csvRowIndex + " out of bounds. CSV rows: " + csvData.size());
            return;
        }
        
        // Write the updated list back to the CSV file using COLLEGE_FILE
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COLLEGE_FILE))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("College CSV file updated successfully.");
    }
    
    // Search the college CSV file for a record by College Code.
    private String[] searchCSVForCollegeByCode(String code) {
        try (BufferedReader br = new BufferedReader(new FileReader(COLLEGE_FILE))) {
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
