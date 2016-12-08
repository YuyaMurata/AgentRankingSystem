package rdarank.agent.user.handler;

import java.sql.Timestamp;

import rdarank.agent.user.message.InitUserMessage;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Profile;
import rdarank.Useragent;
import rdarank.Userlog;

public class InitUserHandler extends MessageHandler {
    @Override
    public Object onMessage(Message msg) throws Exception {
        try {
            InitUserMessage initMsg = (InitUserMessage)msg;

            Useragent user = (Useragent)getEntity();

            TxID tx = getTx();
            Profile prof = user.createProfile(tx);

            //Set User Profile
            prof.setName(tx, initMsg.name);
            prof.setSex(tx, initMsg.sex);
            prof.setAge(tx, initMsg.age);
            prof.setAddress(tx, initMsg.address);
            
            Long time = System.currentTimeMillis();
            Timestamp registerTime = new Timestamp(time);
            prof.setLastAccessTime(tx, registerTime);

            user.setData(tx, 0);
            
            user.setCommunicationID(tx, initMsg.comAgentID);
            
            user.setConnectionCount(tx, 0);

            Userlog log = user.createLog(tx, "init");

            log.setLastAccessTime(tx, registerTime);
            log.setCurrentTime(tx, time);

            //System.out.println("InitHandler of Agent:" + getAgentKey() + " was initialized");

            return "hello from agent:" + getAgentKey();
        } catch(Exception e) {
            throw e;
        }
    }
}
