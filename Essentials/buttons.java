package Essentials;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class buttons extends JPanel {
    JButton searchButton, addStudentButton, delete;

    public buttons(ActionListener listener) {
        setLayout(new FlowLayout());

        // Initialize buttons
        searchButton = new JButton("Search");
        addStudentButton = new JButton("Add Student");
        delete = new JButton("Delete");

        // Add ActionListener from GUI class
        searchButton.addActionListener(listener);
        addStudentButton.addActionListener(listener);
        delete.addActionListener(listener);

        // Add buttons to panel
        add(searchButton);
        add(addStudentButton);
        add(delete);
    }
}