package rdarank.agent.user.logger;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import rdarank.agent.client.AgentConnection;

public class ReadLogUser implements AgentExecutor, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1803475224433854533L;
	private static final String AGENT_TYPE = "useragent";
	private static final String MESSAGE_TYPE = "readLogUserAgent";

	public ReadLogUser() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	AgentKey agentKey;
	public ReadLogUser(AgentKey agentKey) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.agentKey = agentKey;
	}

	@Override
	public Object complete(Collection<Object> results) {
		// TODO 自動生成されたメソッド・スタブ
		if (results == null) {
			return null;
		}
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

	public void get(int numOfAgents) {
            try{
                AgentConnection agconn = AgentConnection.getInstance();
                AgentClient client = agconn.getConnection();
                for(int i=0; i < numOfAgents; i++){
                    String userID = "U#00"+i;
                    agentKey = new AgentKey(AGENT_TYPE, new Object[]{userID});

                    ReadLogUser executor = new ReadLogUser(agentKey);

                    Object reply = client.execute(agentKey, executor);

                    if (reply != null) {
                        LogInfo info = (LogInfo)reply;
    				
                        System.out.println(agentKey + "[");
                        System.out.println("    " + info.toString());
                        System.out.println("]");
                    } else {
                        System.out.println(userID + " was not found");
                    }
                }

                client.close();
            }catch(Exception e){}
	}

}