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
import rda.agent.client.DistributedAgentConnection;
import rda.extension.manager.message.CreateAgentMessage;
import rdarank.server.DistributedServerConnection;

/**
 *
 * @author kaeru
 */
public class LaunchCreateAgent implements AgentExecutor, Serializable {
    private static final long serialVersionUID = -1381710350145867L;
    
    private static final String AGENT_TYPE ="systemmanageragent";
    private static final String MESSAGE_TYPE = "createAgent";
    //private static AgentConnection agcon = AgentConnection.getInstance();
    private static DistributedAgentConnection agcon;
    
    public LaunchCreateAgent() {
    }
    
    AgentKey agentKey;
    String agentType;
    Map agentGroup;
    public LaunchCreateAgent(AgentKey agentKey,String agenttype, Map agentGroup) {
        this.agentKey = agentKey;
        this.agentType = agenttype;
        this.agentGroup = agentGroup;
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
            
            MessageFactory factory = MessageFactory.getFactory();
            CreateAgentMessage msg = (CreateAgentMessage) factory.getMessage(MESSAGE_TYPE);
            msg.setParams(agentType, agentGroup);
            
            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return e;
        }
    }
    
    public void create(AgentClient client, String agentType, Map agentGroup){
        try{
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{AGENT_TYPE});
            
            //System.out.println("Agent Key = "+agentKey);
            
            LaunchSystem executor = new LaunchSystem(agentKey, agentGroup);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println(reply);
            
        } catch (AgentException ex) {
            ex.printStackTrace();
        }
    }
}
