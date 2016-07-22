/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.Map;
import rda.agent.queue.QueueObserver;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public abstract class AgentManager {
    public abstract void add(QueueObserver observe);
    public abstract Boolean getState();
    public abstract Boolean getAutoMode();
    public abstract IDManager getIDManager();
    public abstract WindowController getWindowController();
    public abstract String createCloneAgent(String id, Object state);
    public abstract Integer getNumAgents();
    public abstract Map getMQMap();
}
