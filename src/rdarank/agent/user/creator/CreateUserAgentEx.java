/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.creator;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import java.util.Map;
import rda.agent.queue.MessageQueue;
import rda.agent.template.AgentType;
import rda.data.profile.ProfileGenerator;
import rda.manager.TestCaseManager;
import static rdarank.agent.user.creator.CreateUserAgent.MESSAGE_TYPE;
import rdarank.agent.user.manager.UserAgentManager;
import rdarank.agent.user.message.InitUserMessage;
import rdarank.agent.user.updater.UpdateUser;
import rdarank.agent.user.updater.UpdateUserAnsyncEx;
import rdarank.agent.user.updater.UpdateUserEx;

/**
 *
 * @author kaeru
 */
public class CreateUserAgentEx {
    //Executorを転送する必要が無いときのエージェントの生成
    private static final String AGENT_TYPE = "useragent";
    private static final String MESSGA_TYPE = "initUserAgent";
    
    AgentKey agentKey;

    public void create(String agID, Integer size, Long queuewait, Long agentwait) {
        AgentManager am = AgentManager.getAgentManager();
        try {
            //SystemManager Extension AgentKey
            agentKey = new AgentKey(AGENT_TYPE, new Object[]{agID});

            //CreateAgent
            if (am.exists(agentKey)) {
                System.out.println("UserAgent " + agID + " already exists");
            } else {
                am.createAgent(agentKey);
            }
        } catch (AgentException ex) {
            ex.printStackTrace();
        }
        
        try {
            //Profile
            //Profile Generator
            ProfileGenerator profgen = TestCaseManager.getInstance().profgen;
            Map profile = profgen.getProf(agID);
        

            //Initialze
            MessageFactory factory = MessageFactory.getFactory();
            InitUserMessage msg = (InitUserMessage)factory.getMessage(MESSAGE_TYPE);
            msg.setParams((String) profile.get("Name"), (String) profile.get("Sex"),
                    ((Integer) profile.get("Age")).toString(), (String) profile.get("Address"),
                    (String) profile.get("Agent"));

            Object reply = am.sendMessage(agentKey, msg);

            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");

            //Create AgentQueue & Register Manager
            MessageQueue mq = new MessageQueue(UserAgentManager.getInstance(), agID, size, queuewait, agentwait);
            //mq.setAgentType(new UpdateUser(agID));
            mq.setAgentType(new UpdateUserEx(agID));
            
            //MessageQueueを通さない場合
            //AgentType mq = new UpdateUserAnsyncEx(agID);
            //UserAgentManager.getInstance().register(mq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
