package rdarank.agent.user.updater;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.MessageFactory;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.util.ArrayList;
import rda.agent.client.AgentConnection;
import rdarank.agent.user.message.UpdateUserMessage;

public class UpdateUser implements AgentExecutor, Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -4245098133759745980L;
	private static final String AGENT_TYPE = "useragent";
	private static final String MESSAGE_TYPE = "updateUserAgent";
        
        public UpdateUser(){}

	AgentKey agentKey;
	ArrayList data;
	public UpdateUser(AgentKey agentKey, ArrayList data) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.agentKey = agentKey;
		this.data = data;
	}

	@Override
	public Object complete(Collection<Object> results) {
		// TODO 自動生成されたメソッド・スタブ
		Object[] ret = results.toArray();
		return ret[0];
	}

	@Override
	public Object execute() {
		// TODO 自動生成されたメソッド・スタブ
		try {
			AgentManager agentManager = AgentManager.getAgentManager();
                        
			MessageFactory factory = MessageFactory.getFactory();
			UpdateUserMessage msg;
			msg = (UpdateUserMessage)factory.getMessage(MESSAGE_TYPE);
			msg.setParams(data);

			//Sync Message
			Object ret = agentManager.sendMessage(agentKey, msg);
			//agentManager.putMessage(agentKey, msg);

			return ret;
		} catch (IllegalAccessException | InstantiationException e) {
			// TODO 自動生成された catch ブロック
			return e;
		}
	}

	public void sendUpdateMessage(String agID, ArrayList data){
            try {
                AgentConnection ag = AgentConnection.getInstance();
                AgentClient client = ag.getConnection();
                
                agentKey = new AgentKey(AGENT_TYPE,new Object[]{agID}); 
                    
                UpdateUser executor = new UpdateUser(agentKey, data);
                Object reply = client.execute(agentKey, executor);
                //if(reply == null) System.err.println("Cannot Find Agent : "+agentKey);
                //System.out.println("A message from the agent[" + agentKey + "]: " + reply);
                    
                ag.returnConnection(client);
            } catch (AgentException e) {
            } 
        }
}