package harjoitustyo.tiedot;

/**
 * Class Tiedosto models a file in a file system.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p>
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public class Tiedosto extends Tieto {
    /**
     * The size of the file
     */
    private int koko;
        
    /**
     * File constructor without parameters.
     */
    public Tiedosto(){        
        koko = 0;
    }
    
    /**
     * File constructor with parameters.
     * 
     * @param name the name of the file.
     * @param size the size of the file.
     * @throws IllegalArgumentException if the file name is invalid.
     */
    public Tiedosto(StringBuilder name, int size) throws IllegalArgumentException {
        super(name);
        koko(size);                
    }
    
    /**
     * Returns the file size.
     * 
     * @return size the file's size.
     */
    public int koko() {
        return koko;
    }
    
    /**
     * Changes the file size.
     * 
     * @param size the size of the file.
     * @throws IllegalArgumentException if the size is invalid.
     */
    public void koko(int size) throws IllegalArgumentException {
        if(size < 0){
            throw new IllegalArgumentException();
        }
        else {
            koko = size;
        }
    }
    
    /**
     * String representation of the file.
     * 
     * @return the name of the file and its size.
     */
    @Override
    public String toString() {
        return super.toString() + " " + koko;
    }
}






