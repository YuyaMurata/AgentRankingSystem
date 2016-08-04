/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.SimpleMessage;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import rda.agent.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class Transport implements AgentExecutor, Externalizable{
    private static final String AGENT_TYPE ="intaractionmanageragent";
    private static final String MESSAGE_TYPE="intaractionAgent";

    public Transport(){}
    
    AgentKey agentKey;
    MessageObject msg;
    public Transport(AgentKey agentKey, MessageObject msg) {
        this.agentKey = agentKey;
        this.msg = msg;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(agentKey);
        out.writeObject(msg);
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.agentKey = (AgentKey) in.readObject();
        this.msg = (MessageObject) in.readObject();
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
            msg.set("message", msg);

            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return 0L;
        }
    }
    
    public void sendMessage(AgentClient client, MessageObject msg){
        try{
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{AGENT_TYPE});
            
            Transport executor = new Transport(agentKey, msg);
            Object reply = client.execute(agentKey, executor);
            
            System.out.println(reply);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
