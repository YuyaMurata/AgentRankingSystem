package rdarank.manager;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;

import java.io.Serializable;
import java.util.Collection;

import rda.agent.client.DistributedAgentConnection;
import rdarank.agent.user.manager.UserAgentManager;

public class ExecuteDataStream implements AgentExecutor, Serializable {

    private static final String AGENT_TYPE = "systemmanageragent";
    private static final String MESSAGE_TYPE = "dataGenerate";
    //private static AgentConnection agcon = AgentConnection.getInstance();

    public ExecuteDataStream() {
    }

    AgentKey agentKey;

    public ExecuteDataStream(AgentKey agentKey) {
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

            Message msg = (Message) MessageFactory.getFactory().getMessage(MESSAGE_TYPE);

            //Sync Message
            Object ret = null;//agentManager.sendMessage(agentKey, msg);
            agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            return e;
        }
    }

    public void stream() {
        UserAgentManager manager = UserAgentManager.getInstance();
        for (DistributedAgentConnection agcon : manager.getServer().getConnectionList()) {
            try {
                AgentClient client = agcon.getClient();
                AgentKey agentKey = new AgentKey(AGENT_TYPE, new Object[]{AGENT_TYPE});

                ExecuteDataStream executor = new ExecuteDataStream(agentKey);
                Object reply = client.execute(agentKey, executor);

                //System.out.println(reply);
                agcon.returnConnection(client);
            } catch (AgentException ex) {
            }
        }
    }
}
