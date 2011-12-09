/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knottheorist;

import java.lang.reflect.Type;

/**
 *
 * @author mewer12
 */
public class MainTest<T> {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char[] chars = new char[6];
        char[] charID = new char[6];
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 5; j++) {
                for (int k = j + 1; k < 6; k++) {
                    for (int l = 0; l < 6; l++) {
                        if (l == i)
                            charID[l] = 'i';
                        else if (l == j)
                            charID[l] = 'j';
                        else if (l == k)
                            charID[l] = 'k';
                        else
                            charID[l] = ' ';
                        if (l == i || l == j || l == k) {
                            chars[l] = '<';
                        } else {
                            chars[l] = '>';
                        }
                    }  
                    printlnCharsA(charID);
                    printlnChars(chars);
                }
            }
        }
        
        if (1 == 1) {
            return;
        }
        VoidSet<Integer, Object> test = new VoidSet<Integer, Object>(Object.class);
        System.out.println(test.get(5));
        System.out.println(test.get(5));
        System.out.println(test.get(4));
        System.out.println(test.get(5));
    }

    public static void printlnCharsA(char[] chars) {
        System.out.println("" + chars[0] + chars[1] + "   " + chars[2] + chars[3] + "   " + chars[4] + chars[5]);
    }
    
    public static void printlnChars(char[] chars) {
//        for (int i = 0; i < chars.length; i++) {
//            System.out.print(chars[i]);
//        }
//        System.out.println("");
        System.out.println("" + chars[0] + chars[1] + "   " + chars[2] + chars[3] + "   " + chars[4] + chars[5]);
        System.out.println("" + chars[1] + chars[0] + "   " + chars[3] + chars[2] + "   " + chars[5] + chars[4]);
        System.out.println("            ");
        System.out.println("            ");
    }

    public MainTest() {
        T bob;
        java.lang.reflect.GenericDeclaration g;
    }
}
