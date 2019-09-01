package harjoitustyo.tiedot;
import java.util.*;
import harjoitustyo.apulaiset.*;
import harjoitustyo.omalista.*;

/**
 * Class Hakemisto models a directory in a file system.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public class Hakemisto extends Tieto implements Sailova<Tieto>{
    /**
     * The contents of the directory as a list.
     */
    private OmaLista<Tieto> sisalto;
    
    /**
     * The parent directory of the current directory.
     */
    private Hakemisto ylihakemisto;
    
    /**
     * Directory constructor without parameters.
     */
    public Hakemisto() {
        sisalto = new OmaLista<Tieto>();        
        ylihakemisto = null;
    }
    
    /**
     * Directory constructor with parameters.
     * 
     * @param name the name of the directory.
     * @param rootFolder the parent directory.
     * @throws IllegalArgumentException if the directory name is invalid.
     */
    public Hakemisto(StringBuilder name, Hakemisto rootFolder) throws IllegalArgumentException {
        super(name);
        sisalto = new OmaLista<Tieto>();
        ylihakemisto(rootFolder);
    }
    
    /**
     * Returns the directory name.
     * 
     * @return sisalto, the content list.
     */
    public OmaLista<Tieto> sisalto(){
        return sisalto;
    }
    
    /**
     * Changes the directory's content list.
     * 
     * @param newList new list.
     * @throws IllegalArgumentException if list argument is invalid. 
     */
    public void sisalto(OmaLista<Tieto> newList) throws IllegalArgumentException{
        if(newList == null){
            throw new IllegalArgumentException();
        }
        else{
            sisalto = newList;
        }
    }
    
    /**
     * Returns the parent directory.
     * 
     * @return ylihakemisto, the parent directory.
     */
    public Hakemisto ylihakemisto(){
        return ylihakemisto;
    }
    
    /**
     * Changes parent directory.
     * 
     * @param rootFolder new parent folder.
     */
    public void ylihakemisto(Hakemisto rootFolder){
        ylihakemisto = rootFolder;
    }

    /**
     * Sorts the sisalto lista in ascending order
     */
    public void sortAscending() {
        Collections.sort(sisalto);
    }
    
    /**
     * String representation of the directory.
     * 
     * @return the name attribute, a "/" sign and the size of the directory's content list.
     */    
    @Override
    public String toString() {
        return super.toString() + "/" + " " + sisalto.size();
    }
    
    /**
     * Search method for the directory's contents.
     * 
     * @param hakusana the search word.
     * 
     * @return LinkedList of found Tieto objects.
     */
    @Override
    public LinkedList<Tieto> hae(String hakusana){
        LinkedList<Tieto> resultList = new LinkedList<Tieto>();        
        // Loop through the content list, add matches to result list.
        for (int i = 0; i < sisalto.size(); i++) {
            if (sisalto.get(i).equals(hakusana)) {
                resultList.add(sisalto.get(i));
            }
        }        
        return resultList;
    }
    
    /**
     * Remove method for directory's contents.
     * 
     * @param poistettava the Tieto object to be removed.
     * 
     * @return true if deletion was succesful, false if not.
     */
    @Override
    public boolean poista(Tieto poistettava){
        if (sisalto.contains(poistettava)) {
            sisalto.poista(poistettava);
            return true;
        }
        return false;
    }

    /**
     * Add method for directory.
     * 
     * @param lisattava Tieto object to be added.
     * 
     * @return true if added succesfully, false if not.
     */
    @Override
    public boolean lisaa(Tieto lisattava){
        return sisalto.lisaa(lisattava);
    }
}
