/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knottheorist;

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
    public String name;
    
    // In no way guaranteed to be maintained.
    public HalfCrossing prev = null;
    public HalfCrossing next = null;
}
