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
        setDependencies(gui, de);
        this.programCode = programCode;

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

    private void setDependencies(GUI gui, delete de) {
        this.maingui = gui;
        this.de = de;
    }

    private void deleteprog() {
        if (maingui == null || de == null) {
            System.err.println("Error: GUI and delete dependencies not set.");
            return;
        }

        studentTableModel = maingui.getstudentModel();
        if (studentTableModel == null) {
            System.err.println("Error: Student table model is null.");
            return;
        }

        int columnIndex = 5;
        if (columnIndex < studentTableModel.getColumnCount()) {
            de.removeRowByValue(studentTableModel, programCode, columnIndex, STUDENT_PATH);
        }

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
