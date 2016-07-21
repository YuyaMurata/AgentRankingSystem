/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import java.util.HashMap;
import java.util.Map;
import rda.agent.queue.MessageObject;
import rdarank.Useragent;
import rdarank.extension.AgentIntaractionExtension;

/**
 *
 * @author kaeru
 */
public class IntaractionAgentHandler extends MessageHandler{
    private AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        // マスターエンティティを取得
        Useragent agent = (Useragent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        
        //Agent Communication
        /*Map dataMap = new HashMap();
        dataMap.put("id", agent.getUserID(tx));
        dataMap.put("data", agent.getData(tx));
        
        extension.communicateAgent(new MessageObject(agent.getCommunicationID(tx), dataMap));
        */
        return null;
    }
    
}
