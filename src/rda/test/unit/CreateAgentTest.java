package rda.test.unit;

import rda.agent.rank.creator.CreateRankAgent;
import rda.agent.user.creator.CreateUserAgent;
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
    
    //UserAgentのCreatorで作成
    public static void createUserAgents(Integer numberOfUserAgents){
        CreateUserAgent userAgent = new CreateUserAgent();
        
        for(int i=0; i < numberOfUserAgents; i++)
            userAgent.create("U#00"+i);
    }
    
    //RankAgentのCreatorで作成
    public static void createRankAgents(Integer numberOfRankAgents){
        CreateRankAgent rankAgent = new CreateRankAgent();
        
        for(int i=0; i < numberOfRankAgents; i++)
            rankAgent.create("R#00"+i, QUEUE_LENGTH, 10L, 10L);
        
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