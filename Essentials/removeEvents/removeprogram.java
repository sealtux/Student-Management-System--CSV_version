package Essentials.removeEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.GUI;
import java.awt.Frame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class removeprogram {
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";
    private static final String STUDENT_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    private GUI maingui;
    private delete de;
    private DefaultTableModel programModel;
    private DefaultTableModel studentTableModel;
    private String programCode;

    public removeprogram(GUI gui, delete de, String programCode) {
        this.maingui = gui;
        this.de = de;
        this.programCode = programCode;

        // Check for enrolled students under this program
        List<String> enrolled = getStudentsByProgram(maingui, programCode);
        if (!enrolled.isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Cannot delete program; there are students enrolled in this program.",
                "Deletion Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete program code: " + programCode + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            deleteprog();
        }
    }

    private List<String> getStudentsByProgram(GUI gui, String programCode) {
        DefaultTableModel studentModel = gui.getstudentModel();
        List<String> students = new ArrayList<>();
        if (studentModel == null) return students;
        int colIndex = 5;
        for (int i = 0; i < studentModel.getRowCount(); i++) {
            String code = studentModel.getValueAt(i, colIndex).toString().trim();
            if (code.equals(programCode)) {
                students.add(studentModel.getValueAt(i, 0).toString()); // or any identifier
            }
        }
        return students;
    }

    private void deleteprog() {
        if (maingui == null || de == null) {
            System.err.println("Error: GUI and delete dependencies not set.");
            return;
        }

        // Remove student records for this program
        studentTableModel = maingui.getstudentModel();
        if (studentTableModel == null) {
            System.err.println("Error: Student table model is null.");
        } else {
            de.removeRowByValue(studentTableModel, programCode, 5, STUDENT_PATH);
        }

        // Remove program from CSV and table
        programModel = maingui.getprogramModel();
        List<String[]> updatedData = readCSV(PROGRAM_FILE);
        updatedData.removeIf(row -> row.length > 0 && row[0].trim().equals(programCode));

        de.writeCSV(PROGRAM_FILE, updatedData);
        refreshTable(programModel, updatedData);

        JOptionPane.showMessageDialog(null, "Program deleted successfully.");
    }

    private void refreshTable(DefaultTableModel model, List<String[]> updatedData) {
        model.setRowCount(0);
        for (String[] row : updatedData) {
            model.addRow(row);
        }
        model.fireTableDataChanged();
    }

    private List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
