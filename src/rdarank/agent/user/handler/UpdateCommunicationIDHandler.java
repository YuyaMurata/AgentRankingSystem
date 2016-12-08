package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Useragent;
import rdarank.agent.user.message.UpdateCommunicationIDMessage;

public class UpdateCommunicationIDHandler  extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        UpdateCommunicationIDMessage updateMsg = (UpdateCommunicationIDMessage)msg;
        
        Useragent agent = (Useragent)getEntity();
        
        TxID tx = getTx();
        
        agent.setCommunicationID(tx, updateMsg.communicationID);
        
        return 0L;
    }
    
}
