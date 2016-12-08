package rda.manager;

import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
import rda.window.WindowController;

public abstract class AgentManager {
    public abstract void doShutdown();
    public abstract void initAgent(Map param);
    public abstract void add(QueueObserver observe);
    public abstract Boolean getState();
    public abstract Boolean getAutoMode();
    public abstract Boolean getReserveMode();
    public abstract IDManager getIDManager();
    public abstract WindowController getWindowController();
    public abstract String createAgent();
    public abstract String createCloneAgent(String id, Object state);
    public abstract Integer getNumAgents();
    public abstract Map getMQMap();
    public abstract List<QueueObserver> getObserver();
    public abstract void setServerList(Map map);
    public abstract void setLogger();
    public abstract void register(MessageQueue mq);
    public abstract Object getAgent(String id);
    
    public abstract DistributedAgentConnection getConnection(String id);
}
