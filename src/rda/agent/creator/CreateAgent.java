package rda.agent.creator;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;

import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageQueue;
import rda.agent.updater.UpdateAgent;
import rda.agent.message.InitMessage;
import rda.manager.AgentMessageQueueManager;

public class CreateAgent implements AgentExecutor, Serializable{
    /**
    * 
    */
    private static final long serialVersionUID = 856847026370330593L;
    public static final String AGENT_TYPE = "aggregateagent";
    static final String MESSAGE_TYPE = "initAgent";
	
    public CreateAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    AgentKey agentKey;
    String condition;
    public CreateAgent(AgentKey agentKey, String condition) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
        this.condition = condition;
    }
	
    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        Object[] ret = results.toArray();
        return ret[0];
    }

    @Override
    public Object execute() {
        // TODO 自動生成されたメソッド・スタブ
        try {
            AgentManager agentManager = AgentManager.getAgentManager();
            if (agentManager.exists(agentKey)) {
                return "agent (" + agentKey + ") already exists";
            }
		
            agentManager.createAgent(agentKey);
	
            MessageFactory factory = MessageFactory.getFactory();
            InitMessage msg = (InitMessage)factory.getMessage(MESSAGE_TYPE);
		
            msg.setParams(condition);
		
            Object ret = agentManager.sendMessage(agentKey, msg);
		
            return ret;
        } catch (AgentException | IllegalAccessException | InstantiationException e) {
        }
		
        return null;
    }
	
    public void create(String agID, Integer size, Long queuewait, Long agentwait){
        try {
            AgentConnection agconn = AgentConnection.getInstance();            
            AgentClient client = agconn.getConnection();
            
            agentKey = new AgentKey(AGENT_TYPE,new Object[]{agID});
            
            //Create Agent
            CreateAgent executor = new CreateAgent(agentKey, "Aggregate Conditios :"+agID);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
            
            agconn.returnConnection(client);
            
            //Create AgentQueue
            MessageQueue mq = new MessageQueue(agID, size, queuewait, agentwait);
            mq.setAgentType(new UpdateAgent(agID));
            AgentMessageQueueManager.getInstance().register(mq);
            
            //return mq;
        } catch (AgentException e) {
            //return null;
        }
    }
}