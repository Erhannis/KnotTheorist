/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author CORNELL-COLLEGE\mewer12
 */
public class GridSquare {
    public Crossing crossing = null;

    public static final int KNOT_EMPTY =        0;//    00000;
    public static final int KNOTTYPE_STRAIGHT = 4;//    00100;
    public static final int KNOT_EW =           4;//    00100;
    public static final int KNOT_NS =           5;//    00101;
    public static final int KNOTTYPE_CROSS =    8;//    01000;
    public static final int KNOT_CROSSEW =      8;//    01000;
    public static final int KNOT_CROSSNS =      9;//    01001;
    public static final int KNOTTYPE_TURN =     16;//   10000;
    public static final int KNOT_TURNNE =       16;//   10000;
    public static final int KNOT_TURNNW =       17;//   10001;
    public static final int KNOT_TURNSE =       18;//   10010;
    public static final int KNOT_TURNSW =       19;//   10011;
    public int height = 0;
    public int width = 0;

    public static int knotToImg(int knot) {
        switch (knot) {
            case KNOT_EMPTY:
                return 0;
            case KNOT_CROSSEW:
                return 1;
            case KNOT_CROSSNS:
                return 2;
            case KNOT_EW:
                return 3;
            case KNOT_NS:
                return 4;
            case KNOT_TURNNE:
                return 5;
            case KNOT_TURNNW:
                return 6;
            case KNOT_TURNSE:
                return 7;
            case KNOT_TURNSW:
                return 8;
        }
        return -1;
    }

    ;
    public int state = 0;
    public JLabel body;
    public Icon[] knotIcons = null;
    public KnotGrid parentGrid = null;
    private static final int NONE = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;
    private static final int LEFT = 4;
    private int mouseEnteredSide = NONE;

    public String sideToString(int side) {
        switch (side) {
            case NONE:
                return "None";
            case TOP:
                return "Top";
            case RIGHT:
                return "Right";
            case BOTTOM:
                return "Bottom";
            case LEFT:
                return "Left";
            default:
                return "<Invalid Side>";
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public GridSquare(Icon[] knotIcons, JLabel body, final KnotGrid parentGrid, int width, final int height) {
        this.state = KNOT_EMPTY;
        this.knotIcons = knotIcons;
        this.body = body;
        this.width = width;
        this.height = height;
        body.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.isShiftDown()) {
                    if (state == KNOT_CROSSNS) {
                        setKnot(KNOT_CROSSEW);
                    } else if (state == KNOT_CROSSEW) {
                        setKnot(KNOT_CROSSNS);
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) {
                    setKnot(KNOT_EMPTY);
                    mouseEnteredSide = NONE;
                    return;
                }
                mouseEnteredSide = 0;
                parentGrid.mouseFrom = e.getLocationOnScreen();
            }

            public void mouseReleased(MouseEvent e) {
                // Remove entered?
            }

            public void mouseEntered(MouseEvent e) {
                if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    if (e.isControlDown()) {
                        setKnot(KNOT_EMPTY);
                        mouseEnteredSide = NONE;
                        return;
                    }
                    Point mouseWas = new Point(parentGrid.mouseFrom.x - e.getXOnScreen() + e.getX(), parentGrid.mouseFrom.y - e.getYOnScreen() + e.getY());
                    System.out.println(mouseWas);
                    parentGrid.mouseFrom = e.getLocationOnScreen();
                    if (mouseWas.x < 0) {
                        // From off left
                        if (mouseWas.y < 0) {
                            // From top left
                        } else if (mouseWas.y >= getHeight()) {
                            // From bottom left
                        } else {
                            // Just left
                            mouseEnteredSide = LEFT;
                        }
                    } else if (mouseWas.x >= getWidth()) {
                        // From off right
                        if (mouseWas.y < 0) {
                            // From top right
                        } else if (mouseWas.y >= getHeight()) {
                            // From bottom right
                        } else {
                            // Just right
                            mouseEnteredSide = RIGHT;
                        }
                    } else {
                        // Neither left nor right
                        if (mouseWas.y < 0) {
                            // From off top
                            mouseEnteredSide = TOP;
                        } else if (mouseWas.y >= getHeight()) {
                            // From off bottom
                            mouseEnteredSide = BOTTOM;
                        } else {
                            // Something's wrong; it entered from INSIDE the box.
                        }
                    }
                    switch (state) {
                        case KNOT_EMPTY:
                            //System.out.println("Enter:" + e.getX() + ", " + e.getY());
                            System.out.println(sideToString(mouseEnteredSide));
                            break;
                        case KNOT_CROSSEW:
                        case KNOT_CROSSNS:
                        case KNOT_EW:
                        case KNOT_NS:
                        case KNOT_TURNNE:
                        case KNOT_TURNNW:
                        case KNOT_TURNSE:
                        case KNOT_TURNSW:
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
                if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    crossing = null;
                    //Point mouseWas = new Point(parentGrid.mouseFrom.x - e.getXOnScreen() + e.getX(), parentGrid.mouseFrom.y - e.getYOnScreen() + e.getY());
                    //parentGrid.mouseFrom = e.getLocationOnScreen();
                    int mouseExitedSide = NONE;
                    if (e.getX() < 0) {
                        // To off left
                        if (e.getY() < 0) {
                            // To top left
                        } else if (e.getY() >= getHeight()) {
                            // To bottom left
                        } else {
                            // Just left
                            mouseExitedSide = LEFT;
                        }
                    } else if (e.getX() >= getWidth()) {
                        // To off right
                        if (e.getY() < 0) {
                            // To top right
                        } else if (e.getY() >= getHeight()) {
                            // To bottom right
                        } else {
                            // Just right
                            mouseExitedSide = RIGHT;
                        }
                    } else {
                        // Neither left nor right
                        if (e.getY() < 0) {
                            // To off top
                            mouseExitedSide = TOP;
                        } else if (e.getY() >= getHeight()) {
                            // To off bottom
                            mouseExitedSide = BOTTOM;
                        } else {
                            // Something's wrong; it exited to INSIDE the box.
                        }
                    }
                    System.out.println(e.getPoint());
                    System.out.println(sideToString(mouseExitedSide));
                    System.out.println();
                    switch (state) {
                        case KNOT_EMPTY:
                            //System.out.println("Leave:" + e.getX() + ", " + e.getY());
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_CROSSEW:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_CROSSNS:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_EW:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            if (e.isShiftDown()) {
                                                setKnot(KNOT_CROSSEW);
                                            } else {
                                                setKnot(KNOT_CROSSNS);
                                            }
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            if (e.isShiftDown()) {
                                                setKnot(KNOT_CROSSEW);
                                            } else {
                                                setKnot(KNOT_CROSSNS);
                                            }
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_NS:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            if (e.isShiftDown()) {
                                                setKnot(KNOT_CROSSNS);
                                            } else {
                                                setKnot(KNOT_CROSSEW);
                                            }
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            if (e.isShiftDown()) {
                                                setKnot(KNOT_CROSSNS);
                                            } else {
                                                setKnot(KNOT_CROSSEW);
                                            }
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_TURNNE:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_TURNNW:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_TURNSE:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                            break;
                        case KNOT_TURNSW:
                            switch (mouseEnteredSide) {
                                case NONE:
                                    break;
                                case TOP:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_NS);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case RIGHT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNE);
                                            break;
                                        case RIGHT:
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_EW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case BOTTOM:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_NS);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_TURNSE);
                                            break;
                                        case BOTTOM:
                                            break;
                                        case LEFT:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                case LEFT:
                                    switch (mouseExitedSide) {
                                        case NONE:
                                            break;
                                        case TOP:
                                            setKnot(KNOT_TURNNW);
                                            break;
                                        case RIGHT:
                                            setKnot(KNOT_EW);
                                            break;
                                        case BOTTOM:
                                            setKnot(KNOT_TURNSW);
                                            break;
                                        case LEFT:
                                            break;
                                        default:
                                            // Error!
                                            break;
                                    }
                                    break;
                                default:
                                    // Error!
                                    break;
                            }
                        default:
                            // Error!
                            break;
                    }
                }
            }
        });
        this.parentGrid = parentGrid;
    }

    public void setKnot(int knot) {
        this.body.setIcon(knotIcons[knotToImg(knot)]);
        this.state = knot;
    }
    
    public void flip() {
        if (state == KNOT_CROSSEW) {
            setKnot(KNOT_CROSSNS);
        } else if (state == KNOT_CROSSNS) {
            setKnot(KNOT_CROSSEW);
        }
    }
}
