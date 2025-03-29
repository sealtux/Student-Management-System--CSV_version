package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class removecollege {
    private static final String COLLEGE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\college.csv";
    private static final String PROGRAM_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    private static final String STUDENT_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    private GUI maingui;
    private delete de;
    private DefaultTableModel collegeModel;
    private String collegeCode;


    public removecollege(GUI gui, delete de, String collegeCode) {
        this.maingui = gui;
        this.de = de;
        this.collegeCode = collegeCode;

       
        collegeModel = maingui.getcollegeModel();
        int selectedRow = findCollegeRow(collegeModel, collegeCode);
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "College not found in the table!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmation = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete college: " + collegeCode + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        if (confirmation == JOptionPane.YES_OPTION) {
            deleteCollege(selectedRow);
        }
    }


    private int findCollegeRow(DefaultTableModel model, String collegeCode) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().trim().equals(collegeCode)) {
                return i;
            }
        }
        return -1;
    }

  
    private void deleteCollege(int selectedRow) {
        
        List<String> programsToDelete = getProgramsByCollege(maingui, collegeCode);
        System.out.println("Programs to delete for college " + collegeCode + ": " + programsToDelete);
        
      
        removeStudentsByPrograms(maingui, de, programsToDelete);
        
       
        removeAllProgramsByCollege(maingui, programsToDelete, de);
     
        
        removeCollegeRecord(maingui, collegeCode, selectedRow, de);

        JOptionPane.showMessageDialog(null, "College, its programs, and associated students deleted successfully.");
    }


    private List<String> getProgramsByCollege(GUI gui, String deletedCollege) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String> programs = new ArrayList<>();
        for (int i = 0; i < programModel.getRowCount(); i++) {
            String college = programModel.getValueAt(i, 2).toString().trim();
            if (college.equals(deletedCollege)) {
                String progCode = programModel.getValueAt(i, 0).toString().trim();
                programs.add(progCode);
                System.out.println("Found program " + progCode + " for college " + deletedCollege);
            }
        }
        return programs;
    }
        
   
    private void removeStudentsByPrograms(GUI gui, delete de, List<String> programsToDelete) {
        DefaultTableModel studentModel = gui.getstudentModel();
        if (studentModel == null) {
            System.err.println("Error: Student table model is null.");
            return;
        }
       
        int programColumnIndex = 5; 
        if (programColumnIndex >= studentModel.getColumnCount()) {
            System.err.println("Error: Invalid column index for program codes in student table.");
            return;
        }
        
        for (String programCode : programsToDelete) {
            System.out.println("Attempting to remove student rows with program code: " + programCode);
            for (int i = studentModel.getRowCount() - 1; i >= 0; i--) {
                String cellValue = studentModel.getValueAt(i, programColumnIndex).toString().trim();
                if (cellValue.equalsIgnoreCase(programCode)) {
                    System.out.println("Removing student row at index " + i + " with program code: " + cellValue);
                    studentModel.removeRow(i);
                }
            }
        }
        
        List<String[]> updatedStudentData = new ArrayList<>();
        for (int i = 0; i < studentModel.getRowCount(); i++) {
            int columnCount = studentModel.getColumnCount();
            String[] rowData = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                rowData[j] = studentModel.getValueAt(i, j).toString();
            }
            updatedStudentData.add(rowData);
        }
        de.writeCSV(STUDENT_PATH, updatedStudentData);
    }

   
    private void removeAllProgramsByCollege(GUI gui, List<String> programsToDelete, delete de) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String[]> updatedProgramData = new ArrayList<>();

        for (int i = 0; i < programModel.getRowCount(); i++) {
            String progCode = programModel.getValueAt(i, 0).toString().trim();
            
            if (!programsToDelete.contains(progCode)) {
                int columnCount = programModel.getColumnCount();
                String[] rowData = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    rowData[j] = programModel.getValueAt(i, j).toString();
                }
                updatedProgramData.add(rowData);
            } else {
                System.out.println("Deleting program: " + progCode);
            }
        }
        de.writeCSV(PROGRAM_PATH, updatedProgramData);
        refreshTable(programModel, updatedProgramData);
    }


    private void removeCollegeRecord(GUI gui, String collegeCode, int selectedRow, delete de) {
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
            } else {
                System.out.println("Deleting college record at row: " + i);
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
