package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

public class removecollege {
    private static final String COLLEGE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\college.csv";
    private static final String PROGRAM_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    private static final String STUDENT_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    public removecollege(GUI gui, delete de) {
        JTable collegeTable = gui.getcollegeTable();

        // Create Delete Dialog
        JDialog deleteDialog = new JDialog((Frame) null, "Delete College", true);
        deleteDialog.setLayout(null);
        deleteDialog.setSize(300, 180);
        deleteDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel infoLabel = new JLabel("Select a row and press 'Delete':");
        JButton deleteButton = new JButton("Delete");

        infoLabel.setBounds(50, 30, 200, 25);
        deleteButton.setBounds(85, 80, 130, 25);

        deleteDialog.add(infoLabel);
        deleteDialog.add(deleteButton);
        deleteDialog.setLocationRelativeTo(null);
        deleteDialog.setResizable(false);

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            DefaultTableModel collegeModel = gui.getcollegeModel();
            int selectedRow = collegeTable.getSelectedRow();
          
            
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(deleteDialog, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String collegeCode = collegeModel.getValueAt(selectedRow, 0).toString().trim();

            // Confirmation Message
            int confirmation = JOptionPane.showConfirmDialog(
                    deleteDialog,
                    "Are you sure you want to delete college: " + collegeCode + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                // Step 1: Get related programs

                if (collegeCode.equals("CCS")) {
                    DefaultTableModel studentTableModel = gui.getstudentModel(); //  Correctly get the student table model
                
                    int columnIndex = 5;
                    if (columnIndex >= studentTableModel.getColumnCount()) {
                        JOptionPane.showMessageDialog(deleteDialog, "Invalid column index for program codes.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if (studentTableModel.getRowCount() > 0) {
                        de.removeRowByValue(studentTableModel, "BSCS", columnIndex, STUDENT_PATH); 
                        de.removeRowByValue(studentTableModel, "BSIS", columnIndex, STUDENT_PATH); 
                        de.removeRowByValue(studentTableModel, "BSIT", columnIndex, STUDENT_PATH); 
                        de.removeRowByValue(studentTableModel, "BCA", columnIndex, STUDENT_PATH); 
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "No students found under BSCS.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else if (collegeCode.equals("CHS")) {
                    DefaultTableModel studentTableModel = gui.getstudentModel(); 
                
                    int columnIndex = 5;
                    if (columnIndex >= studentTableModel.getColumnCount()) {
                        JOptionPane.showMessageDialog(deleteDialog, "Invalid column index for program codes.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if (studentTableModel.getRowCount() > 0) { 
                        de.removeRowByValue(studentTableModel, "BSN", columnIndex, STUDENT_PATH); 
                      
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "No students found under BSCS.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else if (collegeCode.equals("COE")) {
                    DefaultTableModel studentTableModel = gui.getstudentModel();
                
                    int columnIndex = 5;
                    if (columnIndex >= studentTableModel.getColumnCount()) {
                        JOptionPane.showMessageDialog(deleteDialog, "Invalid column index for program codes.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if (studentTableModel.getRowCount() > 0) { 
                        de.removeRowByValue(studentTableModel, "BSEE", columnIndex, STUDENT_PATH); 
                        de.removeRowByValue(studentTableModel, "BSECE", columnIndex, STUDENT_PATH); 
                      
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "No students found under BSCS.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else if (collegeCode.equals("CEBA")) {
                    DefaultTableModel studentTableModel = gui.getstudentModel(); 
                
                    int columnIndex = 5;
                    if (columnIndex >= studentTableModel.getColumnCount()) {
                        JOptionPane.showMessageDialog(deleteDialog, "Invalid column index for program codes.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                
                    if (studentTableModel.getRowCount() > 0) { 
                        de.removeRowByValue(studentTableModel, "BSECON", columnIndex, STUDENT_PATH); 
                        de.removeRowByValue(studentTableModel, "BSA", columnIndex, STUDENT_PATH); 
                      
                    } else {
                        JOptionPane.showMessageDialog(deleteDialog, "No students found under BSCS.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                
                
                List<String> programsToDelete = getProgramsByCollege(gui, collegeCode);
                
               
                removeStudentsByPrograms(gui, programsToDelete, de);
                
                removeAllProgramsByCollege(gui, programsToDelete, de);
                
               
                removeCollege(gui, collegeCode, selectedRow, de);
                
                JOptionPane.showMessageDialog(deleteDialog, "College, its programs, and associated students deleted successfully.");
            }
        });

        deleteDialog.setVisible(true);
    }

  
    private List<String> getProgramsByCollege(GUI gui, String deletedCollege) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String> programs = new ArrayList<>();

        for (int i = 0; i < programModel.getRowCount(); i++) {
            String college = programModel.getValueAt(i, 2).toString().trim();
            if (college.equals(deletedCollege)) {
                programs.add(programModel.getValueAt(i, 0).toString().trim());
            }
        }
        return programs;
    }

    
    private void removeStudentsByPrograms(GUI gui, List<String> programsToDelete, delete de) {
        DefaultTableModel studentModel = gui.getstudentModel();
        List<String[]> updatedData = new ArrayList<>();
        int programCodeIndex = 5; // Ensure this index is correct for program codes

        for (int i = 0; i < studentModel.getRowCount(); i++) {
            String programCode = studentModel.getValueAt(i, programCodeIndex).toString().trim();
            if (!programsToDelete.contains(programCode)) {
                int columnCount = studentModel.getColumnCount();
                String[] rowData = new String[columnCount];

                for (int j = 0; j < columnCount; j++) {
                    rowData[j] = studentModel.getValueAt(i, j).toString();
                }
                updatedData.add(rowData);
            }
        }

       
        de.writeCSV(STUDENT_PATH, updatedData);
        refreshTable(studentModel, updatedData);
    }
    

    private void removeAllProgramsByCollege(GUI gui, List<String> programsToDelete, delete de) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String[]> updatedProgramData = new ArrayList<>();

      
        for (int i = 0; i < programModel.getRowCount(); i++) {
            String programCode = programModel.getValueAt(i, 0).toString().trim();
            if (!programsToDelete.contains(programCode)) {
                int columnCount = programModel.getColumnCount();
                String[] rowData = new String[columnCount];

                for (int j = 0; j < columnCount; j++) {
                    rowData[j] = programModel.getValueAt(i, j).toString();
                }
                updatedProgramData.add(rowData);
            }
        }

     
        de.writeCSV(PROGRAM_PATH, updatedProgramData);
        refreshTable(programModel, updatedProgramData);
    }

   
    private void removeCollege(GUI gui, String collegeCode, int selectedRow, delete de) {
        DefaultTableModel collegeModel = gui.getcollegeModel();
        List<String[]> updatedCollegeData = new ArrayList<>();

        for (int i = 0; i < collegeModel.getRowCount(); i++) {
            if (i != selectedRow) {
                int columnCount = collegeModel.getColumnCount();
                String[] rowData = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    rowData[j] = collegeModel.getValueAt(i, j).toString();
                }
                updatedCollegeData.add(rowData);
            }
        }

        
        de.writeCSV(COLLEGE_PATH, updatedCollegeData);
        refreshTable(collegeModel, updatedCollegeData);
    }

    
    private void refreshTable(DefaultTableModel model, List<String[]> updatedData) {
        model.setRowCount(0);
        for (String[] row : updatedData) {
            model.addRow(row);
        }
        model.fireTableDataChanged();
    }
}
