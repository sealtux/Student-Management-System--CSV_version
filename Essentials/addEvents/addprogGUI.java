package Essentials.addEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.GUI;
import Essentials.create;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class addprogGUI {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    GUI mainGui;
    
    public addprogGUI(GUI gui, create writer) {
        this.mainGui = gui;
        
        // Create modal JDialog
        JDialog addProgramDialog = new JDialog();
        addProgramDialog.setTitle("Add Program");
        addProgramDialog.setModal(true);
        addProgramDialog.setSize(500, 280);
        addProgramDialog.setLayout(null);
        addProgramDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Labels
        JLabel collegecodeLabel = new JLabel("College Code:");
        JLabel programCodeLabel = new JLabel("Program Code:");
        JLabel programNameLabel = new JLabel("Program Name:");

        // College options
        String[] collegeOptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegecodeCombo = new JComboBox<>(collegeOptions);

        // Program Code ComboBox (Initially Empty)
        JComboBox<String> programCodeCombo = new JComboBox<>();
        JTextField programNameField = new JTextField();
        
        JButton submitButton = new JButton("Submit");

        // Mapping College -> Program Codes
        Map<String, String[]> programMap = new HashMap<>();
        programMap.put("CCS", new String[]{"", "BSCS", "BSIT", "BCA","BSIS"});
        programMap.put("CHS", new String[]{"", "BSN",});
        programMap.put("COE", new String[]{"", "BSEE", "BSCE", "BSME"});
        programMap.put("CSM", new String[]{"", "BSBIO", "BSMATH", "BSSTAT"});
        programMap.put("CASS", new String[]{
            "", "BA-ELS", "BALCS", "BA-FIL", "BA-HIST-Philippine", "BA-HIST-Intl", "BA-Panitikan",
            "BA-POLS", "BA-PSY", "BA-SOC", "BS-Philosophy", "BS-Psychology"
        });
        
        programMap.put("CED", new String[]{"", "BSED", "BEED", "SPED"});
       
        programMap.put("CEBA", new String[]{"", "BSA", "BSBA-MM", "BSBA-BE", "BSECON", "BSENTREP", "BSHM"});

        programMap.put("COE", new String[]{
            "", "BS-Chemical", "BS-EnvEng", "BS-CivilEng", "BS-CompEng", "BS-ElectricalEng", "BS-Electronics", 
            "BS-IndustrialAutomation", "BS-CeramicsEng", "BS-MetallurgicalEng", "BS-MiningEng", "BS-MechanicalEng"
        });
        

        // Mapping Program Code -> Full Program Name (Excluding MBA and DBA)
        Map<String, String> programFullNames = new HashMap<>();
        // CCS Programs
        programFullNames.put("BSCS", "Bachelor of Science in Computer Science");
        programFullNames.put("BSIT", "Bachelor of Science in Information Technology");
        programFullNames.put("BCA", "Bachelor of Computer Application");
        programFullNames.put("BSIS", "BS in Information Systems");

        // CEBA Programs (Excluded MBA and DBA)
        programFullNames.put("BSA", "Bachelor of Science in Accountancy");
        programFullNames.put("BSBA-MM", "Bachelor of Science in Business Administration, Major in Marketing Management");
        programFullNames.put("BSBA-BE", "Bachelor of Science in Business Administration, Major in Business Economics");
        programFullNames.put("BSECON", "Bachelor of Science in Economics");
        programFullNames.put("BSENTREP", "Bachelor of Science in Entrepreneurship");
        programFullNames.put("BSHM", "Bachelor of Science in Hospitality Management");
        //chs
        programFullNames.put("BSN", "Bachelor of Science in Bachelor of Science in Nursing");
        //cass
        programFullNames.put("BA-ELS", "Bachelor of Arts in English Language Studies");
        programFullNames.put("BALCS", "Bachelor of Arts in Literary and Cultural Studies");
        programFullNames.put("BA-FIL", "Bachelor of Arts in Filipino");
        programFullNames.put("BA-HIST-Philippine", "Bachelor of Arts in History (Philippine-Asian History Track)");
        programFullNames.put("BA-HIST-Intl", "Bachelor of Arts in History (International History Track)");
        programFullNames.put("BA-Panitikan", "Bachelor of Arts in Panitikan");
        programFullNames.put("BA-POLS", "Bachelor of Arts in Political Science");
        programFullNames.put("BA-PSY", "Bachelor of Arts in Psychology");
        programFullNames.put("BA-SOC", "Bachelor of Arts in Sociology");
        programFullNames.put("BS-Philosophy", "Bachelor of Science in Philosophy (Applied Ethics)");
        programFullNames.put("BS-Psychology", "Bachelor of Science in Psychology");

        programFullNames.put("BS-Chemical", "Bachelor of Science in Chemical Engineering");
        programFullNames.put("BS-EnvEng", "Bachelor of Science in Environmental Engineering");
programFullNames.put("BS-CivilEng", "Bachelor of Science in Civil Engineering");
programFullNames.put("BS-CompEng", "Bachelor of Science in Computer Engineering");
programFullNames.put("BS-ElectricalEng", "Bachelor of Science in Electrical Engineering");
programFullNames.put("BS-Electronics", "Bachelor of Science in Electronics and Communication Engineering");
programFullNames.put("BS-IndustrialAutomation", "Bachelor of Science in Industrial Automation and Mechatronics");
programFullNames.put("BS-CeramicsEng", "Bachelor of Science in Ceramics Engineering");
programFullNames.put("BS-MetallurgicalEng", "Bachelor of Science in Metallurgical Engineering");
programFullNames.put("BS-MiningEng", "Bachelor of Science in Mining Engineering");
programFullNames.put("BS-MechanicalEng", "Bachelor of Science in Mechanical Engineering");



        // Set bounds
        collegecodeLabel.setBounds(20, 20, 100, 25);
        collegecodeCombo.setBounds(160, 20, 280, 25);
        programCodeLabel.setBounds(20, 60, 100, 25);
        programCodeCombo.setBounds(160, 60, 280, 25);
        programNameLabel.setBounds(20, 100, 100, 25);
        programNameField.setBounds(160, 100, 280, 25);
        submitButton.setBounds(160, 150, 150, 30);
        
        // Add components
        addProgramDialog.add(collegecodeLabel);
        addProgramDialog.add(collegecodeCombo);
        addProgramDialog.add(programCodeLabel);
        addProgramDialog.add(programCodeCombo);
        addProgramDialog.add(programNameLabel);
        addProgramDialog.add(programNameField);
        addProgramDialog.add(submitButton);
        
        // Center dialog and disable resizing
        addProgramDialog.setLocationRelativeTo(null);
        addProgramDialog.setResizable(false);
        
        // Update Program Code ComboBox based on selected College
        collegecodeCombo.addActionListener(e -> {
            String selectedCollege = (String) collegecodeCombo.getSelectedItem();
            programCodeCombo.removeAllItems();
            if (programMap.containsKey(selectedCollege)) {
                for (String program : programMap.get(selectedCollege)) {
                    programCodeCombo.addItem(program);
                }
            }
            programNameField.setText(""); // Reset Program Name Field
        });

        // Auto-fill Program Name when a Program Code is selected
        programCodeCombo.addActionListener(e -> {
            String selectedProgram = (String) programCodeCombo.getSelectedItem();
            if (programFullNames.containsKey(selectedProgram)) {
                programNameField.setText(programFullNames.get(selectedProgram));
            } else {
                programNameField.setText(""); // Allow manual input if not found
            }
        });

        // Submit button logic
        submitButton.addActionListener(e -> {
            try {
                String programCode = (String) programCodeCombo.getSelectedItem();
                String programName = programNameField.getText().trim();
                String college = (String) collegecodeCombo.getSelectedItem();
                
                if (programCode == null || programCode.isEmpty() || programName.isEmpty() || college.isEmpty()) {
                    JOptionPane.showMessageDialog(addProgramDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check for duplicates in table
                DefaultTableModel programModel = mainGui.getprogramModel();
                boolean duplicateFound = false;
                for (int i = 0; i < programModel.getRowCount(); i++) {
                    if (programModel.getValueAt(i, 0).toString().trim().equals(programCode)) {
                        duplicateFound = true;
                        break;
                    }
                }
                
                if (duplicateFound) {
                    JOptionPane.showMessageDialog(addProgramDialog, "A record with this Program Code already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Add to table and save
                programModel.addRow(new Object[]{programCode, programName, college});
                writer.program(programCode, programName, college);
                
                // Close the dialog
                addProgramDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addProgramDialog, "An error occurred while adding the program.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show dialog
        addProgramDialog.setVisible(true);
    }
}
