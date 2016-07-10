package rdarank.agent.rank.creator;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageQueue;
import rdarank.agent.rank.updater.UpdateRank;
import rdarank.agent.user.message.InitUserMessage;
//import rda.manager.AgentMessageQueueManager;

public class CreateRankAgent implements AgentExecutor, Serializable{
    /**
    * 
    */
    private static final long serialVersionUID = 856847026370330593L;
    public static final String AGENT_TYPE = "useragent";
    static final String MESSAGE_TYPE = "initUserAgent";
	
    public CreateRankAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    AgentKey agentKey;
    HashMap<String, String> prof;
    public CreateRankAgent(AgentKey agentKey, HashMap<String, String> prof) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
        this.prof = prof;
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
            InitUserMessage msg = (InitUserMessage)factory.getMessage(MESSAGE_TYPE);
		
            msg.setParams(prof.get("Name"), prof.get("Sex"), 
                        prof.get("Age"), prof.get("Address"));
		
            Object ret = agentManager.sendMessage(agentKey, msg);
		
            return ret;
        } catch (AgentException | IllegalAccessException | InstantiationException e) {
        }
		
        return null;
    }
	
    public Object create(String agID, Integer size, Long queuewait, Long agentwait){
        try {
            AgentConnection ag = AgentConnection.getInstance();
            
            AgentClient client = ag.getConnection();
                
            agentKey = new AgentKey(AGENT_TYPE,new Object[]{agID});
            
            //Create Agent
            CreateRankAgent executor = new CreateRankAgent(agentKey, prof);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
            
            ag.returnConnection(client);
            
            //Create AgentQueue
            MessageQueue mq = new MessageQueue(agID, size, queuewait, agentwait);
            mq.setAgentType(new UpdateRank(agID));
            
            return mq;
        } catch (AgentException e) {
            return null;
        }
    }
}