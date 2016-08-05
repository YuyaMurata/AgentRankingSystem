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
        
        System.out.println(agentGroup);
        
        DistributedAgentConnection agcon = UserAgentManager.getInstance().getConnection("");
        AgentClient client = agcon.getClient();
        
        agent.create(client, agentGroup);
        agcon.returnConnection(client);
    }
    
    private void agentTypeDeploy(UserAgentManager user, RankAgentManager rank){
        LaunchCreateAgent agent = new LaunchCreateAgent();
        
        //User Agent
        Map agentGroup = new HashMap();
        Map serverGroup = new HashMap();
        
        for(String userID : user.getIDList()){
            Integer hash = Math.abs(userID.hashCode()) % user.getNumServer();
            if(serverGroup.get(hash) == null) serverGroup.put(hash, new ArrayList<>());
            
            List<String> agList = (List<String>) serverGroup.get(hash);
            agList.add(userID);
            serverGroup.put(hash, agList);
        }
        
        for(Object id : serverGroup.keySet()){
            DistributedAgentConnection agcon = user.getConnection(((List<String>)serverGroup.get(id)).get(0));
            
            agentGroup.put("useragent", serverGroup.get(id));
            
            AgentClient client = agcon.getClient();
            agent.create(client, agentGroup);
            agcon.returnConnection(client);
        }
        
        //Rank Agent
        agentGroup = new HashMap();
        serverGroup = new HashMap();
        
        for(String rankID : rank.getIDList()){
            Integer hash = Math.abs(rankID.hashCode()) % rank.getNumServer();
            if(serverGroup.get(hash) == null) serverGroup.put(hash, new ArrayList<>());
            
            List<String> agList = (List<String>) serverGroup.get(hash);
            agList.add(rankID);
            serverGroup.put(hash, agList);
        }
        
        for(Object id : serverGroup.keySet()){
            DistributedAgentConnection agcon = rank.getConnection(((List<String>)serverGroup.get(id)).get(0));
            
            agentGroup.put("rankagent", serverGroup.get(id));
            
            AgentClient client = agcon.getClient();
            agent.create(client, agentGroup);
            agcon.returnConnection(client);
        }
    }
    
    private void agentCloneDeploy(UserAgentManager user, RankAgentManager rank){
    }
}
