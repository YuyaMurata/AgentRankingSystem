package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import com.ibm.agent.exa.entity.Entity;
import java.util.HashMap;
import java.util.Iterator;
import rdarank.Rankagent;
import rdarank.Ranktable;
import rdarank.agent.rank.message.QueryRankingMessage;

public class QueryRankingHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        QueryRankingMessage queryMsg = (QueryRankingMessage) msg;

        Rankagent agent = (Rankagent) getEntity();

        TxID tx = getTx();
        
        Ranktable table = agent.getRankTable(tx, queryMsg.id);
        if(queryMsg.type.equals("")){
            return table.getRank(tx);
        }else if(queryMsg.type.equals("delete")){
            table.remove(tx);
        }else if(queryMsg.type.equals("sync")){
            HashMap map = new HashMap();
            map.put(table.getUserID(tx), table.getLastAccessTime(tx)+","+table.getRank(tx));
            long rank = table.getRank(tx);
            
            Iterator<Entity> it = agent.getRankTableIterator(tx);
            while(it.hasNext()){
                table = (Ranktable) it.next();
                if((table.getRank(tx)) > rank)
                    map.put(table.getUserID(tx), table.getLastAccessTime(tx)+","+table.getRank(tx));
            }
            
            return map;
        }
        
        return null;
    }

}
