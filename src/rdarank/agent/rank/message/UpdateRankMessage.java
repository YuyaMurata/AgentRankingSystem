package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;
import java.util.List;

public class UpdateRankMessage extends Message{
    //Compound Data
    public List messageData;

    public void setParams(List messageData){
        this.messageData = messageData;
    }
}
