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
        //periodDataStream();
        
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
        
        //Agent Deploy (Test)
        LaunchCreateAgent agent = new LaunchCreateAgent();
        Map agentGroup;
        
        //UserAgent
        DistributedAgentConnection agcon = UserAgentManager.getInstance().getConnection("all");
        System.out.println(">>  Server <"+agcon.toString()+">");
        List agList = new ArrayList();
        agList.add("USER#000");agList.add("USER#001");agList.add("USER#002");
        agentGroup = new HashMap();
        agentGroup.put("useragent", agList);
        
        AgentClient client = agcon.getClient();
        agent.create(client, agentGroup);
        agcon.returnConnection(client);
        
        //RankAgent
        agcon = RankAgentManager.getInstance().getConnection("all");
        System.out.println(">>  Server <"+agcon.toString()+">");
        List agrList = new ArrayList();
        agrList.add("RANK#005");
        agentGroup = new HashMap();
        agentGroup.put("rankagent", agrList);
        
        client = agcon.getClient();
        agent.create(client, agentGroup);
        agcon.returnConnection(client);
        
        //Start Logging
        manager.startLogger();
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
    }
    
    private static void shutdownSystem(){
        RankingSystemManager manager = RankingSystemManager.getInstance();
        manager.stopLogger();
        
        ShutdownSystem system = new ShutdownSystem();
        system.shutdownm();
        
        System.out.println("Shutdown System");
    }
}
