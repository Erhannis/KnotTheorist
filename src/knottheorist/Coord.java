/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author mewer12
 */
public class Coord {

    public static VoidSet<Coord, PreSquare> grid = null;
    public int x = 0;
    public int y = 0;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return (getX() << 10) + getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord)) {
            return false;
        }
        if ((((Coord) obj).getX() == this.getX()) && (((Coord) obj).getY() == this.getY())) {
            return true;
        }
        return false;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    public void forceRemove(Coord crd) {
        Set<Entry<Coord, PreSquare>> entries = grid.set.entrySet();
        Entry<Coord, PreSquare> e;
        for (Iterator<Entry<Coord, PreSquare>> i = entries.iterator(); i.hasNext();) {
            e = i.next();
            if (e.getKey().equals(crd)) {
                i.remove();
            }
        }
    }
    
    /**
     * @param x the x to set
     */
    public void setX(int x) {
        PreSquare bucket = grid.get(this);
        forceRemove(this);
        this.x = x;
        forceRemove(this);
        grid.set.put(this, bucket);
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        PreSquare bucket = grid.get(this);
        forceRemove(this);
        this.y = y;
        forceRemove(this);
        grid.set.put(this, bucket);
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
