/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kaeru
 */
public class HashTest {
    public static void main(String[] args) {
        String hash1 = "U#0000";
        String hash2 = "U#0037";
        
        System.out.println("hash1 = "+hash1+" -> "+hash1.hashCode()+" %100 = " +(Math.abs(hash1.hashCode())%100));
        
        System.out.println("hash2 = "+hash2+" -> "+hash2.hashCode()+" %100 = " +(Math.abs(hash2.hashCode())%100));
    }
}
