package Essentials.loadEvents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import Essentials.create;
import Essentials.GUI;

public class loadstudent {
   public loadstudent(GUI gui, create write){
   try (BufferedReader br = new BufferedReader(new FileReader(create.FILE_PATH))) {
            String line;
            br.readLine(); // Skips the first
            DefaultTableModel model = gui.getstudentModel();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            System.out.println("No existing data found.");

        }
    }
}
