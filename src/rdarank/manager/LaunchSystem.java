/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.SimpleMessage;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import rda.agent.client.AgentConnection;
import rda.agent.client.DistributedAgentConnection;
import rdarank.server.DistributedServerConnection;

/**
 *
 * @author kaeru
 */
public class LaunchSystem implements AgentExecutor, Serializable {
    private static final long serialVersionUID = -138177910350145867L;
    
    private static final String AGENT_TYPE ="systemmanageragent";
    private static final String MESSAGE_TYPE = "launchSystem";
    //private static AgentConnection agcon = AgentConnection.getInstance();
    private static DistributedAgentConnection agcon;
    
    public LaunchSystem() {
        agcon = DistributedServerConnection.getInstance().getConnection(0);
    }
    
    AgentKey agentKey;
    Map prop;
    public LaunchSystem(AgentKey agentKey, Map prop) {
        this.agentKey = agentKey;
        this.prop = prop;
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
            
            SimpleMessage msg = (SimpleMessage)MessageFactory.getFactory().getMessage(MESSAGE_TYPE);
            msg.set("property", prop);
            
            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return e;
        }
    }
    
    public void launch(Map prop){
        try{
            AgentClient client = agcon.getConnection();
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{AGENT_TYPE});
            
            //System.out.println("Agent Key = "+agentKey);
            
            LaunchSystem executor = new LaunchSystem(agentKey, prop);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println(reply);
            
            agcon.returnConnection(client);
        } catch (AgentException ex) {
            ex.printStackTrace();
        }
    }
}
