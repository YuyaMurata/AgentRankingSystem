/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.clone;

import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author 悠也
 */
public class AgentCloning {
    public static Boolean mode;
    
    public static String cloning(String originalID, Object originalState){
        if(mode) return "";
        
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        String agentID = manager.createCloneAgent(originalID, originalState);
        manager.registerAgentID(originalID, agentID);
        //((Window)msgpack).setDestID(agID);
        
        String cloneID = agentID;
        
        System.out.println(">> Agent Cloning New Copy From "+ originalID);
        
        return cloneID;
    }
    
    public static String delete(String originalID, String cloneID){
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        manager.deleteAgentID(originalID, cloneID);
        System.out.println(">> Agent Cloning Delete "+ cloneID);
        
        return cloneID;
    }
    
    public static void setAutoMode(Integer auto){
        mode = auto != 1;
    }
}