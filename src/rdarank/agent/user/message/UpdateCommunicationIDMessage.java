package rdarank.agent.user.message;

import com.ibm.agent.exa.Message;

public class UpdateCommunicationIDMessage extends Message{
    //New Communication ID
    public String communicationID;

    public void setParams(String communicationID){
        this.communicationID = communicationID;
    }
}
