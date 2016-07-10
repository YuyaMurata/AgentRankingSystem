/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.other;

/**
 *
 * @author kaeru
 */
public class ExceptionTest {
    public static void main(String[] args) throws Exception{
        try{
            throw new IllegalStateException();
        }catch(IllegalStateException e){
            System.out.println("Illegal Exception");
        }
        
        System.out.println("Try catch Exception");
    }
}
