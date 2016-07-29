/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;
import rdarank.agent.rank.message.QueryRankingMessage;

/**
 *
 * @author kaeru
 */
public class QueryRankingHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        QueryRankingMessage updateMsg = (QueryRankingMessage) msg;

        // マスターエンティティを取得
        Rankagent agent = (Rankagent) getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();

        return 0L;
    }

}
