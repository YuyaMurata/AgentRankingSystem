package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.SimpleMessage;
import java.util.HashMap;
import java.util.Map;
import rda.extension.manager.SystemManagerExtension;

public class LaunchSystemHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        SimpleMessage smsg = (SimpleMessage) msg;
        HashMap<String, Map> prop = (HashMap) smsg.get("property");

        System.out.println("Launch System Handler 1");

        //Launch System
        SystemManagerExtension extension = SystemManagerExtension.getInstance();

        try {
            extension.startRankingSystem(prop);

            return "SystemManager Launch RankingSystem !";
        } catch (Exception e) {
            return e;
        }

    }

}
