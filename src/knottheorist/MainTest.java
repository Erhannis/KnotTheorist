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
        VoidSet<Integer, Object> test = new VoidSet<Integer, Object>(Object.class);
        System.out.println(test.get(5));
        System.out.println(test.get(5));
        System.out.println(test.get(4));
        System.out.println(test.get(5));
    }
    
    public MainTest() {
        T bob;
        java.lang.reflect.GenericDeclaration g;
    }
}
