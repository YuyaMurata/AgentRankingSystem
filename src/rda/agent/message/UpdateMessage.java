package rda.agent.message;

import com.ibm.agent.exa.Message;
import rda.agent.queue.MessageObject;

public class UpdateMessage extends Message{

    //Compound Data
    public MessageObject messageData;

    public void setParams(MessageObject messageData){
        this.messageData = messageData;
    }
}