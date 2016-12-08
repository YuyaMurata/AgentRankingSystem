package rdarank.agent.rank.handler;

import java.util.Iterator;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import com.ibm.agent.exa.entity.Entity;
import rdarank.Rankagent;
import rdarank.Ranklog;
import rdarank.agent.rank.logger.LogInfo;

public class ReadLogRankHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        Rankagent rank = (Rankagent) getEntity();

        TxID tx = getTx();

        StringBuilder accessIDList = new StringBuilder();
        StringBuilder accessTimeList = new StringBuilder();
        Iterator<Entity> it = rank.getLogIterator(tx);
        while (it.hasNext()) {
            Ranklog log = (Ranklog) it.next();
            
            accessIDList.append(log.getAccessID(tx));
            accessIDList.append(",");

            accessTimeList.append(log.getLastAccessTime(tx));
            accessTimeList.append(",");
        }

        LogInfo info = new LogInfo(
                /*rankID*/rank.getRankID(tx),
                /*connectCount*/ rank.getConnectionCount(tx),
                /*accessLog*/ accessIDList.toString(),
                /*accessTime*/ accessTimeList.toString());

        return info;
    }

}
