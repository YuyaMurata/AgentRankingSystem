/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import rda.property.SetProperty;

import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;
import rdarank.deploy.DeployStrategy;
import rdarank.io.TimerInOut;
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
        Long start = System.currentTimeMillis();
        
        //Launch
        launchSystem();
        
        //Execute
        periodDataStream();
        
        //Shutdown
        shutdownSystem();
        
        Long stop = System.currentTimeMillis();
        System.out.println("Main Time : "+(stop-start));
    }
    
    private static RankingSystemManager manager = RankingSystemManager.getInstance();
    private static void launchSystem(){
        //SetProperty To Map
        LaunchSettingsMap settings = new LaunchSettingsMap();
        
        //Init MonitorSystem Manager
        manager.setPropMap(settings.getPropMap());
        manager.launchMonitor();
        
        //Launch System
        System.out.println("Launch Ranking System");
        LaunchSystem system = new LaunchSystem();
        system.launch(settings.getPropMap());
        
        //Deploy and CreateAgent
        DeployStrategy strategy = new DeployStrategy(AGENT_DEPLOY_PATTERN);
        strategy.deploy(UserAgentManager.getInstance(), RankAgentManager.getInstance());
        
        //Logger
        //manager.loggerMonitorStart(System.currentTimeMillis());
        TimerInOut.startTimeOut(System.currentTimeMillis());
    }
    
    private static void periodDataStream(){
        System.out.println("Start Data Stream");
        
        ExecuteDataStream data = new ExecuteDataStream();
        
        for(int i=1; i < TIME_RUN+1; i++){
            System.out.println("Execute Elapsed Time : "+i+" [sec]");
            data.stream();
            try {
                Thread.sleep(TIME_PERIOD);
            } catch (InterruptedException ex) {
            }
        }
        
        //時間調整
        /*try {
                Thread.sleep(TIME_PERIOD);
            } catch (InterruptedException ex) {
            }*/
        
        System.out.println("Stop Data Stream");
    }
    
    private static void shutdownSystem(){
        ShutdownSystem system = new ShutdownSystem();
        system.shutdownm();
        
        //Logger
        //manager.loggerMonitorStop();
        
        System.out.println("Shutdown System");
    }
}
