/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.template;

import rda.log.AgentSystemLogger;

/**
 *
 * @author kaeru
 */
public abstract class AgentLogPrinterTemplate {
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    public abstract void printer();
}
