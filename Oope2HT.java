package harjoitustyo;
import harjoitustyo.Liittyma;

/**
 * Class Oope2HT is a class for running the user interface class in an OOP exercise.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */
public class Oope2HT {
    /**
     * The main method that initiates the user interface class Liittyma.
     * 
     * @param args the command line arguments.
     */
    public static void main(String[] args) {        
        // Create new Liittyma object
        Liittyma UI = new Liittyma();
        // Initiate UI
        UI.mainLoop();
    }
    
}
