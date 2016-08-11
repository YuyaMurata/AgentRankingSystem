package rdarank.agent.rank.handler;

import java.sql.Timestamp;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import rda.agent.queue.MessageObject;
import rdarank.Rankagent;
import rdarank.Ranklog;
import rdarank.Ranktable;
import rdarank.agent.rank.message.UpdateRankMessage;

public class UpdateRankHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateRankMessage updateMsg = (UpdateRankMessage) msg;
        MessageObject msgObj = (MessageObject) updateMsg.messageData;

        // マスターエンティティを取得
        Rankagent agent = (Rankagent) getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();

        Map tableMap = new HashMap();
        for (Object data : msgObj.data) {
            Map user = (Map) data;
            tableMap.putAll(user);
        }

        for (Object id : tableMap.keySet()) {
            Ranktable table = agent.getRankTable(tx, (String) id);
            long d = 0;
            long count;

            if (table == null) {
                table = agent.createRankTable(tx, (String) id);
                table.setRank(tx, 1);
                count = 1;

                long n = agent.getTotalUsers(tx) + 1;
                agent.setTotalUsers(tx, n);
            } else {
                count = table.getConnectionCount(tx) + 1;
            }

            if((Long)tableMap.get(id) > table.getData(tx))
                table.setData(tx, (Long) tableMap.get(id));

            //Table Status
            table.setConnectionCount(tx, count);
            Long time = System.currentTimeMillis();
            Timestamp updateTime = new Timestamp(time);
            table.setCurrentTime(tx, time);
            table.setLastAccessTime(tx, updateTime);
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
        if (log == null) {
            log = agent.createLog(tx, "update");
        }

        // Update LastAccessTime
        Long time = System.currentTimeMillis();
        Timestamp updateTime = new Timestamp(time);
        log.setLastAccessTime(tx, updateTime);
        log.setCurrentTime(tx, time);

        //Long message = avgLatency;
        return 0L;
    }
}
