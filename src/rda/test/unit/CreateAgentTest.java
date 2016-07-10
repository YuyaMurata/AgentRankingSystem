package rda.test.unit;

import rda.agent.creator.CreateAgent;
import rda.manager.AgentMessageQueueManager;
import rda.test.manager.UnitTestManager;
import rda.test.msg.MessageBox;
import rda.test.param.TestParameter;

public class CreateAgentTest extends TestParameter{
    public static void main(String[] args) {
        MessageBox.use("CreateAgents Test");
        
        //Prepare
        UnitTestManager.getInstance().prepareManager();
        
        //User Agents
        //createUserAgents(NUMBER_OF_AGENTS);
        
        //Rank Agents
        //createRankAgents(NUMBER_OF_AGENTS);
        
        //Agents From Manager
        createAgentsFromManager(NUMBER_OF_AGENTS);
        
        createStop();
    }
    
    //RankAgentのCreatorで作成
    public static void createRankAgents(Integer numberOfRankAgents){
        CreateAgent agent = new CreateAgent();
        
        for(int i=0; i < numberOfRankAgents; i++)
            agent.create("R#00"+i, QUEUE_LENGTH, 10L, 10L);
        
    }
    
    //AgentMQManagerで生成
    public static void createAgentsFromManager(Integer numberOfAgents){
        AgentMessageQueueManager.getInstance().createNumberOfAgents(numberOfAgents);    
    }
    
    private static void createStop(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        
        AgentMessageQueueManager.getInstance().doShutdown();
    }
}