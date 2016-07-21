package rdarank.agent.user.handler;

import java.sql.Timestamp;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.queue.MessageObject;
import rdarank.Useragent;
import rdarank.Userlog;
import rdarank.agent.user.message.UpdateUserMessage;
import rdarank.extension.AgentIntaractionExtension;

public class UpdateUserHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateUserMessage updateMsg = (UpdateUserMessage) msg;
        MessageObject msgObj = (MessageObject) updateMsg.messageData;
        
        // マスターエンティティを取得
       Useragent agent = (Useragent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        for(Object data : (List)msgObj.data){
            updateData =  updateData + (int)data;
        }
        updateData = updateData + agent.getData(tx);
        agent.setData(tx, updateData);
        
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
        
        //Agent Communication
        /*AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();
        Map dataMap = new HashMap();
        dataMap.put("id", agent.getUserID(tx));
        dataMap.put("data", updateData);
        extension.communicateAgent(new MessageObject(agent.getCommunicationID(tx), dataMap));
        */
        return 0L;
    }
}