package Essentials.removeEvents;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Essentials.delete;
import Essentials.GUI;



public class removeprogram {
  public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";

          public removeprogram(GUI gui,delete de){
            JFrame deleteprogframe = new JFrame("Delete a Program Code");

            deleteprogframe.setLayout(null);
            deleteprogframe.setSize(300,180);
            
            deleteprogframe.setVisible(true);
            
            JLabel deleteprog = new JLabel("Program Code:");
            JTextField deleteprogbar = new JTextField();
            JButton submit = new JButton("Confirm");
         
            deleteprog.setBounds(30,30,180,25);
            deleteprogbar.setBounds(120,30,70,25);
            submit.setBounds(85,80 , 130, 25);   
         
         
            deleteprogframe.add(deleteprog);
            deleteprogframe.add(deleteprogbar);
            deleteprogframe.add(submit);
         
            submit.addActionListener(e->{
         
         
             DefaultTableModel programmodel = gui.getprogramModel();
             String bar = deleteprogbar.getText().trim();
         
             de.removeRowByValue(programmodel, bar, 0,PROGRAM_FILE);
         
            });
          }
}
