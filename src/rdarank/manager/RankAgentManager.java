/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import java.util.HashMap;
import java.util.Map;
import rda.clone.AgentCloning;
import rda.manager.IDManager;

/**
 *
 * @author kaeru
 */
public class RankAgentManager {
    private static RankAgentManager manager = new RankAgentManager();
    private static Boolean runnable = true;
    private IDManager rankID;
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    
    public RankAgentManager() {
    }
    
    public static RankAgentManager getInstance(){
        return manager;
    }
    
    public void initRankAgent(Map rankAgentMapParam){
        this.queueLength = (Integer)rankAgentMapParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)rankAgentMapParam.get("QUEUE_WAIT");
        this.agentwait = (Long)rankAgentMapParam.get("AGENT_WAIT");
        this.agentMode = (Integer)rankAgentMapParam.get("AGENT_MODE");
        this.reserveMode = (Integer)rankAgentMapParam.get("RESERVE_MODE");
        
        //Init IDManager
        this.rankID = new IDManager(rankAgentMapParam);
        
        AgentCloning.setAutoMode(agentMode);
    }
}
