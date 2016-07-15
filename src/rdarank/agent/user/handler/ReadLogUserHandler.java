package rdarank.agent.user.handler;

import java.util.Iterator;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import com.ibm.agent.exa.entity.Entity;
import rdarank.Useragent;
import rdarank.Userlog;
import rdarank.agent.user.logger.LogInfo;

public class ReadLogUserHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        // マスターエンティティを取得
        Useragent user = (Useragent)getEntity();

        // トランザクションIDを取得
        TxID tx = getTx();

        StringBuilder accessIDList = new StringBuilder();
        StringBuilder accessTimeList = new StringBuilder();
        Iterator<Entity> it = user.getLogIterator(tx);
        while(it.hasNext()){
            Userlog log = (Userlog) it.next();
		
            //AccessLogの取得
            accessIDList.append(log.getAccessID(tx));
            accessIDList.append(",");

            //AccessTimeの取得
            accessTimeList.append(log.getLastAccessTime(tx));
            accessTimeList.append(",");
        }

        LogInfo info = new LogInfo(
            /*userID*/ 			user.getUserID(tx),
            /*connectCount*/		user.getConnectionCount(tx),
            /*accessLog*/ 		accessIDList.toString(),
            /*accessTime*/		accessTimeList.toString());

        return info;
    }

}
