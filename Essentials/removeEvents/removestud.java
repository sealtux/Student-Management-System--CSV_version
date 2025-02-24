package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;

public class removestud {
    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";
    GUI maingui;
    
    public removestud(GUI gui, delete de) {
        this.maingui = gui;
        
        // Create a modal JDialog for "Delete a Student"
        JDialog deleteStudDialog = new JDialog((Frame)null, "Delete a Student", true);
        deleteStudDialog.setLayout(null);
        deleteStudDialog.setSize(300, 180);
        deleteStudDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create components
        JLabel deleteLabel = new JLabel("Enter ID:");
        JTextField deleteBar = new JTextField();
        JButton submit = new JButton("Confirm");

        // Set bounds for components
        deleteLabel.setBounds(30, 30, 180, 25);
        deleteBar.setBounds(85, 30, 70, 25);
        submit.setBounds(85, 80, 130, 25);

        // Add components to the dialog
        deleteStudDialog.add(deleteLabel);
        deleteStudDialog.add(deleteBar);
        deleteStudDialog.add(submit);

        // Center the dialog and disable resizing (removes minimize/maximize)
        deleteStudDialog.setLocationRelativeTo(null);
        deleteStudDialog.setResizable(false);

        // Add action listener for the submit button
        submit.addActionListener(e -> {
            String bar = deleteBar.getText().trim();
            DefaultTableModel model = maingui.getstudentModel();
            de.removeRowByValue(model, bar, 0, FILE_PATH);
            deleteStudDialog.dispose();
        });

        // Finally, show the dialog
        deleteStudDialog.setVisible(true);
    }
}
