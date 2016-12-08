package rda.clone;

import rda.manager.AgentManager;
import rda.manager.IDManager;

public class AgentCloning {
    public static String cloning(AgentManager manager, String sourceID, Object originalState){
        if(!manager.getAutoMode()) return "";
        
        System.out.println(">> Start Agent Cloning");
        
        IDManager id = manager.getIDManager();
        String originalID = id.getOrigID(sourceID);
        
        //Clone
        String cloneID = manager.createCloneAgent(originalID, originalState);
        id.regID(originalID, cloneID);
        
        System.out.println(">> Agent Cloning New Copy From "+ originalID);
        
        return cloneID;
    }
    
    public static String delete(AgentManager manager, String deleteID){
        if(!manager.getAutoMode()) return "";
        
        System.out.println(">> Start Agent Delete");
        
        IDManager id = manager.getIDManager();
        String originalID = id.getOrigID(deleteID);
        
        //Delete
        id.deleteID(originalID, deleteID);
        
        System.out.println(">> Agent Cloning Delete "+ deleteID);
        
        return deleteID;
    }
}