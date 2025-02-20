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
    private DefaultTableModel model;  // this comes from maingui

    // Constructor: receives the main GUI instance and stores its student model
    public modifystudentGUI(GUI gui) {
        this.maingui = gui;
        this.model = maingui.getstudentModel();
        
        // Create a simple modify dialog that prompts for an ID
        JTextField checkerField = new JTextField();
        JLabel IDchecker = new JLabel("Enter ID:");
        JButton IDcheckerSubmit = new JButton("MODIFY");
        JFrame modify = new JFrame("Modify student");
        modify.setLayout(null);
        modify.setSize(300, 180);
        IDchecker.setBounds(30, 30, 180, 25);
        checkerField.setBounds(85, 30, 80, 25);
        IDcheckerSubmit.setBounds(85, 80, 130, 25);
        modify.add(IDchecker);
        modify.add(checkerField);
        modify.add(IDcheckerSubmit);
        modify.setVisible(true);
        
        IDcheckerSubmit.addActionListener(e -> {
            String enteredId = checkerField.getText().trim();
            if (enteredId.isEmpty()) {
                JOptionPane.showMessageDialog(modify, "Please enter an ID.");
                return;
            }
            // Search for the record in the CSV file (including header, so skip header row)
            String[] record = searchCSVForRecordById(enteredId);
            if (record == null) {
                JOptionPane.showMessageDialog(modify, "Record with ID " + enteredId + " not found.");
            } else {
                // Open the edit form with the record data
                Modifiedframe(record);
            }
            modify.dispose();
        });
    }
    
    // Opens an edit form pre-populated with the record's data.
    // Allows the user to change the ID as well.
    private void Modifiedframe(String[] record) {
        // Store the original ID for lookup
        final String originalId = record[0];
        
        JFrame editFrame = new JFrame("Edit Student");
        editFrame.setSize(350, 350);
        editFrame.setLayout(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(record[0]);  // editable so user can change it
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(record[1]);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(record[2]);
        JLabel yearLevelLabel = new JLabel("Year Level:");
        JTextField yearLevelField = new JTextField(record[3]);
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField(record[4]);
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField(record[5]);
        
        // Set bounds for components
        idLabel.setBounds(20, 20, 65, 25);
        idField.setBounds(130, 20, 150, 25);
        firstNameLabel.setBounds(20, 60, 100, 25);
        firstNameField.setBounds(130, 60, 150, 25);
        lastNameLabel.setBounds(20, 100, 100, 25);
        lastNameField.setBounds(130, 100, 150, 25);
        yearLevelLabel.setBounds(20, 140, 100, 25);
        yearLevelField.setBounds(130, 140, 150, 25);
        genderLabel.setBounds(20, 180, 100, 25);
        genderField.setBounds(130, 180, 150, 25);
        programCodeLabel.setBounds(20, 220, 100, 25);
        programCodeField.setBounds(130, 220, 150, 25);
        
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(130, 260, 100, 30);
        
        // Add components to the frame
        editFrame.add(idLabel);
        editFrame.add(idField);
        editFrame.add(firstNameLabel);
        editFrame.add(firstNameField);
        editFrame.add(lastNameLabel);
        editFrame.add(lastNameField);
        editFrame.add(yearLevelLabel);
        editFrame.add(yearLevelField);
        editFrame.add(genderLabel);
        editFrame.add(genderField);
        editFrame.add(programCodeLabel);
        editFrame.add(programCodeField);
        editFrame.add(updateButton);
        
        editFrame.setVisible(true);
        
        updateButton.addActionListener(ae -> {
            // Get new values from fields (ID may be modified)
            String newId = idField.getText().trim();
            String newFirstName = firstNameField.getText().trim();
            String newLastName = lastNameField.getText().trim();
            String newYearLevel = yearLevelField.getText().trim();
            String newGender = genderField.getText().trim();
            String newProgramCode = programCodeField.getText().trim();
            
            // Prepare the updated record array
            String[] newRecord = { newId, newFirstName, newLastName, newYearLevel, newGender, newProgramCode };
            
            // Find the table model row corresponding to the original ID
            int modelRow = findRowById(originalId);
            if (modelRow != -1) {
                // Update the table model with new values (including new ID)
                model.setValueAt(newId, modelRow, 0);
                model.setValueAt(newFirstName, modelRow, 1);
                model.setValueAt(newLastName, modelRow, 2);
                model.setValueAt(newYearLevel, modelRow, 3);
                model.setValueAt(newGender, modelRow, 4);
                model.setValueAt(newProgramCode, modelRow, 5);
                
                // Also update the CSV file for this row
                updateCSVFile(modelRow, newRecord);
            } else {
                JOptionPane.showMessageDialog(editFrame, "Record not found in the table.");
            }
            editFrame.dispose();
        });
    }
    
    // Find a row in the table model by comparing the ID column.
    private int findRowById(String id) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    // Update the CSV file: skip the header (row index 0)
    private void updateCSVFile(int modelRow, String[] newRecord) {
        List<String[]> csvData = new ArrayList<>();
        
        // Step 1: Read all CSV rows into a list using FILE_PATH
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                csvData.add(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // Debug: Print CSV data before update
       
        
        // The table model does not include the header, so the corresponding CSV row is modelRow + 1.
        int csvRowIndex = modelRow + 1;
        if (csvRowIndex >= 1 && csvRowIndex < csvData.size()) {
            csvData.set(csvRowIndex, newRecord);
        } else {
        
            return;
        }
        
        // Debug: Print CSV data after update
       
   
        
        // Step 3: Write the updated list back to the CSV file using FILE_PATH
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
      
    }
    
    // Search CSV file for a record by ID (skips header automatically by checking all rows)
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
