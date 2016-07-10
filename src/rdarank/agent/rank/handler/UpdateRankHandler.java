package rdarank.agent.rank.handler;

import java.sql.Timestamp;

import rda.Log;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.List;
import rda.agent.queue.MessageObject;
import rdarank.Rankagent;
import rdarank.Ranktable;
import rdarank.agent.rank.message.UpdateRankMessage;

public class UpdateRankHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateRankMessage updateMsg = (UpdateRankMessage) msg;

        // マスターエンティティを取得
        Rankagent rank = (Rankagent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        long updateData = 0;
        Long avgLatency = 0L;        
        for(MessageObject msgobj : (List<MessageObject>)updateMsg.messageData){
            updateData =  updateData + msgobj.data;
            avgLatency = avgLatency + msgobj.latency();
        }
        if(avgLatency > 0) avgLatency = avgLatency / updateMsg.data.size();
        
        Ranktable table = rank.getRankTable(tx, updateMsg.userID);
        if(table == null) rank.createRankTable(tx, updateMsg.userID);
        
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