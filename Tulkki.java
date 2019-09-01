package harjoitustyo;
import harjoitustyo.tiedot.*;
import java.util.*;

/**
 * Class Tulkki which models the information
 * needed by command interpreter.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public class Tulkki {
    /**
     * The root directory of the file system.
     */        
    private Hakemisto root;

    /**
     * The current directory.
     */
    private Hakemisto curDir;

    /**
     * Constructor without parameters. 
     */
    public Tulkki() {
        root = new Hakemisto();
        // When created, sets the current directory as root directory 
        curDir = root;        
    }
    
    /**
     * Form the current directory path.
     * 
     * @return the String version of the directory path.
     */    
    public String formPath() {
        // Path string that always starts with "/"
        String path = "";        
        // Starting directory
        Hakemisto dir = curDir;
        
        // Form a reverse string of all the directory names
        while (dir.ylihakemisto() != null) {            
            path = dir.nimi().toString() + "/" + path;
            dir = dir.ylihakemisto();
        }        
        return "/" + path + ">";
    }

    /**
     * Create new directory.
     * 
     * @param dirName the name of the new directory.
     * @throws IllegalArgumentException if the directory already exists.
     */
    public void createDirectory(String dirName) throws IllegalArgumentException {
        // Create new directory object with args and current dir as parent directory
        Hakemisto newDir = new Hakemisto(new StringBuilder(dirName), curDir);
        // Search if name folder of the same name is found
        LinkedList<Tieto> hits = curDir.hae(dirName);
        // If directory doesn't already exist, add it to current directory
        if (hits.isEmpty()) {
            curDir.lisaa(newDir);            
        }
        // If dir exists, throw exception with error msg
        else {
            throw new IllegalArgumentException("Directory exists!");
        }
    }

    /**
     * Change directory with a parameter.
     * 
     * @param destDirName name of the destination directory.
     * @throws IllegalArgumentException if the directory is not found.
     */
    public void changeDirectory(String destDirName) throws IllegalArgumentException {
        // Save current directory to variable
        Hakemisto oldDir = curDir;        
        // Search for the directory
        LinkedList<Tieto> dirList = curDir.hae(destDirName);        
        // Check if 1 matching directory exists
        if (dirList.size() == 1 && dirList.get(0) instanceof Hakemisto){
                // First index of size 1 list returns the right one of non-empty list            
                Hakemisto destDir = (Hakemisto)dirList.getFirst();
                // Change current directory to target directory
                curDir = destDir;
                // Set the origin directory as new parent directory
                curDir.ylihakemisto(oldDir);
        }
        // Move to previous directory
        else if (destDirName.equals("..") && curDir != root) {                        
            // Save parent dir's parent
            Hakemisto newParent = oldDir.ylihakemisto().ylihakemisto();
            // Move to parent directory
            curDir = curDir.ylihakemisto();
            // Set new parent directory
            curDir.ylihakemisto(newParent);            
        }
        // Otherwise throw exception and give error msg
        else {
            throw new IllegalArgumentException("Directory not found!");
        }
    }
    
    /**
     * Change directory without a parameter to the root directory.
     *      
     */
    public void changeDirectory() {
        curDir = root;
        curDir.ylihakemisto(null);        
    }

    /**
     * Create a new file.
     * 
     * @param fileName name of the new file.
     * @param fileSize size of the new file.
     * @throws IllegalArgumentException if the file already exists in the folder.
     */
    public void createFile(String fileName, int fileSize) throws IllegalArgumentException {
        // Construct new file based on parameters
        Tiedosto newFile = new Tiedosto(new StringBuilder(fileName), fileSize);
        // Create a list of possible duplicates
        LinkedList<Tieto> results = curDir.hae(fileName);        
        // If no duplicates, add the new file to current directory
        if (results.isEmpty()) {
            curDir.lisaa((newFile));
        }
        // Non-empty result list means there's a file of the same name
        else {
            throw new IllegalArgumentException("File already exists!");
        }
    }
    
    /**
     * List contents of the directory with parameter.
     * 
     * @param argString the listing parameter.
     * @throws IllegalArgumentException if the folder is empty.
     */
    public void listContents(String argString) throws IllegalArgumentException {                
        // Create a list of search results
        LinkedList<Tieto> results = curDir.hae(argString);        
        // Listing with arg will result in error in empty directory unless the arg is asterisk        
        if (!results.isEmpty() || argString.equals("*")) {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
            }
        }
        // List empty without asterisk or arg doesn't match contents
        else {            
            throw new IllegalArgumentException("Invalid arg!");
        }
    }
    
    /**
     * List contents of the directory without parameter.
     */
    public void listContents() {
        // Create a list of search results
        // Search without arg utilizes the wildcard trait of hae() method
        LinkedList<Tieto> results = curDir.hae("*");
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
    }
    
    /**
     * Remove data from current folder.
     * 
     * @param name the search parameter for the data to be deleted.
     * @throws IllegalArgumentException if the data is not found.
     */
    public void remove(String name) throws IllegalArgumentException {        
        // Create a list of search results
        LinkedList<Tieto> results = curDir.hae(name);        
        // Non-empty list means that one or more matches were found
        if (!results.isEmpty()) {
            // Loop that continues while result list is not empty
            while (!results.isEmpty()) {       
                // Save the first item of the list for deletion
                Tieto delItem = results.getFirst();
                // Delete the item
                curDir.poista((delItem));
                // Rebuild the list without the deleted item
                results = curDir.hae(name);
            }
        }
        // List empty or more matches than one
        else {
            throw new IllegalArgumentException("File not found!");
        }
    }

    /**
     * Change the name of the data in folder.
     * 
     * @param oldName the name of the origin data.
     * @param newName the name of the target data.
     * @throws IllegalArgumentException if data is not found.
     */
    public void rename(String oldName, String newName) throws IllegalArgumentException {
        // Build a list from search results of original name
        LinkedList<Tieto> results = curDir.hae(oldName);
        // Build a list from search results of new name
        LinkedList<Tieto> newResults = curDir.hae(newName);
        // Check if 1 result was found and target name not found
        if (results.size() == 1 && newResults.isEmpty()) {
            // Save the only value from the list
            Tieto renamed = results.getFirst();
            // Change the item's name to arg
            renamed.nimi(new StringBuilder(newName));
            // Sort the list by ascending after change
            curDir.sortAscending();
        }
        // List empty or more matches than one
        else {            
            throw new IllegalArgumentException("Not found!");
        }
    }
}