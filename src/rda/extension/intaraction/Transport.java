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
                
            SimpleMessage smsg = (SimpleMessage)MessageFactory.getFactory().getMessage(MESSAGE_TYPE);
            smsg.set("message", msg);

            //Sync Message
            //Object ret = agentManager.sendMessage(agentKey, smsg);
            agentManager.putMessage(agentKey, smsg);

            //return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return e;
        } catch (AgentException ex) {
            return ex;
        }
        
        return null;
    }
    
    public void sendMessage(AgentClient client, MessageObject msg){
        try{
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{AGENT_TYPE});
            
            Transport executor = new Transport(agentKey, msg);
            Object reply = client.execute(agentKey, executor);
            
            //System.out.println(reply);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
