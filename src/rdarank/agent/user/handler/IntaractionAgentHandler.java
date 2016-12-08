package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Useragent;
import rdarank.agent.user.data.UserData;
import rda.extension.intaraction.AgentIntaractionExtension;

public class IntaractionAgentHandler extends MessageHandler{
    private AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        Useragent agent = (Useragent)getEntity();
        
        TxID tx = getTx();
        
        //UserData
        UserData userData = new UserData(agent.getUserID(tx), agent.getCommunicationID(tx));
        userData.setData(agent.getData(tx));
        
        //Agent Communication
        extension.communicateAgent(userData);
        
        return null;
    }
    
}
