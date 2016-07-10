package rda.agent.message;

import com.ibm.agent.exa.Message;
import rda.agent.template.MessageTemplate;

public class UpdateMessage extends Message{

    //Compound Data
    public MessageTemplate messageData;

    public void setParams(MessageTemplate messageData){
        this.messageData = messageData;
    }
}