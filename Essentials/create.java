package Essentials;

import java.io.*;

public class create {
    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";

    
    public create() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) {
                pw.write("ID#,First Name,Last Name,Age,Year Level,Gender,Program Code\n");
            } catch (IOException e) {
                System.out.println("Error creating the file.");
            }
        }
    }

    public void addStudent(String id, String firstName, String lastName, int age, String yearLevel, String gender, String programCode) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            pw.println(id + "," + firstName + "," + lastName + "," + age + "," + yearLevel + "," + gender + "," + programCode);
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
        }
    }
}
