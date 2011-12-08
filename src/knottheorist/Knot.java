/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 *
 * @author CORNELL-COLLEGE\mewer12
 */
public class Knot {

    public ArrayList<HalfCrossing> halfCrossList;
    //public ArrayList<Crossing> crossList;
    public String representation = null;

    public Knot() {
        halfCrossList = new ArrayList<HalfCrossing>();
        //crossList = new ArrayList<Crossing>();
    }
    public static final int REP_ATL = 0;
    public static final int REP_BKa = 1;
    public int representMode = REP_BKa;
    public String delimiter = " ";

    public void represent() {
        String result = "";
        switch (representMode) {
            case REP_ATL:
                for (int i = 0; i < halfCrossList.size(); i++) {
                    result += halfCrossList.get(i).name;
                    if (halfCrossList.get(i).tb == HalfCrossing.CROSS_TOP) {
                        result += "t";
                    } else if (halfCrossList.get(i).tb == HalfCrossing.CROSS_BOTTOM) {
                        result += "b";
                    } else {
                        result += "e";
                    }
                    if (halfCrossList.get(i).lr == HalfCrossing.CROSS_LEFT) {
                        result += "l";
                    } else if (halfCrossList.get(i).lr == HalfCrossing.CROSS_RIGHT) {
                        result += "r";
                    } else {
                        result += "e";
                    }
                }
                break;
            case REP_BKa:
                for (int i = 0; i < halfCrossList.size(); i++) {
                    if (halfCrossList.get(i).lr == HalfCrossing.CROSS_LEFT) {
                        result += "<";
                        if (halfCrossList.get(i).tb == HalfCrossing.CROSS_TOP) {
                            result += halfCrossList.get(i).name.toUpperCase();
                        } else if (halfCrossList.get(i).tb == HalfCrossing.CROSS_BOTTOM) {
                            result += halfCrossList.get(i).name.toLowerCase();
                        } else {
                            result += "?" + delimiter;
                        }
                        result += delimiter;
                    } else if (halfCrossList.get(i).lr == HalfCrossing.CROSS_RIGHT) {
                        if (halfCrossList.get(i).tb == HalfCrossing.CROSS_TOP) {
                            result += halfCrossList.get(i).name.toUpperCase();
                        } else if (halfCrossList.get(i).tb == HalfCrossing.CROSS_BOTTOM) {
//                            String bucket = halfCrossList.get(i).name.toLowerCase();
                            result += halfCrossList.get(i).name.toLowerCase();
                        } else {
                            result += "?" + delimiter;
                        }
                        result += ">" + delimiter;
                    } else {
                        result += "?" + delimiter;
                    }
                }
                break;
            default:
                result = "Invalid representation mode.";
                break;
        }
        this.representation = result;
    }

    @Override
    public String toString() {
        if (representation == null) {
            // Set representation
        }
        return representation;
    }

    public void remLoops() {
        if (halfCrossList.size() <= 0) {
            return;
        }
        ArrayList<HalfCrossing> remove = new ArrayList<HalfCrossing>();
        for (int i = 0; i < halfCrossList.size() - 1; i++) {
            if (halfCrossList.get(i).name.equals(halfCrossList.get(i + 1).name)) {
                remove.add(halfCrossList.get(i));
                remove.add(halfCrossList.get(i + 1));
            }
        }
        if (halfCrossList.get(halfCrossList.size() - 1).name.equals(halfCrossList.get(0).name)) {
            remove.add(halfCrossList.get(halfCrossList.size() - 1));
            remove.add(halfCrossList.get(0));
        }
        for (int i = 0; i < remove.size(); i++) {
            halfCrossList.remove(remove.get(i));
        }
    }

    public void remSwaps() {
        if (halfCrossList.size() <= 0) {
            return;
        }
        ArrayList<HalfCrossing> remove = new ArrayList<HalfCrossing>();
        for (int i = 0; i < halfCrossList.size() - 1; i++) {
            if (halfCrossList.get(i).tb == halfCrossList.get(i + 1).tb) {
                if (adjacent(halfCrossList.get(i).twin, halfCrossList.get(i + 1).twin)) {
                    remove.add(halfCrossList.get(i));
                    remove.add(halfCrossList.get(i + 1));
                }
            }
        }
        if (halfCrossList.get(halfCrossList.size() - 1).tb == halfCrossList.get(0).tb) {
            if (adjacent(halfCrossList.get(halfCrossList.size() - 1).twin, halfCrossList.get(0).twin)) {
                remove.add(halfCrossList.get(halfCrossList.size() - 1));
                remove.add(halfCrossList.get(0));
            }
        }
        for (int i = 0; i < remove.size(); i++) {
            halfCrossList.remove(remove.get(i));
        }
    }

    public boolean adjacent(HalfCrossing a, HalfCrossing b) {
        int i = halfCrossList.indexOf(a);
        // What LOONY decided -1 % 3 is -1?!?!?!  That's USELESS!  AAAARGH!!
        if ((halfCrossList.get((i + halfCrossList.size() - 1) % halfCrossList.size()) == b) || halfCrossList.get((i + 1) % halfCrossList.size()) == b) {
            return true;
        }
        return false;
    }

    public void downgrade() {
        char nameChar = 'A';
        HashSet<HalfCrossing> done = new HashSet<HalfCrossing>();
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (!done.contains(halfCrossList.get(i))) {
                halfCrossList.get(i).name = String.valueOf(nameChar);
                halfCrossList.get(i).twin.name = String.valueOf(nameChar++);
                done.add(halfCrossList.get(i));
                done.add(halfCrossList.get(i).twin);
            }
        }
    }

    public void display() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < halfCrossList.size(); i++) {
            HalfCrossing c = halfCrossList.get(i);
            PreGrid gr = new PreGrid();
        }
    }

    public String digest(String rep) {

        return "success";
    }

    public ArrayList<HashSet<HalfCrossing>> tris = new ArrayList<HashSet<HalfCrossing>>();
    
    public void tris() {
        if (halfCrossList.size() < 6) {
            return;//in shame.
        }
        for (int i = 0; i < halfCrossList.size(); i++) {
            halfCrossList.get(i).next = halfCrossList.get((i + 1) % halfCrossList.size());
            halfCrossList.get((i + 1) % halfCrossList.size()).prev = halfCrossList.get(i);
        }
        ArrayList<HashSet<HalfCrossing>> tris = new ArrayList<HashSet<HalfCrossing>>();
        for (int i = 0; i < halfCrossList.size(); i++) {
            HalfCrossing[] tri = new HalfCrossing[3];
            tri[0] = halfCrossList.get(i);
            if ((halfCrossList.get((i + 1) % halfCrossList.size()) == tri[0]) ||
                    (halfCrossList.get((i + 1) % halfCrossList.size()) == tri[0].twin)) {
                continue;
            }
            tri[1] = halfCrossList.get((i + 1) % halfCrossList.size());
            if ((tri[1].twin.prev != tri[0])
                    && (tri[1].twin.prev != tri[0].twin)
                    && (tri[1].twin.prev != tri[1])
                    && (tri[1].twin.prev != tri[1].twin)) {
                tri[2] = tri[1].twin.prev;
                if ((tri[2].twin.prev == tri[0].twin)
                        || (tri[2].twin.next == tri[0].twin)) {
                    // Found!
                    HashSet<HalfCrossing> triSet = new HashSet<HalfCrossing>();
                    triSet.add(tri[0]);
                    triSet.add(tri[1]);
                    triSet.add(tri[1].twin);
                    triSet.add(tri[2]);
                    triSet.add(tri[2].twin);
                    triSet.add(tri[0].twin);
                    if (checkLoose(triSet)) {
                        tris.add(triSet);
                    }
                }
            }
            if ((tri[1].twin.next != tri[0])
                    && (tri[1].twin.next != tri[0].twin)
                    && (tri[1].twin.next != tri[1])
                    && (tri[1].twin.next != tri[1].twin)) {
                tri[2] = tri[1].twin.next;
                if ((tri[2].twin.prev == tri[0].twin)
                        || (tri[2].twin.next == tri[0].twin)) {
                    // Found!
                    HashSet<HalfCrossing> triSet = new HashSet<HalfCrossing>();
                    triSet.add(tri[0]);
                    triSet.add(tri[1]);
                    triSet.add(tri[1].twin);
                    triSet.add(tri[2]);
                    triSet.add(tri[2].twin);
                    triSet.add(tri[0].twin);
                    if (checkLoose(triSet)) {
                        tris.add(triSet);
                    }
                }
            }
        }

        // Now remove the duplicates.
        ArrayList<HashSet<HalfCrossing>> remove = new ArrayList<HashSet<HalfCrossing>>();
        for (int i = 0; i < tris.size(); i++) {
            HashSet<HalfCrossing> thisTri = tris.get(i);
            for (int j = 0; j < i; j++) {
                HashSet<HalfCrossing> prevTri = tris.get(j);
                if (prevTri.containsAll(thisTri)) {
                    remove.add(thisTri);
                    break;
                }
            }
        }
        //tris.removeAll(remove);
        for (HashSet<HalfCrossing> r : remove) {
            tris.remove(r);
        }
        
        // So, now we have a list of tris.  Which ones do we swap?
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tris.size(); i++) {
            for (HalfCrossing c : tris.get(i)) {
                sb.append(c.name);
            }
            sb.append("\n");
        }
        this.tris = tris;
        JOptionPane.showMessageDialog(null, "Tris are as follows:\n" + sb.toString());
    }
    
    public boolean checkLoose(HashSet<HalfCrossing> tri) {
        for (HalfCrossing c : tri) {
            if (tri.contains(c.next) && (c.tb == c.next.tb))
                return true;
        }
        return false;
    }
    
    public void swapTri(HashSet<HalfCrossing> tri) {
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (tri.contains(halfCrossList.get(i))) {
                HalfCrossing bucket = halfCrossList.get(i);
                halfCrossList.set(i, halfCrossList.get((i + 1) % halfCrossList.size()));
                halfCrossList.set((i + 1) % halfCrossList.size(), bucket);
                i++;
            }
        }
    }
}
