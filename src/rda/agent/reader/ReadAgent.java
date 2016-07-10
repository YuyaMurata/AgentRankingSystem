package rda.agent.reader;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import com.ibm.agent.exa.AgentException;

import rda.agent.client.AgentConnection;

public class ReadAgent implements AgentExecutor, Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 6385839930575941905L;
	
    private static final String AGENT_TYPE = "aggregateagent";
    private static final String MESSAGE_TYPE = "readAgent";

    public ReadAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    AgentKey agentKey;
    public ReadAgent(AgentKey agentKey) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
    }

    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        if (results == null) return null;
        
        Object[] ret = results.toArray();
        return ret[0];
    }

    @Override
    public Object execute() {
        // TODO 自動生成されたメソッド・スタブ
        try{
            AgentManager agentManager = AgentManager.getAgentManager();

            MessageFactory factory = MessageFactory.getFactory();
            Message msg = factory.getMessage(MESSAGE_TYPE);

            Object ret = agentManager.sendMessage(agentKey, msg);

            return ret;
        }catch(IllegalAccessException | InstantiationException e){
            return e;
        }
    }

    public AgentInfo read(String id) {
        AgentConnection ag = AgentConnection.getInstance();
        AgentClient client = ag.getConnection();
        
        try{
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{id});

            ReadAgent executor = new ReadAgent(agentKey);

            Object reply = client.execute(agentKey, executor);

            AgentInfo info = null;
            if (reply != null) {
                info = (AgentInfo)reply;
                System.out.println(agentKey + "[");
                System.out.println("    " + info.toString());
                System.out.println("]");
            } else {
                System.out.println(id + " was not found");
            }
            
            return info;
        }catch(AgentException e){
            return null;
        } finally {
            ag.returnConnection(client);
        }
    }
}
