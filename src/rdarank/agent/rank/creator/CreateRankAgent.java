package rdarank.agent.rank.creator;

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

import rdarank.agent.rank.message.InitRankMessage;
import rdarank.agent.rank.updater.UpdateRank;
import rdarank.agent.rank.manager.RankAgentManager;

public class CreateRankAgent implements AgentExecutor, Serializable{
    /**
    * 
    */
    private static final long serialVersionUID = 111847026370330593L;
    public static final String AGENT_TYPE = "rankagent";
    static final String MESSAGE_TYPE = "initRankAgent";
	
    public CreateRankAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    AgentKey agentKey;
    String mes;
    public CreateRankAgent(AgentKey agentKey, String mes) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
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
            
            System.out.println("RankAgent AgentManager"+agentKey);
		
            agentManager.createAgent(agentKey);
	
            System.out.println("Create RankAgent AgentManager");
            
            MessageFactory factory = MessageFactory.getFactory();
            InitRankMessage msg = (InitRankMessage)factory.getMessage(MESSAGE_TYPE);
            
            System.out.println("RankAgent Message");
            
            //Set InitMessage Data
            msg.setParams(mes);
		
            Object ret = agentManager.sendMessage(agentKey, msg);
            
            System.out.println("RankAgent AgentManager : "+ret);
            
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
            
            System.out.println("AgentKey Generate : "+agentKey);
            
            //Create Agent
            CreateRankAgent executor = new CreateRankAgent(agentKey, "No Message");
            Object reply = client.execute(agentKey, executor);
            
            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
            
            agconn.returnConnection(client);
            
            //Create AgentQueue
            MessageQueue mq = new MessageQueue(RankAgentManager.getInstance(), agID, size, queuewait, agentwait);
            mq.setAgentType(new UpdateRank(agID));
            RankAgentManager.getInstance().register(mq);
            
            //return mq;
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
    }
}