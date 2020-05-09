import java.util.Scanner;

public class Main
{ 
        public static void main(String[] args) throws Exception 
        {  
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter file to read");
        String fileName = myObj.nextLine();  // Read user input
        FileIO file = new FileIO(fileName); // read data
        file.outputFile(); //output information.
        System.out.println("Reading: " + fileName);  // Output user input  
        myObj.close();   
    }
  }