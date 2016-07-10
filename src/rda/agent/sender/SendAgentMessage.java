/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.sender;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.List;
import rda.agent.client.AgentConnection;
import rda.agent.queue.PutMessage;
import rda.agent.template.AgentType;
import rda.window.Window;

/**
 *
 * @author kaeru
 */
public class SendAgentMessage extends AgentType{
    private static final long serialVersionUID = -853123152041L;
    
    private static final String MESSAGE_TYPE = "putmessage";
    private static final String AGENT_TYPE = "aggregateagent";
    private String agID;

    public SendAgentMessage() {
    }

    AgentKey agentKey;
    List msgPack;
    public SendAgentMessage(AgentKey agentKey, List msgPack) {
        this.agentKey = agentKey;
        this.msgPack = msgPack;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(agentKey);
        out.writeObject(msgPack);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.agentKey = (AgentKey) in.readObject();
        this.msgPack = (List) in.readObject();
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
            PutMessage msg = (PutMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams(msgPack);

            //ASync Message
            Object ret = null;// agentManager.sendMessage(agentKey, msg);
            agentManager.putMessage(agentKey, msg);
            
            return ret;
        } catch (IllegalAccessException | InstantiationException ex) {
            return 0L;
        } catch (AgentException ex) {
            return 0L;
        }
    }
    
    @Override
    public void sendMessage(Object data) {
        if(data == null) return;
        
        Window win = (Window) data;
        this.agID = win.getDestID();
        
        try {
            AgentConnection agconn = AgentConnection.getInstance();
            AgentClient client = agconn.getConnection();
            
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{win.getDestID()});
            SendAgentMessage executor = new SendAgentMessage(agentKey, win.unpack());
            
            //Test
            //System.out.println(" >> SendMessage Agent = " + win.getDestID() + " : " + win.unpack().size());
            
            //Async Message
            Object reply = client.execute(agentKey, executor);
            
            //if(reply != null)
            //    System.out.println(" >> PutMessage Reply = "+agentKey +":"+ reply);
            
            agconn.returnConnection(client);
        } catch (AgentException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public String getID() {
        return this.agID;
    }
}
