package rda.agent.template;

import rda.log.AgentSystemLogger;

public abstract class AgentLogPrinterTemplate {
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    public abstract void printer();
}
