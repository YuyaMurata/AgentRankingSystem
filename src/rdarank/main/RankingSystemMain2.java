/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import com.ibm.agent.exa.client.AgentClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;
import rda.property.SetProperty;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;
import rdarank.deploy.DeployStrategy;
import rdarank.manager.ExecuteDataStream;
import rdarank.manager.LaunchCreateAgent;
import rdarank.manager.LaunchSettingsMap;
import rdarank.manager.LaunchSystem;
import rdarank.manager.RankingSystemManager;
import rdarank.manager.ShutdownSystem;
import rdarank.server.DistributedServerConnection;

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
        //SetProperty To Map
        LaunchSettingsMap settings = new LaunchSettingsMap();
        
        //Init MonitorSystem Manager
        RankingSystemManager manager = RankingSystemManager.getInstance();
        manager.setPropMap(settings.getPropMap());
        manager.launchMonitor();
        
        //Launch System
        System.out.println("Launch Ranking System");
        LaunchSystem system = new LaunchSystem();
        system.launch(settings.getPropMap());
        
        //Deploy and CreateAgent
        DeployStrategy strategy = new DeployStrategy(AGENT_DEPLOY_PATTERN);
        strategy.deploy(UserAgentManager.getInstance(), RankAgentManager.getInstance());
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
        
        System.out.println("Stop Data Stream");
    }
    
    private static void shutdownSystem(){
        ShutdownSystem system = new ShutdownSystem();
        system.shutdownm();
        
        System.out.println("Shutdown System");
    }
}
