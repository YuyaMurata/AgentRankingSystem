/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.manager;

import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class UnitTestManager extends TestParameter{
    private static UnitTestManager manager = new UnitTestManager();
    private IDManager id;
    public static UnitTestManager getInstance(){
        return manager;
    }
    
    public void prepareManager(){
        AgentMessageQueueManager ag = AgentMessageQueueManager.getInstance();
        id = new IDManager(idParam);
        
        ag.initAgentMessageQueueManager(agentMQParam);
        ag.setIDManager(id);
    }
    
    public void createPseudoAgents(){
        for(int i=0; i < NUMBER_OF_AGENTS; i++)
            id.initRegID(id.genID());
    }
}
