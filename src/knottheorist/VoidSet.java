/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * I should probably name this better.  If the requested object doesn't exist,
 * it creates one and returns that.  I was thinking "pulls it out of the void"
 * or something.
 * @author mewer12
 */
public class VoidSet<T, U> {
    public HashMap<T, U> set = new HashMap<T, U>();
    public Class<U> cls;
    
    /**
     * Ugh.  I'm finding more and more annoying things about Java.
     * Just pass in the value class, like so:
     * VoidSet<Integer, Object> example = new VoidSet<Integer, Object>(Object.class);
     * The value class needs to have a default constructor.
     * @param cls 
     */
    public VoidSet(Class<U> cls) {
        this.cls = cls;
    }
    
    public U get(T index) {
        U result = set.get(index);
        Integer i;
        VoidSet<T, Integer> greg = new VoidSet<T, Integer>(Integer.class);
        if (result != null)
            return result;
        try {
            result = cls.newInstance();
            if (result instanceof PreSquare) {
                ((PreSquare)result).coord = (Coord)index;
            }
            set.put(index, result);
        } catch (InstantiationException ex) {
            Logger.getLogger(VoidSet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VoidSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public U put(T key, U value) {
        return set.put(key, value);
    }
    
    public T getKey(U value) {
        for (Entry<T,U> e : set.entrySet()) {
            if (e.getValue().equals(value)) {
                return e.getKey();
            }
        }
        return null;
    }
}
