package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;
import rdarank.agent.rank.message.UpdateRankingMessage;

public class UpdateRankingHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        UpdateRankingMessage updateMsg = (UpdateRankingMessage) msg;

        // マスターエンティティを取得
        Rankagent agent = (Rankagent) getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();

        return 0L;
    }

}
