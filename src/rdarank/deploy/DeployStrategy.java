/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.deploy;

import com.ibm.agent.exa.client.AgentClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;
import rdarank.manager.LaunchCreateAgent;

/**
 *
 * @author kaeru
 */
public class DeployStrategy {
    private Integer pattern = 0;
    public DeployStrategy(Integer pattern) {
        this.pattern = pattern;
    }
    
    public void deploy(UserAgentManager user, RankAgentManager rank){
        switch(pattern){
            case 0: localDeploy(user, rank);
                    break;
            case 1: agentTypeDeploy(user, rank);
                    break;
            case 2: agentCloneDeploy(user, rank);
                    break;
        }
    }
    
    private void localDeploy(UserAgentManager user, RankAgentManager rank){
        LaunchCreateAgent agent = new LaunchCreateAgent();
        
        Map agentGroup = new HashMap();
        agentGroup.put("useragent", user.getIDList());
        agentGroup.put("rankagent", rank.getIDList());
        
        //System.out.println(agentGroup);
        
        DistributedAgentConnection agcon = UserAgentManager.getInstance().getConnection("0");
        AgentClient client = agcon.getClient();
        
        agent.create(client, agentGroup);
        agcon.returnConnection(client);
    }
    
    private void agentTypeDeploy(UserAgentManager user, RankAgentManager rank){
        LaunchCreateAgent agent = new LaunchCreateAgent();
        
        //User Agent
        Map agentGroup = new HashMap();
        Map<DistributedAgentConnection, List> serverGroup = new HashMap();
        
        for(String userID : user.getIDList()){
            DistributedAgentConnection agcon = user.getConnection(userID);
            
            if(serverGroup.get(agcon) == null) serverGroup.put(agcon, new ArrayList<>());
            
            List<String> agList = (List<String>) serverGroup.get(agcon);
            agList.add(userID);
            serverGroup.put(agcon, agList);
        }
        
        for(DistributedAgentConnection agcon : serverGroup.keySet()){
            System.out.println(">> Deploy Agent Server = "+agcon.toString());
            
            agentGroup.put("useragent", serverGroup.get(agcon));
            
            AgentClient client = agcon.getClient();
            agent.create(client, agentGroup);
            agcon.returnConnection(client);
        }
        
        //Rank Agent
        agentGroup = new HashMap();
        serverGroup = new HashMap();
        
        for(String rankID : rank.getIDList()){
            DistributedAgentConnection agcon = rank.getConnection(rankID);
            
            if(serverGroup.get(agcon) == null) serverGroup.put(agcon, new ArrayList<>());
            
            List<String> agList = (List<String>) serverGroup.get(agcon);
            agList.add(rankID);
            serverGroup.put(agcon, agList);
        }
        
        for(DistributedAgentConnection agcon : serverGroup.keySet()){
            System.out.println(">> Deploy Agent Server = "+agcon.toString());
            
            agentGroup.put("rankagent", serverGroup.get(agcon));
            
            AgentClient client = agcon.getClient();
            agent.create(client, agentGroup);
            agcon.returnConnection(client);
        }
    }
    
    private void agentCloneDeploy(UserAgentManager user, RankAgentManager rank){
        LaunchCreateAgent agent = new LaunchCreateAgent();
        
        //User Agent
        Map agentGroup = new HashMap();
        Map<DistributedAgentConnection, List> serverGroup = new HashMap();
        
        for(String userID : user.getIDList()){
            DistributedAgentConnection agcon = user.getConnection(userID);
            
            if(serverGroup.get(agcon) == null) serverGroup.put(agcon, new ArrayList<>());
            
            List<String> agList = (List<String>) serverGroup.get(agcon);
            agList.add(userID);
            serverGroup.put(agcon, agList);
        }
        
        for(DistributedAgentConnection agcon : serverGroup.keySet()){
            System.out.println(">> Deploy Agent Server = "+agcon.toString());
            
            agentGroup.put("useragent", serverGroup.get(agcon));
            //Rank Agent
            agentGroup.put("rankagent", rank.getIDList());
            
            AgentClient client = agcon.getClient();
            agent.create(client, agentGroup);
            agcon.returnConnection(client);
        }
    }
}
