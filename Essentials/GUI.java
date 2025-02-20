package Essentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Essentials.addEvents.*;
import Essentials.loadEvents.*;
import Essentials.modifiedEvents.*;
import Essentials.removeEvents.*;
import java.util.Arrays;

public class GUI extends JFrame implements ActionListener {
    private JButton searchbutton, studbuttons, addStudentButton, probgButton, collegebutton, addprog, addcollege;
    private JButton delete, deleteprog, deletecollege;
    private JButton modifystudent,modifyprogram,modifycollege;
    private JTextField searchbar;
    private DefaultTableModel model, programmodel, collegemodel;
    private JTable table, programTable, collegetable;
    private TableRowSorter<DefaultTableModel> sorter, progsorter, collsorter;
    private create writer; // File handler object
    delete deleter;
    JScrollPane tableScrollPane, progpane, collegepane;
    JFrame menu, collegeadd;
    // jComboBox for sorting
    private JComboBox<String> comboBox;
    // sorters
    private JComboBox<String> progsortcombo, collsortcombo;
    addstudentGUI classstudent;
    addcollegeGUI classcollege;
    addprogGUI classprogram;
    removestud deletestudent;
    removecollege deletecoll;
    removeprogram deleteprogram;
     modifystudentGUI modifystud;
     modifyprogram modifyprog;
    
    // CSV file path constant – change this to your actual file location.
    

    public GUI() {
        // Initialize file handler and deleter
        writer = new create();
        deleter = new delete();

        // Table column names (assumes no header row in the CSV)
        String[] columnNames = {"ID", "First Name", "Last Name", "Year Level", "Gender", "Program Code"};
        model = new DefaultTableModel(columnNames, 0);

        // Create table and sorter
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Program table
        String[] progname = {"Program Code", "Program Name", "College Code"};
        programmodel = new DefaultTableModel(progname, 0);
        programTable = new JTable(programmodel);
        progsorter = new TableRowSorter<>(programmodel);
        programTable.setRowSorter(progsorter);
        programTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        programTable.getColumnModel().getColumn(0).setPreferredWidth((int)(585 * 0.2));
        programTable.getColumnModel().getColumn(1).setPreferredWidth((int)(585 * 0.6));
        programTable.getColumnModel().getColumn(2).setPreferredWidth((int)(585 * 0.2));

        // College table
        String[] collheader = {"Sort by:", "Sort by College Code", "Sort by College Name"};
        collsortcombo = new JComboBox<>(collheader);
        AutoCompletion.enable(collsortcombo);
        collsortcombo.setBounds(625, 220, 150, 30);
        
        String[] coll = {"College Code", "College Name"};
        collegemodel = new DefaultTableModel(coll, 0);
        collegetable = new JTable(collegemodel);
        collsorter = new TableRowSorter<>(collegemodel);
        collegetable.setRowSorter(collsorter);

        // jComboBox for sorting student table
        String[] options = {"Sort by:", "Sort by ID", "Sort by First Name", "Sort by Last Name", "Sort by Year Level", "Sort by Gender", "Sort by Program Code"};
        comboBox = new JComboBox<>(options);
        AutoCompletion.enable(comboBox);
        comboBox.setBounds(625, 220, 150, 30);

        // jComboBox for sorting program table
        String[] progcombo = {"Sort by:", "Sort by Program Code", "Sort by Program Name", "Sort by College Code"};
        progsortcombo = new JComboBox<>(progcombo);
        AutoCompletion.enable(progsortcombo);
        progsortcombo.setBounds(625, 220, 150, 30);

        // Add tables to scroll panes
        progpane = new JScrollPane(programTable);
        progpane.setBounds(25, 231, 585, 230);
        progpane.setVisible(false);
        
        collegepane = new JScrollPane(collegetable);
        collegepane.setBounds(25, 231, 585, 230);
        collegepane.setVisible(false);
        
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(25, 231, 585, 230);

        // Load data
        new loadprogram(this, writer);
        new loadstudent(this, writer);
        new loadcollege(this, writer);


        // JFrame setup
        menu = new JFrame();
        menu.setBounds(400, 150, 800, 505);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search bar and search button
        searchbar = new JTextField();
        searchbar.setBounds(474, 110, 100, 30);
        searchbutton = new JButton();
        searchbutton.setBounds(575, 109, 45, 30);
        searchbar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filtertable();
                filterprogram();
                filtercollege();
            }
        });

        // Create other buttons
        studbuttons = new JButton("STUDENTS");
        studbuttons.setBounds(30, 180, 80, 24);
        studbuttons.setFont(new Font("Tahoma", Font.BOLD, 9));

        probgButton = new JButton("PROGRAM");
        probgButton.setBounds(120, 180, 80, 24);
        probgButton.setFont(new Font("Tahoma", Font.BOLD, 9));

        collegebutton = new JButton("COLLEGE");
        collegebutton.setBounds(210, 180, 80, 24);
        collegebutton.setFont(new Font("Tahoma", Font.BOLD, 9));

        addStudentButton = new JButton("Add Student");
        addStudentButton.setBounds(28, 110, 90, 30);
        addStudentButton.setFont(new Font("Tahoma", Font.BOLD, 8));

        addprog = new JButton("Add program");
        addprog.setBounds(128, 80, 90, 30);
        addprog.setFont(new Font("Tahoma", Font.BOLD, 8));

        addcollege = new JButton("Add college");
        addcollege.setBounds(228, 80, 90, 30);
        addcollege.setFont(new Font("Tahoma", Font.BOLD, 8));

        delete = new JButton("Delete Student");
        delete.setBounds(328, 80, 90, 30);
        delete.setFont(new Font("Tahoma", Font.BOLD, 6));

        deleteprog = new JButton("Delete Program");
        deleteprog.setBounds(28, 120, 90, 30);
        deleteprog.setFont(new Font("Tahoma", Font.BOLD, 6));

        deletecollege = new JButton("Delete College");
        deletecollege.setBounds(128, 120, 90, 30);
        deletecollege.setFont(new Font("Tahoma", Font.BOLD, 6));

        modifystudent = new JButton("Modify Student");
        modifystudent.setBounds(228, 120, 90, 30);
        modifystudent.setFont(new Font("Tahoma", Font.BOLD, 6));

        modifyprogram = new JButton("Modify Program");
        modifyprogram.setBounds(328, 120, 90, 30);
        modifyprogram.setFont(new Font("Tahoma", Font.BOLD, 6));

        modifycollege = new JButton("Modify College");
        modifycollege.setBounds(428, 120, 90, 30);
        modifycollege.setFont(new Font("Tahoma", Font.BOLD, 6));

        // Set table row heights
        table.setRowHeight(25);
        programTable.setRowHeight(25);
        collegetable.setRowHeight(25);

        // Add components to menu
        menu.add(modifystudent);
        menu.add(addcollege);
        menu.add(searchbutton);
        menu.add(searchbar);
        menu.add(progsortcombo);
        menu.add(tableScrollPane);
        menu.add(studbuttons);
        menu.add(addStudentButton);
        menu.add(comboBox);
        menu.add(progpane);
        menu.add(probgButton);
        menu.add(collegepane);
        menu.add(collegebutton);
        menu.add(collsortcombo);
        menu.add(addprog);
        menu.add(delete);
        menu.add(deleteprog);
        menu.add(deletecollege);
        menu.add(modifyprogram);
        menu.add(modifycollege);

        // Action listeners
        searchbutton.addActionListener(this);
        studbuttons.addActionListener(this);
        collegebutton.addActionListener(this);
        addStudentButton.addActionListener(this);
        probgButton.addActionListener(this);
        comboBox.addActionListener(this);
        progsortcombo.addActionListener(this);
        collsortcombo.addActionListener(this);
        addprog.addActionListener(this);
        addcollege.addActionListener(this);
        delete.addActionListener(this);
        
        deleteprog.addActionListener(this);
        deletecollege.addActionListener(this);
        modifystudent.addActionListener(this);
        modifyprogram.addActionListener(this);
        modifycollege.addActionListener(this);

        // Add some components to the layered pane
        JLayeredPane layeredPane = menu.getLayeredPane();
        layeredPane.add(addStudentButton, JLayeredPane.DRAG_LAYER);
        layeredPane.add(searchbutton, JLayeredPane.DRAG_LAYER);
        layeredPane.add(searchbar, JLayeredPane.DRAG_LAYER);

        progsortcombo.setVisible(false);
        collsortcombo.setVisible(false);
        menu.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifystudent) {
            modifystud = new modifystudentGUI(this);
        }
        if(e.getSource() == modifyprogram){
            modifyprog = new modifyprogram(this);
        }
        if (e.getSource() == searchbutton) {
            filtertable();
        }
        if (e.getSource() == studbuttons) {
            tableScrollPane.setVisible(true);
            progpane.setVisible(false);
            comboBox.setVisible(true);
            collegepane.setVisible(false);
            searchbar.setText("");
            progsortcombo.setVisible(false);
            collsortcombo.setVisible(false);
        }
        if (e.getSource() == probgButton) {
            tableScrollPane.setVisible(false);
            progpane.setVisible(true);
            comboBox.setVisible(false);
            collegepane.setVisible(false);
            progsortcombo.setVisible(true);
            searchbar.setText("");
            collsortcombo.setVisible(false);
        }
        if (e.getSource() == collegebutton) {
            comboBox.setVisible(false);
            tableScrollPane.setVisible(false);
            progpane.setVisible(false);
            collegepane.setVisible(true);
            progsortcombo.setVisible(false);
            searchbar.setText("");
            collsortcombo.setVisible(true);
        }
        if (e.getSource() == progsortcombo) {
            String selectedprog = (String) progsortcombo.getSelectedItem();
            switch (selectedprog) {
                case "Sort by Program Code":
                    progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                    break;
                case "Sort by Program Name":
                    progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                    break;
                case "Sort by College Code":
                    progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
                    break;
                case "Sort by:":
                    progsorter.setSortKeys(null);
                    break;
            }
        }
        if (e.getSource() == comboBox) {
            String selected = (String) comboBox.getSelectedItem();
            switch (selected) {
                case "Sort by ID":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                    break;
                case "Sort by First Name":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                    break;
                case "Sort by Last Name":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
                    break;
                case "Sort by Year Level":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(4, SortOrder.ASCENDING)));
                    break;
                case "Sort by Gender":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(5, SortOrder.ASCENDING)));
                    break;
                case "Sort by Program Code":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(6, SortOrder.ASCENDING)));
                    break;
                case "Sort by:":
                    sorter.setSortKeys(null);
                    break;
            }
        }
        if (e.getSource() == collsortcombo) {
            String selectedcoll = (String) collsortcombo.getSelectedItem();
            switch (selectedcoll) {
                case "Sort by College Code":
                    collsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                    break;
                case "Sort by College Name":
                    collsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                    break;
                case "Sort by:":
                    collsorter.setSortKeys(null);
                    break;
            }
        }
        if (e.getSource() == addcollege) {
            classcollege = new addcollegeGUI(this, writer);
        }
        if (e.getSource() == addStudentButton) {
            classstudent = new addstudentGUI(this, writer);
        }
        if (e.getSource() == addprog) {
            classprogram = new addprogGUI(this, writer);
        }
        if (e.getSource() == delete) {
            deletestudent = new removestud(this, deleter);
        }
        if (e.getSource() == deleteprog) {
            deleteprogram = new removeprogram(this, deleter);
        }
        if (e.getSource() == deletecollege) {
            deletecoll = new removecollege(this, deleter);
        }
      
    }

    public DefaultTableModel getstudentModel() {
        return model;
    }

    public DefaultTableModel getcollegeModel() {
        return collegemodel;
    }

    public DefaultTableModel getprogramModel() {
        return programmodel;
    }

    // Filtering methods
    private void filtertable() {
        String query = searchbar.getText().trim();
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private void filterprogram() {
        String query = searchbar.getText().trim();
        if (query.isEmpty()) {
            progsorter.setRowFilter(null);
        } else {
            progsorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    private void filtercollege() {
        String query = searchbar.getText().trim();
        if (query.isEmpty()) {
            collsorter.setRowFilter(null);
        } else {
            collsorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    
}
