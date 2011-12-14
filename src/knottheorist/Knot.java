/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final int REP_NTL = 2;
    public int representMode = REP_BKa;
    public String delimiter = " ";

    public void represent() {//halfCrossList.clear()
        String result = "";
        switch (representMode) {
            case REP_ATL:
            case REP_NTL:
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

    public void remLoops() {//halfCrossList.clear()
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

    public void remSwaps() { //TODO There's something wrong with this function!
        if (halfCrossList.size() <= 0) {
            return;
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            ArrayList<HalfCrossing> remove = new ArrayList<HalfCrossing>();
            if (halfCrossList.size() <= 0) {
                return;
            }
            if (halfCrossList.get(halfCrossList.size() - 1).tb == halfCrossList.get(0).tb) {
                if (adjacent(halfCrossList.get(halfCrossList.size() - 1).twin, halfCrossList.get(0).twin)) {
                    remove.add(halfCrossList.get(halfCrossList.size() - 1));
                    remove.add(halfCrossList.get(0));
                    remove.add(halfCrossList.get(halfCrossList.size() - 1).twin);
                    remove.add(halfCrossList.get(0).twin);
                    halfCrossList.removeAll(remove);
                    changed = true;

//                    // Debugging
//                    String errCheck = validate();
//                    if (!"success".equals(errCheck)) {
//                        System.err.println("Inside remSwaps: " + errCheck);
//                    }

                    continue;
                }
            }
            for (int i = 0; i < halfCrossList.size() - 1; i++) {
                if (halfCrossList.get(i).tb == halfCrossList.get(i + 1).tb) {
                    if (adjacent(halfCrossList.get(i).twin, halfCrossList.get(i + 1).twin)) {
                        remove.add(halfCrossList.get(i));
                        remove.add(halfCrossList.get(i + 1));
                        remove.add(halfCrossList.get(i).twin);
                        remove.add(halfCrossList.get(i + 1).twin);
                        halfCrossList.removeAll(remove);
                        changed = true;

//                        // Debugging
//                        String errCheck = validate();
//                        if (!"success".equals(errCheck)) {
//                            System.err.println("Inside remSwaps: " + errCheck);
//                        }

                        break;
                    }
                }
            }
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
        //ArrayList<BigInteger> values = new ArrayList<BigInteger>();
        boolean found = false;
        BigInteger biggest = null;
        int maxIndex = 0;
        for (int i = 0; i < halfCrossList.size(); i++) {
            BigInteger weight = getWeight();
            //values.add(weight);
            if (!found || (weight.compareTo(biggest) == 1)) {
                biggest = weight;
                maxIndex = i;
                found = true;
            }
            halfCrossList.add(halfCrossList.remove(0)); // It's like ROTL.
        }
        for (int i = 0; i < maxIndex; i++) {
            halfCrossList.add(halfCrossList.remove(0)); // It's like ROTL.
        }

        HashSet<HalfCrossing> done;
        switch (representMode) {
            case REP_ATL:
            case REP_BKa:
                char nameChar = 'A';
                done = new HashSet<HalfCrossing>();
                for (int i = 0; i < halfCrossList.size(); i++) {
                    if (!done.contains(halfCrossList.get(i))) {
                        halfCrossList.get(i).name = String.valueOf(nameChar);
                        halfCrossList.get(i).twin.name = String.valueOf(nameChar++);
                        done.add(halfCrossList.get(i));
                        done.add(halfCrossList.get(i).twin);
                    }
                }
                break;
            case REP_NTL:
                int nameID = 1;
                done = new HashSet<HalfCrossing>();
                for (int i = 0; i < halfCrossList.size(); i++) {
                    if (!done.contains(halfCrossList.get(i))) {
                        halfCrossList.get(i).name = Integer.toString(nameID);
                        halfCrossList.get(i).twin.name = Integer.toString(nameID++);
                        done.add(halfCrossList.get(i));
                        done.add(halfCrossList.get(i).twin);
                    }
                }
                break;
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

    public class KnotScanner {

        public String text;
        public int index;
        public int mode;

        public KnotScanner(String text, int mode) {
            this.text = text.replaceAll(" ", "");
            this.index = 0;
            this.mode = mode;
            switch (mode) {
                case REP_ATL:
                case REP_BKa:
                    break;
                case REP_NTL:
                    break;
                default:
                    break;
            }
        }

        public HalfCrossing nextCrossing() {
            HalfCrossing result = null;
            switch (mode) {
                case REP_ATL:
                    result = new HalfCrossing();
                    result.name = text.substring(index, 1 + index++);
                    if ("t".equals(text.substring(index, 1 + index++))) {
                        result.tb = HalfCrossing.CROSS_TOP;
                    } else {
                        result.tb = HalfCrossing.CROSS_BOTTOM;
                    }
                    if ("l".equals(text.substring(index, 1 + index++))) {
                        result.lr = HalfCrossing.CROSS_LEFT;
                    } else {
                        result.lr = HalfCrossing.CROSS_RIGHT;
                    }
                    return result;
                case REP_BKa:
                    result = new HalfCrossing();
                    if (index >= text.length()) {
                        return null;
                    }
                    if (text.indexOf("<", index) == -1 && text.indexOf(">", index) == -1) {
                        return null;
                    }
                    if (text.charAt(index) == '<') {
                        result.lr = HalfCrossing.CROSS_LEFT;
                        index++;
                        result.name = text.substring(index, 1 + index);
                        if (result.name.equals(result.name.toUpperCase())) {
                            result.tb = HalfCrossing.CROSS_TOP;
                        } else {
                            result.tb = HalfCrossing.CROSS_BOTTOM;
                            result.name = result.name.toUpperCase();
                        }
                        index++;
                    } else {
                        result.lr = HalfCrossing.CROSS_RIGHT;
                        result.name = text.substring(index, 1 + index);
                        if (result.name.equals(result.name.toUpperCase())) {
                            result.tb = HalfCrossing.CROSS_TOP;
                        } else {
                            result.tb = HalfCrossing.CROSS_BOTTOM;
                            result.name = result.name.toUpperCase();
                        }
                        index += 2;
                    }
                    return result;
                case REP_NTL:
                    result = new HalfCrossing();
                    if (index >= text.length()) {
                        return null;
                    }
                    while (!Character.isDigit(text.charAt(index))) {
                        index++;
                        if (index >= text.length()) {
                            return null;
                        }
                    }
                    StringBuilder digits = new StringBuilder();
                    while (Character.isDigit(text.charAt(index))) {
                        digits.append(text.charAt(index++));
                    }
                    result.name = digits.toString();
                    if (text.charAt(index++) == 't') {
                        result.tb = HalfCrossing.CROSS_TOP;
                    } else {
                        result.tb = HalfCrossing.CROSS_BOTTOM;
                    }
                    if (text.charAt(index++) == 'l') {
                        result.lr = HalfCrossing.CROSS_LEFT;
                    } else {
                        result.lr = HalfCrossing.CROSS_RIGHT;
                    }
                    return result;
                default:
                    return null;
            }
        }
    }

    public String digest(String rep) {
//        ArrayList<HalfCrossing> newHC = new ArrayList<HalfCrossing>();
        halfCrossList = new ArrayList<HalfCrossing>();
        //int newMode = 0;
        HalfCrossing newh = null;
        if (rep.length() == 0) {
//            halfCrossList = newHC;
            represent();
        } else {
            KnotScanner ks;
            if (Character.isDigit(rep.charAt(0))) {
                representMode = REP_NTL;
                ks = new KnotScanner(rep, REP_NTL);
            } else if (rep.charAt(0) == '<' || rep.charAt(1) == '>') {
                representMode = REP_BKa;
                ks = new KnotScanner(rep, REP_BKa);
            } else {
                representMode = REP_ATL;
                ks = new KnotScanner(rep, REP_ATL);
            }
            while ((newh = ks.nextCrossing()) != null) {
                halfCrossList.add(newh);
            }
            for (int i = 0; i < halfCrossList.size(); i++) {
                HalfCrossing left = halfCrossList.get(i);
                HalfCrossing next = halfCrossList.get((i + 1) % halfCrossList.size());
                left.next = next;
                next.prev = left;
                if (left.dir == -1) {
                    left.dir = HalfCrossing.DIR_RIGHT;
                    for (int j = i + 1; j < halfCrossList.size(); j++) {
                        HalfCrossing right = halfCrossList.get(j);
                        if (left.name.equals(right.name)) {
                            left.twin = right;
                            right.twin = left;
                            if (left.lr == HalfCrossing.CROSS_LEFT) {
                                right.dir = HalfCrossing.DIR_UP;
                            } else {
                                right.dir = HalfCrossing.DIR_DOWN;
                            }
                        }
                    }
                }
            }
        }
        return "success";
    }
    public ArrayList<HashSet<HalfCrossing>> tris = new ArrayList<HashSet<HalfCrossing>>();

    public void tris() {
        ArrayList<HashSet<HalfCrossing>> tris = new ArrayList<HashSet<HalfCrossing>>();
        if (halfCrossList.size() < 6) {
            this.tris = tris;
            return;//in shame.
        }
        for (int i = 0; i < halfCrossList.size(); i++) {
            halfCrossList.get(i).triPair = new HashMap<HashSet<HalfCrossing>, HalfCrossing>();
            halfCrossList.get(i).next = halfCrossList.get((i + 1) % halfCrossList.size());
            halfCrossList.get((i + 1) % halfCrossList.size()).prev = halfCrossList.get(i);
        }
        for (int i = 0; i < halfCrossList.size(); i++) {
            HalfCrossing[] tri = new HalfCrossing[3];
            int[] num = new int[6];
            tri[0] = halfCrossList.get(i);
            num[0] = 0;
            if ((halfCrossList.get((i + 1) % halfCrossList.size()) == tri[0])
                    || (halfCrossList.get((i + 1) % halfCrossList.size()) == tri[0].twin)) {
                continue;
            }
            tri[1] = halfCrossList.get((i + 1) % halfCrossList.size());
            num[1] = 1;
// Since apparently pairs must all point in or all point out, (01)(21)(??) would break that.            
//  DISPROVEN         
            if ((tri[1].twin.prev != tri[0])
                    && (tri[1].twin.prev != tri[0].twin)
                    && (tri[1].twin.prev != tri[1])
                    && (tri[1].twin.prev != tri[1].twin)) {
                tri[2] = tri[1].twin.prev;
                num[2] = 2;
                num[3] = 1;
                if ((tri[2].twin.prev == tri[0].twin)
                        || (tri[2].twin.next == tri[0].twin)) {
                    if (tri[2].twin.prev == tri[0].twin) {
                        //(01)(21)(02)
                        num[4] = 0;
                        num[5] = 2;
                    } else {
                        //(01)(21)(20)
                        num[4] = 2;
                        num[5] = 0;
                    }
                    // The idea behind this is that it now appears that:
                    //   If n and n.twin are both on the left or both on the right,
                    //   of pairs, their partners must face the same way as each other.
                    //   Otherwise opposite.
                    if (num[1] == num[2]) {
                        // 01 12
                        if (tri[0].lr == tri[2].lr) {
                            continue;
                        }
                        if (num[3] == num[4]) {
                            // 01 12 20
                            if (tri[1].twin.lr == tri[0].twin.lr) {
                                continue;
                            }
                        } else {
                            // 01 12 02
                            if (tri[1].twin.lr != tri[0].twin.lr) {
                                continue;
                            }
                        }
                    } else {
                        // 01 21
                        if (tri[0].lr != tri[2].lr) {
                            continue;
                        }
                        if (num[2] == num[4]) {
                            // 01 21 20                            
                            if (tri[1].twin.lr != tri[0].twin.lr) {
                                continue;
                            }
                        } else {
                            // 01 21 02  
                            if (tri[1].twin.lr == tri[0].twin.lr) {
                                continue;
                            }
                        }
                    }
                    // Found!
                    HashSet<HalfCrossing> triSet = new HashSet<HalfCrossing>();
                    triSet.add(tri[0]);
                    triSet.add(tri[1]);
                    triSet.add(tri[1].twin);
                    triSet.add(tri[2]);
                    triSet.add(tri[2].twin);
                    triSet.add(tri[0].twin);
                    tri[0].triPair.put(triSet, tri[1]);
                    tri[1].triPair.put(triSet, tri[0]);
                    tri[1].twin.triPair.put(triSet, tri[2]);
                    tri[2].triPair.put(triSet, tri[1].twin);
                    tri[2].twin.triPair.put(triSet, tri[0].twin);
                    tri[0].twin.triPair.put(triSet, tri[2].twin);
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
                num[2] = 1;
                num[3] = 2;
                // I had checked for tri[2].twin.prev == tri[0].twin, but that led
                //   to (01)(12)(02), which breaks inter-pair agreement.
                //   Which has been disproven.  We're checking again.
                if ((tri[2].twin.prev == tri[0].twin)
                        || (tri[2].twin.next == tri[0].twin)) {
                    //(01)(12)()
                    if (tri[2].twin.prev == tri[0].twin) {
                        //(01)(12)(02)
                        num[4] = 0;
                        num[5] = 2;
                    } else {
                        num[4] = 2;
                        num[5] = 0;
                    }
                    // The idea behind this is that it now appears that:
                    //   If n and n.twin are both on the left or both on the right,
                    //   of pairs, their partners must face the same way as each other.
                    //   Otherwise opposite.
                    if (num[1] == num[2]) {
                        // 01 12
                        if (tri[0].lr == tri[2].lr) {
                            continue;
                        }
                        if (num[3] == num[4]) {
                            // 01 12 20
                            if (tri[1].twin.lr == tri[0].twin.lr) {
                                continue;
                            }
                        } else {
                            // 01 12 02
                            if (tri[1].twin.lr != tri[0].twin.lr) {
                                continue;
                            }
                        }
                    } else {
                        // 01 21
                        if (tri[0].lr != tri[1].twin.lr) {
                            continue;
                        }
                        if (num[2] == num[4]) {
                            // 01 21 20                            
                            if (tri[1].twin.lr != tri[0].twin.lr) {
                                continue;
                            }
                        } else {
                            // 01 21 02  
                            if (tri[1].twin.lr == tri[0].twin.lr) {
                                continue;
                            }
                        }
                    }
                    // Found!
                    HashSet<HalfCrossing> triSet = new HashSet<HalfCrossing>();
                    triSet.add(tri[0]);
                    triSet.add(tri[1]);
                    triSet.add(tri[1].twin);
                    triSet.add(tri[2]);
                    triSet.add(tri[2].twin);
                    triSet.add(tri[0].twin);
                    tri[0].triPair.put(triSet, tri[1]);
                    tri[1].triPair.put(triSet, tri[0]);
                    tri[1].twin.triPair.put(triSet, tri[2]);
                    tri[2].triPair.put(triSet, tri[1].twin);
                    tri[2].twin.triPair.put(triSet, tri[0].twin);
                    tri[0].twin.triPair.put(triSet, tri[2].twin);
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

        this.tris = tris;
        // So, now we have a list of tris.  Which ones do we swap?
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < tris.size(); i++) {
//            for (HalfCrossing c : tris.get(i)) {
//                sb.append(c.name);
//            }
//            sb.append("\n");
//        }
//        JOptionPane.showMessageDialog(null, "Tris are as follows:\n" + sb.toString());
    }

    /**
     * This makes sure it's not a trefoil-type crossing.
     * @param tri
     * @return 
     */
    public boolean checkLoose(HashSet<HalfCrossing> tri) {
        for (HalfCrossing c : tri) {
            if ((c.triPair.get(tri) != null) && (c.tb == c.triPair.get(tri).tb)) {
                return true;
            }
        }
        return false;
    }

    public void swapTri(HashSet<HalfCrossing> tri) {
        HashSet<HalfCrossing> swapped = new HashSet<HalfCrossing>();
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (tri.contains(halfCrossList.get(i))) {
                HalfCrossing left = halfCrossList.get((i - 1 + halfCrossList.size()) % halfCrossList.size());
                HalfCrossing middle = halfCrossList.get(i);
                HalfCrossing right = halfCrossList.get((i + 1) % halfCrossList.size());
                if (right == middle.triPair.get(tri)) {
                    if (!swapped.contains(middle) && !swapped.contains(right)) {
                        swapped.add(middle);
                        swapped.add(right);
                        halfCrossList.set(i, right);
                        halfCrossList.set((i + 1) % halfCrossList.size(), middle);
                    }
                } else if (left == middle.triPair.get(tri)) {
                    if (!swapped.contains(middle) && !swapped.contains(left)) {
                        swapped.add(middle);
                        swapped.add(left);
                        halfCrossList.set(i, left);
                        halfCrossList.set((i - 1 + halfCrossList.size()) % halfCrossList.size(), middle);
                    }
                } else {
                    System.err.println("Tri is incontiguous!");
                }
            }
        }
    }

    public String validate() { //TODO Maybe should also check for duplicate names.
        if ((halfCrossList.size() % 2) != 0) {
            return "Invalid: odd number of half-crossings.";
        }
        for (HalfCrossing c : halfCrossList) {
            if (c == null) {
                return "Invalid: null in list.";
            }
            if (c.twin == null || !halfCrossList.contains(c.twin)) {
                return "Invalid: " + c.name + " has no match.";
            }
            if (c == c.twin) {
                return "Invalid: " + c.name + " is its own match.";
            }
            if (c.tb == c.twin.tb) {
                return "Invalid: " + c.name + " is on top both ways.";
            }
            if (c.lr == c.twin.lr) {
                return "Invalid: " + c.name + " has invalid lr pair.";
            }
            if (c.lr != HalfCrossing.CROSS_LEFT && c.lr != HalfCrossing.CROSS_RIGHT) {
                return "Invalid: " + c.name + " has invalid lr value.";
            }
            if (c.tb != HalfCrossing.CROSS_TOP && c.tb != HalfCrossing.CROSS_BOTTOM) {
                return "Invalid: " + c.name + " has invalid tb value.";
            }
        }
        for (int i = 0; i < halfCrossList.size(); i++) {
            HalfCrossing left = halfCrossList.get(i);
            int r = halfCrossList.indexOf(left.twin);
            if (i < r) {
                int sum = 0;
                for (int j = i + 1; j < r; j++) {
                    if (halfCrossList.get(j).lr == HalfCrossing.CROSS_LEFT) {
                        sum++;
                    } else {
                        sum--;
                    }
                }
                if (sum != 0) {
                    return "Invalid: There is an lr imbalance between " + left.name;
                }
            }
        }
        if (!checkTopology()) {
            return "Invalid: Topology error.";
        }
        return "success";
    }

    public boolean checkTopology() {
        HalfCrossing[] hc = new HalfCrossing[halfCrossList.size()];
        halfCrossList.toArray(hc); // Because it's easier.
        for (int i = 0; i < hc.length; i++) {
            hc[i].pos = i;
        }

        char[] state = new char[hc.length];
        HashSet<String> doneSet = new HashSet<String>();
        for (int i = 0; i < hc.length; i++) {
            int lefti = i;
            int righti = hc[i].twin.pos;

//            if (!doneSet.contains(hc[i].name)) {
            for (int j = 0; j < state.length; j++) {
                state[j] = '-'; // For unset.
            }
//                printlnChars(state);
            state[i] = 'e'; // For end point.
            state[righti] = 'e';
//                printlnChars(state);
            boolean makeGates = true;
            for (int j = ((lefti + 1) % state.length); j != lefti; j = (j + 1) % state.length) {
                if (state[j] == 'e') {
                    makeGates = !makeGates;
                } else if (makeGates) {
                    if (state[hc[j].twin.pos] == 'G') {
                        state[hc[j].twin.pos] = 'x'; // For invalid.
                        state[j] = 'x';
                    } else {
                        state[j] = 'G';
                    }
                }
//                    printlnChars(state);
            }
            for (int j = 0; j < state.length; j++) {
                if (state[j] == 'G') {
                    state[hc[j].twin.pos] = 'g';
                }
            }
            boolean inside = false;
            for (int j = 0; j < state.length; j++) {
                if (state[j] == 'g') {
                    inside = !inside;
                } else if (state[j] == '-') {
                    state[j] = (inside ? 'i' : 'o'); // For inside and outside, respectively.
                }
//                    printlnChars(state);
            }

            char[] names = new char[state.length];
            for (int j = 0; j < names.length; j++) {
                names[j] = hc[j].name.charAt(0);
            }
//                printlnChars(names);

            // Now check them.
            for (int j = 0; j < state.length; j++) {
                if ((state[j] == 'i' && state[hc[j].twin.pos] == 'o')
                        || (state[j] == 'o' && state[hc[j].twin.pos] == 'i')) {
                    return false;
                }
            }
//                System.out.println("");
//            }
        }
        return true;
    }

    public void printlnChars(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]);
        }
        System.out.println("");
    }
    public boolean branched = false;

    public void full12() {
        boolean changed = true;
        while (changed) {
            changed = false;
            represent();
            String was = representation;

//            // Debugging
//            String errCheck = validate();
//            if (!"success".equals(errCheck)) {
//                System.err.println("Before change: " + errCheck);
//            }

            remLoops();

//            // Debugging
//            errCheck = validate();
//            if (!"success".equals(errCheck)) {//halfCrossList.clear()
//                System.err.println("After loops: " + errCheck);
//            }

            remSwaps();

//            // Debugging
//            errCheck = validate();
//            if (!"success".equals(errCheck)) {
//                System.err.println("After swaps: " + errCheck);
//            }

            represent();
            if (was.equals(representation)) {
                changed = false;
            } else {
                changed = true;
            }
        }
        downgrade();
        represent();
    }

    public Knot copy() {
        Knot copy = new Knot();
        copy.delimiter = this.delimiter;
        copy.representMode = this.representMode;
        copy.representation = this.representation;
        //TODO Sometime in the future, copy tris here.
        for (HalfCrossing h : halfCrossList) {
            h.copy = h.copy();
        }
        for (int i = 0; i < halfCrossList.size(); i++) {
            halfCrossList.get(i).copy.twin = halfCrossList.get(i).twin.copy;
            copy.halfCrossList.add(halfCrossList.get(i).copy);
        }
        // For good measure.
        for (int i = 0; i < copy.halfCrossList.size(); i++) {
            copy.halfCrossList.get(i).next = copy.halfCrossList.get((i + 1) % copy.halfCrossList.size());
            copy.halfCrossList.get((i + 1) % copy.halfCrossList.size()).prev = copy.halfCrossList.get(i);
        }

        return copy;
    }

    public BigInteger getWeight() {
        BigInteger result = new BigInteger("0");
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (i != 0) {
                result = result.shiftLeft(2);
            }
            if (halfCrossList.get(i).tb == HalfCrossing.CROSS_TOP) {
                result = result.add(new BigInteger("2"));
            }
            if (halfCrossList.get(i).lr == HalfCrossing.CROSS_LEFT) {
                result = result.add(BigInteger.ONE);
            }
        }
        return result;
    }

    public void branch(HashMap<String, Knot> knots) {
        full12();
        downgrade();
        represent();
        if (knots.containsKey(representation)) {
            return;
        }
        knots.put(representation, this);
        tris();
        for (int i = 0; i < tris.size(); i++) {
            Knot copy = this.copy();
            copy.tris();
            if (tris.size() != copy.tris.size()) {
                System.err.println("Tris different!!");
                tris();
                copy.tris();
            }

//            // Debugging
//            String errCheck = copy.validate();
//            if (!"success".equals(errCheck)) {//copy.tris.clear()
//                System.err.println("Before tri: " + errCheck);
//            }//copy.representation
//            //1,14,15
            copy.swapTri(copy.tris.get(i));//halfCrossList.clear()

//            // Debugging
//            errCheck = copy.validate();
//            if (!"success".equals(errCheck)) {
//                System.err.println("After tri: " + errCheck);
//            }

            copy.branch(knots);
        }

        for (int i = 0; i < halfCrossList.size(); i++) {
            Knot copy = this.copy();

            HalfCrossing left = copy.halfCrossList.get(i);
            if (copy.isClosed(left, left.twin)) {
                // You can just untwist the cluster of crossings, but without the twist

//                // Debugging
//                String errCheck = copy.validate();
//                if (!"success".equals(errCheck)) {
//                    System.err.println("Before twist: " + errCheck);
//                }

                copy.halfCrossList.remove(left);
                copy.halfCrossList.remove(left.twin);

//                // Debugging
//                errCheck = copy.validate();
//                if (!"success".equals(errCheck)) {
//                    System.err.println("After twist: " + errCheck);
//                }
                copy.branch(knots);
            }
            //copy.invertCrossing(copy.halfCrossList.get(i));

        }
    }

    public void flipOver() {
        for (HalfCrossing h : halfCrossList) {
            switch (h.dir) { // I'm actually not sure what to do with the direction.
                case HalfCrossing.DIR_RIGHT:
                    h.dir = HalfCrossing.DIR_DOWN;
                    break;
                case HalfCrossing.DIR_DOWN:
                    h.dir = HalfCrossing.DIR_RIGHT;
                    break;
                case HalfCrossing.DIR_LEFT:
                    h.dir = HalfCrossing.DIR_UP;
                    break;
                case HalfCrossing.DIR_UP:
                    h.dir = HalfCrossing.DIR_LEFT;
                    break;
            }
            if (h.lr == HalfCrossing.CROSS_LEFT) {
                h.lr = HalfCrossing.CROSS_RIGHT;
            } else {
                h.lr = HalfCrossing.CROSS_LEFT;
            }
            if (h.tb == HalfCrossing.CROSS_TOP) {
                h.tb = HalfCrossing.CROSS_BOTTOM;
            } else {
                h.tb = HalfCrossing.CROSS_TOP;
            }
        }
    }

    public void flipBack() {
        ArrayList<HalfCrossing> flipped = new ArrayList<HalfCrossing>();
        for (int i = halfCrossList.size() - 1; i >= 0; i--) {
            flipped.add(halfCrossList.get(i));
        }
        halfCrossList = flipped;
    }

    public void invertCrossing(HalfCrossing left) {
        int index = -1;
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (halfCrossList.get(i) == left) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        if (!isClosed(left, left.twin)) {
            return;
        }
        while (true) {
            HalfCrossing bucket = halfCrossList.get(index % halfCrossList.size());
            if (bucket.lr == HalfCrossing.CROSS_LEFT) {
                bucket.lr = HalfCrossing.CROSS_RIGHT;
            } else {
                bucket.lr = HalfCrossing.CROSS_LEFT;
            }
            if (bucket.tb == HalfCrossing.CROSS_TOP) {
                bucket.tb = HalfCrossing.CROSS_BOTTOM;
            } else {
                bucket.tb = HalfCrossing.CROSS_TOP;
            }
            if (bucket == left.twin) {
                return;
            }
            index++;
        }
    }

    public boolean isClosed(HalfCrossing left, HalfCrossing right) {
        int index = -1;
        for (int i = 0; i < halfCrossList.size(); i++) {
            if (halfCrossList.get(i) == left) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        HashSet<HalfCrossing> contents = new HashSet<HalfCrossing>();
        while (true) {
            HalfCrossing bucket = halfCrossList.get(index % halfCrossList.size());
            contents.add(bucket);
            if (bucket == right) {
                break;
            }
            index++;
        }
        for (HalfCrossing h : contents) {
            if (!contents.contains(h.twin)) {
                return false;
            }
        }
        return true;
    }
}
