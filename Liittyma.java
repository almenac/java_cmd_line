package harjoitustyo;
import harjoitustyo.apulaiset.In;

/**
 * Class Liittyma which handles the command line parameter in the program.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public class Liittyma {
    /**
     * Constant for the generic error message.
     */
    private final static String ERR = "Error!";
    
    /**
     * Instance of the Tulkki class which handles the commands given by this class.
     */
    private Tulkki shell;

    /**
     * Constructor without parameters.
     */
    public Liittyma() {
        shell = new Tulkki();
    }

    /**
     * The main loop of the program. Handles user input and passes commands to the command interpreter Tulkki.
     */
    public void mainLoop() {
        // Continue flag
        boolean quit = false;        
        // Greet the user before entering main loop
        System.out.println("Welcome to SOS.");        
        // While loop that continues unless quit flag is turned
        while(!quit){            
            // Print the path in the prompt
            System.out.print(shell.formPath());
            // Read command from user
            String command = In.readString();            
            // Case statements and exception handling for all the commands
            // Every case calls the command interpreter's methods            
            try {            
                // Exit program
                if (command.equals("exit")) {
                    System.out.println("Shell terminated.");
                    quit = true;
                }            
                // Make directory
                else if (command.startsWith("md")) {                
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command takes only one parameter
                    if (args.length == 2) {
                        String dirName = args[1];
                        shell.createDirectory(dirName);                    
                    }
                    else {
                        System.out.println(ERR);
                    }
                }
                // Change directory without parameter
                // Command "cd" on its own always returns to root directory
                else if (command.equals("cd")) {                    
                    shell.changeDirectory();
                }
                // Change directory with parameter
                else if (command.startsWith("cd")) {                
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command only accepts one argument 
                    if (args.length == 2) {
                        String dirName = args[1];
                        shell.changeDirectory(dirName);
                    }
                    else {
                        System.out.println(ERR);
                    }
                }
                // Make file
                else if (command.startsWith("mf")) {
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command only accepts two arguments
                    if (args.length == 3) {                        
                        String fileName = args[1];
                        int fileSize = Integer.parseInt(args[2]);
                        shell.createFile(fileName, fileSize);                    
                    }
                    else {
                        System.out.println(ERR);
                    }
                }            
                // Remove content
                else if (command.startsWith("rm")) {
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command accepts only one argument
                    if (args.length == 2) {
                        String delName = args[1];                                        
                        shell.remove(delName);                    
                    }
                    else {                    
                        System.out.println(ERR);
                    }                
                }
                // Rename content
                else if (command.startsWith("mv")) {
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command only accepts two arguments
                    if (args.length == 3) {
                        String oldName = args[1];
                        String newName = args[2];                    
                        shell.rename(oldName, newName);                    
                    }
                    else {
                        System.out.println(ERR);
                    }
                }
                // List directory contents without parameter
                else if (command.equals("ls")) {                
                    shell.listContents();
                }
                // List directory contents with parameter
                else if (command.startsWith("ls")) {                
                    // Parse arguments
                    String[] args = getArg(command);
                    // Command only accepts one argument
                    if (args.length == 2) {                    
                        String argName = args[1];                                        
                        shell.listContents(argName);                    
                    }
                    else {
                        System.out.println(ERR);
                    }
                }
                // Otherwise invalid command and error msg
                else {
                    System.out.println(ERR);
                }
            }
            // Catch all argument exceptions from the main loop
            catch (IllegalArgumentException e) {
                System.out.println(ERR);
            }
        }
    }

    /**
     * Helper method for splitting multi-part commands into separate parts.
     * 
     * @param command the command String from the user.
     * @return String array of the parts separated by spaces.
     */
    public String[] getArg(String command) {        
        String[] commParts = command.split(" ");
        return commParts;
    }
}