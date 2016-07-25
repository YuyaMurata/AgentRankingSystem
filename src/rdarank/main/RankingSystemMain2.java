/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

/**
 *
 * @author kaeru
 */
public class RankingSystemMain2 {
    public static void main(String[] args) {
        //Launch
        launchSystem();
        
        //Execute
        periodDataStream();
        
        //Shutdown
        shutdownSystem();
    }
    
    private static void launchSystem(){
        //Launch System
        System.out.println("Launch Ranking System");
        
        
    }
    
    private static void periodDataStream(){
        System.out.println("Start Data Stream");
        
    }
    
    private static void shutdownSystem(){
        
        
        System.out.println("Shutdown System");
    }
}
