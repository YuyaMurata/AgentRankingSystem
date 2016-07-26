/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.creator;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import rda.agent.queue.MessageQueue;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.rank.message.InitRankMessage;
import rdarank.agent.rank.updater.UpdateRank;

/**
 *
 * @author kaeru
 */
public class CreateRankAgentEx {
    public static final String AGENT_TYPE = "rankagent";
    static final String MESSAGE_TYPE = "initRankAgent";
    
    public void create(String agID, Integer size, Long queuewait, Long agentwait){
        AgentManager am = AgentManager.getAgentManager();
        try{
            AgentKey rankAgentKey = new AgentKey(AGENT_TYPE,new Object[]{agID});
            if (am.exists(rankAgentKey)) {
                System.out.println("RankAgent = "+ agID + " already exists");
            } else {
                am.createAgent(rankAgentKey);
            }
            
            am.createAgent(rankAgentKey);
	
            MessageFactory factory = MessageFactory.getFactory();
            InitRankMessage msg = (InitRankMessage)factory.getMessage(MESSAGE_TYPE);
		
            //Set InitMessage Data
            msg.setParams();
		
            Object ret = am.sendMessage(rankAgentKey, msg);
            
            System.out.println(ret);
            
            //Create AgentQueue
            MessageQueue mq = new MessageQueue(RankAgentManager.getInstance(), agID, size, queuewait, agentwait);
            mq.setAgentType(new UpdateRank(agID));
            RankAgentManager.getInstance().register(mq);
            
        }catch (Exception e){
        }
    }
}