package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;

import Essentials.GUI;
import java.awt.Frame;


public class removeprogram {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    private static final String STUDENT_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    GUI maingui;
    

    public removeprogram(GUI gui, delete de) {
        this.maingui = gui;
        JTable programTable = maingui.getprogramTable();

      
        JDialog deleteProgDialog = new JDialog((Frame) null, "Delete Program", true);
        deleteProgDialog.setLayout(null);
        deleteProgDialog.setSize(300, 180);
        deleteProgDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel infoLabel = new JLabel("Select a row and press 'Delete':");
        JButton deleteButton = new JButton("Delete");

        infoLabel.setBounds(50, 30, 200, 25);
        deleteButton.setBounds(85, 80, 130, 25);

        deleteProgDialog.add(infoLabel);
        deleteProgDialog.add(deleteButton);
        deleteProgDialog.setLocationRelativeTo(null);
        deleteProgDialog.setResizable(false);

       
        deleteButton.addActionListener(e -> {
            int selectedRow = programTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(deleteProgDialog, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
                
               
            }

            DefaultTableModel programModel = maingui.getprogramModel();
            String programCode = programModel.getValueAt(selectedRow, 0).toString().trim(); 


          
            int confirmation = JOptionPane.showConfirmDialog(
                    deleteProgDialog,
                    "Are you sure you want to delete program code: " + programCode + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
              
                if (programCode.equals(programCode)) {
                    DefaultTableModel studentTableModel = gui.getstudentModel(); 
                
                    int columnIndex = 5;
                    if (columnIndex >= studentTableModel.getColumnCount()) {
                        JOptionPane.showMessageDialog(deleteProgDialog, "Invalid column index for program codes.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if (studentTableModel.getRowCount() > 0) { 
                        de.removeRowByValue(studentTableModel, programCode, columnIndex, STUDENT_PATH); 
                       
                    } else {
                        JOptionPane.showMessageDialog(deleteProgDialog, "No students found under BSCS.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
               
              
               
                de.removeRowByValue(programModel, programCode, 0, PROGRAM_FILE);

                
                programModel.removeRow(selectedRow);

                

                JOptionPane.showMessageDialog(deleteProgDialog, "Program deleted successfully.");
            }
        });

        deleteProgDialog.setVisible(true);
    }
}
