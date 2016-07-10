package rda.agent.logger;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import rda.agent.client.AgentConnection;

public class ReadLogAgent implements AgentExecutor, Serializable{
    /**
     * 
    */
    private static final long serialVersionUID = 1803475224433854533L;
    private static final String AGENT_TYPE = "aggregateagent";
    private static final String MESSAGE_TYPE = "readLogAgent";

    public ReadLogAgent() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    AgentKey agentKey;
    public ReadLogAgent(AgentKey agentKey) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
    }

    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        if (results == null) {
            return null;
        }
    
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

    public LogInfo get(String id) {
        AgentConnection ag = AgentConnection.getInstance();
        AgentClient client = ag.getConnection();
        
        try{
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{id});

            ReadLogAgent executor = new ReadLogAgent(agentKey);

            Object reply = client.execute(agentKey, executor);

            LogInfo info = null;
            if (reply != null) {
                info = (LogInfo)reply;
    				
                System.out.println(agentKey + "[");
                System.out.println("    " + info.toString());
                System.out.println("]");
            } else {
                System.out.println(id + " was not found");
            }
            
            return info;
        }catch(Exception e){
            return null;
        }finally {
            ag.returnConnection(client);
        }
    }
}