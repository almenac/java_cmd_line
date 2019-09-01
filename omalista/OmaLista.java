package harjoitustyo.omalista;
import harjoitustyo.apulaiset.*;
import java.util.*;

/**
 * Class OmaLista is a custom LinkedList which has for adding items in
 * ascending order and removing references from it.
 * <p>
 * Exercise, Olio-ohjelmoinnin perusteet II, Spring 2019.
 * <p> 
 * @author Ville Kauppila (ville.kauppila@tuni.fi)
 * Student number: 430962
 */

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E>{
    /**
     * Method for adding elements into the list in ascending order.
     * 
     * @param newItem the item to be added
     * @return true if adding was succesful, false if not
     * 
     */    
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(E newItem) {        
        // Only accept arguments that implement Comparable
        if (newItem == null || !(newItem instanceof Comparable)) {
            return false;
        }
        // If the list is empty, the item can simply be added, since it goes to the first index of the list.
        if (size() == 0){
            add(newItem);
            return true;
        }
        // Loop through the list
        for (int i = 0; i < size(); i++) {            
            // Cast the current item and item to be added as Comparable
            Comparable compListItem = (Comparable) get(i);
            Comparable compNewItem = (Comparable) newItem;            
            // Get the comparison value from the two items based on alphabet position
            int compResult = compListItem.compareTo(compNewItem);
            // If the list item is greater than the addable item, add it to list item's index
            if (compResult > 0) {                
                add(i, newItem);
                return true;
            }            
            // If the addable item is greater than any of the list items, add it to the end.
            if (size() -1 == (i)){
                add(newItem);
                return true;
            }
        }
        return false;        
    }

    /**
     * Method for removing all references to an item in the list.
     * 
     * @param removableItem the object to be removed.
     * @return removeCount the number of references removed.
     */
    public int poista(E removableItem) {
        // Counter for removed items
        int removeCount = 0;
        if (removableItem != null) {            
            int i = 0;        
            
            // Loop through the size
            while (i < size()) {            
                // If match, remove the item and raise remove counter, but not the index counter
                if (get(i) == removableItem) {
                    remove(i);
                    removeCount ++;
                }
                // If no match, raise the index counter
                else{
                    i++;
                }
            }
        }        
        return removeCount;
    }
}
