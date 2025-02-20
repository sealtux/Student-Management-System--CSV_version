package Essentials.addEvents;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import Essentials.GUI;
import Essentials.create;

public class addstudentGUI {
    private GUI mainGUI;  
 
    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    public addstudentGUI(GUI gui, create writer) {
        this.mainGUI = gui;  // Initialize GUI reference

       
   
      
        JComboBox<String> gendercombo;

        // Create new JFrame for "Add Student" form
        JFrame addStudentFrame = new JFrame("Add Student");
        addStudentFrame.setSize(350, 250);
        addStudentFrame.setLayout(null);  
        addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create input fields
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
       
      
        JLabel yearLevelLabel = new JLabel("Year Level:");
        JTextField yearLevelField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField();

        // ComboBox for gender
        String[] genderOptions = {"", "M", "F"};
        gendercombo = new JComboBox<>(genderOptions);

        // Positioning UI elements
        idLabel.setBounds(20, 20, 65, 25);
        idField.setBounds(40, 20, 65, 25);

        firstNameLabel.setBounds(20, 60, 120, 25);
        firstNameField.setBounds(90, 60, 80, 25);

        lastNameLabel.setBounds(180, 60, 80, 25);
        lastNameField.setBounds(249, 60, 80, 25);

      
    

        yearLevelLabel.setBounds(190, 100, 80, 25);
        yearLevelField.setBounds(260, 100, 60, 25);

        genderLabel.setBounds(80, 100, 80, 25);
        gendercombo.setBounds(130, 100, 50, 25);

        programCodeLabel.setBounds(110, 20, 150, 25);
        programCodeField.setBounds(200, 20, 40, 25);

        // Submit button
        JButton submitButton = new JButton("Add Student");
        submitButton.setBounds(110, 160, 130, 25);

        // Add components to JFrame
        addStudentFrame.add(idLabel);
        addStudentFrame.add(idField);
        addStudentFrame.add(firstNameLabel);
        addStudentFrame.add(firstNameField);
        addStudentFrame.add(lastNameLabel);
        addStudentFrame.add(lastNameField);
      
    
        addStudentFrame.add(yearLevelLabel);
        addStudentFrame.add(yearLevelField);
        addStudentFrame.add(genderLabel);
        addStudentFrame.add(gendercombo);
        addStudentFrame.add(programCodeLabel);
        addStudentFrame.add(programCodeField);
        addStudentFrame.add(submitButton);

        addStudentFrame.setVisible(true);

        // Action listener for submit button
        submitButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                
                String yearLevel = yearLevelField.getText().trim();
                
                String selectedGender = (String) gendercombo.getSelectedItem();
                String programCode = programCodeField.getText().trim();

                // Validate input
                if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || yearLevel.isEmpty() || selectedGender.isEmpty() || programCode.isEmpty()) {
                    JOptionPane.showMessageDialog(addStudentFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add student to table
                DefaultTableModel model = mainGUI.getstudentModel();
                
                model.addRow(new Object[]{id, firstName, lastName,  yearLevel, selectedGender, programCode});

                // Save to CSV (if implemented)
                writer.addStudent(id, firstName, lastName, yearLevel, selectedGender, programCode);

                // Close the add student window after successful entry
                addStudentFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addStudentFrame, "Please enter a valid number for age.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
