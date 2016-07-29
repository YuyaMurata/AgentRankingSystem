/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import rda.property.SetProperty;
import rdarank.manager.ExecuteDataStream;
import rdarank.manager.LaunchSettingsMap;
import rdarank.manager.LaunchSystem;
import rdarank.manager.RankingSystemManager;
import rdarank.manager.ShutdownSystem;

/**
 *
 * @author kaeru
 */
public class RankingSystemMain2 implements SetProperty{
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
        LaunchSystem system = new LaunchSystem();
        
        //SetProperty To Map
        LaunchSettingsMap settings = new LaunchSettingsMap();
        
        system.launch(settings.getPropMap());
        
        //
        RankingSystemManager manager = RankingSystemManager.getInstance();
        manager.setPropMap(settings.getPropMap());
        manager.launchMonitor();
        manager.startLogger();
    }
    
    private static void periodDataStream(){
        System.out.println("Start Data Stream");
        
        ExecuteDataStream data = new ExecuteDataStream();
        
        for(int i=0; i < TIME_RUN; i++){
            System.out.println("Execute Elapsed Time : "+i+" [sec]");
            data.stream();
            try {
                Thread.sleep(TIME_PERIOD);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private static void shutdownSystem(){
        RankingSystemManager manager = RankingSystemManager.getInstance();
        manager.stopLogger();
        
        ShutdownSystem system = new ShutdownSystem();
        system.shutdownm();
        
        System.out.println("Shutdown System");
    }
}
