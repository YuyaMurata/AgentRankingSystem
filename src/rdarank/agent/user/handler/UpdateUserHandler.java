package rdarank.agent.user.handler;

import java.sql.Timestamp;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;

import rda.agent.queue.MessageObject;

import rdarank.Useragent;
import rdarank.Userlog;
import rdarank.agent.user.message.UpdateUserMessage;

public class UpdateUserHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        UpdateUserMessage updateMsg = (UpdateUserMessage) msg;
        MessageObject msgObj = (MessageObject) updateMsg.messageData;
        
        Useragent agent = (Useragent)getEntity();
        
        TxID tx = getTx();
        
        long updateData = 0;
        for(Object data : msgObj.data){
            updateData =  updateData + (int)data;
        }
        agent.setData(tx, updateData + agent.getData(tx));
        
        //Agent Status
        //Connection
        agent.setConnectionCount(tx, agent.getConnectionCount(tx) + 1);
        //Message Latency
        agent.setMessageLatency(tx, msgObj.latency());
        //Agent Message Queue
        //agent.setMessageQueueLength(tx, msgObj.getLength());

        // Update Log Records
        Userlog log = agent.getLog(tx, "update");
        if(log == null)
            log = agent.createLog(tx, "update");
        
        // Update LastAccessTime
        Long time = System.currentTimeMillis();
        Timestamp updateTime = new Timestamp(time);
        log.setLastAccessTime(tx, updateTime);
        log.setCurrentTime(tx, time);
        
        //Long message = avgLatency;
        
        
        return 0L;
    }
}