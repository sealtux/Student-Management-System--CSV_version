package Essentials.modifiedEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import Essentials.GUI;

public class modifystudentGUI {

    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";
    
    private GUI maingui;
    private DefaultTableModel model;  // Table model for students

    // Constructor: receives the main GUI instance and stores its student model
    public modifystudentGUI(GUI gui) {
        this.maingui = gui;
        this.model = maingui.getstudentModel();
        
        // Create a modal dialog that prompts for an ID
        JDialog modifyDialog = new JDialog((java.awt.Frame) null, "Modify Student", true);
        modifyDialog.setLayout(null);
        modifyDialog.setSize(300, 180);
        modifyDialog.setLocationRelativeTo(null);
        modifyDialog.setResizable(false);
        
        JTextField checkerField = new JTextField();
        JLabel IDchecker = new JLabel("Enter ID:");
        JButton IDcheckerSubmit = new JButton("MODIFY");
        
        IDchecker.setBounds(30, 30, 180, 25);
        checkerField.setBounds(85, 30, 80, 25);
        IDcheckerSubmit.setBounds(85, 80, 130, 25);
        
        modifyDialog.add(IDchecker);
        modifyDialog.add(checkerField);
        modifyDialog.add(IDcheckerSubmit);
        
        // Add action listener BEFORE showing the dialog
        IDcheckerSubmit.addActionListener(e -> {
            String enteredId = checkerField.getText().trim();
            if (enteredId.isEmpty()) {
                JOptionPane.showMessageDialog(modifyDialog, "Please enter an ID.");
                return;
            }
            // Search for the record in the CSV file (skipping header in the table)
            String[] record = searchCSVForRecordById(enteredId);
            if (record == null) {
                JOptionPane.showMessageDialog(modifyDialog, "Record with ID " + enteredId + " not found.");
            } else {
                // Open the edit form with the record data
                modifiedFrame(record);
            }
            modifyDialog.dispose();
        });
        
        modifyDialog.setVisible(true);
    }
    
    // Opens an edit form pre-populated with the student's data.
    // Allows the user to change the ID as well.
    private void modifiedFrame(String[] record) {
    
        // Store the original ID for lookup
        final String originalId = record[0];
        
        // Create a modal edit dialog
        JDialog editDialog = new JDialog((java.awt.Frame) null, "Edit Student", true);
        editDialog.setSize(350, 350);
        editDialog.setLayout(null);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setLocationRelativeTo(null);
        editDialog.setResizable(false);
        
        // Create labels and text fields
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(record[0]);  // Editable so user can change it
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(record[1]);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(record[2]);
        JLabel yearLevelLabel = new JLabel("Year Level:");
       
        JLabel genderLabel = new JLabel("Gender:");
      
        JLabel programCodeLabel = new JLabel("Program Code:");
        DefaultTableModel programModel = maingui.getprogramModel();
        JComboBox<String> programCodeCombo = new JComboBox<>();
        String[] yearModel = {"", "1st year", "2nd year", "3rd year", "4th year"};
        JComboBox<String> yearLevelCombo = new JComboBox<>(yearModel);
        String[] genderOptions = {"", "M", "F"};
        JComboBox<String> genderCombo = new JComboBox<>(genderOptions);
        
        for (int i = 0; i < programModel.getRowCount(); i++) {
            programCodeCombo.addItem(programModel.getValueAt(i, 0).toString().trim());
        }
        yearLevelCombo.setSelectedItem(record[3].trim());
        genderCombo.setSelectedItem(record[4].trim());
        programCodeCombo.setSelectedItem(record[5].trim());
        
      

        

        idLabel.setBounds(20, 20, 65, 25);
        idField.setBounds(130, 20, 150, 25);
        firstNameLabel.setBounds(20, 60, 100, 25);
        firstNameField.setBounds(130, 60, 150, 25);
        lastNameLabel.setBounds(20, 100, 100, 25);
        lastNameField.setBounds(130, 100, 150, 25);
        yearLevelLabel.setBounds(20, 140, 100, 25);
        yearLevelCombo.setBounds(130, 140, 150, 25);
        genderLabel.setBounds(20, 180, 100, 25);
        genderCombo.setBounds(130, 180, 150, 25);
        programCodeLabel.setBounds(20, 220, 100, 25);
        programCodeCombo .setBounds(130, 220, 150, 25);
        
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(130, 260, 100, 30);
        
        
        editDialog.add(idLabel);
        editDialog.add(idField);
        editDialog.add(firstNameLabel);
        editDialog.add(firstNameField);
        editDialog.add(lastNameLabel);
        editDialog.add(lastNameField);
        editDialog.add(yearLevelLabel);
        editDialog.add(yearLevelCombo);
        editDialog.add(genderLabel);
        editDialog.add(genderCombo);
        editDialog.add(programCodeLabel);
        editDialog.add(programCodeCombo );
        editDialog.add(updateButton);
        

        updateButton.addActionListener(ae -> {
           
            String newId = idField.getText().trim();
            String newFirstName = firstNameField.getText().trim();
            String newLastName = lastNameField.getText().trim();
            String newYearLevel = (String)yearLevelCombo.getSelectedItem();
            String newGender = (String)genderCombo.getSelectedItem();
            String newProgramCode = (String) programCodeCombo.getSelectedItem();
            
           
            if (newId.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty() ||
                newYearLevel.isEmpty() || newGender.isEmpty() || newProgramCode.isEmpty()) {
                JOptionPane.showMessageDialog(editDialog, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            if (!newId.equals(originalId)) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String existingId = model.getValueAt(i, 0).toString().trim();
                    if (existingId.equals(newId)) {
                        JOptionPane.showMessageDialog(editDialog, "A record with this ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            
  
            String[] newRecord = { newId, newFirstName, newLastName, newYearLevel, newGender, newProgramCode };
            
        
            int modelRow = findRowById(originalId);
            if (modelRow != -1) {
                model.setValueAt(newId, modelRow, 0);
                model.setValueAt(newFirstName, modelRow, 1);
                model.setValueAt(newLastName, modelRow, 2);
                model.setValueAt(newYearLevel, modelRow, 3);
                model.setValueAt(newGender, modelRow, 4);
                model.setValueAt(newProgramCode, modelRow, 5);
                
                updateCSVFile(modelRow, newRecord);
            } else {
                JOptionPane.showMessageDialog(editDialog, "Record not found in the table.");
            }
            editDialog.dispose();
        });
        
    
        editDialog.setVisible(true);
    };
    
 
    private int findRowById(String id) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(id)) {
                return i;
            }
        }
        return -1;
    }
 
    private void updateCSVFile(int modelRow, String[] newRecord) {
        List<String[]> csvData = new ArrayList<>();
        
      
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
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
        
       
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("CSV file updated successfully.");
    }
    
    private String[] searchCSVForRecordById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length > 0 && rowData[0].trim().equals(id)) {
                    return rowData;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
