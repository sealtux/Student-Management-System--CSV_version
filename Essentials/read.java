package Essentials;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;


public class read {
    
    private String filePath = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv"; // Path of the file

    public read() {
        System.out.println("Try again");
    }

    public read(String searchTerm) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
          
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); 
                String street = values[0];

                    if (street.equalsIgnoreCase(searchTerm)) { // Case insensitive search
                        System.out.println("Found: " + line);


                    }
                }
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error: Unable to read file!");
            e.printStackTrace();
        }
    }
}
