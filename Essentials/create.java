package Essentials;

import java.io.*;

public class create {
    public static final String FILE_PATH = "C:\\Users\\Admin\\Desktop\\ccc151\\students.csv";
    public static final String COLLEGE_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\college.csv";
    public static final String PROGRAM_FILE = "C:\\Users\\Admin\\Desktop\\ccc151\\program.csv";

    public create() {
    
        createFileWithHeaderIfNeeded(FILE_PATH, "ID#,First Name,Last Name,Age,Year Level,Gender,Program Code\n");
    }

    private void createFileWithHeaderIfNeeded(String path, String header) {
        File file = new File(path);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
                pw.write(header);
            } catch (IOException e) {
                System.out.println("Error creating the file: " + path);
            }
        }
    }

    public void addStudent(String id, String firstName, String lastName, int age, String yearLevel, String gender, String programCode) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            pw.println(id + "," + firstName + "," + lastName + "," + age + "," + yearLevel + "," + gender + "," + programCode);
        } catch (IOException e) {
            System.out.println("Error writing to the student file.");
        }
    }

    public void program( String programCode, String programName, String college) {
        // Do not create a file unless data is being added
    
        

        try (PrintWriter pw = new PrintWriter(new FileWriter(PROGRAM_FILE, true))) {
            
            pw.println(programCode + "," + programName + "," + college);
        } catch (IOException e) {
            System.out.println("Error writing to the program file.");
        }
    }

public void college( String collegecode, String collegename){
    
    try (PrintWriter pw = new PrintWriter(new FileWriter(COLLEGE_FILE, true))) {
        pw.println(  collegecode + "," + collegename);

}catch(IOException e){
System.out.println("Error writing to the program file.");
}
   
}
}
