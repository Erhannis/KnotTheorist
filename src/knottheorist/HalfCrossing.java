/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knottheorist;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author CORNELL-COLLEGE\mewer12
 */
public class HalfCrossing {
    public static final int CROSS_NONE =   0;// 00;
    public static final int CROSS_TOP =    1;// 01;
    public static final int CROSS_BOTTOM = 2;// 10;
    public static final int CROSS_LEFT =   1;// 01;
    public static final int CROSS_RIGHT =  2;// 10;

//    public static final int DIR_UP = 0;
//    public static final int DIR_RIGHT = 1;
//    public static final int DIR_DOWN = 2;
//    public static final int DIR_LEFT = 3;
    public static final int DIR_RIGHT = 1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 4;
    public static final int DIR_UP = 8;

    public Crossing parent;
    //public int linearPosition;
    public int tb = 0; // Top or Bottom;
    public int lr = 0; // Left or Right;
    public int dir = -1; // Not really integral knot theory, but helpful.
    public HalfCrossing twin;
    public HalfCrossing copy;
    public String name;
    
    // In no way guaranteed to be maintained.
    public HalfCrossing prev = null;
    public HalfCrossing next = null;
    public int pos = -1;
    
    /**
     * Note: There is no (convenient) way from here to automatically associate
     * a copy of an HC with the copy of its twin.  I suggest setting "copy" to a
     * copy for all HCs, then correlating after all are done.
     * @return 
     */
    public HalfCrossing copy() {
        HalfCrossing copy = new HalfCrossing();
        copy.dir = this.dir;
        copy.lr = this.lr;
        copy.name = this.name;
        copy.pos = this.pos;
        copy.tb = this.tb;
        return copy;
    }
    
    public HashMap<HashSet<HalfCrossing>, HalfCrossing> triPair = new HashMap<HashSet<HalfCrossing>, HalfCrossing>();
    
    public String toString() {
        return this.name + (tb == CROSS_TOP ? 't' : 'b') + (lr == CROSS_LEFT ? 'l' : 'r');
    }
}
