package Essentials.loadEvents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import Essentials.GUI;
import Essentials.create;

public class loadcollege {
    public loadcollege(GUI gui, create write){
        try(BufferedReader br = new BufferedReader(new FileReader(create.COLLEGE_FILE))){
            String line;
            br.readLine();
            DefaultTableModel collegemodel = gui.getcollegeModel();
            while((line = br.readLine()) !=null){
                String[] data = line.split(",");
                collegemodel.addRow(data);
             
            }
    
        }catch(IOException e){
            System.out.println("No existing file found");
        }
    }
    
}
