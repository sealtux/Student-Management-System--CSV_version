package Essentials.modifiedEvents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Essentials.GUI;
import java.awt.Frame;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class modifycollege {
    private static final String COLLEGE_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\college.csv";
    private static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";

    public modifycollege(GUI gui, String oldCode, String oldName) {
        int rowIndex = findRowIndex(gui.getcollegeModel(), oldCode);
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(null, "College not found in the table!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        openModifyDialog(gui, oldCode, oldName, rowIndex);
    }

    public modifycollege(GUI gui) {
        JTable collegeTable = gui.getcollegeTable();

        JDialog modifyDialog = new JDialog((Frame) null, "Modify College", true);
        modifyDialog.setLayout(null);
        modifyDialog.setSize(350, 220);
        modifyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel infoLabel = new JLabel("Select a row and press 'Modify':");
        JButton modifyButton = new JButton("Modify");

        infoLabel.setBounds(50, 30, 200, 25);
        modifyButton.setBounds(100, 80, 130, 25);

        modifyDialog.add(infoLabel);
        modifyDialog.add(modifyButton);
        modifyDialog.setLocationRelativeTo(null);
        modifyDialog.setResizable(false);

        modifyButton.addActionListener(e -> {
            DefaultTableModel collegeModel = gui.getcollegeModel();
            int selectedRow = collegeTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(modifyDialog, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int modelRow = collegeTable.convertRowIndexToModel(selectedRow);
            String collegeCode = collegeModel.getValueAt(modelRow, 0).toString().trim();
            String collegeName = collegeModel.getValueAt(modelRow, 1).toString().trim();

            modifyDialog.dispose();
            openModifyDialog(gui, collegeCode, collegeName, modelRow);
        });

        modifyDialog.setVisible(true);
    }

    private int findRowIndex(DefaultTableModel collegeModel, String collegeCode) {
        for (int i = 0; i < collegeModel.getRowCount(); i++) {
            if (collegeModel.getValueAt(i, 0).toString().trim().equals(collegeCode)) {
                return i;
            }
        }
        return -1;
    }

    private void openModifyDialog(GUI gui, String oldCode, String oldName, int rowIndex) {
        JDialog editDialog = new JDialog((JFrame) null, "Edit College", true);
        editDialog.setSize(350, 250);
        editDialog.setLayout(null);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel codeLabel = new JLabel("College Code:");
        JTextField codeField = new JTextField(oldCode);
        JLabel nameLabel = new JLabel("College Name:");
        JTextField nameField = new JTextField(oldName);

        codeLabel.setBounds(20, 20, 100, 25);
        codeField.setBounds(130, 20, 150, 25);
        nameLabel.setBounds(20, 60, 100, 25);
        nameField.setBounds(130, 60, 150, 25);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(130, 120, 100, 30);

        editDialog.add(codeLabel);
        editDialog.add(codeField);
        editDialog.add(nameLabel);
        editDialog.add(nameField);
        editDialog.add(updateButton);

        updateButton.addActionListener(ae -> {
            String newCode = codeField.getText().trim();
            String newName = nameField.getText().trim();

            if (!newCode.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(editDialog, "College Code must contain a value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!newName.matches(".*[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(editDialog, "College Name must contain a value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (collegeExists(gui.getcollegeModel(), newCode, newName, rowIndex)) {
                JOptionPane.showMessageDialog(editDialog, "A college with this code or name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(editDialog,
                    "Are you sure you want to update this college?", "Confirm Update",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                updateCollegeRecord(gui, rowIndex, oldCode, newCode, newName);
                editDialog.dispose();
            }
        });

        editDialog.setLocationRelativeTo(null);
        editDialog.setResizable(false);
        editDialog.setVisible(true);
    }

    private boolean collegeExists(DefaultTableModel model, String code, String name, int ignoreRow) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (i == ignoreRow) continue;
            String existingCode = model.getValueAt(i, 0).toString().trim();
            String existingName = model.getValueAt(i, 1).toString().trim();
            if (existingCode.equals(code) || existingName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void updateCollegeRecord(GUI gui, int rowIndex, String oldCode, String newCode, String newName) {
        DefaultTableModel model = gui.getcollegeModel();

        model.setValueAt(newCode, rowIndex, 0);
        model.setValueAt(newName, rowIndex, 1);

        updateCollegeCSVFile(oldCode, newCode, newName);
        updateProgramsCollegeCode(gui, oldCode, newCode);
    }

    private void updateCollegeCSVFile(String oldCode, String newCode, String newName) {
        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COLLEGE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                csvData.add(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        for (String[] row : csvData) {
            if (row[0].equals(oldCode)) {
                row[0] = newCode;
                row[1] = newName;
                break;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COLLEGE_FILE))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateProgramsCollegeCode(GUI gui, String oldCollegeCode, String newCollegeCode) {
        DefaultTableModel programModel = gui.getprogramModel();
        List<String[]> programData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PROGRAM_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (row[2].equals(oldCollegeCode)) {
                    row[2] = newCollegeCode;
                }
                programData.add(row);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PROGRAM_FILE))) {
            for (String[] row : programData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < programModel.getRowCount(); i++) {
            if (programModel.getValueAt(i, 2).toString().equals(oldCollegeCode)) {
                programModel.setValueAt(newCollegeCode, i, 2);
            }
        }
    }
}
