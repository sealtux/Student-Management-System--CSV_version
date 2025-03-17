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
        JTable studentTable = maingui.getStudentTable(); 

     
        JDialog deleteStudDialog = new JDialog((Frame) null, "Delete Student", true);
        deleteStudDialog.setLayout(null);
        deleteStudDialog.setSize(300, 180);
        deleteStudDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JLabel infoLabel = new JLabel("Select a row and press 'Delete':");
        JButton deleteButton = new JButton("Delete");

        infoLabel.setBounds(50, 30, 200, 25);
        deleteButton.setBounds(85, 80, 130, 25);

        deleteStudDialog.add(infoLabel);
        deleteStudDialog.add(deleteButton);
        deleteStudDialog.setLocationRelativeTo(null);
        deleteStudDialog.setResizable(false);

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(deleteStudDialog, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = maingui.getstudentModel();
            String selectedId = model.getValueAt(selectedRow, 0).toString().trim(); 

            // Confirmation Message
            int confirmation = JOptionPane.showConfirmDialog(
                    deleteStudDialog,
                    "Are you sure you want to delete student ID: " + selectedId + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                de.removeRowByValue(model, selectedId, 0, FILE_PATH);
                JOptionPane.showMessageDialog(deleteStudDialog, "Student deleted successfully.");
            }
        });

        deleteStudDialog.setVisible(true);
    }
}
