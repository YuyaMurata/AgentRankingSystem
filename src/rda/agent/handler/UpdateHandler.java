package rda.agent.handler;

import java.sql.Timestamp;

import rda.Log;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.List;
import rda.Aggregateagent;
import rda.agent.queue.MessageObject;
import rda.agent.message.UpdateMessage;

public class UpdateHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateMessage updateMsg = (UpdateMessage) msg;
        MessageObject msgObj = (MessageObject) updateMsg.messageData;
        
        // マスターエンティティを取得
        Aggregateagent agent = (Aggregateagent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        for(Object data : (List)msgObj.data){
            updateData =  updateData + (int)data;
        }
        
        agent.setData(tx, agent.getData(tx)+updateData);
        
        //Agent Status
        //Connection
        agent.setConnectionCount(tx, agent.getConnectionCount(tx) + 1);
        //Message Latency
        agent.setMessageLatency(tx, msgObj.latency());
        //Agent Message Queue
        //agent.setMessageQueueLength(tx, msgObj.getLength());

        // Update Log Records
        Log log = agent.getLog(tx, "update");
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