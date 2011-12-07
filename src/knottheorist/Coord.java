/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

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

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        PreSquare bucket = grid.get(this);
        this.x = x;
        grid.set.remove(this);
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
        this.y = y;
        grid.set.remove(this);
        grid.set.put(this, bucket);
    }
}
