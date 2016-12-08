package rdarank.agent.rank.handler;

import java.sql.Timestamp;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;
import rdarank.Ranklog;
import rdarank.agent.rank.message.InitRankMessage;

public class InitRankHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        try {
            InitRankMessage initMsg = (InitRankMessage) msg;

            Rankagent rank = (Rankagent) getEntity();

            TxID tx = getTx();

            rank.setTotalUsers(tx, 0);
            
            rank.setConnectionCount(tx, 0L);

            Long time = System.currentTimeMillis();
            Timestamp registerTime = new Timestamp(time);

            Ranklog log = rank.createLog(tx, "init");

            log.setLastAccessTime(tx, registerTime);
            log.setCurrentTime(tx, time);

            //System.out.println("InitHandler of Agent:" + getAgentKey() + " was initialized");
            return "hello from agent:" + getAgentKey();
        } catch (Exception e) {
            throw e;
        }
    }
}
