package rdarank.agent.user.updater;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import rda.agent.queue.MessageObject;
import rda.agent.template.AgentType;

import rdarank.agent.user.message.UpdateUserMessage;

public class UpdateUserEx extends AgentType{
    /**
    *
    */
    private static final String AGENT_TYPE = "useragent";
    private static final String MESSAGE_TYPE = "updateUserAgent";
    private String agID;

    public UpdateUserEx(String agID){
        this.agID = agID;
        this.agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});
    }

    AgentKey agentKey;
    @Override
    public void sendMessage(Object data){
        if(data == null) return;
            
        AgentManager agentManager = AgentManager.getAgentManager();
        try {
            MessageFactory factory = MessageFactory.getFactory();
            UpdateUserMessage msg = (UpdateUserMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams((MessageObject)data);

            //Sync Message
            Object ret = agentManager.sendMessage(agentKey, msg);
            //agentManager.putMessage(agentKey, msg);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    @Override
    public String getID() {
        return this.agID;
    }
}