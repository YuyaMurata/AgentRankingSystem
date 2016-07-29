/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.creator;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import java.util.Map;
import rda.data.profile.ProfileGenerator;
import rda.manager.TestCaseManager;
import rdarank.agent.user.message.InitUserMessage;

/**
 *
 * @author kaeru
 */
public class CreateUserAgentEx {
    private static final String AGENT_TYPE = "useragent";
    private static final String MESSGA_TYPE = "initUserAgent";
    AgentKey agentKey;
    
    public void create(String agID, Integer size, Long queuewait, Long agentwait){
        AgentManager am = AgentManager.getAgentManager();
        try {
            //SystemManager Extension AgentKey
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});

            //CreateAgent
            if (am.exists(agentKey)) {
                System.out.println("UserAgent "+agID+" already exists");
            } else {
                am.createAgent(agentKey);
            }
        } catch (AgentException ex) {
            ex.printStackTrace();
        }
        try{    
            //Profile
            //Profile Generator
            ProfileGenerator profgen = TestCaseManager.getInstance().profgen;
            Map profile = profgen.getProf(agID);
            
            //Initialze
            InitUserMessage msg = new InitUserMessage();
            msg.setParams(  (String)profile.get("Name"), (String)profile.get("Sex"), 
                            ((Integer) profile.get("Age")).toString(),  (String)profile.get("Address"),
                            (String) profile.get("Agent"));
            
            Object reply = am.sendMessage(agentKey,msg);
            
            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
