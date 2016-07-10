package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;
import java.util.List;
import rda.agent.template.MessageTemplate;

public class UpdateRankMessage extends Message{
    //Compound Data
    public MessageTemplate messageData;

    public void setParams(MessageTemplate messageData){
        this.messageData = messageData;
    }
}
