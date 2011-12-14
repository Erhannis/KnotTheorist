/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author mewer12
 */
public class PreGrid {

//    public int cols = 0;
//    public int rows = 0;
    public VoidSet<Coord, PreSquare> grid = new VoidSet<Coord, PreSquare>(PreSquare.class);

    public PreGrid() {
        Coord.grid = grid;
    }
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int UP = 8;
    public static final int CCW = 1;
    public static final int CW = 2;

    public int dirX(int dir) {
        if ((dir & RIGHT) != 0) {
            return 1;
        }
        if ((dir & LEFT) != 0) {
            return -1;
        }
        return 0;
    }

    public int dirY(int dir) {
        if ((dir & DOWN) != 0) {
            return 1;
        }
        if ((dir & UP) != 0) {
            return -1;
        }
        return 0;
    }

    public int cw(int dir) {
        return (dir << 1) % 15;
    }

    public int ccw(int dir) {
        return (dir == 1 ? 8 : dir >> 1);
    }

    public int rot(int dir, int rot) {
        if (rot == CCW) {
            return ccw(dir);
        } else {
            return cw(dir);
        }
    }

    public int unrot(int dir, int rot) {
        if (rot == CCW) {
            return cw(dir);
        } else {
            return ccw(dir);
        }
    }

    public int flip(int dir) {
        return ccw(ccw(dir));
    }

    public class SnakeLink {

        public int x1 = 0;
        public int y1 = 0;
        public int x2 = 0;
        public int y2 = 0;
        public int dir = RIGHT;
        public int rot = CCW;
        public int outside = 0;
        public SnakeLink next = null;
        public SnakeLink prev = null;

        public SnakeLink() {
        }

        public SnakeLink(int x1, int y1, int x2, int y2, int dir, int rot, int outside) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.dir = dir;
            this.rot = rot;
            this.outside = outside;
        }
    }
    public JCheckBox boxContinue = new JCheckBox("Blank", true);
    public JCheckBox boxStep = new JCheckBox("Blank", true);
    public JPanel panel = null;
    public ArrayList<SnakeLink> snake = null;
    public static boolean abort = false;

    public void pause(ArrayList<SnakeLink> snake) {
        if (abort) {//abort=true;
            abort = false;
            throw new RuntimeException("Aborted!");
        }
        while (!(boxContinue.isSelected() || boxStep.isSelected())) {
            if (abort) {
                abort = false;
                throw new RuntimeException("Aborted!");
            }
            this.snake = snake;
            panel.paintImmediately(0, 0, 1280, 1024);
            this.snake = null;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(PreGrid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (boxStep != null) {
            boxStep.setSelected(false);
        }
    }

    public void readKnot(Knot knot) {
        grid = new VoidSet<Coord, PreSquare>(PreSquare.class);
        Coord.grid = grid;
        HashMap<HalfCrossing, PreSquare> crossReference = new HashMap<HalfCrossing, PreSquare>();
        int dir = RIGHT;
        int x = 0;
        int y = 0;
        HalfCrossing toCross = null;
        if (knot.halfCrossList.size() > 0) {
            knot.halfCrossList.add(knot.halfCrossList.get(0));
        }
        try {
            for (int i = 0; i < knot.halfCrossList.size(); i++) {
                toCross = knot.halfCrossList.get(i);
                if (crossReference.containsKey(toCross.twin)) {
                    crossReference.put(toCross, crossReference.get(toCross.twin));
                    // AAAAHHHHHHHH SNAAAAKE TIIIIME!
                    ArrayList<SnakeLink> ccwSnake = null;
                    ArrayList<SnakeLink> cwSnake = null;
                    ArrayList<SnakeLink> snake = null;
                    int ccwX = x;
                    int cwX = x;
                    int ccwDir = dir;
                    int ccwY = y;
                    int cwY = y;
                    int cwDir = dir;

                    int rot = CCW;
                    for (int r = 0; r < 2; r++) {
                        if (r == 0) {
                            rot = CCW;
                            snake = new ArrayList<SnakeLink>();
                            ccwSnake = snake;
                            x = ccwX;
                            y = ccwY;
                            dir = ccwDir;
                        } else {
                            rot = CW;
                            snake = new ArrayList<SnakeLink>();
                            cwSnake = snake;
                            x = cwX;
                            y = cwY;
                            dir = cwDir;
                        }
                        int[] xy = snakeStart(x, y, dir, rot);
                        x = xy[0];
                        y = xy[1];
                        dir = rot(dir, rot);
                        boolean done = false;

                        // Add first link
                        snake.add(new SnakeLink(x, y, x + dirX(dir), y + dirY(dir), dir, rot, unrot(dir, rot)));
                        // Move
                        x += dirX(dir);
                        y += dirY(dir);
                        pause(snake);
                        //// Check arrive
                        ////// If trailing snakey-bit borders input side of toCross, done break.
                        if (checkArrive(x, y, dir, rot, toCross)) {
                            done = true;
                        }

                        while (!done) {//grid//snake.clear()
                            if (snake.size() >= 500) {
                                // We've got a problem.
                                if ((snake.size() % 100) < 2) {
                                    System.err.println("Huge snake!");
                                    SnakeLink comp = snake.get(snake.size() - 1);
                                    for (SnakeLink l : snake) {
                                        if (comp != l && comp.x1 == l.x1 && comp.x2 == l.x2 && comp.y1 == l.y1 && comp.y2 == l.y2) {
                                            System.err.println("HUGE SNAKE ABORT!");
                                            JOptionPane.showMessageDialog(null, "Snake loop detected! Aborting! (i=" + i + ")");
                                            abort = true;
                                            throw new RuntimeException("Abort!");
                                        }
                                    }
                                }
                            }
                            // Check change edge/turn/hug/arrive
                            //// Check hug
                            ////// If sandwiched divisible, yes continue
                            if (checkHug(x, y, dir, rot)) {
                                dir = rot(dir, rot);
                            } else if (checkStraight(x, y, dir, rot)) {
                            } else if (checkWide(x, y, dir, rot)) {
                                dir = unrot(dir, rot);
                            } else {
                                //// Double back
                                dir = flip(dir);
                            }
                            // Push trail
                            snake.add(new SnakeLink(x, y, x + dirX(dir), y + dirY(dir), dir, rot, unrot(dir, rot)));
                            // Move
                            x += dirX(dir);
                            y += dirY(dir);
                            //// Check arrive
                            ////// If trailing snakey-bit borders input side of toCross, done break.
                            pause(snake);//crossReference.get(toCross)
                            if (checkArrive(x, y, dir, rot, toCross)) {
                                done = true;
                                break;
                            }
                        }

                        // Probably important to cut off dead end loops.
                        HashSet<SnakeLink> remove = new HashSet<SnakeLink>();
                        for (int j = 0; j < snake.size() - 1; j++) {
                            SnakeLink prior = snake.get(j);
                            if (!remove.contains(prior)) {
                                for (int k = j + 1; k < snake.size(); k++) { //NOTE There may be a bit of haziness for the very end segments.
                                    SnakeLink latter = snake.get(k);
                                    // Check tip incidence
                                    if ((prior.x2 == latter.x2) && (prior.y2 == latter.y2)) {
                                        for (int l = j + 1; l <= k; l++) {
                                            remove.add(snake.get(l));
                                        }
                                    }
                                    // I think that should do it, actually.
//                    // Check link incidence
//                    if ((prior.x1 == latter.x2) && (prior.y1 == latter.y2)
//                            && (prior.x2 == latter.x1) && (prior.y2 == latter.y1)) {
//                        // Loop detected!
//                        if (j != 0) { 
//                            for (int l = j; l <= k; l++) {
//                                remove.add(snake.get(l));
//                            }
//                        } else {
//                            for (int l = j; l < k; l++) {
//                                // Need to leave one next to the starting gate.
//                                remove.add(snake.get(l));
//                            }
//                        }
//                        break;
//                    }
                                }
                            }
                        }
                        snake.removeAll(remove);
                        pause(snake);
                        if (r == 0) {
                            ccwX = x;
                            ccwY = y;
                            ccwDir = dir;
                        } else {
                            cwX = x;
                            cwY = y;
                            cwDir = dir;
                        }
                    }
                    if (ccwSnake.size() <= cwSnake.size()) {
                        snake = ccwSnake;
                        x = ccwX;
                        y = ccwY;
                        dir = ccwDir;
                    } else {
                        snake = cwSnake;
                        x = cwX;
                        y = cwY;
                        dir = cwDir;
                    }

                    // Now make the snake extensible, for grid cleaving.
                    for (int j = 0; j < snake.size(); j++) {
                        if (j == 0) {
                            snake.get(0).prev = null;
                            snake.get(0).next = null;
                            if (snake.size() > 1) {
                                snake.get(0).next = snake.get(1);
                            }
                        } else if (j == snake.size() - 1) {
                            snake.get(j).prev = snake.get(j - 1);
                            snake.get(j).next = null;
                        } else {
                            snake.get(j).prev = snake.get(j - 1);
                            snake.get(j).next = snake.get(j + 1);
                        }
                    }

                    // Make room to expand.
                    boolean changed = true;
                    while (changed) {
                        changed = false;
                        for (int j = 0; j < snake.size(); j++) {
                            SnakeLink l = snake.get(j);
                            if (!checkRoom(l)) {
                                // MAKE room!
                                if ((l.dir == UP) || (l.dir == DOWN)) {
                                    changed = true;
                                    cleaveGrid(l.x1, true, snake);
                                    pause(snake);
                                } else {
                                    changed = true;
                                    cleaveGrid(l.y1, false, snake);
                                    pause(snake);
                                }
                                // I'm not going to break.  ALL links should have room, independently of the others.
                                break;
                            }
                        }
                        if (!changed) {
                            for (int j = 0; j < snake.size() - 1; j++) {
                                SnakeLink l = snake.get(j);
                                SnakeLink ln = snake.get(j + 1);
                                if (rot(l.dir, l.rot) == ln.dir) {
                                    SnakeLink extend = new SnakeLink(l.x1 + dirX(l.dir), l.y1 + dirY(l.dir), l.x2 + dirX(l.dir), l.y2 + dirY(l.dir), l.dir, l.rot, l.outside);
                                    if (!checkRoom(extend)) {
                                        // MAKE room!
                                        if ((extend.dir == UP) || (extend.dir == DOWN)) {
                                            changed = true;
                                            cleaveGrid(extend.x1, true, snake);
                                            pause(snake);
                                        } else {
                                            changed = true;
                                            cleaveGrid(extend.y1, false, snake);
                                            pause(snake);
                                        }
                                        // I'm not going to break.  ALL links should have room, independently of the others.
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // Expand the snake!
                    for (int j = 0; j < snake.size(); j++) {
                        SnakeLink l = snake.get(j);
                        expandLink(l, knot.halfCrossList.get(i - 1), toCross);
                        pause(snake);
                    }
                    for (int j = 0; j < snake.size() - 1; j++) {
                        SnakeLink l = snake.get(j);
                        SnakeLink ln = snake.get(j + 1);
                        if (rot(l.dir, l.rot) == ln.dir) {
                            SnakeLink extend = new SnakeLink(l.x1 + dirX(l.dir), l.y1 + dirY(l.dir), l.x2 + dirX(l.dir), l.y2 + dirY(l.dir), l.dir, l.rot, l.outside);
                            expandLink(extend, knot.halfCrossList.get(i - 1), toCross);
                            pause(snake);
                        }
                    }

                    // Remove square tails
                    HalfCrossing fromCross = knot.halfCrossList.get(i - 1);
                    HashSet<PreSquare> newSquares = new HashSet<PreSquare>();
                    for (PreSquare p : grid.set.values()) {
                        if (p.fromCross == fromCross && p.toCross == toCross) {
                            if (adjacent(p, crossReference.get(fromCross)) && related(p, crossReference.get(fromCross))) {
                            } else if (adjacent(p, crossReference.get(toCross)) && related(p, crossReference.get(toCross))) {
                            } else {
                                newSquares.add(p);
                            }
                        }
                    }
                    changed = (newSquares.size() > 0);
                    while (changed) {
                        changed = false;
                        HashSet<PreSquare> removeSquare = new HashSet<PreSquare>();
                        for (PreSquare p : newSquares) {
                            int count = 0;
                            if (related(p, grid.get(new Coord(p.coord.x + 1, p.coord.y)))) {
                                count++;
                            }
                            if (related(p, grid.get(new Coord(p.coord.x, p.coord.y + 1)))) {
                                count++;
                            }
                            if (related(p, grid.get(new Coord(p.coord.x - 1, p.coord.y)))) {
                                count++;
                            }
                            if (related(p, grid.get(new Coord(p.coord.x, p.coord.y - 1)))) {
                                count++;
                            }
                            if (count < 2) {
                                removeSquare.add(p);
                            }
                        }
                        for (PreSquare p : removeSquare) {
                            p.crossing = null;
                            p.fromCross = null;
                            p.toCross = null;
                            p.isCrossing = false;
                            p.empty = true;
                            changed = true;
                            newSquares.remove(p);
                            pause(snake);
                        }
                    }

                    dir = toCross.dir;
                    Coord pos = crossReference.get(toCross).coord;
                    x = pos.getX() + dirX(dir);
                    y = pos.getY() + dirY(dir);
                } else {
                    if (i != 0) {
                        // Add extra pipe.
                        Coord crd = new Coord(x, y);
                        PreSquare p = grid.get(crd);
                        if (!p.empty) {
                            switch (dir) {
                                case RIGHT:
                                    cleaveGrid(x, true, null);
                                    crd = new Coord(x, y);
                                    p = grid.get(crd);
                                    if (!p.empty) {
                                        System.err.println("Not empty");
                                    }
                                    break;
                                case DOWN:
                                    cleaveGrid(y, false, null);
                                    crd = new Coord(x, y);
                                    p = grid.get(crd);
                                    if (!p.empty) {
                                        System.err.println("Not empty");
                                    }
                                    break;
                                case LEFT:
                                    cleaveGrid(x + 1, true, null);
                                    x++;
                                    crd = new Coord(x, y);
                                    p = grid.get(crd);
                                    if (!p.empty) {
                                        System.err.println("Not empty");
                                    }
                                    break;
                                case UP:
                                    cleaveGrid(y + 1, false, null);
                                    y++;
                                    crd = new Coord(x, y);
                                    p = grid.get(crd);
                                    if (!p.empty) {
                                        System.err.println("Not empty");
                                    }
                                    break;
                            }
                        }
                        p.coord = grid.getKey(p);
                        if (p.coord == null) {
                            System.err.println("Didn't fetch!");
                        }
                        p.empty = false;
                        p.fromCross = knot.halfCrossList.get(i - 1);
                        p.toCross = knot.halfCrossList.get(i);
                        x += dirX(dir);
                        y += dirY(dir);
                        pause(null);
                    } else {
                        dir = toCross.dir;
                    }
                    while (toCross.dir != dir) {
                        toCross.dir = cw(toCross.dir);
                        toCross.twin.dir = cw(toCross.twin.dir);
                    }
                    Coord crd = new Coord(x, y);
                    PreSquare p = grid.get(crd);
                    if (!p.empty) {
                        switch (dir) {
                            case RIGHT:
                                cleaveGrid(x, true, null);
                                crd = new Coord(x, y);
                                p = grid.get(crd);
                                if (!p.empty) {
                                    System.err.println("Not empty");
                                }
                                break;
                            case DOWN:
                                cleaveGrid(y, false, null);
                                crd = new Coord(x, y);
                                p = grid.get(crd);
                                if (!p.empty) {
                                    System.err.println("Not empty");
                                }
                                break;
                            case LEFT:
                                cleaveGrid(x + 1, true, null);
                                x++;
                                crd = new Coord(x, y);
                                p = grid.get(crd);
                                if (!p.empty) {
                                    System.err.println("Not empty");
                                }
                                break;
                            case UP:
                                cleaveGrid(y + 1, false, null);
                                y++;
                                crd = new Coord(x, y);
                                p = grid.get(crd);
                                if (!p.empty) {
                                    System.err.println("Not empty");
                                }
                                break;
                        }
                    }
                    p.coord = grid.getKey(p);
                    if (p.coord == null) {
                        System.err.println("Didn't fetch!");
                    }
                    p.empty = false;
                    p.isCrossing = true;
                    p.crossing = toCross;
                    crossReference.put(toCross, p);
                    x += dirX(dir);
                    y += dirY(dir);
                    pause(null);
                }
            }
        } finally {
            if (knot.halfCrossList.size() > 0) {
                knot.halfCrossList.remove(knot.halfCrossList.size() - 1);
            }
        }

//        // Now snake back.  Maybe we can just go ccw for now?  Is possible?
//        // So, squares are considered to be lower right of their coord.
//        ArrayList<SnakeLink> snake = new ArrayList<SnakeLink>();
//        int dir = RIGHT;
//        HalfCrossing fromCross = grid.get(new Coord(x, y)).crossing;
//        HalfCrossing toCross = c;
//        if (grid.get(new Coord(x, y)).crossing.lr == HalfCrossing.CROSS_RIGHT) {
//            // Start on right hand side: bottom, here.
//        } else {
//            // Start on left hand side: top, here.
//            dir = LEFT;
//            x++;
//            snake.add(new SnakeLink(x, y, x + dirX(dir), y + dirY(dir), dir, CCW, cw(dir)));
//            x += dirX(dir);
//            y += dirY(dir);
//            // Check for impending collisions.
//            if (grid.get(new Coord(x - 1, y - 1)).coord != null) {
//                if ((grid.get(new Coord(x - 1, y - 1)).fromCross != fromCross) || (grid.get(new Coord(x - 1, y - 1)).toCross != toCross)) {
//                    // Turn right!
//                    dir = cw(dir);
//                }
//            } else if (grid.get(new Coord(x - 1, y)).coord == null) {
//                // Hug the left turn.
//                dir = ccw(dir);
//            }
//        }


    }

    public boolean adjacent(PreSquare a, PreSquare b) {
        if (a.coord == null || b.coord == null) {
            return false;
        }
        if (Math.abs(a.coord.x - b.coord.x) == 1) {
            if (a.coord.y == b.coord.y) {
                return true;
            }
            return false;
        } else if (Math.abs(a.coord.y - b.coord.y) == 1) {
            if (a.coord.x == b.coord.x) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public int[] snakeStart(int x, int y, int dir, int rot) {
        int[] result = new int[2];
        switch (dir) {
            case RIGHT:
                if (rot == CCW) {
                    result[0] = x;
                    result[1] = y + 1;
                } else {
                    result[0] = x;
                    result[1] = y;
                }
                break;
            case DOWN:
                if (rot == CCW) {
                    result[0] = x;
                    result[1] = y;
                } else {
                    result[0] = x + 1;
                    result[1] = y;
                }
                break;
            case LEFT:
                if (rot == CCW) {
                    result[0] = x + 1;
                    result[1] = y;
                } else {
                    result[0] = x + 1;
                    result[1] = y + 1;
                }
                break;
            case UP:
                if (rot == CCW) {
                    result[0] = x + 1;
                    result[1] = y + 1;
                } else {
                    result[0] = x;
                    result[1] = y + 1;
                }
                break;
        }
        return result;
    }

    public void expandLink(SnakeLink l, HalfCrossing fromCross, HalfCrossing toCross) {
        switch (l.dir) {
            case RIGHT:
                if (l.rot == CCW) {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x1, l.y1);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                } else {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x1, l.y1 - 1);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                }
            case DOWN:
                if (l.rot == CCW) {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x1 - 1, l.y1);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                } else {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x1, l.y1);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                }
            case LEFT:
                if (l.rot == CCW) {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x2, l.y2 - 1);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                } else {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x2, l.y2);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                }
            case UP:
                if (l.rot == CCW) {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x2, l.y2);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                } else {
                    PreSquare p = new PreSquare();
                    p.empty = false;
                    p.coord = new Coord(l.x2 - 1, l.y2);
                    p.fromCross = fromCross;
                    p.toCross = toCross;
                    grid.put(p.coord, p);
                    p.coord = grid.getKey(p);
                    return;
                }
        }
    }

    public boolean checkRoom(SnakeLink l) {
        switch (l.dir) {
            case RIGHT:
                if (l.rot == CCW) {
                    if (grid.get(new Coord(l.x1, l.y1)).empty) {
                        return true;
                    }
                    return false;
                } else {
                    if (grid.get(new Coord(l.x1, l.y1 - 1)).empty) {
                        return true;
                    }
                    return false;
                }
            case DOWN:
                if (l.rot == CCW) {
                    if (grid.get(new Coord(l.x1 - 1, l.y1)).empty) {
                        return true;
                    }
                    return false;
                } else {
                    if (grid.get(new Coord(l.x1, l.y1)).empty) {
                        return true;
                    }
                    return false;
                }
            case LEFT:
                if (l.rot == CCW) {
                    if (grid.get(new Coord(l.x2, l.y2 - 1)).empty) {
                        return true;
                    }
                    return false;
                } else {
                    if (grid.get(new Coord(l.x2, l.y2)).empty) {
                        return true;
                    }
                    return false;
                }
            case UP:
                if (l.rot == CCW) {
                    if (grid.get(new Coord(l.x2, l.y2)).empty) {
                        return true;
                    }
                    return false;
                } else {
                    if (grid.get(new Coord(l.x2 - 1, l.y2)).empty) {
                        return true;
                    }
                    return false;
                }
        }
        return false;
    }

    public void cleaveGrid(int coord, boolean verticalLine, ArrayList<SnakeLink> snake) {
        boolean vert = verticalLine;//grid.get(new Coord(1,6)).empty
        Collection<PreSquare> squares = grid.set.values();
        Class<HashSet<PreSquare>> bob = (Class<HashSet<PreSquare>>) (new HashSet<PreSquare>()).getClass();
        VoidSet<Integer, HashSet<PreSquare>> lines = new VoidSet<Integer, HashSet<PreSquare>>(bob); // Man, Java can be stupid.
        for (PreSquare p : squares) {
            if (p.coord != null) {
                if (vert) {
                    if (p.coord.getX() >= coord) {
                        lines.get(p.coord.getX()).add(p);
                    }
                } else {//(vert ? p.coord.x : p.coord.y)
                    if (p.coord.getY() >= coord) {
                        lines.get(p.coord.getY()).add(p);
                    }
                }
            }
        }
        ArrayList<Integer> lineNums = new ArrayList<Integer>();
        lineNums.addAll(lines.set.keySet());
        boolean found = false;
        int minC = 0;
        int maxC = 0;
        // Get spread.
        for (int i : lineNums) {
            if (!found) {
                minC = i;
                maxC = i;
                found = true;
            } else if (i < minC) {
                minC = i;
            } else if (i > maxC) {
                maxC = i;
            }
        }
        // Shift row by row.
        if (vert) {
            for (int i = maxC; i > minC; i--) {
                for (PreSquare p : lines.get(i)) {
                    p.coord.setX(p.coord.getX() + 1);
                }
            }
        } else {
            for (int i = maxC; i > minC; i--) {
                for (PreSquare p : lines.get(i)) {
                    p.coord.setY(p.coord.getY() + 1);
                    //pause(null);
                }
            }
        }
        // Now shift the last row and grow connections.
//        int index = 0;
        for (PreSquare p : lines.get(minC)) {//grid.get(new Coord(1,6)).empty //grid.set.entrySet()
//            index++;
//            if (index == 2) {
//                System.err.println("Blah!" + index);
//            }
            if (vert) {
                p.coord.setX(p.coord.getX() + 1);
            } else {
                p.coord.setY(p.coord.getY() + 1);
            }
            PreSquare left;
            if (vert) {
                left = grid.get(new Coord(p.coord.getX() - 2, p.coord.getY()));
            } else {
                left = grid.get(new Coord(p.coord.getX(), p.coord.getY() - 2));
            }
            if (!left.empty) {
                int breakX = left.coord.x;
                int breakY = left.coord.y;
                breakX = breakX;
                if (p.isCrossing) {
                    if (left.isCrossing) {
                        // UGH.  That's a MESS.  Gonna put a pipe between all connected crossings to prevent it.
                        // So, do nothing.
//                            if ((left.crossing.dir == RIGHT) || (left.crossing.dir == LEFT)) {
//                                // left
//                                if ((p.crossing.dir == RIGHT) || (p.crossing.dir == LEFT)) {
//                                    
//                                } else {
//                                    
//                                }
//                            } else {
//                                // left.twin
//                                if ((p.crossing.dir == RIGHT) || (p.crossing.dir == LEFT)) {
//                                    
//                                } else {
//                                    
//                                }
//                            }
                    } else {
                        if (vert) {
                            if ((left.fromCross == p.crossing && p.crossing.dir == LEFT)
                                    || (left.toCross == p.crossing && p.crossing.dir == RIGHT)
                                    || (left.fromCross == p.crossing.twin && p.crossing.twin.dir == LEFT)
                                    || (left.toCross == p.crossing.twin && p.crossing.twin.dir == RIGHT)) {
                                // Put in a new pipe chunk.
                                PreSquare bucket;
                                bucket = grid.get(new Coord(p.coord.getX() - 1, p.coord.getY()));
                                bucket.coord = grid.getKey(bucket);
                                bucket.empty = false;
                                bucket.fromCross = left.fromCross;
                                bucket.toCross = left.toCross;
                            }
                        } else {
                            if ((left.fromCross == p.crossing && p.crossing.dir == UP)
                                    || (left.toCross == p.crossing && p.crossing.dir == DOWN)
                                    || (left.fromCross == p.crossing.twin && p.crossing.twin.dir == UP)
                                    || (left.toCross == p.crossing.twin && p.crossing.twin.dir == DOWN)) {
                                // Put in a new pipe chunk.
                                PreSquare bucket;
                                bucket = grid.get(new Coord(p.coord.getX(), p.coord.getY() - 1));
                                bucket.coord = grid.getKey(bucket);
                                bucket.empty = false;
                                bucket.fromCross = left.fromCross;
                                bucket.toCross = left.toCross;
                            }
                        }
                    }
                } else {
                    if (left.isCrossing) {
                        if (vert) {
                            if ((p.fromCross == left.crossing && left.crossing.dir == RIGHT)
                                    || (p.toCross == left.crossing && left.crossing.dir == LEFT)
                                    || (p.fromCross == left.crossing.twin && left.crossing.twin.dir == RIGHT)
                                    || (p.toCross == left.crossing.twin && left.crossing.twin.dir == LEFT)) {
                                // Put in a new pipe chunk.
                                PreSquare bucket;
                                bucket = grid.get(new Coord(p.coord.getX() - 1, p.coord.getY()));
                                bucket.coord = grid.getKey(bucket);
                                bucket.empty = false;
                                bucket.fromCross = p.fromCross;
                                bucket.toCross = p.toCross;
                            }
                        } else {
                            if ((p.fromCross == left.crossing && left.crossing.dir == DOWN)
                                    || (p.toCross == left.crossing && left.crossing.dir == UP)
                                    || (p.fromCross == left.crossing.twin && left.crossing.twin.dir == DOWN)
                                    || (p.toCross == left.crossing.twin && left.crossing.twin.dir == UP)) {
                                // Put in a new pipe chunk.
                                PreSquare bucket;
                                bucket = grid.get(new Coord(p.coord.getX(), p.coord.getY() - 1));
                                bucket.coord = grid.getKey(bucket);
                                bucket.empty = false;
                                bucket.fromCross = p.fromCross;
                                bucket.toCross = p.toCross;
                            }
                        }
                    } else {
                        if ((p.fromCross == left.fromCross) && (p.toCross == left.toCross)) {
                            // Put in a new pipe chunk.
                            PreSquare bucket;
                            if (vert) {
                                bucket = grid.get(new Coord(p.coord.getX() - 1, p.coord.getY()));
                                bucket.coord = grid.getKey(bucket);
                            } else {
                                bucket = grid.get(new Coord(p.coord.getX(), p.coord.getY() - 1));
                                bucket.coord = grid.getKey(bucket);
                            }
                            bucket.empty = false;
                            bucket.fromCross = p.fromCross;
                            bucket.toCross = p.toCross;
                        }
                    }
                }
            }
        }
//            if (!grid.get(new Coord(1,6)).empty) {
//                System.err.println("Blah!" + index);
//            }

        if (snake != null) {
            // Now cleave snake.
            if (vert) {
                for (SnakeLink l : snake) {
                    if ((l.x1 > coord) || (l.x2 > coord)) {
                        l.x1++;
                        l.x2++;
                    } else if ((l.x1 == coord) && (l.x2 == coord) && (l.outside == LEFT)) {
                        l.x1++;
                        l.x2++;
                    }
                }
            } else {
                for (SnakeLink l : snake) {
                    if ((l.y1 > coord) || (l.y2 > coord)) {
                        l.y1++;
                        l.y2++;
                    } else if ((l.y1 == coord) && (l.y2 == coord) && (l.outside == UP)) {
                        l.y1++;
                        l.y2++;
                    }
                }
            }
            // Knit ends.
            if (vert) {
                for (SnakeLink l : snake) {
                    if (l.next != null) {
                        if (l.x2 < l.next.x1) {
                            // Insert connector.
                            SnakeLink bucket = new SnakeLink(l.x2, l.y2, l.next.x1, l.next.y1, RIGHT, l.rot, unrot(RIGHT, l.rot));
                            bucket.prev = l;
                            bucket.next = l.next;
                            l.next.prev = bucket;
                            l.next = bucket;
                        } else if (l.x2 > l.next.x1) {
                            // Insert connector.
                            SnakeLink bucket = new SnakeLink(l.x2, l.y2, l.next.x1, l.next.y1, LEFT, l.rot, unrot(LEFT, l.rot));
                            bucket.prev = l;
                            bucket.next = l.next;
                            l.next.prev = bucket;
                            l.next = bucket;
                        }
                    }
                }
            } else {
                for (SnakeLink l : snake) {
                    if (l.next != null) {
                        if (l.y2 < l.next.y1) {
                            // Insert connector.
                            SnakeLink bucket = new SnakeLink(l.x2, l.y2, l.next.x1, l.next.y1, DOWN, l.rot, unrot(DOWN, l.rot));
                            bucket.prev = l;
                            bucket.next = l.next;
                            l.next.prev = bucket;
                            l.next = bucket;
                        } else if (l.y2 > l.next.y1) {
                            // Insert connector.
                            SnakeLink bucket = new SnakeLink(l.x2, l.y2, l.next.x1, l.next.y1, UP, l.rot, unrot(UP, l.rot));
                            bucket.prev = l;
                            bucket.next = l.next;
                            l.next.prev = bucket;
                            l.next = bucket;
                        }
                    }
                }
            }
            // Regather.
            if (snake.size() > 0) {
                SnakeLink current = snake.get(0);
                snake.clear();
                snake.add(current);
                while (current.next != null) {
                    current = current.next;
                    snake.add(current);
                }
            }
        }
        // Done!
    }

    public boolean checkArrive(int x, int y, int dir, int rot, HalfCrossing toCross) {
        PreSquare[] borders = getBorders(x - dirX(dir), y - dirY(dir), dir);
        if (borders[0].crossing == toCross) {
            // It's clockwise from the link.
            // Make sure it's on the interior edge.
            if (rot == CW) {
                // Check if we're on the input side.
                if (cw(dir) == borders[0].crossing.dir) {
                    return true;
                }
            }
        } else if (borders[0].crossing == toCross.twin) {
            // It's clockwise from the link.
            if (rot == CW) {
                if (cw(dir) == borders[0].crossing.twin.dir) {
                    return true;
                }
            }
        } else if (borders[1].crossing == toCross) {
            // It's counterclockwise from the link.
            if (rot == CCW) {
                if (ccw(dir) == borders[1].crossing.dir) {
                    return true;
                }
            }
        } else if (borders[1].crossing == toCross.twin) {
            // It's counterclockwise from the link.
            if (rot == CCW) {
                if (ccw(dir) == borders[1].crossing.twin.dir) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkHug(int x, int y, int dir, int rot) {
        PreSquare[] borders = getBorders(x, y, rot(dir, rot));
        return divisible(borders[0], borders[1]);
    }

    public boolean checkStraight(int x, int y, int dir, int rot) {
        PreSquare[] borders = getBorders(x, y, dir);
        return divisible(borders[0], borders[1]);
    }

    public boolean checkWide(int x, int y, int dir, int rot) {
        PreSquare[] borders = getBorders(x, y, unrot(dir, rot));
        return divisible(borders[0], borders[1]);
    }

    public PreSquare[] getBorders(int x, int y, int dir) {
        PreSquare[] result = new PreSquare[2];
        switch (dir) {
            case RIGHT:
                result[0] = grid.get(new Coord(x, y));
                result[1] = grid.get(new Coord(x, y - 1));
                break;
            case DOWN:
                result[0] = grid.get(new Coord(x - 1, y));
                result[1] = grid.get(new Coord(x, y));
                break;
            case LEFT:
                result[0] = grid.get(new Coord(x - 1, y - 1));
                result[1] = grid.get(new Coord(x - 1, y));
                break;
            case UP:
                result[0] = grid.get(new Coord(x, y - 1));
                result[1] = grid.get(new Coord(x - 1, y - 1));
                break;
        }
        return result;
    }

    public boolean divisible(PreSquare a, PreSquare b) {
        if ((a == null) || (a.empty) || (b == null) || (b.empty)) {
            return true;
        }
        if ((!a.isCrossing) && (!b.isCrossing)) {
            if ((a.fromCross == b.fromCross) && (a.toCross == b.toCross)) {
                return false;
            }
            return true;
        } else if (a.isCrossing) {
            if (b.isCrossing) {
                // I think we're avoiding this one with liberal use of pipes.
                return true;
            } else {
                if ((b.fromCross == a.crossing && (dirFromTo(a.coord, b.coord) == a.crossing.dir))
                        || (b.fromCross == a.crossing.twin && (dirFromTo(a.coord, b.coord) == a.crossing.twin.dir))
                        || (b.toCross == a.crossing && (dirFromTo(b.coord, a.coord) == a.crossing.dir))
                        || (b.toCross == a.crossing.twin && (dirFromTo(b.coord, a.coord) == a.crossing.twin.dir))) {
                    return false;
                }
                return true;
            }
        } else if (b.isCrossing) {
            if ((a.fromCross == b.crossing && (dirFromTo(b.coord, a.coord) == b.crossing.dir))
                    || (a.fromCross == b.crossing.twin && (dirFromTo(b.coord, a.coord) == b.crossing.twin.dir))
                    || (a.toCross == b.crossing && (dirFromTo(a.coord, b.coord) == b.crossing.dir))
                    || (a.toCross == b.crossing.twin && (dirFromTo(a.coord, b.coord) == b.crossing.twin.dir))) {
                return false;
            }
            return true;
        }
        return true;
    }

    public int dirFromTo(Coord fromPos, Coord toPos) {
        if (toPos.x > fromPos.x) {
            return RIGHT;
        } else if (toPos.x < fromPos.x) {
            return LEFT;
        } else if (toPos.y < fromPos.y) {
            return UP;
        }
        return DOWN;
    }

    public KnotGrid toKnotGrid(Icon[] btnColors, Random rng, KnotTheoristView parentView, int squareWidth, int squareHeight) {
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        boolean oneFound = false;
        Collection<PreSquare> squares = grid.set.values();
        for (PreSquare p : squares) {
            if (!oneFound) {
                minX = p.coord.getX();
                maxX = p.coord.getX();
                minY = p.coord.getY();
                maxY = p.coord.getY();
                oneFound = true;
            } else {
                if (p.coord.getX() < minX) {
                    minX = p.coord.getX();
                } else if (p.coord.getX() > maxX) {
                    maxX = p.coord.getX();
                }
                if (p.coord.getY() < minY) {
                    minY = p.coord.getY();
                } else if (p.coord.getY() > maxY) {
                    maxY = p.coord.getY();
                }
            }
        }

        KnotGrid result = new KnotGrid(maxX - minX + 1, maxY - minY + 1);
        result.init(btnColors, rng, parentView, squareWidth, squareHeight);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                int newX = x - minX;
                int newY = y - minY;
                PreSquare p = grid.get(new Coord(x, y));
                if (!p.empty) {
                    if (p.isCrossing) {
                        if (p.crossing.tb == HalfCrossing.CROSS_TOP) {
                            if ((p.crossing.dir == HalfCrossing.DIR_LEFT) || (p.crossing.dir == HalfCrossing.DIR_RIGHT)) {
                                result.squares[newX][newY].setKnot(GridSquare.KNOT_CROSSEW);
                            } else {
                                result.squares[newX][newY].setKnot(GridSquare.KNOT_CROSSNS);
                            }
                        } else {
                            if ((p.crossing.dir == HalfCrossing.DIR_LEFT) || (p.crossing.dir == HalfCrossing.DIR_RIGHT)) {
                                result.squares[newX][newY].setKnot(GridSquare.KNOT_CROSSNS);
                            } else {
                                result.squares[newX][newY].setKnot(GridSquare.KNOT_CROSSEW);
                            }
                        }
                    } else {
                        int dirs = 0;
                        if (related(p, grid.get(new Coord(x + 1, y)))) {
                            dirs += RIGHT;
                        }
                        if (related(p, grid.get(new Coord(x, y + 1)))) {
                            dirs += DOWN;
                        }
                        if (related(p, grid.get(new Coord(x - 1, y)))) {
                            dirs += LEFT;
                        }
                        if (related(p, grid.get(new Coord(x, y - 1)))) {
                            dirs += UP;
                        }
                        result.squares[newX][newY].setKnot(dirsToType(dirs));
                    }
                }
            }
        }
        return result;
    }

    public int dirsToType(int dirs) {
        if ((dirs & RIGHT) != 0) {
            if ((dirs & DOWN) != 0) {
                return GridSquare.KNOT_TURNSE;
            } else {
                if ((dirs & LEFT) != 0) {
                    return GridSquare.KNOT_EW;
                } else {
                    return GridSquare.KNOT_TURNNE;
                }
            }
        } else {
            if ((dirs & DOWN) != 0) {
                if ((dirs & LEFT) != 0) {
                    return GridSquare.KNOT_TURNSW;
                } else {
                    return GridSquare.KNOT_NS;
                }
            } else {
                return GridSquare.KNOT_TURNNW;
            }
        }
    }

    public boolean related(PreSquare a, PreSquare b) {
        if (a.empty || b.empty) {
            return false;
        }
        if (a.isCrossing) {
            if (b.isCrossing) {
                return false;
            }
            if ((b.fromCross == a.crossing && (dirFromTo(a.coord, b.coord) == a.crossing.dir))
                    || (b.fromCross == a.crossing.twin && (dirFromTo(a.coord, b.coord) == a.crossing.twin.dir))
                    || (b.toCross == a.crossing && (dirFromTo(b.coord, a.coord) == a.crossing.dir))
                    || (b.toCross == a.crossing.twin && (dirFromTo(b.coord, a.coord) == a.crossing.twin.dir))) {
                return true;
            }
            return false;
        } else {
            if (b.isCrossing) {
                if ((a.fromCross == b.crossing && (dirFromTo(b.coord, a.coord) == b.crossing.dir))
                        || (a.fromCross == b.crossing.twin && (dirFromTo(b.coord, a.coord) == b.crossing.twin.dir))
                        || (a.toCross == b.crossing && (dirFromTo(a.coord, b.coord) == b.crossing.dir))
                        || (a.toCross == b.crossing.twin && (dirFromTo(a.coord, b.coord) == b.crossing.twin.dir))) {
                    return true;
                }
                return false;
            } else {
                if (a.fromCross == b.fromCross && a.toCross == b.toCross) {
                    return true;
                }
                return false;
            }
        }
    }
}
