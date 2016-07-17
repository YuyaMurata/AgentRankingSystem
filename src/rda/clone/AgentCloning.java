/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.clone;

import rda.manager.AgentManager;
import rda.manager.IDManager;

/**
 *
 * @author 悠也
 */
public class AgentCloning {
    public static String cloning(AgentManager manager, String sourceID, Object originalState){
        if(manager.getAutoMode()) return "";
        
        IDManager id = manager.getIDManager();
        String originalID = id.getOrigID(sourceID);
        
        //Clone
        String cloneID = manager.createCloneAgent(originalID, originalState);
        id.regID(originalID, cloneID);
        
        System.out.println(">> Agent Cloning New Copy From "+ originalID);
        
        return cloneID;
    }
    
    public static String delete(AgentManager manager, String deleteID){
        if(manager.getAutoMode()) return "";
        
        IDManager id = manager.getIDManager();
        String originalID = id.getOrigID(deleteID);
        
        //Delete
        id.deleteID(originalID, deleteID);
        
        System.out.println(">> Agent Cloning Delete "+ deleteID);
        
        return deleteID;
    }
}