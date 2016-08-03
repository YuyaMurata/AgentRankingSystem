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
import rda.extension.manager.message.CreateAgentMessage;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

/**
 *
 * @author kaeru
 */
public class CreateAgentHandler  extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        CreateAgentMessage ag = (CreateAgentMessage) msg;
        
        System.out.println("Launch CreateAgent");
        
        SystemManagerExtension extinsion = SystemManagerExtension.getInstance();
        
        RankAgentManager rank = RankAgentManager.getInstance();
        UserAgentManager user = UserAgentManager.getInstance();
        
        System.out.println(ag.agenttype + " = "+ag.agentGroup);
        
        if((ag.agenttype).contains("rankagent"))
            for(String agID : ag.agentGroup.get("rankagent"))
                rank.createAgent(agID);
        else if((ag.agenttype).contains("useragent"))
            for(String agID : ag.agentGroup.get("useragent"))
                user.createAgent(agID);
        else{
            for(String agID : ag.agentGroup.get("rankagent"))
                rank.createAgent(agID);
            
            for(String agID : ag.agentGroup.get("useragent"))
                user.createAgent(agID);
        }
            
        return "Success CreateAgent !!";
    }
}