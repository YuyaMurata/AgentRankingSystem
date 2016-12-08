package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;

public class ReadRankHandler extends MessageHandler {

    @Override
    public Object onMessage(Message arg0) throws Exception {
        Rankagent rank = (Rankagent) getEntity();

        TxID tx = getTx();

        return rank.getRankID(tx);
    }

}
