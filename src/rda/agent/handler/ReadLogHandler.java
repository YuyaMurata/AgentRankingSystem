package rda.agent.handler;

import java.util.Iterator;

import rda.Log;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import com.ibm.agent.exa.entity.Entity;
import rda.Aggregateagent;
import rda.agent.logger.LogInfo;

public class ReadLogHandler extends MessageHandler{
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        // マスターエンティティを取得
        Aggregateagent agent = (Aggregateagent)getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();

        StringBuilder accessIDList = new StringBuilder();
        StringBuilder accessTimeList = new StringBuilder();
        Iterator<Entity> it = agent.getLogIterator(tx);
        while(it.hasNext()){
            Log log = (Log) it.next();
            //AccessLogの取得
            accessIDList.append(log.getAccessID(tx));
            accessIDList.append(",");

            //AccessTimeの取得
            accessTimeList.append(log.getLastAccessTime(tx));
            accessTimeList.append(",");
        }

        LogInfo info = new LogInfo(
            /*AgentID*/      agent.getAgentID(tx),
            /*ConnectCount*/ agent.getConnectionCount(tx),
            /*AccessLog*/ 	 accessIDList.toString(),
            /*AccessTime*/	 accessTimeList.toString());

        return info;
    }
}
