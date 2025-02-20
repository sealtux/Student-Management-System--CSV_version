package Essentials.removeEvents;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Essentials.delete;
import Essentials.GUI;

public class removestud {
      GUI maingui;
      public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";
      public  removestud (GUI gui, delete de ){

      this.maingui = gui;

    JFrame deletestud = new JFrame("Delete a Student");
    deletestud.setLayout(null);
    deletestud.setSize(300,180);
    JLabel deleteindicate = new JLabel("Enter ID:");
    JTextField deletebar = new JTextField();
    JButton submitdelete = new JButton("Confirm");
  

    deleteindicate.setBounds(30,30,180,25);
    deletebar.setBounds(85,30,70,25);
    submitdelete.setBounds(85,80 , 130, 25);

    deletestud.add(deleteindicate);
    deletestud.add(deletebar);
    deletestud.add(submitdelete);


    deletestud.setVisible(true);

submitdelete.addActionListener(e->{
    String bar = deletebar.getText().trim();
    DefaultTableModel model = maingui.getstudentModel();
    de.removeRowByValue(model, bar, 0,FILE_PATH);
    });
}
}
