package Essentials.addEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.GUI;
import Essentials.create;
import java.awt.Frame;
import Essentials.AutoCompletion;

public class addprogGUI {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    GUI mainGui;
    
    public addprogGUI(GUI gui, create writer) {
        this.mainGui = gui;
        
        // Create a modal JDialog for "Add Program" form using the no-argument constructor
        JDialog addProgramDialog = new JDialog();
        addProgramDialog.setTitle("Add Program");
        addProgramDialog.setModal(true);
        addProgramDialog.setSize(350, 220);
        addProgramDialog.setLayout(null);
        addProgramDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Create components
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField();
        JLabel programNameLabel = new JLabel("Program Name:");
        JTextField programField = new JTextField();
        JLabel collegecodeLabel = new JLabel("College Code:");
        String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegecodeCombo = new JComboBox<>(collegeOptions);
        // Optionally, if AutoCompletion is available:
        // AutoCompletion.enable(collegecodeCombo);
        JButton submitButton = new JButton("Submit");
        
        // Set bounds for components
        programCodeLabel.setBounds(20, 20, 100, 25);
        programCodeField.setBounds(130, 20, 180, 25);
        programNameLabel.setBounds(20, 60, 100, 25);
        programField.setBounds(130, 60, 180, 25);
        collegecodeLabel.setBounds(20, 100, 100, 25);
        collegecodeCombo.setBounds(130, 100, 180, 25);
        submitButton.setBounds(110, 150, 130, 30);
        
        // Add components to dialog
        addProgramDialog.add(programCodeLabel);
        addProgramDialog.add(programCodeField);
        addProgramDialog.add(programNameLabel);
        addProgramDialog.add(programField);
        addProgramDialog.add(collegecodeLabel);
        addProgramDialog.add(collegecodeCombo);
        addProgramDialog.add(submitButton);
        
        // Center dialog and prevent resizing (which removes minimize/maximize)
        addProgramDialog.setLocationRelativeTo(null);
        addProgramDialog.setResizable(false);
        
        // Add action listener for the submit button BEFORE showing the dialog
        submitButton.addActionListener(e -> {
            try {
                // Convert program code to uppercase
                String programCode = programCodeField.getText().trim().toUpperCase();
                String programName = programField.getText().trim();
                String college = (String) collegecodeCombo.getSelectedItem();
                
                if (programCode.isEmpty() || programName.isEmpty() || college.isEmpty()) {
                    JOptionPane.showMessageDialog(addProgramDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check for duplicates in the table model before adding
                DefaultTableModel programmodel = mainGui.getprogramModel();
                boolean duplicateFound = false;
                for (int i = 0; i < programmodel.getRowCount(); i++) {
                    String existingCode = programmodel.getValueAt(i, 0).toString().trim();
                    if (existingCode.equals(programCode)) {
                        duplicateFound = true;
                        break;
                    }
                }
                
                if (duplicateFound) {
                    JOptionPane.showMessageDialog(addProgramDialog, "A record with this Program Code already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // If no duplicate, add program to table model
                programmodel.addRow(new Object[]{programCode, programName, college});
                
                // Save to CSV using writer's method (assuming writer.program is implemented)
                writer.program(programCode, programName, college);
                
                // Close the dialog after successful entry
                addProgramDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addProgramDialog, "An error occurred while adding the program.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Finally, show the dialog
        addProgramDialog.setVisible(true);
    }
}
