/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.creator;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import rda.agent.queue.MessageQueue;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.rank.message.InitRankMessage;
import rdarank.agent.rank.updater.UpdateRank;
import rdarank.agent.rank.updater.UpdateRankEx;

/**
 *
 * @author kaeru
 */
public class CreateRankAgentEx {
    //Executorを転送する必要が無いときのエージェントの生成
    public static final String AGENT_TYPE = "rankagent";
    static final String MESSAGE_TYPE = "initRankAgent";

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
            //Initialze
            InitRankMessage msg = new InitRankMessage();
            msg.setParams("No Message");

            Object reply = am.sendMessage(agentKey, msg);

            System.out.println("Agent[" + agentKey + "] was created. Reply is [" + reply + "]");

            //Create AgentQueue & Register Manager
            MessageQueue mq = new MessageQueue(RankAgentManager.getInstance(), agID, size, queuewait, agentwait);
            //mq.setAgentType(new UpdateRank(agID));
            mq.setAgentType(new UpdateRankEx(agID));
            RankAgentManager.getInstance().register(mq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
