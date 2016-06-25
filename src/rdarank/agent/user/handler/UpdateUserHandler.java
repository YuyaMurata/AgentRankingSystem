package rdarank.agent.user.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.Useragent;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.List;
import rdarank.agent.queue.MessageObject;
import rdarank.agent.user.message.UpdateUserMessage;

public class UpdateUserHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateUserMessage updateMsg = (UpdateUserMessage) msg;

        // マスターエンティティを取得
        Useragent user = (Useragent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        Long avgLatency = 0L;        
        for(MessageObject msgobj : (List<MessageObject>)updateMsg.data){
            updateData =  updateData + msgobj.data;
            avgLatency = avgLatency + msgobj.latency();
        }
        if(avgLatency > 0) avgLatency = avgLatency / updateMsg.data.size();
        
        user.setData(tx, user.getData(tx)+updateData);

        long updateCount = user.getConnectionCount(tx) + 1;
        user.setConnectionCount(tx, updateCount);

        // Update Log Records
        Log log = user.getLog(tx, "update");
        if(log == null)
            log = user.createLog(tx, "update");
        
        // Update LastAccessTime
        Long time = System.currentTimeMillis();
        Timestamp updateTime = new Timestamp(time);
        log.setLastAccessTime(tx, updateTime);
        log.setCurrentTime(tx, time);
        
        Long message = avgLatency;
        
        return message;
    }
}