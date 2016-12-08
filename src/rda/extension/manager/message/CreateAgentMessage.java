package rda.extension.manager.message;

import com.ibm.agent.exa.Message;
import java.util.List;
import java.util.Map;

public class CreateAgentMessage extends Message {
    public Map<String, List<String>> agentGroup;

    // パラメータをセット
    public void setParams(Map agentGroup) {
        this.agentGroup = agentGroup;
    }
}
