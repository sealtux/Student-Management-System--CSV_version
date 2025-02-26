package Essentials.addEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.GUI;
import Essentials.create;
import java.awt.Frame;

public class addstudentGUI {
    private GUI mainGUI;  
    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    public addstudentGUI(GUI gui, create writer) {
        this.mainGUI = gui;  // Initialize GUI reference

        // Create a modal JDialog for "Add Student" form
        JDialog addStudentDialog = new JDialog();
        addStudentDialog.setTitle("Add Student");
        addStudentDialog.setModal(true);
        addStudentDialog.setSize(350, 350);
        addStudentDialog.setLayout(null);
        addStudentDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create input fields and labels
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel yearLevelLabel = new JLabel("Year Level:");
        String[] yearModel = {"", "1st year", "2nd year", "3rd year", "4th year"};
        JComboBox<String> yearLevelCombo = new JComboBox<>(yearModel);
        JLabel genderLabel = new JLabel("Gender:");
        String[] genderOptions = {"", "M", "F"};
        JComboBox<String> genderCombo = new JComboBox<>(genderOptions);
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField();

        // Set bounds for UI elements
        idLabel.setBounds(20, 20, 65, 25);
        idField.setBounds(90, 20, 200, 25);
        firstNameLabel.setBounds(20, 60, 65, 25);
        firstNameField.setBounds(90, 60, 200, 25);
        lastNameLabel.setBounds(20, 100, 65, 25);
        lastNameField.setBounds(90, 100, 200, 25);
        yearLevelLabel.setBounds(20, 140, 65, 25);
        yearLevelCombo.setBounds(90, 140, 200, 25);
        genderLabel.setBounds(20, 180, 65, 25);
        genderCombo.setBounds(90, 180, 200, 25);
        programCodeLabel.setBounds(20, 220, 100, 25);
        programCodeField.setBounds(120, 220, 170, 25);

        // Submit button
        JButton submitButton = new JButton("Add Student");
        submitButton.setBounds(110, 260, 130, 30);

        // Add components to the dialog
        addStudentDialog.add(idLabel);
        addStudentDialog.add(idField);
        addStudentDialog.add(firstNameLabel);
        addStudentDialog.add(firstNameField);
        addStudentDialog.add(lastNameLabel);
        addStudentDialog.add(lastNameField);
        addStudentDialog.add(yearLevelLabel);
        addStudentDialog.add(yearLevelCombo);
        addStudentDialog.add(genderLabel);
        addStudentDialog.add(genderCombo);
        addStudentDialog.add(programCodeLabel);
        addStudentDialog.add(programCodeField);
        addStudentDialog.add(submitButton);

        // Center the dialog, prevent resizing, and then show it
        addStudentDialog.setLocationRelativeTo(null);
        addStudentDialog.setResizable(false);

        // Add action listener for the submit button BEFORE showing the dialog
        submitButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String yearLevel = (String) yearLevelCombo.getSelectedItem();
                String selectedGender = (String) genderCombo.getSelectedItem();
                String programCode = programCodeField.getText().trim();

                // Validate input: All fields must be filled
                if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    yearLevel.isEmpty() || selectedGender.isEmpty() || programCode.isEmpty()) {
                    JOptionPane.showMessageDialog(addStudentDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate ID format: must be "yyyy-nnnn"
                if (!id.matches("\\d{4}-\\d{4}")) {
                    JOptionPane.showMessageDialog(addStudentDialog, "ID must be in the format yyyy-nnnn.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Ensure the first four digits (year) are at least 2000
                int year = Integer.parseInt(id.substring(0, 4));
                if (year < 2000) {
                    JOptionPane.showMessageDialog(addStudentDialog, "The year in the ID must be at least 2000.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Ensure numeric part is not "0000"
                String numericPart = id.substring(5);
                if (numericPart.equals("0000")) {
                    JOptionPane.showMessageDialog(addStudentDialog, "The numeric part of the ID cannot be 0000.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Duplicate check: ensure the ID is unique in the table model
                DefaultTableModel tableModel = mainGUI.getstudentModel();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String existingId = tableModel.getValueAt(i, 0).toString().trim();
                    if (existingId.equals(id)) {
                        JOptionPane.showMessageDialog(addStudentDialog, "A record with this ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Check that the program code exists in the program table.
                DefaultTableModel programModel = mainGUI.getprogramModel();
                boolean programExists = false;
                for (int i = 0; i < programModel.getRowCount(); i++) {
                    String existingProgram = programModel.getValueAt(i, 0).toString().trim();
                    if (existingProgram.equals(programCode)) {
                        programExists = true;
                        break;
                    }
                }
                if (!programExists) {
                    JOptionPane.showMessageDialog(addStudentDialog, "Invalid Program Code! Please enter a program code that exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If all checks pass, add student to table model
                tableModel.addRow(new Object[]{id, firstName, lastName, yearLevel, selectedGender, programCode});

                // Save to CSV using writer's addStudent method
                writer.addStudent(id, firstName, lastName, yearLevel, selectedGender, programCode);

                // Close the dialog after successful entry
                addStudentDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addStudentDialog, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addStudentDialog.setVisible(true);
    }
}
