package rdarank.agent.user.handler;

import java.sql.Timestamp;

import rda.Log;
import rda.Profile;
import rda.Useragent;
import rdarank.agent.user.message.InitUserMessage;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;

/**
 *　INITメッセージのメッセージハンドラ．エージェントのデータの初期化を行う．
 */
public class InitUserHandler extends MessageHandler {
	@Override
	public Object onMessage(Message msg) throws Exception {
		try {
			InitUserMessage initMsg = (InitUserMessage)msg;

			// マスターエンティティを取得
			Useragent user = (Useragent)getEntity();

			// トランザクションIDを取得
			TxID tx = getTx();
			Profile prof = user.createProfile(tx);

			//Set User Profile
			// 名前をセット
			prof.setName(tx, initMsg.name);
			// 性別をセット
			prof.setSex(tx, initMsg.sex);
			// 年齢をセット
			prof.setAge(tx, initMsg.age);
			// 住所をセット
			prof.setAddress(tx, initMsg.address);
			// 登録日
                        Long time = System.currentTimeMillis();
			Timestamp registerTime = new Timestamp(time);
			prof.setLastAccessTime(tx, registerTime);

			//UserAgent初期化
			//GPSデータのクリア
			user.setData(tx, 0);
			//更新回数のクリア
			user.setConnectionCount(tx, 0);

			// set User Log
			Log log = user.createLog(tx, "init");

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
