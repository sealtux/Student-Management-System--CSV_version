package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;

public class removeprogram {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";

    public removeprogram(GUI gui, delete de) {
        // Create a modal JDialog for "Delete a Program Code"
        JDialog deleteProgDialog = new JDialog((Frame) null, "Delete a Program Code", true);
        deleteProgDialog.setLayout(null);
        deleteProgDialog.setSize(300, 180);
        deleteProgDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create components
        JLabel deleteProgLabel = new JLabel("Program Code:");
        JTextField deleteProgField = new JTextField();
        JButton submit = new JButton("Confirm");

        // Set bounds for components
        deleteProgLabel.setBounds(30, 30, 180, 25);
        deleteProgField.setBounds(120, 30, 70, 25);
        submit.setBounds(85, 80, 130, 25);

        // Add components to dialog
        deleteProgDialog.add(deleteProgLabel);
        deleteProgDialog.add(deleteProgField);
        deleteProgDialog.add(submit);

        // Center the dialog and disable resizing (removes minimize/maximize)
        deleteProgDialog.setLocationRelativeTo(null);
        deleteProgDialog.setResizable(false);

        // Add action listener for the submit button before showing the dialog
        submit.addActionListener(e -> {
            String programCode = deleteProgField.getText().trim();
            if (!programCode.isEmpty()) {
                DefaultTableModel programmodel = gui.getprogramModel();
                de.removeRowByValue(programmodel, programCode, 0, PROGRAM_FILE);
            }
            deleteProgDialog.dispose();
        });

        // Finally, show the dialog
        deleteProgDialog.setVisible(true);
    }
}
