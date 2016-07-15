package rdarank.agent.rank.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Rankagent;

public class ReadRankHandler extends MessageHandler{

	@Override
	public Object onMessage(Message arg0) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		// マスターエンティティを取得
		Rankagent rank = (Rankagent)getEntity();

		// トランザクションIDを取得
		TxID tx = getTx();

		return 0L;
	}

}
