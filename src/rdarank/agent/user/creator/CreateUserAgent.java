package rdarank.agent.user.creator;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;

import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageQueue;
import rda.data.profile.ProfileGenerator;
import rda.manager.TestCaseManager;

import rdarank.agent.user.message.InitUserMessage;
import rdarank.agent.user.updater.UpdateUser;
import rdarank.agent.user.manager.UserAgentManager;

public class CreateUserAgent implements AgentExecutor, Serializable{
    /**
    * 
    */
    private static final long serialVersionUID = 856847026370330593L;
    public static final String AGENT_TYPE = "useragent";
    static final String MESSAGE_TYPE = "initUserAgent";
	
    public CreateUserAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    AgentKey agentKey;
    Map prof;
    public CreateUserAgent(AgentKey agentKey, Map prof) {
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
            
            msg.setParams(  (String)prof.get("Name"), (String)prof.get("Sex"), 
                            ((Integer) prof.get("Age")).toString(),  (String)prof.get("Address"),
                            (String) prof.get("Agent"));
		
            Object ret = agentManager.sendMessage(agentKey, msg);
		
            return ret;
        } catch (AgentException | IllegalAccessException | InstantiationException e) {
            return e;
        }
    }
	
    public void create(String agID, Integer size, Long queuewait, Long agentwait){
        try {
            AgentConnection agconn = AgentConnection.getInstance();            
            AgentClient client = agconn.getClient();
            
            agentKey = new AgentKey(AGENT_TYPE,new Object[]{agID});
            
            //Profile Generator
            ProfileGenerator profgen = TestCaseManager.getInstance().profgen;
            Map profile = profgen.getProf(agID);
            
            //Create Agent
            CreateUserAgent executor = new CreateUserAgent(agentKey, profile);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
            
            agconn.returnConnection(client);
            
            //Create AgentQueue
            MessageQueue mq = new MessageQueue(UserAgentManager.getInstance(), agID, size, queuewait, agentwait);
            mq.setAgentType(new UpdateUser(agID));
            UserAgentManager.getInstance().register(mq);
            
            //return mq;
        } catch (AgentException e) {
            //return null;
        }
    }
}