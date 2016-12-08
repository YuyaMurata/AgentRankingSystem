package rda.extension.intaraction.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.SimpleMessage;

import rda.agent.queue.MessageObject;
import rda.extension.intaraction.AgentIntaractionExtension;
import rda.extension.manager.SystemManagerExtension;

public class IntaractionAgentHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        SimpleMessage smsg = (SimpleMessage)msg;
        MessageObject trans = (MessageObject) smsg.get("message");

        //Intaraction Agent
        while(!AgentIntaractionExtension.getInstance().transport(trans) 
                    && SystemManagerExtension.getInstance().getState()){
            try{
                Thread.sleep(10L);
            }catch(InterruptedException e){
            }
            //System.out.println("Transport Int : "+trans.toString());
        }
        
        return "-- AgentIntaraction Transport Message" + trans.toString() + "-->";
    }   
}
