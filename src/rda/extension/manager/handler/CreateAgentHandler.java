package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;

import rda.extension.manager.SystemManagerExtension;
import rda.extension.manager.message.CreateAgentMessage;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

public class CreateAgentHandler extends MessageHandler {

	@Override
	public Object onMessage(Message msg) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		CreateAgentMessage ag = (CreateAgentMessage) msg;

		System.out.println("Launch CreateAgent");

		SystemManagerExtension extension = SystemManagerExtension.getInstance();

		RankAgentManager rank = RankAgentManager.getInstance();
		UserAgentManager user = UserAgentManager.getInstance();

		System.out.println(ag.agentGroup);

		Integer numAgents = 0;
		String type = "";

		if (ag.agentGroup.get("rankagent") != null) {
			type = "rankagent";
			for (String agID : ag.agentGroup.get("rankagent")) {
				rank.createAgent(agID);

				numAgents = numAgents + 1;
			}

			type = type + "[" + numAgents + "] ";
		}

		numAgents = 0;
		if (ag.agentGroup.get("useragent") != null) {
			type = type + "useragent";
			for (String agID : ag.agentGroup.get("useragent")) {
				user.createAgent(agID);
				numAgents = numAgents + 1;
			}

			type = type + "[" + numAgents + "] ";

			extension.setDataStreamUseAgentList(user.getAgentList());
		}

		return "Success " + type + "CreateAgent !!";
	}
}
