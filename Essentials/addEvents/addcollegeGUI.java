package Essentials.addEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Essentials.AutoCompletion;
import Essentials.GUI;
import Essentials.create;

public class addcollegeGUI {
    private GUI mainGUI;

    public addcollegeGUI(GUI mainGUI, create writer) {
        this.mainGUI = mainGUI;  // Store main GUI reference

        JFrame addcollegeframe = new JFrame("Add College");
        JButton submit = new JButton("Submit");

        submit.setBounds(110, 120, 130, 25);

        addcollegeframe.setSize(350, 200);
        addcollegeframe.setLayout(null);

        JLabel collegecode = new JLabel("College Code:");
        String[] collegeoptions = {"", "CCS", "CEBA", "CHS", "COE", "CSM", "CASS", "CED"};
        JComboBox<String> collegecombo = new JComboBox<>(collegeoptions);
        AutoCompletion.enable(collegecombo);
        collegecode.setBounds(20, 20, 100, 25);
        collegecombo.setBounds(110, 20, 180, 25);

        JLabel collegename = new JLabel("College Name:");
        String[] collegenameoptions = {
            "", "College of Computer Studies", "College of Economics and Business Administration",
            "College of Health Sciences", "College of Engineering",
            "College of Science and Mathematics", "College of Arts and Social Sciences",
            "College of Education"
        };
        JComboBox<String> collegenamecombo = new JComboBox<>(collegenameoptions);
        AutoCompletion.enable(collegenamecombo);
        collegename.setBounds(20, 70, 100, 25);
        collegenamecombo.setBounds(110, 70, 180, 25);

        addcollegeframe.add(collegecode);
        addcollegeframe.add(collegename);
        addcollegeframe.add(collegecombo);
        addcollegeframe.add(collegenamecombo);
        addcollegeframe.add(submit);

        addcollegeframe.setVisible(true);

        // **Mapping College Codes to College Names**
        Map<String, String> collegeMap = new HashMap<>();
        collegeMap.put("CCS", "College of Computer Studies");
        collegeMap.put("CEBA", "College of Economics and Business Administration");
        collegeMap.put("CHS", "College of Health Sciences");
        collegeMap.put("COE", "College of Engineering");
        collegeMap.put("CSM", "College of Science and Mathematics");
        collegeMap.put("CASS", "College of Arts and Social Sciences");
        collegeMap.put("CED", "College of Education");

        // **Action Listener to Auto-Update College Name**
        collegecombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCode = (String) collegecombo.getSelectedItem();
                if (collegeMap.containsKey(selectedCode)) {
                    collegenamecombo.setSelectedItem(collegeMap.get(selectedCode));
                } else {
                    collegenamecombo.setSelectedItem("");
                }
            }
        });

        submit.addActionListener(e -> {
            String collegeco = (String) collegecombo.getSelectedItem();
            String collegena = (String) collegenamecombo.getSelectedItem();

            if (collegeco.isEmpty() || collegena.isEmpty()) {
                JOptionPane.showMessageDialog(addcollegeframe, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // **Save the selected values**
            writer.college(collegeco, collegena);

            if (mainGUI != null) {
                DefaultTableModel collegemodel = mainGUI.getcollegeModel();
                collegemodel.addRow(new Object[]{collegeco, collegena});
            } else {
                System.out.println("Error: mainGUI is null!");
            }

            addcollegeframe.dispose();
        });
    }
}
