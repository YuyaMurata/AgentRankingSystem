/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.SimpleMessage;

import rda.agent.queue.MessageObject;
import rda.extension.intaraction.AgentIntaractionExtension;

/**
 *
 * @author kaeru
 */
public class IntaractionAgentHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        SimpleMessage smsg = (SimpleMessage)msg;
        MessageObject trans = (MessageObject) smsg.get("message");
        
        //Intaraction Agent
        while(!AgentIntaractionExtension.getInstance().transport(trans)){
            Thread.sleep(10L);
        }
        
        return 0L;
    }
    
}
