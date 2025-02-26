package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.AutoCompletion;
import Essentials.GUI;
import java.awt.Frame;

public class removecollege {
    private static final String COLLEGE_PATH = "college.csv";

    public removecollege(GUI gui, delete de) {
        // Create a modal JDialog for "Delete a College"
        JDialog deleteDialog = new JDialog((Frame)null, "Delete a College", true);
        deleteDialog.setLayout(null);
        deleteDialog.setSize(300, 180);
        deleteDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel deleteLabelcol = new JLabel("College Name:");
        String[] collegedeleteoption = {" ", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};  
        JComboBox<String> collegedel = new JComboBox<>(collegedeleteoption);
        AutoCompletion.enable(collegedel);
        JButton submit = new JButton("Confirm");

        // Set bounds for components
        deleteLabelcol.setBounds(30, 30, 180, 25);
        collegedel.setBounds(120, 30, 70, 25);
        submit.setBounds(85, 80, 130, 25);

        // Add components to dialog
        deleteDialog.add(deleteLabelcol);
        deleteDialog.add(collegedel);
        deleteDialog.add(submit);

        // Center the dialog and prevent resizing (which removes minimize/maximize)
        deleteDialog.setLocationRelativeTo(null);
        deleteDialog.setResizable(false);

        // Add action listener for the submit button
        submit.addActionListener(e -> {
            String collegeval = (String) collegedel.getSelectedItem();
            DefaultTableModel collegemodel = gui.getcollegeModel();
            de.removeRowByValue(collegemodel, collegeval, 0, COLLEGE_PATH);
            deleteDialog.dispose();
        });

        // Finally, show the dialog
        deleteDialog.setVisible(true);
    }
}  