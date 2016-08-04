/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager.message;

import com.ibm.agent.exa.Message;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class CreateAgentMessage extends Message {
    public Map<String, List<String>> agentGroup;

    // パラメータをセット
    public void setParams(Map agentGroup) {
        this.agentGroup = agentGroup;
    }
}
