/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import rda.agent.queue.QueueObserver;

/**
 *
 * @author kaeru
 */
public abstract class AgentManager {
    public abstract void add(QueueObserver observe);
    public abstract Boolean getState();
}
