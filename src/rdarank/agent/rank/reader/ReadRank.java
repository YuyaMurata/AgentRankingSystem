package rdarank.agent.rank.reader;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.util.ArrayList;
import rda.agent.client.AgentConnection;

public class ReadRank implements AgentExecutor, Serializable{
    /**
    　* 
     */
    private static final long serialVersionUID = 6385839930575941905L;
	
    private static final String AGENT_TYPE = "rankagent";
    private static final String MESSAGE_TYPE = "readRankAgent";

    public ReadRank() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    AgentKey agentKey;
    public ReadRank(AgentKey agentKey) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.agentKey = agentKey;
    }

    @Override
    public Object complete(Collection<Object> results) {
        // TODO 自動生成されたメソッド・スタブ
        if (results == null) return null;
        
        Object[] ret = results.toArray();
        return ret[0];
    }

    @Override
    public Object execute() {
        // TODO 自動生成されたメソッド・スタブ
        try{
            AgentManager agentManager = AgentManager.getAgentManager();

            MessageFactory factory = MessageFactory.getFactory();
            Message msg = factory.getMessage(MESSAGE_TYPE);

            Object ret = agentManager.sendMessage(agentKey, msg);

            return ret;
        }catch(IllegalAccessException | InstantiationException e){
            return e;
        }
    }

    public ArrayList<RankInfo> read(int numOfAgents) {
        AgentConnection ag = AgentConnection.getInstance();
        AgentClient client = ag.getConnection();
            
        try{
            ArrayList<RankInfo> list = new ArrayList<>();

            for(int i=0; i < numOfAgents; i++){
                String rankID = "R#00"+i;
                agentKey = new AgentKey(AGENT_TYPE, new Object[]{rankID});

                ReadRank executor = new ReadRank(agentKey);

                Object reply = client.execute(agentKey, executor);

                if (reply != null) {
                    RankInfo info = (RankInfo)reply;
                    System.out.println(agentKey + "[");
                    System.out.println("    " + info.toString());
                    System.out.println("]");
                                
                    list.add(info);
                } else {
                    System.out.println(rankID + " was not found");
                }
            }                  
            return list;
        }catch(Exception e){
            return null;
        } finally {
            ag.returnConnection(client);
        }
    }

}
