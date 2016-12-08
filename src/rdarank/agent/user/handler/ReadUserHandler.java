package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Profile;
import rdarank.Useragent;
import rdarank.agent.user.reader.UserInfo;

public class ReadUserHandler extends MessageHandler {

    @Override
    public Object onMessage(Message arg0) throws Exception {
        Useragent user = (Useragent) getEntity();

        TxID tx = getTx();

        Profile prof = user.getProfile(tx);

        UserInfo info = new UserInfo(
                /*UserID*/user.getUserID(tx),
                /*Name*/ prof.getName(tx),
                /*Sex*/ prof.getSex(tx),
                /*Age*/ prof.getAge(tx),
                /*address*/ prof.getAddress(tx),
                /*data*/ user.getData(tx),
                /*count */ user.getConnectionCount(tx)
        );

        return info;
    }

}
