package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
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

        // Check for existing programs under this college
        List<String> programsUnderCollege = getProgramsByCollege(maingui, collegeCode);
        if (!programsUnderCollege.isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Cannot delete college; there are programs under this college.",
                "Deletion Error",
                JOptionPane.ERROR_MESSAGE
            );
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
            deleteCollegeRecord(selectedRow);
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

    private List<String> getProgramsByCollege(GUI gui, String collegeCode) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String> programs = new ArrayList<>();
        for (int i = 0; i < programModel.getRowCount(); i++) {
            String college = programModel.getValueAt(i, 2).toString().trim();
            if (college.equals(collegeCode)) {
                programs.add(programModel.getValueAt(i, 0).toString().trim());
            }
        }
        return programs;
    }

    private void deleteCollegeRecord(int selectedRow) {
        DefaultTableModel collegeModel = maingui.getcollegeModel();
        List<String[]> updatedCollegeData = new ArrayList<>();

        for (int i = 0; i < collegeModel.getRowCount(); i++) {
            if (i != selectedRow) {
                int cols = collegeModel.getColumnCount();
                String[] row = new String[cols];
                for (int j = 0; j < cols; j++) {
                    row[j] = collegeModel.getValueAt(i, j).toString();
                }
                updatedCollegeData.add(row);
            } else {
                System.out.println("Deleting college record at row: " + i);
            }
        }

        de.writeCSV(COLLEGE_PATH, updatedCollegeData);
        refreshTable(collegeModel, updatedCollegeData);
        JOptionPane.showMessageDialog(null, "College deleted successfully.");
    }

    private void refreshTable(DefaultTableModel model, List<String[]> updatedData) {
        model.setRowCount(0);
        for (String[] row : updatedData) {
            model.addRow(row);
        }
        model.fireTableDataChanged();
    }
}