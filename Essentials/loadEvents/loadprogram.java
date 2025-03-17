package Essentials.loadEvents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

import Essentials.GUI;
import Essentials.create;

public class loadprogram {
    public loadprogram(GUI gui,create writer){
try(BufferedReader br = new BufferedReader(new FileReader(create.PROGRAM_FILE))){
String line;

DefaultTableModel programmodel = gui.getprogramModel();
while((line = br.readLine()) !=null){
    String[] data = line.split(",");
    programmodel.addRow(data);
    
}
}catch(IOException e){
    System.out.println("No existing file found");
}
    }
}
