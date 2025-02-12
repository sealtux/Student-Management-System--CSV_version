package Essentials;
import javax.swing.*;
import javax.swing.event.RowSorterEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.*;
import java.util.Arrays;
import java.util.Set;


public class GUI extends JFrame implements ActionListener {
    private JButton searchbutton, studbuttons, addStudentButton,probgButton,collegebutton,addprog;
    private JTextField searchbar;
    private DefaultTableModel model,programmodel,collegemodel;
    private JTable table,programTable,collegetable;
    private TableRowSorter<DefaultTableModel> sorter,progsorter,collsorter;
    private create writer; // File handler object
    JScrollPane tableScrollPane,progpane,collegepane;
    JFrame menu,collegeadd;
    //jcombo menu
    private JComboBox<String> comboBox,gendercombo,collegecombo,collegenamecombo;
    //sorters
    private JComboBox<String> progsortcombo,collsortcombo;
    

    public GUI() {
      
        // Table column names
       
        writer = new create(); // Initialize file handler




        // Table model Load from CSV if available
        String[] columnNames = {"ID", "First Name", "Last Name", "Age", "Year Level", "Gender", "Program Code"};
        model = new DefaultTableModel(columnNames, 0);
           // Create a sorter

           table = new JTable(model);
           sorter = new TableRowSorter<>(model);
           table.setRowSorter(sorter);

    
    

        String[] progname = {"Program Code","Program Name","College Code"};
        programmodel = new DefaultTableModel(progname,0);

        programTable = new JTable(programmodel);
        progsorter = new TableRowSorter<>(programmodel);
        programTable.setRowSorter(progsorter);
        String[] prog ={"Sort by:","Sort by Program Code", "Sort by Program Name", "Sort by College Code"};
        progsortcombo = new JComboBox<>(prog);
    

        String[] collheader ={"Sort by:","Sort by College Code","Sort by College Name"};
        collsortcombo = new JComboBox<>(collheader);
        collsortcombo.setBackground(Color.lightGray);
        AutoCompletion.enable(collsortcombo);
    

        collsortcombo.setBounds(625, 220, 150, 30);
        
        String[] coll = {"College Code","College Name"};
       collegemodel = new DefaultTableModel(coll,0); 
       collegetable = new JTable(collegemodel);
       collsorter = new TableRowSorter<>(collegemodel);
       collegetable.setRowSorter(collsorter);
    
       
    
       loadcollege();


        //Jcombo for sorting 
        String[] options = {"Sort by:","Sort by ID","Sort by First Name","Sort by Last Name","Sort by Age","Sort by Year Level", "Sort by Gender","Sort by Program Code"};
         comboBox = new JComboBox<>(options);
         loadStudentsFromCSV();
         comboBox.setBackground(Color.lightGray);
         AutoCompletion.enable( comboBox);
        comboBox.setBounds(625, 220, 150, 30);

        //Jcombo for prog
        String[] progcombo = {"Sort by:","Sort by Program Code","Sort by Program Name","Sort by College Code"};
        progsortcombo = new JComboBox<>(progcombo);
        progsortcombo.setBackground(Color.lightGray);
        loadprogram();
        AutoCompletion.enable(progsortcombo);
        progsortcombo.setBounds(625, 220, 150, 30);

        // Add table to scroll pane
         progpane = new JScrollPane(programTable);
        progpane.setBounds(25,231,585,230);
        progpane.setVisible(false);
        

        collegepane = new JScrollPane(collegetable);
        collegepane.setBounds(25,231,585,230);
        collegepane.setVisible(false);


        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(25, 231, 585, 230);
        
        // JFrame setup
        menu = new JFrame();
        menu.setBounds(400,150,800,505  );
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search bar
        searchbar = new JTextField();
        searchbar.setBounds(474, 110, 100, 30);
        searchbutton = new JButton();
        searchbutton.setBounds(575, 109, 45, 30);
        searchbutton.setBackground(Color.lightGray);

      

searchbar.addKeyListener(new KeyAdapter() {
    public void keyReleased(KeyEvent e) {
        filtertable();
        filterprogram();
    }
});


        //sending the panel to the back
        JLayeredPane layeredPane = menu.getLayeredPane();
      
     
       // Students button
        studbuttons = new JButton("STUDENTS");
        studbuttons.setBounds(30, 180, 80, 24);
        studbuttons.setFont(new Font("Tahoma", Font.BOLD, 9));
        studbuttons.setBackground(Color.lightGray);


        probgButton = new JButton("PROGRAM");
        probgButton .setBounds(120, 180, 80, 24);
        probgButton .setFont(new Font("Tahoma", Font.BOLD, 9));
        probgButton.setBackground(Color.lightGray);
 
        collegebutton = new JButton("COLLEGE");
        collegebutton.setBounds(210,180,80,24);
        collegebutton .setFont(new Font("Tahoma", Font.BOLD, 9));
        collegebutton.setBackground(Color.lightGray);

        // Add Student Button
        addStudentButton = new JButton("Add Student");
        addStudentButton.setBounds(28, 80, 90, 30);
       addStudentButton.setFont(new Font("Tahoma", Font.BOLD, 8));
       addStudentButton.setBackground(Color.lightGray);
      
       //Add prog button
       addprog = new JButton("Add program");
       addprog.setBounds(128, 80, 90, 30);
       addprog.setFont(new Font("Tahoma", Font.BOLD, 8));
       addprog.setBackground(Color.lightGray);
       
        //pixels for the table
        table.setRowHeight(25);
        programTable.setRowHeight(25);
        collegetable.setRowHeight(25);
        
        // Add components to the frame
       // menu.add(boardLabel);
        menu.add(searchbutton);
        menu.add(searchbar);
        menu.add( progsortcombo);
        menu.add(tableScrollPane);
        menu.add(studbuttons);
       
        menu.add(addStudentButton);
     
        menu.add(comboBox);
        menu.add(progpane);
        menu.add(probgButton );
        menu.add(collegepane);
        menu.add(collegebutton);
        menu.add(collsortcombo);
;       menu.add(addprog);

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
       

        //layeredPane.add(boardLabel, JLayeredPane.DEFAULT_LAYER); // Back layer
        layeredPane.add(addStudentButton,JLayeredPane.DRAG_LAYER);
        layeredPane.add(searchbutton, JLayeredPane.DRAG_LAYER);   // Front layer
        layeredPane.add(searchbar, JLayeredPane.DRAG_LAYER);
    

        //starting buttons 
        progsortcombo.setVisible(false);
        collsortcombo.setVisible(false);
        // Show window
        menu.setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {

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
        if(e.getSource()==probgButton){
            tableScrollPane.setVisible(false);
            progpane.setVisible(true);
            comboBox.setVisible(false);
            collegepane.setVisible(false);
            progsortcombo.setVisible(true);
            searchbar.setText("");
            collsortcombo.setVisible(false);
        }
        if(e.getSource() ==collegebutton ){
            comboBox.setVisible(false);
            tableScrollPane.setVisible(false);
            progpane.setVisible(false);
            collegepane.setVisible(true);
            progsortcombo.setVisible(false);
            searchbar.setText("");
            collsortcombo.setVisible(true);
        }
        //jcombo box
      




        if(e.getSource() == progsortcombo){
            String selectedprog = (String) progsortcombo.getSelectedItem();
            switch(selectedprog){
               
                case"Sort by Program Code":
                progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0,SortOrder.ASCENDING)));
                break;
                case "Sort by Program Name":
                progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1,SortOrder.ASCENDING)));
                break;
                case"Sort by College Code":
                progsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(2,SortOrder.ASCENDING)));
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
        if(e.getSource() == collsortcombo){
            String selectedcoll = (String) collsortcombo.getSelectedItem();
            switch(selectedcoll){
               case"Sort by College Code":
               collsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0,SortOrder.ASCENDING)));
               break;
               case"Sort by College Name":
               collsorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1,SortOrder.ASCENDING)));
               break;
               case "Sort by:":
                collsorter.setSortKeys(null); 
                break;
            } 
        }


        if (e.getSource() == addStudentButton) {
            addStudent();
            
        }
        if(e.getSource() == addprog){
            addprogram();
            
        }
    }

    private void addprogram(){
        JFrame addcollegeframe = new JFrame("Add Student");
         addcollegeframe.setSize(350,359);
         addcollegeframe.setLayout(null);
         addcollegeframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         addcollegeframe.setVisible(true);
         JLabel programCodeLabel = new JLabel("Program Code:");
         JTextField programCodeField = new JTextField();
         
   
         programCodeLabel.setBounds(20,10,180,25);
         programCodeField.setBounds(110,10, 40, 25);

         addcollegeframe.add(programCodeLabel);
         addcollegeframe.add(programCodeField);

         addcollegeframe.setVisible(true);

    }

    private void addStudent() {
        // Create a new JFrame for the Add Student form
        JFrame addStudentFrame = new JFrame("Add Student");
        addStudentFrame.setSize(350, 359);
        addStudentFrame.setLayout(null);  // Set null layout for manual positioning
        addStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        // Create input fields
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel yearLevelLabel = new JLabel("Year Level:");
        JTextField yearLevelField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JLabel programCodeLabel = new JLabel("Program Code:");
        JTextField programCodeField = new JTextField();
        JLabel programname = new JLabel("Program Name:");
        JTextField progfield = new JTextField();
        
         //Jcombo for the college
        JLabel collegecode = new JLabel("College Code:");
        String[] collegeoptions={"","CCS","CEBA","CHS","COE","CSM","CASS","CED"};
        collegecombo = new JComboBox<>(collegeoptions);
        collegecombo.setBackground(Color.lightGray);
        AutoCompletion.enable(collegecombo);

        //JCombo for the college name
        JLabel collegename = new JLabel("College Name:");
        String[] collegenameoptions = {"","College of Computer Studies","College of Engineering","College of Health Sciences","College of Education",
        "College of Arts and Social Sciences","College of Science and Mathematics ","College of Economics and Business Administration"};
        collegenamecombo = new JComboBox<>( collegenameoptions);
        AutoCompletion.enable(collegenamecombo);
       
        
    
        // Submit button
        JButton submitButton = new JButton("Add Student");
    
        // Set bounds for labels and fields
        idLabel.setBounds(20, 20, 65, 25);
        idField.setBounds(  40, 20, 65, 25);
        firstNameLabel.setBounds(20, 60, 120,25);
        firstNameField.setBounds(90, 60, 80, 25);
        lastNameLabel.setBounds(180,  60, 80, 25);
        lastNameField.setBounds(249 , 60, 80, 25);
        

        ageLabel.setBounds(20,  100, 80, 25);
        ageField.setBounds(50 , 100, 20, 25);
        
        yearLevelLabel.setBounds(190, 100, 80, 25);
        yearLevelField.setBounds(260, 100, 60, 25);

        String[] genderoptions ={"","M","F"};
        gendercombo = new JComboBox<>(genderoptions);
        AutoCompletion.enable(gendercombo);
       
        gendercombo.setBounds(130,100,40,25);
        genderLabel.setBounds(80, 100, 80, 25);
        gendercombo.setBackground(Color.lightGray);

        programCodeLabel.setBounds(110  , 20, 150, 25);
        programCodeField.setBounds(200 , 20, 40, 25);
        
        programname.setBounds(20,140,180,25 );
        progfield.setBounds(115,140,150,25);
      
        collegecode.setBounds(20,180,150,25);
        collegecombo.setBounds(110,180,60,25);
     
        collegename.setBounds(20,220,150,25);
        collegenamecombo.setBounds(110,220,220,25);


        submitButton.setBounds(180,270 , 130, 25);
        submitButton.setBackground(Color.lightGray);
        collegenamecombo.setBackground(Color.lightGray);
       
        // Add components to the JFrame
        
        addStudentFrame.add(idLabel);
        addStudentFrame.add(idField);
        addStudentFrame.add(firstNameLabel);
        addStudentFrame.add(firstNameField);
        addStudentFrame.add(lastNameLabel);
        addStudentFrame.add(lastNameField);
        addStudentFrame.add(ageLabel);
        addStudentFrame.add(ageField);
        addStudentFrame.add(yearLevelLabel);
        addStudentFrame.add(yearLevelField);
        addStudentFrame.add(genderLabel);
        addStudentFrame.add(gendercombo);
        addStudentFrame.add(programCodeLabel);
        addStudentFrame.add(programCodeField);
        addStudentFrame.add(submitButton);
        addStudentFrame.add(programname);
        addStudentFrame.add(progfield);
        addStudentFrame.add(collegecode);
        addStudentFrame.add(collegecombo);
        addStudentFrame.add(collegename);
        addStudentFrame.add(collegenamecombo);
    
        // Show the Add Student form
        addStudentFrame.setVisible(true);
    
        // Action listener for submit button
        submitButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String yearLevel = yearLevelField.getText().trim();
                String selectedgender = (String) gendercombo.getSelectedItem();
              //
                String programCode = programCodeField.getText().trim();
                String progname = progfield.getText().trim();
                String college = (String)collegecombo.getSelectedItem();
                String collegenameobj = (String) collegenamecombo.getSelectedItem();
    //still not tried to all of the jcombox/jtextfield

                // Validate input
                if (progname.isEmpty()||id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || yearLevel.isEmpty() || selectedgender.isEmpty() || programCode.isEmpty()) {
                    JOptionPane.showMessageDialog(addStudentFrame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Add student to table
                model.addRow(new Object[]{id, firstName, lastName, age, yearLevel, selectedgender, programCode});
    
                // Save to CSV (if implemented)
                writer.addStudent(id, firstName, lastName, age, yearLevel, selectedgender, programCode);
                
                programmodel.addRow(new Object[]{programCode,progname,college});
                
                writer.program(programCode,progname,college);

                collegemodel.addRow(new Object[]{college,collegenameobj});

                writer.college( college, collegenameobj);

               // writer.college(id);
    
                // Close the add student window after successful entry
                addStudentFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addStudentFrame, "Please enter a valid number for age.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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

    private void loadprogram(){
try(BufferedReader br = new BufferedReader(new FileReader(create.PROGRAM_FILE))){
String line;
br.readLine();
while((line = br.readLine()) !=null){
    String[] data = line.split(",");
    programmodel.addRow(data);
    
}
}catch(IOException e){
    System.out.println("No existing file found");
}

    }

    private void loadcollege(){
    try(BufferedReader br = new BufferedReader(new FileReader(create.COLLEGE_FILE))){
        String line;
        br.readLine();
        while((line = br.readLine()) !=null){
            String[] data = line.split(",");
            collegemodel.addRow(data);
         
        }

    }catch(IOException e){
        System.out.println("No existing file found");
    }

    }



// read 
    private void filtertable() {
        String query = searchbar.getText().trim();

        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
        
    }
    private void filterprogram(){
        String query = searchbar.getText().trim();

        if(query.isEmpty()){
            progsorter.setRowFilter(null);
         } else{
                progsorter.setRowFilter(RowFilter.regexFilter("(?i)"+query));
            }
        }
    }
