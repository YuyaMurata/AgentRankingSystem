/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.SimpleMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.extension.manager.SystemManagerExtension;

/**
 *
 * @author kaeru
 */
public class CreateAgentHandler  extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        SimpleMessage smsg = (SimpleMessage)msg;
        HashMap<String, List> agentGroup = (HashMap)smsg.get("agent");
        
        System.out.println("Launch System Handler 1");
        
        //Launch System
        SystemManagerExtension extension = SystemManagerExtension.getInstance();
        
        return 0L;
    }
}