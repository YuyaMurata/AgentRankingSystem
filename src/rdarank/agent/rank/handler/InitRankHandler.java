package rdarank.agent.rank.handler;

import java.sql.Timestamp;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;
import rdarank.Ranklog;
import rdarank.agent.rank.message.InitRankMessage;

/**
 *　INITメッセージのメッセージハンドラ．エージェントのデータの初期化を行う．
 */
public class InitRankHandler extends MessageHandler {
	@Override
	public Object onMessage(Message msg) throws Exception {
		try {
			InitRankMessage initMsg = (InitRankMessage)msg;

			// マスターエンティティを取得
			Rankagent rank = (Rankagent)getEntity();

			// トランザクションIDを取得
			TxID tx = getTx();

			//RankAgent初期化
			//User数のクリア
			rank.setTotalUsers(tx, 0);
			//更新回数のクリア
			rank.setConnectionCount(tx, 0L);
                        
                        // 登録日
                        Long time = System.currentTimeMillis();
                        Timestamp registerTime = new Timestamp(time);
                        
			// set Rank Log
			Ranklog log = rank.createLog(tx, "init");
                        
			// 最終更新日
			log.setLastAccessTime(tx, registerTime);
                        log.setCurrentTime(tx, time);

			//System.out.println("InitHandler of Agent:" + getAgentKey() + " was initialized");

			// 処理結果としてエージェントキーを含む文字列を戻す
			return "hello from agent:" + getAgentKey();
		} catch(Exception e) {
			throw e;
		}
	}
}
