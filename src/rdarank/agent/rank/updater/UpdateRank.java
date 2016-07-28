package rdarank.agent.rank.updater;

import java.util.Collection;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageObject;
import rda.agent.template.AgentType;
import rda.agent.template.MessageTemplate;
import rdarank.agent.rank.message.UpdateRankMessage;

public class UpdateRank extends AgentType {
    /**
    *
    */
    private static final long serialVersionUID = -42450933759745980L;
    private static final String AGENT_TYPE = "rankagent";
    private static final String MESSAGE_TYPE = "updateRankAgent";
    private static AgentConnection agcon;
    private String agID;

    public UpdateRank() {
    }
    
    public UpdateRank(String agID){
        this.agID = agID;
        this.agcon = AgentConnection.getInstance();
        this.agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});
    }

    AgentKey agentKey;
    MessageTemplate data;
    public UpdateRank(AgentKey agentKey, MessageTemplate data) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
        this.data = data;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(agentKey);
        out.writeObject(data);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.agentKey = (AgentKey) in.readObject();
        this.data = (MessageTemplate) in.readObject();
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
            UpdateRankMessage msg = (UpdateRankMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams(data);

            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);

            return ret;
        } catch (IllegalAccessException | InstantiationException e) {
            // TODO 自動生成された catch ブロック
            return 0L;
        }
    }

    @Override
    public void sendMessage(Object data){
        if(data == null) return;
            
        try {
            AgentClient client = agcon.getConnection();
                
            UpdateRank executor = new UpdateRank(agentKey, (MessageObject)data);
            
            Object reply = client.execute(agentKey, executor);
            
            agcon.returnConnection(client);
        } catch (AgentException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public String getID() {
        return this.agID;
    }
}