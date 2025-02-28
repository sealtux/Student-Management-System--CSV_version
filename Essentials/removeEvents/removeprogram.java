package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;

public class removeprogram {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";

    public removeprogram(GUI gui, delete de) {
  
        JDialog deleteProgDialog = new JDialog((Frame) null, "Delete a Program Code", true);
        deleteProgDialog.setLayout(null);
        deleteProgDialog.setSize(300, 180);
        deleteProgDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel deleteProgLabel = new JLabel("Program Code:");
        JTextField deleteProgField = new JTextField();
        JButton submit = new JButton("Confirm");

   
        deleteProgLabel.setBounds(30, 30, 180, 25);
        deleteProgField.setBounds(120, 30, 70, 25);
        submit.setBounds(85, 80, 130, 25);

  
        deleteProgDialog.add(deleteProgLabel);
        deleteProgDialog.add(deleteProgField);
        deleteProgDialog.add(submit);

        deleteProgDialog.setLocationRelativeTo(null);
        deleteProgDialog.setResizable(false);

   
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
