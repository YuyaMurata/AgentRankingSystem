/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.msg;

/**
 *
 * @author kaeru
 */
public class MessageBox {
    public static void use(String s){
        char decochar = '/';
        int length = s.getBytes().length;
        
        for(int i=0; i < length+4; i++)
            System.out.print(decochar);
        System.out.println("");
        
        System.out.println(decochar+" "+s+" "+decochar);
        
        for(int i=0; i < length+4; i++)
            System.out.print(decochar);
        System.out.println("");
    }
}
