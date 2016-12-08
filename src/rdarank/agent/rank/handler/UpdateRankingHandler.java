package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.Iterator;
import rdarank.Rankagent;
import rdarank.Ranktable;
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
        
        Iterator it = agent.getRankTableIterator(tx);
        while(it.hasNext()){
            Ranktable table = (Ranktable) it.next();
            Iterator subit = agent.getRankTableIterator(tx);
            Long rank = 1L;
            while(subit.hasNext()){
                Ranktable subtable = (Ranktable) subit.next();
                if(table.getUserID(tx).equals(subtable.getUserID(tx))) continue;
                if(table.getData(tx) < subtable.getData(tx)) rank++;
            }
            table.setRank(tx, rank);
        }
        
        return 0L;
    }

}
