package rda.agent.template;

public abstract class AgentType {
    public abstract void sendMessage(Object data); 
    public abstract String getID();
}
