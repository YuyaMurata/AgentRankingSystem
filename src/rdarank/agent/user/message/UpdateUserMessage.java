package rdarank.agent.user.message;

import com.ibm.agent.exa.Message;
import java.util.List;
import rda.agent.template.MessageTemplate;

public class UpdateUserMessage extends Message{

    //Compound Data
    public MessageTemplate messageData;

    public void setParams(MessageTemplate messageData){
        this.messageData = messageData;
    }
}
