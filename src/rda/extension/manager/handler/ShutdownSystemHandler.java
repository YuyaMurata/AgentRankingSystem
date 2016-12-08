package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import rda.extension.manager.SystemManagerExtension;

public class ShutdownSystemHandler extends MessageHandler {

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ

        //Shutdown
        System.out.println(">> Shutdown Handle !");
        SystemManagerExtension extension = SystemManagerExtension.getInstance();
        extension.shutdown();

        return "Shutdown RankingSystem !";
    }

}
