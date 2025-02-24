package Essentials.addEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.GUI;
import Essentials.create;
import java.awt.Frame;
import Essentials.AutoCompletion;
import java.util.HashMap;
import java.util.Map;

public class addcollegeGUI {
    private GUI mainGUI;

    public addcollegeGUI(GUI mainGUI, create writer) {
        this.mainGUI = mainGUI;  // Store main GUI reference

        // Create a modal JDialog for "Add College"
        JDialog addCollegeDialog = new JDialog((Frame) null, "Add College", true);
        addCollegeDialog.setSize(350, 200);
        addCollegeDialog.setLayout(null);
        addCollegeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton submit = new JButton("Submit");
        submit.setBounds(110, 120, 130, 25);

        JLabel collegecode = new JLabel("College Code:");
        String[] collegeoptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegecombo = new JComboBox<>(collegeoptions);
        AutoCompletion.enable(collegecombo);  // Ensure AutoCompletion exists and is imported
        collegecode.setBounds(20, 20, 100, 25);
        collegecombo.setBounds(110, 20, 180, 25);

        JLabel collegename = new JLabel("College Name:");
        String[] collegenameoptions = {
            "", "College of Computer Studies", "College of Economics and Business Administration",
            "College of Health Sciences", "College of Engineering",
            "College of Science and Mathematics", "College of Arts and Social Sciences",
            "College of Education"
        };
        JComboBox<String> collegenamecombo = new JComboBox<>(collegenameoptions);
        AutoCompletion.enable(collegenamecombo);
        collegename.setBounds(20, 70, 100, 25);
        collegenamecombo.setBounds(110, 70, 180, 25);

        addCollegeDialog.add(collegecode);
        addCollegeDialog.add(collegename);
        addCollegeDialog.add(collegecombo);
        addCollegeDialog.add(collegenamecombo);
        addCollegeDialog.add(submit);

        // Mapping College Codes to College Names
        Map<String, String> collegeMap = new HashMap<>();
        collegeMap.put("CCS", "College of Computer Studies");
        collegeMap.put("CEBA", "College of Economics and Business Administration");
        collegeMap.put("CHS", "College of Health Sciences");
        collegeMap.put("COE", "College of Engineering");
        collegeMap.put("CSM", "College of Science and Mathematics");
        collegeMap.put("CASS", "College of Arts and Social Sciences");
        collegeMap.put("CED", "College of Education");

        // Auto-update College Name when code is selected
        collegecombo.addActionListener(e -> {
            String selectedCode = (String) collegecombo.getSelectedItem();
            if (collegeMap.containsKey(selectedCode)) {
                collegenamecombo.setSelectedItem(collegeMap.get(selectedCode));
            } else {
                collegenamecombo.setSelectedItem("");
            }
        });

        // Add action listener for the submit button BEFORE showing the dialog
        submit.addActionListener(e -> {
            String collegeco = (String) collegecombo.getSelectedItem();
            String collegena = (String) collegenamecombo.getSelectedItem();

            if (collegeco.isEmpty() || collegena.isEmpty()) {
                JOptionPane.showMessageDialog(addCollegeDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if record already exists in the table
            DefaultTableModel model = mainGUI.getcollegeModel();
            boolean exists = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().trim().equals(collegeco)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                JOptionPane.showMessageDialog(addCollegeDialog, "Record with College Code " + collegeco + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                addCollegeDialog.dispose();
                return;
            }

            // Save to CSV using writer's method
            writer.college(collegeco, collegena);

            // Add to the table model if mainGUI is not null
            if (mainGUI != null) {
                model.addRow(new Object[]{collegeco, collegena});
            } 

            addCollegeDialog.dispose();
        });

        // Center the dialog, prevent resizing, then show the dialog
        addCollegeDialog.setLocationRelativeTo(null);
        addCollegeDialog.setResizable(false);
        addCollegeDialog.setVisible(true);
    }
}
