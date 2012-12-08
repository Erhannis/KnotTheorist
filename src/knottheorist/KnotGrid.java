/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.awt.Component;
import java.awt.Point;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author CORNELL-COLLEGE\mewer12
 */
public class KnotGrid {

//    public static final int DIR_UP = 0;
//    public static final int DIR_RIGHT = 1;
//    public static final int DIR_DOWN = 2;
//    public static final int DIR_LEFT = 3;
    public static final int DIR_RIGHT = 1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 4;
    public static final int DIR_UP = 8;
    public boolean initialized = false;
    public int rows = 0;
    public int cols = 0;
    public GridSquare[][] squares = null;
    //public GridSquare[][] buttonsDown = null;
    public KnotTheoristView parentView = null;
    public Point mouseFrom = new Point();
    public Icon[] knotIcons = null;
    public int squareWidth = 0;
    public int squareHeight = 0;

    public KnotGrid() {
    }

    public KnotGrid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void init(Icon[] btnColors, Random rng, KnotTheoristView parentView, int squareWidth, int squareHeight) {        
        this.parentView = parentView;
        this.knotIcons = btnColors;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        squares = new GridSquare[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                JLabel bucket = new JLabel("", btnColors[GridSquare.KNOT_EMPTY], JLabel.CENTER);
                bucket.setSize(squareWidth, squareHeight);
                squares[x][y] = new GridSquare(bucket, this, squareWidth, squareHeight);
                //buttons[x][y].numLinks = rng.nextInt(maxChildren - minChildren + 1) + minChildren;
                //buttons[x][y].links = new GridButtonLink[buttons[x][y].numLinks];
            }
        }
    }
//    public void goInDir(int[] coords, int dir) {
//    }
    public static final int CROSS_ERROR = 0;//  00;
    public static final int CROSS_LEFT = 1;// 01;
    public static final int CROSS_RIGHT = 2;// 10;

    public int getRightLeft(int thisDir, int otherDir) {
        switch (thisDir) {
            case DIR_UP:
                switch (otherDir) {
                    case DIR_UP:
                        return CROSS_ERROR;
                    case DIR_RIGHT:
                        return CROSS_RIGHT;
                    case DIR_DOWN:
                        return CROSS_ERROR;
                    case DIR_LEFT:
                        return CROSS_LEFT;
                    default:
                        return CROSS_ERROR;
                }
            case DIR_RIGHT:
                switch (otherDir) {
                    case DIR_UP:
                        return CROSS_LEFT;
                    case DIR_RIGHT:
                        return CROSS_ERROR;
                    case DIR_DOWN:
                        return CROSS_RIGHT;
                    case DIR_LEFT:
                        return CROSS_ERROR;
                    default:
                        return CROSS_ERROR;
                }
            case DIR_DOWN:
                switch (otherDir) {
                    case DIR_UP:
                        return CROSS_ERROR;
                    case DIR_RIGHT:
                        return CROSS_LEFT;
                    case DIR_DOWN:
                        return CROSS_ERROR;
                    case DIR_LEFT:
                        return CROSS_RIGHT;
                    default:
                        return CROSS_ERROR;
                }
            case DIR_LEFT:
                switch (otherDir) {
                    case DIR_UP:
                        return CROSS_RIGHT;
                    case DIR_RIGHT:
                        return CROSS_ERROR;
                    case DIR_DOWN:
                        return CROSS_LEFT;
                    case DIR_LEFT:
                        return CROSS_ERROR;
                    default:
                        return CROSS_ERROR;
                }
        }
        return CROSS_ERROR;
    }

    public Knot readGrid(int mode) {
        Knot result = new Knot();
        result.representMode = mode;
        int x = 0;
        int y = 0;
        int dir = -1;
        boolean foundStart = false;
        for (y = 0; y < rows; y++) {
            for (x = 0; x < cols; x++) {
                squares[x][y].crossing = null;
            }
        }
        for (y = 0; y < rows; y++) {
            for (x = 0; x < cols; x++) {
                if (squares[x][y].state != GridSquare.KNOT_EMPTY) {
                    if (squares[x][y].state != GridSquare.KNOT_TURNSE) {
                        result.representation = "Break at " + x + ", " + y;
                        return result;
                    }
                    foundStart = true;
                    dir = DIR_UP;
                    break;
                }
            }
            if (foundStart) {
                break;
            }
        }
        int startX = x;
        int startY = y;

        char nameChar = 'A';
        int nameID = 1;
        switch (mode) {
            case Knot.REP_ATL:
            case Knot.REP_BKa:
                nameChar = 'A';
                break;
            case Knot.REP_NTL:
                nameID = 1;
                break;
            default:
                break;
        }

        int count = 0;
        if (x >= cols || y >= rows) {
            result.represent();
            return result;
        }
        boolean done = false;
        while (!done) {
            count++;
            if (x >= cols || y >= rows) {
                result.representation = "Break at " + x + ", " + y;
                return result;
            }
            switch (squares[x][y].state) {
                case GridSquare.KNOT_EMPTY:
                    result.representation = "Break at " + x + ", " + y;
                    return result;
                case GridSquare.KNOT_CROSSEW:
                    switch (dir) {
                        case DIR_UP:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                // There ought to be sub-method methods.
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }

                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            y--;
                            break;
                        case DIR_RIGHT:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            x++;
                            break;
                        case DIR_DOWN:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            y++;
                            break;
                        case DIR_LEFT:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            x--;
                            break;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_CROSSNS:
                    switch (dir) {
                        case DIR_UP:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            y--;
                            break;
                        case DIR_RIGHT:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            x++;
                            break;
                        case DIR_DOWN:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            y++;
                            break;
                        case DIR_LEFT:
                            if (squares[x][y].crossing == null) {
                                squares[x][y].crossing = new Crossing();
                                squares[x][y].crossing.A = new HalfCrossing();
                                squares[x][y].crossing.B = new HalfCrossing();
                                squares[x][y].crossing.A.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.A.dir = dir;
                                squares[x][y].crossing.A.parent = squares[x][y].crossing;
                                squares[x][y].crossing.A.twin = squares[x][y].crossing.B;
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_TOP;
                                squares[x][y].crossing.B.dir = -1;
                                squares[x][y].crossing.B.parent = squares[x][y].crossing;
                                squares[x][y].crossing.B.twin = squares[x][y].crossing.A;
                                switch (mode) {
                                    case Knot.REP_ATL:
                                    case Knot.REP_BKa:
                                        squares[x][y].crossing.A.name = String.valueOf(nameChar);
                                        squares[x][y].crossing.B.name = String.valueOf(nameChar);
                                        nameChar++;
                                        break;
                                    case Knot.REP_NTL:
                                        squares[x][y].crossing.A.name = Integer.toString(nameID);
                                        squares[x][y].crossing.B.name = Integer.toString(nameID);
                                        nameID++;
                                        break;
                                }
                                result.halfCrossList.add(squares[x][y].crossing.A);
                            } else {
                                squares[x][y].crossing.B.tb = HalfCrossing.CROSS_BOTTOM;
                                squares[x][y].crossing.B.dir = dir;
                                squares[x][y].crossing.A.lr = getRightLeft(squares[x][y].crossing.A.dir, squares[x][y].crossing.B.dir);
                                squares[x][y].crossing.B.lr = getRightLeft(squares[x][y].crossing.B.dir, squares[x][y].crossing.A.dir);
                                result.halfCrossList.add(squares[x][y].crossing.B);
                            }
                            x--;
                            break;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_EW:
                    switch (dir) {
                        case DIR_UP:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_RIGHT:
                            x++;
                            break;
                        case DIR_DOWN:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_LEFT:
                            x--;
                            break;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_NS:
                    switch (dir) {
                        case DIR_UP:
                            y--;
                            break;
                        case DIR_RIGHT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_DOWN:
                            y++;
                            break;
                        case DIR_LEFT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_TURNNE:
                    switch (dir) {
                        case DIR_UP:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_RIGHT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_DOWN:
                            dir = DIR_RIGHT;
                            x++;
                            break;
                        case DIR_LEFT:
                            dir = DIR_UP;
                            y--;
                            break;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_TURNNW:
                    switch (dir) {
                        case DIR_UP:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_RIGHT:
                            dir = DIR_UP;
                            y--;
                            break;
                        case DIR_DOWN:
                            dir = DIR_LEFT;
                            x--;
                            break;
                        case DIR_LEFT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_TURNSE:
                    switch (dir) {
                        case DIR_UP:
                            dir = DIR_RIGHT;
                            x++;
                            break;
                        case DIR_RIGHT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_DOWN:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_LEFT:
                            dir = DIR_DOWN;
                            y++;
                            break;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                case GridSquare.KNOT_TURNSW:
                    switch (dir) {
                        case DIR_UP:
                            dir = DIR_LEFT;
                            x--;
                            break;
                        case DIR_RIGHT:
                            dir = DIR_DOWN;
                            y++;
                            break;
                        case DIR_DOWN:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        case DIR_LEFT:
                            result.representation = "Break at " + x + ", " + y;
                            return result;
                        default:
                            result.representation = "Error at " + x + ", " + y;
                            return result;
                    }
                    break;
                default:
                    result.representation = "Error at " + x + ", " + y;
                    return result;
            }
            if ((x == startX) && (y == startY)) {
                done = true;
            }
        }

        result.represent();

        return result;
    }

    public boolean shu() {
        for (int x = 0; x < cols; x++) {
            if (this.squares[x][0].state != GridSquare.KNOT_EMPTY) {
                return false;
            }
        }
        for (int y = 0; y < rows - 1; y++) {
            for (int x = 0; x < cols; x++) {
                squares[x][y].crossing = squares[x][y + 1].crossing;
                squares[x][y].setKnot(squares[x][y + 1].state);
            }
        }
        for (int x = 0; x < cols; x++) {
            squares[x][rows - 1].crossing = null;
            squares[x][rows - 1].setKnot(GridSquare.KNOT_EMPTY);
        }
        return true;
    }

    public boolean shd() {
        for (int x = 0; x < cols; x++) {
            if (this.squares[x][rows - 1].state != GridSquare.KNOT_EMPTY) {
                return false;
            }
        }
        for (int y = rows - 1; y > 0; y--) {
            for (int x = 0; x < cols; x++) {
                squares[x][y].crossing = squares[x][y - 1].crossing;
                squares[x][y].setKnot(squares[x][y - 1].state);
            }
        }
        for (int x = 0; x < cols; x++) {
            squares[x][0].crossing = null;
            squares[x][0].setKnot(GridSquare.KNOT_EMPTY);
        }
        return true;
    }

    public boolean shl() {
        for (int y = 0; y < rows; y++) {
            if (this.squares[0][y].state != GridSquare.KNOT_EMPTY) {
                return false;
            }
        }
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols - 1; x++) {
                squares[x][y].crossing = squares[x + 1][y].crossing;
                squares[x][y].setKnot(squares[x + 1][y].state);
            }
        }
        for (int y = 0; y < rows; y++) {
            squares[cols - 1][y].crossing = null;
            squares[cols - 1][y].setKnot(GridSquare.KNOT_EMPTY);
        }
        return true;
    }

    public boolean shr() {
        for (int y = 0; y < rows; y++) {
            if (this.squares[cols - 1][y].state != GridSquare.KNOT_EMPTY) {
                return false;
            }
        }
        for (int y = 0; y < rows; y++) {
            for (int x = cols - 1; x > 0; x--) {
                squares[x][y].crossing = squares[x - 1][y].crossing;
                squares[x][y].setKnot(squares[x - 1][y].state);
            }
        }
        for (int y = 0; y < rows; y++) {
            squares[0][y].crossing = null;
            squares[0][y].setKnot(GridSquare.KNOT_EMPTY);
        }
        return true;
    }
    
    public void refreshImages() {
        if (parentView.SQUARE_WIDTH != this.squareWidth ||
            parentView.SQUARE_HEIGHT != this.squareHeight) {
            squareWidth = parentView.SQUARE_WIDTH;
            squareHeight = parentView.SQUARE_HEIGHT;
            
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    squares[i][j].setKnot(squares[i][j].state);
                    squares[i][j].width = squareWidth;
                    squares[i][j].height = squareHeight;
                    squares[i][j].body.setSize(squareWidth, squareHeight);
                }
            }
            parentView.gridPanel.setSize(cols * squareWidth, rows * squareHeight);
            parentView.gridPanel.validate();
        } else {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    squares[i][j].setKnot(squares[i][j].state);
                }
            }
        }
    }
}
