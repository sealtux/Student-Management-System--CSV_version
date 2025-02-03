package Essentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.*;
import java.util.Arrays;

public class GUI extends JFrame implements ActionListener {
    private JButton searchbutton, studbuttons, addStudentButton;
    private JTextField searchbar;
  
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;
    private create writer; // File handler object
    //jcombo
    private JComboBox<String> comboBox;
   

    public GUI() {
      
        // Table column names
        String[] columnNames = {"ID", "First Name", "Last Name", "Age", "Year Level", "Gender", "Program Code"};
        writer = new create(); // Initialize file handler

     

        // Table model Load from CSV if available
        model = new DefaultTableModel(columnNames, 0);
        loadStudentsFromCSV();



        // Create JTable
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        //Jcombo for sorting 
        String[] options = {"Sort by:","Sort by ID","Sort by FirstName","Sort by LastName","Sort by Age","Sort by Year Level", "Sort by Gender","Sort by Program Code"};
         comboBox = new JComboBox<>(options);
        comboBox.setBounds(620, 220, 150, 30);

        // Add table to scroll pane
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(25, 220, 585, 230);
        
        // JFrame setup
        JFrame menu = new JFrame();
        menu.setBounds(400,150,800,505  );
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search bar
        searchbar = new JTextField();
        searchbar.setBounds(474, 110, 100, 30);
        searchbutton = new JButton();
searchbutton.setBounds(575, 109, 45, 30);

      

        


        //sending the panel to the back
        JLayeredPane layeredPane = menu.getLayeredPane();
      
     
       // Students button
        studbuttons = new JButton("STUDENTS");
        studbuttons.setBounds(30, 180, 80, 24);
        studbuttons.setFont(new Font("Tahoma", Font.BOLD, 9));
        
   


        // Add Student Button
        addStudentButton = new JButton("Add Student");
        addStudentButton.setBounds(28, 80, 90, 30);
       addStudentButton.setFont(new Font("Tahoma", Font.BOLD, 9));
  

        //pixels for the table
        table.setRowHeight(25);
        
        // Add components to the frame
       // menu.add(boardLabel);
        menu.add(searchbutton);
        menu.add(searchbar);
      
        menu.add(tableScrollPane);
        menu.add(studbuttons);
       
        menu.add(addStudentButton);
     
        menu.add(comboBox);
        
       

        // Action listeners
        searchbutton.addActionListener(this);
        studbuttons.addActionListener(this);
    
     
        addStudentButton.addActionListener(this);
    
        comboBox.addActionListener(this);
        
       

        //layeredPane.add(boardLabel, JLayeredPane.DEFAULT_LAYER); // Back layer
        layeredPane.add(addStudentButton,JLayeredPane.DRAG_LAYER);
        layeredPane.add(searchbutton, JLayeredPane.DRAG_LAYER);   // Front layer
        layeredPane.add(searchbar, JLayeredPane.DRAG_LAYER);
    

        // Show window
        menu.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchbutton) {
            String name = searchbar.getText();
            System.out.println("Search: " + name);
        }
        if (e.getSource() == studbuttons) {
            System.out.println("Students button clicked.");
        }
        //jcombo box
        if (e.getSource() == comboBox) {
             String selected = (String) comboBox.getSelectedItem();
             switch (selected) {
                case "Sort by ID":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                    break;
                case "Sort by FirstName":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                    break;
                case "Sort by LastName":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(2, SortOrder.ASCENDING)));
                    break;
                case "Sort by Age":
                    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(3, SortOrder.ASCENDING)));
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


        if (e.getSource() == addStudentButton) {
            addStudent();
        }
    }

    private void addStudent() {
        // Create input fields
        JTextField idField = new JTextField(10);
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField ageField = new JTextField(10);
        JTextField yearLevelField = new JTextField(10);
        JTextField genderField = new JTextField(10);
        JTextField programCodeField = new JTextField(10);
    
        // Create a panel to hold inputs
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Year Level:"));
        panel.add(yearLevelField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Program Code:"));
        panel.add(programCodeField);
    
        // Show dialog
        int option = JOptionPane.showConfirmDialog(null, panel, "Enter Student Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String yearLevel = yearLevelField.getText();
            String gender = genderField.getText();
            String programCode = programCodeField.getText();

            // Add to table
            model.addRow(new Object[]{id, firstName, lastName, age, yearLevel, gender, programCode});

            // Save to CSV
            writer.addStudent(id, firstName, lastName, age, yearLevel, gender, programCode);
       
        }
    }

    private void loadStudentsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(create.FILE_PATH))) {
            String line;
            br.readLine(); // Skips the first
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            System.out.println("No existing data found.");
        }
    }
}