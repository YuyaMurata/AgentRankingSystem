package rdarank.agent.rank.handler;

import rda.Profile;
import rda.Useragent;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.agent.user.reader.UserInfo;

public class ReadRankHandler extends MessageHandler{

	@Override
	public Object onMessage(Message arg0) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		// マスターエンティティを取得
		Useragent user = (Useragent)getEntity();

		// トランザクションIDを取得
		TxID tx = getTx();

		Profile prof = user.getProfile(tx);

		UserInfo info = new UserInfo(
				/*UserID*/	user.getUserID(tx),
				/*Name*/ 	prof.getName(tx),
				/*Sex*/ 	prof.getSex(tx),
				/*Age*/		prof.getAge(tx),
				/*address*/	prof.getAddress(tx),
				/*data*/	user.getData(tx),
                                /*count */      user.getConnectionCount(tx)
                                );

		return info;
	}

}
