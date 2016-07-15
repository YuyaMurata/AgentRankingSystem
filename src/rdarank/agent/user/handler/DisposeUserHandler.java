package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;

/**
 * DISPOSEメッセージのメッセージハンドラ．
 * エージェントの削除を行う．
 */
public class DisposeUserHandler extends MessageHandler {

	@Override
	public Object onMessage(Message msg) throws Exception {
		try {
			//エージェントの削除．
			disposeAgent();
			return getAgentKey() + " was disposed";
		} catch(Exception e) {
			throw e;
		}
	}
}
