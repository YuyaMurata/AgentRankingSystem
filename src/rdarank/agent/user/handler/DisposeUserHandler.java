package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;

public class DisposeUserHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        try {
            disposeAgent();
            return getAgentKey() + " was disposed";
        } catch (Exception e) {
            throw e;
        }
    }
}
