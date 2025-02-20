package Essentials.removeEvents;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import Essentials.delete;
import Essentials.AutoCompletion;
import Essentials.GUI;

public class removecollege {
    private static final String COLLEGE_PATH = "college.csv";
    public removecollege(GUI gui, delete de){

JFrame deletecol = new JFrame("Delete a College");
    deletecol.setLayout(null);
    deletecol.setSize(300,180);
    
    JLabel deleteLabelcol = new JLabel("College Name:");
    String[] collegedeleteoption ={" ","CCS","CEBA","CHS","COE","CSM","CASS","CED"};  
    JComboBox<String> collegedel = new JComboBox<>(collegedeleteoption);
  
    AutoCompletion.enable(collegedel);
    JButton submit = new JButton("Confirm");
    deletecol.setVisible(true);

    deleteLabelcol.setBounds(30,30,180,25);
    collegedel.setBounds(120,30,70,25);
    submit.setBounds(85,80 , 130, 25);

    deletecol.add(deleteLabelcol);
    deletecol.add(collegedel);
    deletecol.add(submit);

    submit.addActionListener(e->{

String collegeval = (String) collegedel.getSelectedItem();

DefaultTableModel collegemodel = gui.getcollegeModel();
de.removeRowByValue(collegemodel, collegeval, 0,COLLEGE_PATH);

    });
    
    }
}
