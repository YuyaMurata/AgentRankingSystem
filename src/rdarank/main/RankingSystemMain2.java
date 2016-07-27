/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import rdarank.agent.rank.creator.CreateRankAgent;
import rdarank.manager.ExecuteDataStream;
import rdarank.manager.LaunchSettingsMap;
import rdarank.manager.LaunchSystem;
import rdarank.manager.ShutdownSystem;

/**
 *
 * @author kaeru
 */
public class RankingSystemMain2 {
    public static void main(String[] args) {
        /*//Launch
        launchSystem();
        
        //Execute
        periodDataStream();
        
        //Shutdown
        shutdownSystem();*/
        
        //Test
        CreateRankAgent rankAgent = new CreateRankAgent();
        rankAgent.create("R#000", 1000, 10L, 10L);
    }
    
    private static void launchSystem(){
        //Launch System
        System.out.println("Launch Ranking System");
        LaunchSystem system = new LaunchSystem();
        
        //SetProperty To Map
        LaunchSettingsMap settings = new LaunchSettingsMap();
        
        system.launch(settings.getPropMap());
    }
    
    private static void periodDataStream(){
        System.out.println("Start Data Stream");
        
        ExecuteDataStream data = new ExecuteDataStream();
        
        /*for(int i=0; i < 5; i++){
            data.stream();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }*/
        data.stream();
    }
    
    private static void shutdownSystem(){
        ShutdownSystem system = new ShutdownSystem();
        system.shutdownm();
        
        System.out.println("Shutdown System");
    }
}
