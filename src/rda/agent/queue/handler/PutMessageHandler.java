/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.Aggregateagent;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.agent.queue.PutMessage;
import rda.agent.updater.UpdateAgent;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author kaeru
 */
public class PutMessageHandler extends MessageHandler{
    private MessageQueue mq;
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        PutMessage putMsg = (PutMessage)msg;
        
        Aggregateagent agent = (Aggregateagent)getEntity();
        TxID tx = getTx();
        String id = agent.getAgentID(tx);
        
        //Get MessageQueue
        //MessageQueue mq = (MessageQueue)manager.getMQMap().get(id);
        
        //System.out.println(" >> Agent Put MessageQueue Check!! "+ id +" - "+putMsg.toString());
        
        //Put Message
        if(mq == null){
            mq = new MessageQueue(id, 1000, 100L, 10L);
            mq.setAgentType(new UpdateAgent(id));
            manager.register(mq);
        }
        
        agent.setMessageQueueLength(tx, mq.getQueueLenght());

        mq.put(putMsg.msgPack);
        
        return mq.getID() + " : success put messages = "+mq.getQueueLenght();
    } 
}