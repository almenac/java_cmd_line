package harjoitustyo.tiedot;
import harjoitustyo.apulaiset.*;
import java.io.*;

/**
 * Abstract class Tieto which contains attributes and
 * methods common to classes that deal with information.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public abstract class Tieto implements Comparable<Tieto>, Tietoinen, Serializable {
    /**
     * The name of the information.
     */
    private StringBuilder nimi;
    
    /**
     * Tieto constructor without parameters.
     */
    public Tieto() {
        nimi = new StringBuilder("");
    }
    
    /**
     * Tieto constructor with parameters. Uses the nimi() method within the class.
     * 
     * @param n the name of the new Tieto.
     */
    public Tieto(StringBuilder n) throws IllegalArgumentException {
        nimi(n);
    }
    
    /**
     * Returns the name of Tieto.
     * 
     * @return nimi, the name of tieto.
     */    
    public StringBuilder nimi() {
        return nimi;
    }
    
    /**
     * Checks if a name passed as argument is valid.
     * 
     * @param testName name to be checked.
     * 
     * @return true if the name is valid, false if invalid.
     * 
     */    
    public boolean validName(StringBuilder testName){        
        // Check for null
        if (testName == null) {
            return false;
        }
        // StringBuilder cast to String
        String nameString = testName.toString();
        // Is a single character valid
        boolean validChar = true;
        // Is the whole string valid
        boolean validInput = true;        
        // Array that contains all the valid chars
        char[] allowed = new char[] 
        {'a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L',
        'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9','0','_','.'};
        
        // Check for special case of ".."
        if (nameString.equals("..")) {
            validInput = false;
        }
        // Two-dimensional loop to check if string contains invalid characters
        // Outer loop through the string's characters
        for(int i = 0; i < nameString.length(); i++){
            // If inner loop returns false even once, string contains invalid characters
            if (validChar == false) {                
                validInput = false;
            }
            // Turn validChar to false before inner loop
            validChar = false;
            // Inner loop through the allowed characters
            for(int t = 0; t < allowed.length; t++){
                // If i is valid, turn validChar true
                if (nameString.charAt(i) == allowed[t]){
                    validChar = true;
                }
            }
        }
        return validInput;
    }

    /**
     * Changes the nimi attribute of Tieto.
     * 
     * @param uusiNimi new name.
     * @throws IllegalArgumentException if uusiNimi is invalid. 
     */    
    public void nimi(StringBuilder uusiNimi) throws IllegalArgumentException {        
        // Check for null. 
        if (uusiNimi == null) {
            // null throws exception
            throw new IllegalArgumentException();
        }
        // Check the validity if uusiNimi with validName method, store to variable
        boolean allOk = validName(uusiNimi);
        // If input is valid, change name
        if (allOk == false || uusiNimi.length() < 1) {            
            // Invalid input throws exception
            throw new IllegalArgumentException();
        }
        else {
            nimi = uusiNimi;
        }
    }

    /**
     * String representation of the Tieto class.
     * 
     * @return the String version of StringBuilder nimi.
     */
    @Override
    public String toString(){
        // Convert nimi to String
        return nimi.toString();
    }
    
    /**
     * Equals comparison for the names.
     * 
     * @param compObj the object that Tieto is compared to.
     * 
     * @return false is objects are equal, false if not.
     */
    @Override
    public boolean equals(Object compObj){
        // Try/catch block for comparing objects
        try{
            // Create test instance of Tieto from argument object
            Tieto testInfo = (Tieto)compObj;
            // Store nimi of this instance to String
            String origName = nimi.toString();
            // Store nimi of comparable object to String
            String compName = testInfo.nimi.toString();
            // Return the result of String.equals() comparison
            return origName.equals(compName);
        }
        // If try block fails for any reason, the comparison fails and returns false
        catch (Exception e){            
            return false;
        }
    }

    /**
     * Search method as a String comparison with wild card (*) functionality. 
     * Implementation of the equals() method of Tietoinen interface.
     * 
     * @param searchWord String that's compared to nimi.
     * 
     * @return true if the argument matches exactly or with wild card, false otherwise.
     */
    @Override
    public boolean equals(String searchWord){
        // Check for null
        if (searchWord == null){
            return false;
        }        
        // Cast nimi to String
        String nameString = nimi.toString();
        // String that will contain the search word without asterisks
        String starlessSearchWord = "";        
        // Asterisk count
        int stars = 0;
        // Asterisk at start
        boolean starStart = false;
        // Asterisk at end
        boolean starEnd = false;
        // Asterisk at both start and end
        boolean starBoth = false;
        // Count the number of stars
        for(int i = 0; i < searchWord.length(); i++){
            if(searchWord.charAt(i) == '*'){
                stars += 1;                
            }            
        }        
        // Check star status and build comparison strings for later comparison
        if (stars > 0) {                        
            // One asterisk always matches
            if (searchWord.equals("*")){                
                return true;
            }
            // Two asterisks never matches
            else if (searchWord.equals("**")) {
                return false;
            }
            // More than 2 asterisks never matches
            else if (stars > 2) {
                return false;
            }            
            // Asterisk found both at start and end
            else if (searchWord.charAt(0) == '*' && searchWord.charAt(searchWord.length() -1) == '*'){
                starBoth = true;
                // Start loop from second index and stop one before last
                for (int i = 1; i <= searchWord.length() -2; i++) {                    
                    starlessSearchWord += searchWord.charAt(i);
                }
            }
            // Asterisk found at start of String
            else if (searchWord.charAt(0) == '*'){
                starStart = true;
                // Start loop at second index, continue until the end of String
                for (int i = 1; i < searchWord.length(); i++) {                    
                    starlessSearchWord += searchWord.charAt(i);
                }                
            }
            // Asterisk found at end of String
            else if (searchWord.charAt(searchWord.length() -1) == '*') {
                starEnd = true;                
                for (int i = 0; i < searchWord.length() -1; i++) {
                    starlessSearchWord += searchWord.charAt(i);
                }                
            }            
        }
        // Compare the Strings based on asterisk status
        // Argument matches nimi exactly
        if (nameString.equals(searchWord)) {
            return true;
        }
        // Asterisk at beginning, check the end of nimi
        else if (starStart) {
            // nimi ends with the search word with asterisks removed
            return nameString.endsWith(starlessSearchWord.substring(0));
        }
        // Asterisk at end, check the start of nimi
        else if (starEnd) {
            // nimi starts with the search word with asterisks removed
            return nameString.startsWith(starlessSearchWord.substring(0));
        }
        // Asterisk found at both start and end, check the middle
        else if (starBoth) {
            // nimi contains the searchword with asterisks removed
            return nameString.contains(starlessSearchWord);
        }        
        // Otherwise return false
        return false;
    }
    
    /**
     * Compare two Tieto objects.
     * 
     * @param compInfo Tieto object to compare to.
     * 
     * @return -1 if smaller, 1 if larger and 0 if equal size.
     */
    @Override
    public int compareTo(Tieto compInfo){
        // String version of nimi
        String origName = nimi.toString();
        // String version of argument
        String compName = compInfo.nimi().toString();
        // Int result of standard compareTo() method
        int result = origName.compareTo(compName);
        
        // Results under 0 are considered -1
        if(result < 0){
            return -1;
        }
        // Results over 0 are considered 1
        else if(result > 0){
            return 1;
        }
        // Otherwise result is 0 and is returned as is
        else{
            return result;
        }        
    }
}
