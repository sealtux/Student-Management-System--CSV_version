package Essentials.addEvents;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Essentials.AutoCompletion;
import Essentials.GUI;
import Essentials.create;

public class addprogGUI {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    GUI mainGui;
public addprogGUI(GUI gui, create writer){

    this.mainGui = gui;

    JFrame addProgramFrame = new JFrame("");
        addProgramFrame.setSize(350, 200);
        addProgramFrame.setLayout(null);
        addProgramFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField();
    
        JLabel programNameLabel = new JLabel("Program Name:");
        JTextField programField = new JTextField();
    
        JLabel collegecodeLabel = new JLabel("College Code:");
        String[] collegeoptions={"","CCS","CEBA","CHS","COE","CSM","CASS","CED"};
        
        JComboBox<String> collegecodeCombo = new JComboBox<>(collegeoptions);
    
        JButton submitButton = new JButton("Submit");
    
        // Set bounds properly
        programCodeLabel.setBounds(180,10,180,25);
        programCodeField.setBounds(270,10, 40, 25);
    
        programNameLabel.setBounds(20, 40, 100, 25);
        programField.setBounds(130, 40, 180, 25);
    
        collegecodeLabel.setBounds(20, 70, 100, 25);
        collegecodeCombo.setBounds(130, 70, 180, 25);
    
        submitButton.setBounds(110, 120, 130, 25);
       
        AutoCompletion.enable(collegecodeCombo);
      
        
    
        // Add components to frame
        addProgramFrame.add(programCodeLabel);
        addProgramFrame.add(programCodeField);
        addProgramFrame.add(programNameLabel);
        addProgramFrame.add(programField);
        addProgramFrame.add(collegecodeLabel);
        addProgramFrame.add(collegecodeCombo);
        addProgramFrame.add(submitButton);
    
        addProgramFrame.setVisible(true);
    
        submitButton.addActionListener(e -> {
            try {
                String programCode = programCodeField.getText().trim();
                String programName = programField.getText().trim();
                String college = (String) collegecodeCombo.getSelectedItem();
    
                if (programCode.isEmpty() || programName.isEmpty() || college.isEmpty()) {
                    JOptionPane.showMessageDialog(addProgramFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Ensure `programmodel` and `writer` exist
                  DefaultTableModel programmodel = mainGui.getprogramModel();
                    programmodel.addRow(new Object[]{programCode, programName, college});
                
    
                    writer.program(programCode, programName, college);
                

                addProgramFrame.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addProgramFrame, "An error occurred while adding the program.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

}
}
