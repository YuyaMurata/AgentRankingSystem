package rdarank.agent.rank.handler;

import java.sql.Timestamp;


import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.List;
import rda.agent.queue.MessageObject;
import rdarank.Rankagent;
import rdarank.Ranklog;
import rdarank.Ranktable;
import rdarank.agent.rank.message.UpdateRankMessage;

public class UpdateRankHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateRankMessage updateMsg = (UpdateRankMessage) msg;
        MessageObject msgObj = (MessageObject) updateMsg.messageData;
        
        // マスターエンティティを取得
        Rankagent agent = (Rankagent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        for(Object data : (List)msgObj.data){
            String userID = "test#00";
            
            long d = 0;
            
            //UpdateRank Object
            Ranktable table = agent.getRankTable(tx, userID);
            if(table == null){
                table = agent.createRankTable(tx, userID);
                long n = agent.getTotalUsers(tx) + 1;
                agent.setTotalUsers(tx, n);
                
                //Test Data
                d = (int)data;
            }else {
                //Test Data
                d = table.getData(tx) + (int)data;
            }
            
            table.setData(tx, d);
        }
        
        //Agent Status
        //Connection
        agent.setConnectionCount(tx, agent.getConnectionCount(tx) + 1);
        //Message Latency
        agent.setMessageLatency(tx, msgObj.latency());
        //Agent Message Queue
        //agent.setMessageQueueLength(tx, msgObj.getLength());

        // Update Log Records
        Ranklog log = agent.getLog(tx, "update");
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